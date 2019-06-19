package ru.kulikovman.skbkonturtest;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
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
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    Log.d("myLog", "Поисковый запрос: " + s.toString());
                    showClearButton(true);
                    showLoading(true);

                    // Получаем контакты по запросу
                    updateContactListByQuery(s.toString());
                } else {
                    showClearButton(false);
                }
            }
        });

        // Инициализация списка
        contactAdapter = new ContactAdapter();
        contactAdapter.setContactClickListener(this);

        RecyclerView contactList = binding.contactList;
        contactList.setLayoutManager(new LinearLayoutManager(activity));
        contactList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        contactList.setAdapter(contactAdapter);
        contactList.setHasFixedSize(false);

        // Подгружаем контакты
        loadContactList();

        // Обновление контактов
        updateContactList();

        binding.setModel(this);
    }

    private void loadContactList() {
        LiveData<List<SimpleContact>> contactsFromDatabase = model.getContacts();
        contactsFromDatabase.observe(this, new Observer<List<SimpleContact>>() {
            @Override
            public void onChanged(List<SimpleContact> contacts) {
                Log.d("myLog", "Контактов в списке: " + contacts.size());
                contactAdapter.setContacts(contacts);
                contactAdapter.notifyDataSetChanged();

                showLoading(false);
            }
        });
    }

    private void updateContactList() {
        // Обновляет если прошло достаточно времени
        if (model.isNeedUpdateContacts()) {
            LiveData<List<Contact>> contactsFromServer = model.getContactsFromServer();
            contactsFromServer.observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> contacts) {
                    model.updateContacts(contacts);
                }
            });
        }
    }

    private void updateContactListByQuery(String query) {
        showLoading(true);

        LiveData<List<SimpleContact>> contacts = model.getContactsByQuery(query);
        contacts.observe(this, new Observer<List<SimpleContact>>() {
            @Override
            public void onChanged(List<SimpleContact> contacts) {
                contactAdapter.setContacts(contacts);
                contactAdapter.notifyDataSetChanged();

                showLoading(false);
            }
        });
    }

    public void clearSearchField() {
        // Очищает поле и подгружает котакты
        binding.search.setText(null);
        showLoading(true);
        loadContactList();
    }

    @Override
    public void onContactClick(SimpleContact simpleContact) {
        // Сохраняем нажатый контакт
        model.selectContact(simpleContact);

        // Запуск экрана с подробной информацией о контакте
        if (model.getSelectedContact() != null) {
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_infoFragment);
        }
    }

    private void showLoading(boolean isShow) {
        binding.loading.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private void showClearButton(boolean isShow) {
        binding.clear.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}
