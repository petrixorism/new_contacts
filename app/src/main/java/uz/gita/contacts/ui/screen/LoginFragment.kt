package uz.gita.contacts.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contacts.R
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.databinding.FragmentHomeBinding
import uz.gita.contacts.databinding.FragmentLoginBinding
import uz.gita.contacts.ui.viewmodel.LoginViewModel
import uz.gita.contacts.ui.viewmodel.impl.LoginViewModelImpl
import uz.gita.contacts.utils.makeVisibleOrGone
import uz.gita.contacts.utils.showSnackBar
import uz.gita.contacts.utils.showToast

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.failLiveData.observe(viewLifecycleOwner, failObserver)
        viewModel.loginLiveData.observe(viewLifecycleOwner, loginObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)

        binding.apply {
            loginButton.setOnClickListener {
                viewModel.login(
                    LoginRequest(
                        binding.phoneEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                )
            }
            toRegisterButton.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

    }

    private val failObserver = Observer<String> {
        showToast(it)
    }
    private val loginObserver = Observer<Unit> {
        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
    }
    private val notConnectionObserver = Observer<Unit> {
        showSnackBar("No internet connection")
    }
    private val progressObserver = Observer<Boolean> {
        makeVisibleOrGone(binding.animationView, it)
    }
}