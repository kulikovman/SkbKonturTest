package ru.kulikovman.skbkonturtest;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.databinding.FragmentSearchBinding;
import ru.kulikovman.skbkonturtest.ui.adapter.ContactAdapter;
import ru.kulikovman.skbkonturtest.util.sweet.SweetTextWatcher;

public class SearchFragment extends Fragment implements ContactAdapter.ContactClickListener {

    private MainActivity activity;
    private FragmentSearchBinding binding;
    private ContactViewModel model;

    private ContactAdapter contactAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Настройка экшен бара
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }

        model = ViewModelProviders.of(activity).get(ContactViewModel.class);

        initUI();
    }

    private void initUI() {
        // Слушатель поискового поля
        binding.search.addTextChangedListener(new SweetTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    binding.clear.setVisibility(View.VISIBLE);

                    // Делаем поиск по введенному запросу
                    // ...

                }
            }
        });

        // Инициализация списка контактов
        contactAdapter = new ContactAdapter();
        contactAdapter.setContactClickListener(this);

        RecyclerView contactList = binding.contactList;
        contactList.setLayoutManager(new LinearLayoutManager(activity));
        contactList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        contactList.setAdapter(contactAdapter);
        contactList.setHasFixedSize(false);

        // Подписываемся на контакты
        LiveData<List<Contact>> contactsFromDatabase = model.getContactsFromDatabase();
        contactsFromDatabase.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                Log.d("myLog", "Контактов в списке: " + contacts.size());

                contactAdapter.setContacts(contacts);
                contactAdapter.notifyDataSetChanged();
                binding.loading.setVisibility(View.INVISIBLE);
            }
        });

        // Обновление контактов
        if (model.isNeedUpdateContacts()) {
            LiveData<List<Contact>> contactsFromServer = model.getContactsFromServer();
            contactsFromServer.observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> contacts) {
                    model.updateContacts(contacts);
                }
            });
        }

        binding.setModel(this);
    }

    public void clearSearchField() {
        binding.search.setText(null);
        binding.clear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onContactClick(Contact contact) {
        // Сохраняем нажатый контакт
        model.selectContact(contact);

        // Запуск экрана с подробной информацией о контакте
        if (model.getSelectedContact() != null) {
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_infoFragment);
        }
    }
}
