package ru.kulikovman.skbkonturtest.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
import ru.kulikovman.skbkonturtest.databinding.ItemContactBinding;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<SimpleContact> contacts = new ArrayList<>();

    private ContactClickListener contactClickListener;

    public interface ContactClickListener {
        void onContactClick(SimpleContact simpleContact);
    }

    public void setContactClickListener(ContactClickListener contactClickListener) {
        this.contactClickListener = contactClickListener;
    }

    public void setContacts(List<SimpleContact> contacts) {
        this.contacts = contacts;
    }

    public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemContactBinding binding;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            contactClickListener.onContactClick(binding.getContact());
        }
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContactBinding binding = ItemContactBinding.inflate(inflater, parent, false);
        return new ContactHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.binding.setContact(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
