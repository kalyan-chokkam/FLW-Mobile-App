package org.piramalswasthya.sakhi.ui.home_activity.village_level_forms.survey_register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.piramalswasthya.sakhi.R

class SurveyRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = SurveyRegisterFragment()
    }

    private lateinit var viewModel: SurveyRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_survey_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SurveyRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}