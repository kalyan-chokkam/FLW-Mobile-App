package org.piramalswasthya.sakhi.ui.home_activity.death_reports.cdr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.piramalswasthya.sakhi.adapters.BenListAdapterForForm
import org.piramalswasthya.sakhi.databinding.FragmentDisplaySearchRvButtonBinding
import org.piramalswasthya.sakhi.ui.home_activity.all_ben.new_ben_registration.NewBenRegTypeFragment
import org.piramalswasthya.sakhi.work.WorkerUtils

@AndroidEntryPoint
class CdrListFragment : Fragment() {

    private val binding: FragmentDisplaySearchRvButtonBinding by lazy {
        FragmentDisplaySearchRvButtonBinding.inflate(layoutInflater)
    }

    private val viewModel: CdrListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNextPage.visibility = View.GONE
        val benAdapter = BenListAdapterForForm(
            BenListAdapterForForm.ClickListener(
                {
                    Toast.makeText(context, "Ben : $it clicked", Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(context, "Household : $it clicked", Toast.LENGTH_SHORT).show()
                },
                {
                    WorkerUtils.triggerSyncWorker(requireContext())
                }
            ) { hhId, benId ->
                findNavController().navigate(
                    CdrListFragmentDirections.actionCdrListFragmentToCdrObjectFragment(hhId, benId)
                )
            }, "CDR Form")
        binding.rvAny.adapter = benAdapter

        viewModel.benList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty())
                binding.flEmpty.visibility = View.VISIBLE
            else
                binding.flEmpty.visibility = View.GONE

            benAdapter.submitList(it)
        }
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.filterText(p0?.toString() ?: "")
            }

        }
        binding.searchView.setOnFocusChangeListener { searchView, b ->
            if (b)
                (searchView as EditText).addTextChangedListener(searchTextWatcher)
            else
                (searchView as EditText).removeTextChangedListener(searchTextWatcher)

        }
    }

}