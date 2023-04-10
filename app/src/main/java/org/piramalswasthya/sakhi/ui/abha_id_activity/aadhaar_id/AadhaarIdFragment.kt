package org.piramalswasthya.sakhi.ui.abha_id_activity.aadhaar_id

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.piramalswasthya.sakhi.R
import org.piramalswasthya.sakhi.databinding.FragmentAadhaarIdBinding
import org.piramalswasthya.sakhi.ui.abha_id_activity.aadhaar_id.AadhaarIdViewModel.State


@AndroidEntryPoint
class AadhaarIdFragment : Fragment() {

    private var _binding: FragmentAadhaarIdBinding? = null
    private val binding: FragmentAadhaarIdBinding
        get() = _binding!!

    private val viewModel: AadhaarIdViewModel by viewModels()

    private lateinit var navController: NavController

    private val aadharDisclaimer by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle("Individual’s consent for creation of ABHA Number.")
            .setMessage(context?.getString(R.string.aadhar_disclaimer_consent_text))
            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            .create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAadhaarIdBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        var isValidAadhar = false
        binding.btnGenerateOtp.setOnClickListener {
            viewModel.generateOtpClicked(binding.tietAadhaarNumber.text.toString())

        }
        binding.aadharConsentCheckBox.setOnCheckedChangeListener{ _, ischecked ->
            binding.btnGenerateOtp.isEnabled = isValidAadhar && ischecked
        }
        binding.aadharDisclaimer.setOnClickListener{
            aadharDisclaimer.show()
        }
        binding.tietAadhaarNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                isValidAadhar = s != null && s.length == 12
                binding.btnGenerateOtp.isEnabled = isValidAadhar
                        && binding.aadharConsentCheckBox.isChecked
            }

        })

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state!!) {
                State.IDLE -> {}
                State.LOADING -> {
                    binding.clContentAadharId.visibility = View.INVISIBLE
                    binding.pbLoadingAadharId.visibility = View.VISIBLE
                    binding.clError.visibility = View.INVISIBLE
                }
                State.SUCCESS -> {
                    viewModel.resetState()
                    findNavController().navigate(
                        AadhaarIdFragmentDirections.actionAadhaarIdFragmentToAadhaarOtpFragment(
                            viewModel.txnId, viewModel.mobileNumber
                        )
                    )
                }
                State.ERROR_SERVER -> {
                    binding.pbLoadingAadharId.visibility = View.INVISIBLE
                    binding.clContentAadharId.visibility = View.VISIBLE
                    binding.clError.visibility = View.INVISIBLE
                    binding.tvErrorText.visibility = View.VISIBLE
                }
                State.ERROR_NETWORK -> {
                    binding.clContentAadharId.visibility = View.INVISIBLE
                    binding.pbLoadingAadharId.visibility = View.INVISIBLE
                    binding.clError.visibility = View.VISIBLE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvErrorText.text = it
                viewModel.resetErrorMessage()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

