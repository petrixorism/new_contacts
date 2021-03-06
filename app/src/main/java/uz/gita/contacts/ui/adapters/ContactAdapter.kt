package uz.gita.contacts.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contacts.R
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.databinding.ItemContactBinding


class ContactAdapter : ListAdapter<ContactResponse, ContactAdapter.ViewHolder>(DiffItem) {

    private var moreListener: ((ItemListenerData) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val binding by viewBinding(ItemContactBinding::bind)

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.contactName.text = "$firstName $lastName"
                binding.contactPhone.text = phone
            }
        }

        init {
            binding.moreButton.setOnClickListener {
                moreListener?.invoke(
                    ItemListenerData(
                        binding.moreButton,
                        getItem(absoluteAdapterPosition)
                    )
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }


    object DiffItem : DiffUtil.ItemCallback<ContactResponse>() {
        override fun areItemsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContactResponse,
            newItem: ContactResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    fun setMoreButtonListener(block: (ItemListenerData) -> Unit) {
        moreListener = block
    }
}

data class ItemListenerData(val button: Button, val data: ContactResponse)