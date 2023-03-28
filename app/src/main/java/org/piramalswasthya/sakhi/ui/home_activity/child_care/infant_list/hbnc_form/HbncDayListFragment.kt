package org.piramalswasthya.sakhi.ui.home_activity.child_care.infant_list.hbnc_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.piramalswasthya.sakhi.R
import org.piramalswasthya.sakhi.adapters.HBNCDayGridAdapter
import org.piramalswasthya.sakhi.databinding.RvIconGridBinding
import timber.log.Timber

@AndroidEntryPoint
class HbncDayListFragment : Fragment() {


    private var _binding : RvIconGridBinding? = null

    private val viewModel: HbncDayListViewModel by viewModels()

    private val binding : RvIconGridBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RvIconGridBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpImmunizationIconRvAdapter()
    }

    private fun setUpImmunizationIconRvAdapter() {
        val rvLayoutManager = GridLayoutManager(context, requireContext().resources.getInteger(R.integer.icon_grid_span))
        binding.rvIconGrid.layoutManager = rvLayoutManager
        val iconAdapter = HBNCDayGridAdapter(
            HBNCDayGridAdapter.HbncIconClickListener { hhId, benId, count ->
                Timber.d("benId : $benId hhId : $hhId $count")
                findNavController().navigate(
                    HbncDayListFragmentDirections.actionHbncDayListFragmentToHbncFragment(hhId, benId, count,))
            })

        binding.rvIconGrid.adapter = iconAdapter

        iconAdapter.submitList(viewModel.dayList)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}