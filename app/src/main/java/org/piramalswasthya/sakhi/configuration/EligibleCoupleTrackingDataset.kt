package org.piramalswasthya.sakhi.configuration

import android.content.Context
import org.piramalswasthya.sakhi.R
import org.piramalswasthya.sakhi.database.room.SyncState
import org.piramalswasthya.sakhi.helpers.Languages
import org.piramalswasthya.sakhi.helpers.setToStartOfTheDay
import org.piramalswasthya.sakhi.model.BenRegCache
import org.piramalswasthya.sakhi.model.EligibleCoupleTrackingCache
import org.piramalswasthya.sakhi.model.FormElement
import org.piramalswasthya.sakhi.model.InputType
import java.util.Calendar

class EligibleCoupleTrackingDataset(
    context: Context, currentLanguage: Languages
) : Dataset(context, currentLanguage) {

    private var dateOfVisit = FormElement(
        id = 1,
        inputType = InputType.DATE_PICKER,
        title = resources.getString(R.string.tracking_date),
        arrayId = -1,
        required = true,
        max = System.currentTimeMillis(),
        hasDependants = true

    )

    private val financialYear = FormElement(
        id = 2,
        inputType = InputType.TEXT_VIEW,
        title = resources.getString(R.string.ectdset_fin_yrs),
        required = false,
    )

    private val month = FormElement(
        id = 3,
        inputType = InputType.TEXT_VIEW,
        title = resources.getString(R.string.ectdset_month),
        arrayId = R.array.visit_months,
        entries = resources.getStringArray(R.array.visit_months),
        required = false
    )

    private val isPregnancyTestDone = FormElement(
        id = 4,
        inputType = InputType.RADIO,
        title = resources.getString(R.string.ectdset_is_preg_tst_done),
        entries = resources.getStringArray(R.array.ectdset_yes_no_dont),
        required = false,
        hasDependants = true
    )

    private val pregnancyTestResult = FormElement(
        id = 5,
        inputType = InputType.RADIO,
        title = resources.getString(R.string.ectdset_preg_tst_rlt),
        entries = resources.getStringArray(R.array.ectdset_po_neg),
        required = true,
        hasDependants = true
    )

    private val isPregnant = FormElement(
        id = 6,
        inputType = InputType.RADIO,
        title = resources.getString(R.string.ectdset_name_wo_preg),
        entries = resources.getStringArray(R.array.ectdset_yes_no_dont),
        required = false,
        hasDependants = true
    )

    private val usingFamilyPlanning = FormElement(
        id = 7,
        inputType = InputType.RADIO,
        title = resources.getString(R.string.ectdset_fly_plan_mthd),
        entries = resources.getStringArray(R.array.ectdset_yes_no),
        required = false,
        hasDependants = true
    )

    private val methodOfContraception = FormElement(
        id = 8,
        inputType = InputType.DROPDOWN,
        title = resources.getString(R.string.ectdset_mthd_conpt),
        arrayId = R.array.method_of_contraception,
        entries = resources.getStringArray(R.array.method_of_contraception),
        required = false,
        hasDependants = true

    )

    private val anyOtherMethod = FormElement(
        id = 9,
        inputType = InputType.EDIT_TEXT,
        title = resources.getString(R.string.ectdset_other_mthd),
        required = true,
        etInputType = android.text.InputType.TYPE_CLASS_TEXT,
        etMaxLength = 50
    )


    fun getIndexOfIsPregnant() = getIndexById(isPregnant.id)

    suspend fun setUpPage(
        ben: BenRegCache?,
        dateOfReg: Long,
        lastTrack: EligibleCoupleTrackingCache?,
        saved: EligibleCoupleTrackingCache?
    ) {
        val list = mutableListOf(
            dateOfVisit,
            financialYear,
            month,
            isPregnancyTestDone,
            isPregnant,
//            usingFamilyPlanning,
        )
        if (saved == null) {
            dateOfVisit.value = getDateFromLong(System.currentTimeMillis())
            dateOfVisit.value?.let {
                financialYear.value = getFinancialYear(it)
                month.value =
                    resources.getStringArray(R.array.visit_months)[Companion.getMonth(it)!!]
            }

            dateOfVisit.min = lastTrack?.let {
                Calendar.getInstance().apply {
                    timeInMillis = it.visitDate
                    val currentMonth = get(Calendar.MONTH)
                    if (currentMonth == 11) {
                        set(Calendar.YEAR, get(Calendar.YEAR) + 1)
                        set(Calendar.MONTH, 0)
                    } else {
                        set(Calendar.MONTH, currentMonth + 1)
                    }
                    set(Calendar.DAY_OF_MONTH, 1)
                    setToStartOfTheDay()
                }.timeInMillis
            } ?: dateOfReg
        } else {
            dateOfVisit.value = getDateFromLong(saved.visitDate)
            financialYear.value = getFinancialYear(dateString = dateOfVisit.value)
            month.value =
                resources.getStringArray(R.array.visit_months)[Companion.getMonth(dateOfVisit.value)!!]
            isPregnancyTestDone.value =
                getLocalValueInArray(R.array.yes_no, saved.isPregnancyTestDone)
            if (isPregnancyTestDone.value == resources.getStringArray(R.array.yes_no)[0]) {
                list.add(list.indexOf(isPregnancyTestDone) + 1, pregnancyTestResult)
                pregnancyTestResult.value = saved.pregnancyTestResult
            }
            isPregnant.value = getLocalValueInArray(R.array.yes_no, saved.isPregnant)
            if (isPregnant.value == resources.getStringArray(R.array.yes_no)[1]) {
                list.add(usingFamilyPlanning)
                saved.usingFamilyPlanning?.let {
                    usingFamilyPlanning.value =
                        if (it) resources.getStringArray(R.array.yes_no)[0] else resources.getStringArray(
                            R.array.yes_no
                        )[1]
                }
                usingFamilyPlanning.value =
                    if (saved.usingFamilyPlanning == true) resources.getStringArray(R.array.yes_no)[1] else resources.getStringArray(
                        R.array.yes_no
                    )[1]
                if (saved.usingFamilyPlanning == true) {
                    list.add(methodOfContraception)
                    if (saved.methodOfContraception in resources.getStringArray(R.array.method_of_contraception)) {
                        methodOfContraception.value = saved.methodOfContraception
                    } else if (saved.methodOfContraception != null) {
                        methodOfContraception.value =
                            resources.getStringArray(R.array.method_of_contraception).last()
                        list.add(anyOtherMethod)
                        anyOtherMethod.value = saved.methodOfContraception
                    }
                }
            }

        }
        setUpPage(list)

    }

    override suspend fun handleListOnValueChanged(formId: Int, index: Int): Int {
        return when (formId) {
            dateOfVisit.id -> {
                financialYear.value = Companion.getFinancialYear(dateOfVisit.value)
                month.value =
                    resources.getStringArray(R.array.visit_months)[Companion.getMonth(dateOfVisit.value)!!]
                -1
            }

            isPregnancyTestDone.id -> {
                isPregnant.isEnabled = true
                triggerDependants(
                    source = isPregnancyTestDone,
                    passedIndex = index,
                    triggerIndex = 0,
                    target = pregnancyTestResult
                )
            }

            pregnancyTestResult.id -> {
                if (pregnancyTestResult.value == resources.getStringArray(R.array.ectdset_po_neg)[0]) {
                    isPregnant.value = resources.getStringArray(R.array.yes_no)[0]
                    isPregnant.isEnabled = false
                } else {
                    isPregnant.value = null
                    isPregnant.isEnabled = true
                }
                handleListOnValueChanged(isPregnant.id, 0)

            }

            isPregnant.id -> {
                triggerDependants(
                    source = isPregnant,
                    passedIndex = index,
                    triggerIndex = 1,
                    target = usingFamilyPlanning,
                    targetSideEffect = listOf(methodOfContraception, anyOtherMethod)
                )
            }

            usingFamilyPlanning.id -> {
                triggerDependants(
                    source = usingFamilyPlanning,
                    passedIndex = index,
                    triggerIndex = 0,
                    target = methodOfContraception,
                    targetSideEffect = listOf(anyOtherMethod)
                )
            }

            methodOfContraception.id -> {
                triggerDependants(
                    source = methodOfContraception,
                    passedIndex = index,
                    triggerIndex = methodOfContraception.entries!!.lastIndex,
                    target = anyOtherMethod
                )
            }

            anyOtherMethod.id -> {
                validateAllAlphabetsSpaceOnEditText(anyOtherMethod)
            }

            else -> -1
        }
    }

    override fun mapValues(cacheModel: FormDataModel, pageNumber: Int) {
        (cacheModel as EligibleCoupleTrackingCache).let { form ->
            form.visitDate = getLongFromDate(dateOfVisit.value)
            form.isPregnancyTestDone = isPregnancyTestDone.value
            form.pregnancyTestResult = pregnancyTestResult.value
            form.isPregnant = isPregnant.value
            form.usingFamilyPlanning =
                usingFamilyPlanning.value?.let { it == resources.getStringArray(R.array.yes_no)[0] }
            if (methodOfContraception.value == resources.getStringArray(R.array.method_of_contraception)
                    .last()
            ) {
                form.methodOfContraception = anyOtherMethod.value
            } else {
                form.methodOfContraception = methodOfContraception.value
            }
        }
    }

    fun updateBen(benRegCache: BenRegCache) {
        benRegCache.genDetails?.let {
            it.reproductiveStatus =
                englishResources.getStringArray(R.array.nbr_reproductive_status_array)[1]
            it.reproductiveStatusId = 2
        }
        if (benRegCache.processed != "N") benRegCache.processed = "U"
        benRegCache.syncState = SyncState.UNSYNCED
    }
}