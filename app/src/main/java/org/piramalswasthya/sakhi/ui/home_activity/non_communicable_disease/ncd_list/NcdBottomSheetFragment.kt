package org.piramalswasthya.sakhi.ui.home_activity.non_communicable_disease.ncd_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.piramalswasthya.sakhi.adapters.NcdCbacAdapter
import org.piramalswasthya.sakhi.databinding.BottomSheetNcdBinding

@AndroidEntryPoint
class NcdBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetNcdBinding? = null
    private val binding: BottomSheetNcdBinding
        get() = _binding!!

    private val viewModel: NcdListViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetNcdBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvImmCat.adapter = NcdCbacAdapter(NcdCbacAdapter.NcdCbacElementClickListener {
            val benId = viewModel.getSelectedBenId()
            findNavController().navigate(

                NcdListFragmentDirections.actionNcdListFragmentToCbacFragment(benId,
                    it,
                    viewModel.getAshaId()
                )
            )
            dismiss()
        })

        lifecycleScope.launch {
            viewModel.ncdDetails.collect {
                (_binding?.rvImmCat?.adapter as NcdCbacAdapter?)?.submitList(it)
            }
        }
    }


//    private fun submitListToVaccinationRv(detail: ImmunizationDetailsDomain) {
//        val list = ChildImmunizationCategory.values().map { category ->
//            VaccineCategoryDomain(category,
//                vaccineStateList = detail.vaccineStateList.filter { it.vaccineCategory == category })
//        }.filter { it.vaccineStateList.isNotEmpty() }
//        Timber.d("Called list at bottom sheet ${_binding?.rvImmCat?.adapter} ${detail.ben.benId} $list")
//
//        (_binding?.rvImmCat?.adapter as ImmunizationCategoryAdapter?)?.submitList(list)
//    }

}