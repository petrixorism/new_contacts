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
import uz.gita.contacts.databinding.FragmentMainBinding
import uz.gita.contacts.ui.viewmodel.MainViewModel
import uz.gita.contacts.ui.viewmodel.impl.MainViewModelImpl
import uz.gita.contacts.utils.makeVisibleOrGone

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.splashLiveData.observe(viewLifecycleOwner, splashObserver)
        viewModel.splash()

        binding.apply {
            loginButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
            }
            registerButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToRegisterFragment())
            }
            nextButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToHomeFragment())
            }
            socially.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToBaseUrlFragment())
            }

        }
    }

    private val splashObserver = Observer<Boolean> {

        binding.apply {
            makeVisibleOrGone(nextButton, it)
            makeVisibleOrGone(registerButton, !it)
            makeVisibleOrGone(loginButton, !it)
            makeVisibleOrGone(view, !it)
        }

    }

}