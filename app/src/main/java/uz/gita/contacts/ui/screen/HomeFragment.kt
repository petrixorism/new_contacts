package uz.gita.contacts.ui.screen

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contacts.R
import uz.gita.contacts.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)


}