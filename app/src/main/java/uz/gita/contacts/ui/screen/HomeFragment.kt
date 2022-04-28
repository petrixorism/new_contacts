package uz.gita.contacts.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contacts.R
import uz.gita.contacts.data.model.request.AddContactRequest
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.databinding.FragmentHomeBinding
import uz.gita.contacts.ui.adapters.ContactAdapter
import uz.gita.contacts.ui.viewmodel.HomeViewModel
import uz.gita.contacts.ui.viewmodel.impl.HomeViewModelImpl
import uz.gita.contacts.utils.makeVisibleOrGone
import uz.gita.contacts.utils.showSnackBar
import uz.gita.contacts.utils.showToast

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val adapter = ContactAdapter()
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.adapter = adapter

        viewModel.getAllContacts()
        viewModel.apply {
            getAllContactsLiveData.observe(viewLifecycleOwner, getContactsObserver)
            progressLiveData.observe(viewLifecycleOwner, progressObserver)
            deleteContactLiveData.observe(viewLifecycleOwner, deleteContactsObserver)
            updateContactLiveData.observe(viewLifecycleOwner, updateContactsObserver)
            failLiveData.observe(viewLifecycleOwner, failContactsObserver)
            notConnectionLiveData.observe(viewLifecycleOwner, noConnectionContactsObserver)
            addContactLiveData.observe(viewLifecycleOwner, addContactObserver)
            logoutLiveData.observe(viewLifecycleOwner, logoutObserver)
        }

        binding.settingButton.setOnClickListener {
            showLogoutPopUpMenu()
        }

        binding.addContactButton.setOnClickListener {
            addBottomSheet()
        }
        adapter.setMoreButtonListener {
            showPopUp(it.button, it.data)
        }


    }

    private val logoutObserver = Observer<Unit> {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
    }
    private val getContactsObserver = Observer<List<ContactResponse>> {
        adapter.submitList(it)
    }
    private val progressObserver = Observer<Boolean> {
        Log.d("PRGRSS", it.toString())
        makeVisibleOrGone(binding.progressCircular, it)
    }
    private val deleteContactsObserver = Observer<ContactResponse> {
        showSnackBar("Contact has been deleted")
        viewModel.getAllContacts()

    }
    private val updateContactsObserver = Observer<ContactResponse> {
        showSnackBar("Contact has been updated")
        viewModel.getAllContacts()

    }
    private val failContactsObserver = Observer<String> {
        showToast(it)
    }
    private val noConnectionContactsObserver = Observer<Unit> {
        showSnackBar("No internet connection")
    }
    private val addContactObserver = Observer<ContactResponse> {
        showSnackBar("Contact has been added")
        viewModel.getAllContacts()
    }


    @SuppressLint("CutPasteId")
    fun editBottomSheet(data: ContactResponse) {

        val dialog = BottomSheetDialog(this.requireContext(), R.style.DialogStyle)
        val view = layoutInflater.inflate(R.layout.bottomsheet_edit_contact, null)

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val addButton = dialog.findViewById<Button>(R.id.edit_contact_button)
        val firstName = dialog.findViewById<EditText>(R.id.contact_first_name)
        val lastName = dialog.findViewById<EditText>(R.id.contact_last_name)
        val phone = dialog.findViewById<EditText>(R.id.contact_phone)

        firstName!!.setText(data.firstName)
        lastName!!.setText(data.lastName)
        phone!!.setText(data.phone)

        addButton!!.setOnClickListener {
            viewModel.updateContact(
                ContactRequest(
                    data.id,
                    firstName.text.toString(),
                    lastName.text.toString(),
                    phone.text.toString()
                )
            )
            dialog.dismiss()
        }

    }

    @SuppressLint("CutPasteId")
    fun addBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        val view = layoutInflater.inflate(R.layout.bottomsheet_add_contact, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val addButton = dialog.findViewById<Button>(R.id.add_contact_button)
        val firstName = dialog.findViewById<EditText>(R.id.contact_first_name)
        val lastName = dialog.findViewById<EditText>(R.id.contact_last_name)
        val phone = dialog.findViewById<EditText>(R.id.contact_phone)


        addButton!!.setOnClickListener {
            viewModel.addContact(
                AddContactRequest(
                    firstName!!.text.toString(),
                    lastName!!.text.toString(),
                    phone!!.text.toString()
                )
            )
            dialog.dismiss()
        }

    }

    private fun showPopUp(button: Button, data: ContactResponse) {
        val popup = PopupMenu(requireContext(), button)
        popup.inflate(R.menu.item_menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_contact -> {
                    data.id?.let { id ->
                        viewModel.deleteContact(id)
                    }
                    return@setOnMenuItemClickListener true
                }
                R.id.edit_contact -> {
                    editBottomSheet(data)
                    return@setOnMenuItemClickListener true
                }
            }

            return@setOnMenuItemClickListener false
        }
    }

    fun showLogoutPopUpMenu() {
        val popup = PopupMenu(requireContext(), binding.settingButton)
        popup.inflate(R.menu.setting_menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    viewModel.logout()
                    return@setOnMenuItemClickListener true
                }

            }
            return@setOnMenuItemClickListener false
        }
    }

}