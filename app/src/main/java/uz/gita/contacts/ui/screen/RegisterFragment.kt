package uz.gita.contacts.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contacts.R
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse
import uz.gita.contacts.databinding.FragmentRegisterBinding
import uz.gita.contacts.ui.viewmodel.RegisterViewModel
import uz.gita.contacts.ui.viewmodel.impl.RegisterViewModelImpl
import uz.gita.contacts.utils.makeVisibleOrGone
import uz.gita.contacts.utils.showToast

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.apply {
            progressLiveData.observe(viewLifecycleOwner, progressObserver)
            registrationLiveData.observe(viewLifecycleOwner, regObserver)
            failLiveData.observe(viewLifecycleOwner, failObserver)
            notConnectionLiveData.observe(viewLifecycleOwner, progressObserver)
            verifyLiveData.observe(viewLifecycleOwner, verifyObserver)
        }

        binding.apply {
            registerButton.setOnClickListener {
                viewModel.register(
                    RegisterRequest(
                        firstName = this.nameEditText.text.toString(),
                        lastName = this.lastNameEditText.text.toString(),
                        phone = this.phoneEditText.text.toString(),
                        password = this.passwordEditText.text.toString()
                    )
                )
            }

            toLogButton.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

    }

    private val progressObserver = Observer<Boolean> {
        makeVisibleOrGone(binding.animationView, it)
    }
    private val regObserver = Observer<RegisterResponse> {
        showToast(it.message)
        showBottomSheet()
    }
    private val failObserver = Observer<String> {
        showToast(it)
    }
    private val verifyObserver = Observer<TokenResponse> {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
    }


    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        val view = layoutInflater.inflate(R.layout.bottomsheet_verfy_sms_code, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val verifyBtn = dialog.findViewById<Button>(R.id.verify_sms_btn)
        val phoneTv = dialog.findViewById<TextView>(R.id.text_description)
        val smsCode = dialog.findViewById<EditText>(R.id.sms_code_et)

        phoneTv?.text = "We have sent SMS code to ${binding.phoneEditText.text}"

        verifyBtn!!.setOnClickListener {
            viewModel.verify(
                VerifyRequest(
                    binding.phoneEditText.text.toString(),
                    smsCode?.text.toString()
                )
            )
            dialog.dismiss()
        }
    }

}