package org.piramalswasthya.sakhi.configuration

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Range
import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.piramalswasthya.sakhi.R
import org.piramalswasthya.sakhi.helpers.Languages
import org.piramalswasthya.sakhi.model.FormElement
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


/**
 * Base class to be extended to use as a sandwich between viewModel and repository objects.
 * @see org.piramalswasthya.sakhi.adapters.FormInputAdapter
 */
abstract class Dataset(context: Context, currentLanguage: Languages) {

    /**
     * Resource object of currently selected language. To be used to get language specific strings from strings.xml.
     */
    protected var resources: Resources
    protected var englishResources: Resources

    init {
        englishResources = context.resources
        resources = getLocalizedResources(context, currentLanguage)
    }

    /**
     * Helper function to get resource instance chosen language.
     */
    private fun getLocalizedResources(context: Context, currentLanguage: Languages): Resources {
        val desiredLocale = Locale(currentLanguage.symbol)
        var conf = context.resources.configuration
        conf = Configuration(conf)
        conf.setLocale(desiredLocale)
        val localizedContext: Context = context.createConfigurationContext(conf)
        return localizedContext.resources
    }

    protected companion object {
        fun getLongFromDate(dateString: String?): Long {
            val f = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val date = dateString?.let { f.parse(it) }
            return date?.time ?: 0L
        }

        fun getDateFromLong(dateLong: Long): String? {
            if (dateLong == 0L) return null
            val cal = Calendar.getInstance()
            cal.timeInMillis = dateLong
            val f = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            return f.format(cal.time)


        }
    }


    private val list = mutableListOf<FormElement>()

    private val _listFlow = MutableStateFlow<List<FormElement>>(emptyList())
    val listFlow = _listFlow.asStateFlow()

    private val _alertErrorMessageFlow = MutableStateFlow<String?>(null)
    val alertErrorMessageFlow = _alertErrorMessageFlow.asStateFlow()

    suspend fun resetErrorMessageFlow() {
        _alertErrorMessageFlow.emit(null)
    }

    protected fun FormElement.getPosition(): Int {
        return value?.let { entries?.indexOf(it)?.plus(1) } ?: 0
    }

    protected fun FormElement.getStringFromPosition(position: Int): String? {
        return if (position <= 0) null else entries?.get(position - 1)
    }

    protected fun FormElement.getEnglishStringFromPosition(position: Int): String? {
        return if (position <= 0) null else englishResources.getStringArray(arrayId)[position - 1]
    }


    protected abstract suspend fun handleListOnValueChanged(formId: Int, index: Int): Int

    abstract fun mapValues(cacheModel: FormDataModel, pageNumber: Int = 0)
    protected fun getIndexOfElement(element: FormElement) = list.indexOf(element)
    suspend fun updateList(formId: Int, index: Int) {
        val updateIndex = handleListOnValueChanged(formId, index)
        if (updateIndex != -1) {
            val newList = list.toMutableList()
//            if (updateUIForCurrentElement) {
//                Timber.d("Updating UI element ...")
//                newList[updateIndex] = list[updateIndex].cloneForm()
//                updateUIForCurrentElement = false
//            }
            Timber.d("Emitting ${newList}}")
            _listFlow.emit(newList)
        }
    }

    protected suspend fun setUpPage(mList: List<FormElement>) {
        list.clear()
        list.addAll(mList)
        _listFlow.emit(list.toMutableList())
    }


    protected fun triggerDependants(
        source: FormElement,
        passedIndex: Int,
        triggerIndex: Int,
        target: List<FormElement>,
        targetSideEffect: List<FormElement>? = null
    ): Int {
        return if (passedIndex == triggerIndex) {
            if (!list.containsAll(target)) {
                val listIndex = list.indexOf(source)
                list.addAll(
                    listIndex + 1, target
                )
                listIndex
            } else -1
        } else {
            val anyRemoved = list.removeAll(
                target
            )
            if (anyRemoved) {
                target.forEach {
                    it.value = null
                }
                targetSideEffect?.let { sideEffectList ->
                    list.removeAll(sideEffectList)
                    sideEffectList.forEach { it.value = null }
                }
                list.indexOf(source)
            } else -1
        }
    }

    protected fun triggerDependantsReverse(
        source: FormElement,
        passedIndex: Int,
        triggerIndex: Int,
        target: List<FormElement>,
        targetSideEffect: List<FormElement>? = null
    ): Int {
        return if (passedIndex != triggerIndex) {
            if (!list.containsAll(target)) {
                val listIndex = list.indexOf(source)
                list.addAll(
                    listIndex + 1, target
                )
                listIndex
            } else -1
        } else {
            val anyRemoved = list.removeAll(
                target
            )
            if (anyRemoved) {
                target.forEach {
                    it.value = null
                }
                targetSideEffect?.let { sideEffectList ->
                    list.removeAll(sideEffectList)
                    sideEffectList.forEach { it.value = null }
                }
                list.indexOf(source)
            } else -1
        }
    }


    protected fun triggerDependants(
        source: FormElement,
        passedIndex: Int,
        triggerIndex: Int,
        target: FormElement,
        targetSideEffect: List<FormElement>? = null
    ): Int {
        return if (passedIndex == triggerIndex) {
            if (!list.contains(target)) {
                val listIndex = list.indexOf(source)
                list.add(
                    listIndex + 1, target
                )
                listIndex
            } else -1
        } else {
            val anyRemoved = list.remove(
                target
            )
            if (anyRemoved) {
                target.value = null
                targetSideEffect?.let { sideEffectList ->
                    list.removeAll(sideEffectList)
                    sideEffectList.forEach { it.value = null }
                }
                list.indexOf(source)
            } else -1
        }
    }

    protected fun triggerDependants(
        age: Int,
        ageUnit: FormElement,
        ageTriggerRange: Range<Int>,
        ageUnitTriggerIndex: Int,
        target: FormElement,
        placeAfter: FormElement,
        targetSideEffect: List<FormElement>? = null
    ): Int {
        Timber.d("YTRU")
        return if (age in ageTriggerRange && ageUnit.value == ageUnit.entries?.get(
                ageUnitTriggerIndex
            )
        ) {
            if (!list.contains(target)) {
                val listIndex = list.indexOf(placeAfter)
                list.add(
                    listIndex + 1, target
                )
                listIndex + 1
            } else -1
        } else {
            val anyRemoved = list.remove(
                target
            )
            if (anyRemoved) {
                target.value = null
                targetSideEffect?.let { sideEffectList ->
                    list.removeAll(sideEffectList)
                    sideEffectList.forEach { it.value = null }
                }
                302
            } else -1
        }
    }

    protected fun triggerDependants(
        source: FormElement,
        removeItems: List<FormElement>,
        addItems: List<FormElement>,
        position: Int = -1,
    ): Int {
        removeItems.forEach {
            it.value = null
        }
        list.removeAll(removeItems)
//        list.removeAll(addItems)
        addItems.forEach {
            if (list.contains(it)) list.remove(it)
        }
        val addPosition = position.takeIf { it != -1 } ?: (list.indexOf(source) + 1)
        list.addAll(addPosition, addItems)
        return addPosition
//        return if (age in ageTriggerRange && ageUnit.value == ageUnit.entries?.get(
//                ageUnitTriggerIndex
//            )
//        ) {
//            if (!list.contains(target)) {
//                val listIndex = list.indexOf(placeAfter)
//                list.add(
//                    listIndex + 1, target
//                )
//                listIndex + 1
//            } else
//                -1
//        } else {
//            val anyRemoved = list.remove(
//                target
//            )
//            if (anyRemoved) {
//                target.value = null
//                targetSideEffect?.let { sideEffectList ->
//                    list.removeAll(sideEffectList)
//                    sideEffectList.forEach { it.value = null }
//                }
//                302
//            } else -1
//        }
    }


    private fun getDiffYears(a: Calendar, b: Calendar): Int {
        var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || a.get(Calendar.MONTH) == b.get(
                Calendar.MONTH
            ) && a.get(
                Calendar.DAY_OF_MONTH
            ) > b.get(
                Calendar.DAY_OF_MONTH
            )
        ) {
            diff--
        }
        return diff
    }

    protected fun getDiffMonths(a: Calendar, b: Calendar): Int {
        var diffY = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || a.get(Calendar.MONTH) == b.get(
                Calendar.MONTH
            ) && a.get(
                Calendar.DAY_OF_MONTH
            ) > b.get(
                Calendar.DAY_OF_MONTH
            )
        ) {
            diffY--
        }
        if (diffY != 0) return -1
        var diffM = b.get(Calendar.MONTH) - a.get(Calendar.MONTH)
        if (a.get(Calendar.DAY_OF_MONTH) > b.get(Calendar.DAY_OF_MONTH)) {
            diffM--
        }
        if (diffM < 0) diffM += 12

        return diffM
    }

    private fun getDiffDays(a: Calendar, b: Calendar): Int {
        val millisDiff = b.timeInMillis - a.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(millisDiff).toInt()
    }

    protected fun getEddFromLmp(lmp: Long) =

        Calendar.getInstance().apply {
            timeInMillis = lmp
            add(Calendar.WEEK_OF_YEAR, 40)
        }.timeInMillis


    protected suspend fun emitAlertErrorMessage(
        @StringRes errorMessage: Int
    ) {
        _alertErrorMessageFlow.emit(resources.getString(errorMessage))
    }

    protected fun assignValuesToAgeAndAgeUnitFromDob(
        dob: Long, ageElement: FormElement, ageUnitElement: FormElement
    ): Int {
        ageUnitElement.errorText = null
        ageElement.errorText = null
        val calDob = Calendar.getInstance().apply {
            timeInMillis = dob
        }
        val calNow = Calendar.getInstance()
        val yearsDiff = getDiffYears(calDob, calNow)
        if (yearsDiff > 0) {
            ageUnitElement.value = ageUnitElement.entries?.last()
            ageElement.value = yearsDiff.toString()
            return -1
        } else {
            val monthDiff = getDiffMonths(calDob, calNow)
            if (monthDiff > 0) {
                ageUnitElement.value = ageUnitElement.entries?.get(1)
                ageElement.value = monthDiff.toString()
                return -1
            } else {
                val dayDiff = getDiffDays(calDob, calNow)
                if (dayDiff > 0) {
                    ageUnitElement.value = ageUnitElement.entries?.get(0)
                    ageElement.value = dayDiff.toString()
                    return -1
                }
            }
            return -1
        }
    }


    protected fun assignValuesToAgeFromDob(
        dob: Long, ageElement: FormElement
    ): Int {
        ageElement.errorText = null
        val calDob = Calendar.getInstance().apply {
            timeInMillis = dob
        }
        val calNow = Calendar.getInstance()
        val yearsDiff = getDiffYears(calDob, calNow)
        if (yearsDiff > 0) {
            ageElement.value = yearsDiff.toString()
        }
        return -1
    }


    protected fun validateEmptyOnEditText(formElement: FormElement): Int {
        if (formElement.required) {
            if (formElement.value.isNullOrEmpty()) formElement.errorText =
                resources.getString(R.string.form_input_empty_error)
            else {
                formElement.errorText = null
            }
        }
        return -1
    }

    private fun String.isAllUppercaseOrSpace() =
        takeIf { it.isNotEmpty() }?.toCharArray()?.all { it.isUpperCase() || it.isWhitespace() }
            ?: false


    private fun String.isAllAlphabetsAndSpace() =
        takeIf { it.isNotEmpty() }?.toCharArray()?.all { it.isWhitespace() || it.isLetter() }
            ?: false

    protected fun validateAllCapsOrSpaceOnEditText(formElement: FormElement): Int {
        if (formElement.allCaps) {
            formElement.value?.takeIf { it.isNotEmpty() }?.isAllUppercaseOrSpace()?.let {
                Timber.d("Is ok : $it")
                formElement.errorText = if (it) null
                else resources.getString(R.string.form_input_upper_case_error)
            } ?: run {
                if (!formElement.required) formElement.errorText = null
            }
        }
        return -1
    }

    protected fun validateAllAlphabetsSpaceOnEditText(formElement: FormElement): Int {
        formElement.value?.takeIf { it.isNotEmpty() }?.isAllAlphabetsAndSpace()?.let {
            if (it) formElement.errorText = null
            else formElement.errorText =
                resources.getString(R.string.form_input_alphabet_space_only_error)
        }
        return -1
    }

    protected fun validateIntMinMax(formElement: FormElement): Int {
        formElement.errorText = formElement.value?.takeIf { it.isNotEmpty() }?.toLong()?.let {
            formElement.min?.let { min ->
                formElement.max?.let { max ->
                    if (it < min) {
                        resources.getString(
                            R.string.form_input_min_limit_error, formElement.title, min
                        )
                    } else if (it > max) {
                        resources.getString(
                            R.string.form_input_max_limit_error, formElement.title, max
                        )
                    } else null
                }
            }
        }
        return -1
    }


    protected fun validateMobileNumberOnEditText(formElement: FormElement): Int {
        formElement.errorText = formElement.value?.takeIf { it.isNotEmpty() }?.toLong()?.let {
            if (it < 6_000_000_000L) resources.getString(R.string.form_input_error_invalid_mobile) else null
        }
        return -1
    }

    protected fun validateRchIdOnEditText(formElement: FormElement): Int {
        formElement.errorText = formElement.value?.takeIf { it.isNotEmpty() }?.let {
            if (it.length < 12) resources.getString(R.string.form_input_error_invalid_rch) else null
        }
        return -1
    }

    protected fun validateAadharNoOnEditText(formElement: FormElement): Int {
        formElement.errorText = formElement.value?.takeIf { it.isNotEmpty() }?.let {
            if (it.length < 12 || (it.isNotEmpty() && it.first() == '0')) resources.getString(R.string.form_input_error_invalid_aadhar) else null
        }
        return -1
    }

    fun getIndexById(id: Int): Int {
        return list.find { it.id == id }?.let {
            list.indexOf(it)
        } ?: -1
    }

    fun setValueById(id: Int, value: String?) {
        list.find { it.id == id }?.let {
            it.value = value
        }
    }




}