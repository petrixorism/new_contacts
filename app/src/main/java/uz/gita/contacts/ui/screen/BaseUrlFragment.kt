package uz.gita.contacts.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contacts.R
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.databinding.FragmentBaseUrlBinding

class BaseUrlFragment : Fragment(R.layout.fragment_base_url) {

    private val binding by viewBinding(FragmentBaseUrlBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.button.setOnClickListener {
            SharedPref.getInstance().apply {
                token = ""
                isLogedIn = false
                base_url = binding.baseUrlEt.text.toString()
            }
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

}