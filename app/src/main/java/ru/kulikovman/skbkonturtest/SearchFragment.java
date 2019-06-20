package ru.kulikovman.skbkonturtest;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
import ru.kulikovman.skbkonturtest.databinding.FragmentSearchBinding;
import ru.kulikovman.skbkonturtest.repository.DataRepository;
import ru.kulikovman.skbkonturtest.ui.adapter.ContactAdapter;
import ru.kulikovman.skbkonturtest.util.sweet.SweetTextWatcher;

public class SearchFragment extends Fragment implements ContactAdapter.ContactClickListener, SwipeRefreshLayout.OnRefreshListener {

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
        model = ViewModelProviders.of(activity).get(ContactViewModel.class);

        initUI();
    }

    private void initUI() {
        // Инициализация поискового поля
        binding.search.addTextChangedListener(new SweetTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    Log.d("myLog", "Поисковый запрос: " + s.toString());

                    model.saveSearchQuery(s.toString());
                    showClearButton(true);
                } else {
                    model.saveSearchQuery(null);
                    showClearButton(false);
                }

                // Загружаем контакты
                loadContactList();
            }
        });

        binding.search.setText(model.getSearchQuery());

        // Инициализация списка
        contactAdapter = new ContactAdapter();
        contactAdapter.setContactClickListener(this);

        RecyclerView contactList = binding.contactList;
        contactList.setLayoutManager(new LinearLayoutManager(activity));
        contactList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        contactList.setAdapter(contactAdapter);
        contactList.setHasFixedSize(true);
        contactList.setItemAnimator(new DefaultItemAnimator());

        // Инициализация контейнера для обновления при свайпе вниз
        binding.swipeRefreshLayout.setOnRefreshListener(this);

        // Отслеживание статуса соединения
        LiveData<Integer> connectionStatus = model.getConnectionStatus();
        connectionStatus.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                Log.d("myLog", "Статус интернет соединения: " + status);
                if (status == DataRepository.NO_CONNECTION) {
                    // Сообщение об отсутствии подключения
                    Snackbar.make(binding.searchContainer, getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();

                    // Отключение индикаторов загрузки
                    binding.swipeRefreshLayout.setRefreshing(false);
                    showLoading(false);
                }
            }
        });

        // Загружаем контакты
        loadContactList();

        // Обновление контактов если прошло достаточно времени
        if (model.isNeedUpdateContacts()) {
            updateContactList();
        }

        binding.setModel(this);
    }

    private void loadContactList() {
        showLoading(true);

        LiveData<List<SimpleContact>> contacts = model.getContacts();
        contacts.observe(this, new Observer<List<SimpleContact>>() {
            @Override
            public void onChanged(List<SimpleContact> contacts) {
                Log.d("myLog", "Контактов в списке: " + contacts.size());
                contactAdapter.setContacts(contacts);
                contactAdapter.notifyDataSetChanged();

                // Отключение индикаторов загрузки
                if (contacts.size() > 0) {
                    binding.swipeRefreshLayout.setRefreshing(false);
                    showLoading(false);
                }
            }
        });
    }

    private void updateContactList() {
        LiveData<List<Contact>> contactsFromServer = model.getContactsFromServer();
        contactsFromServer.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                model.updateContacts(contacts);
            }
        });
    }

    @Override
    public void onRefresh() {
        clearSearchQuery();

        // Обновление контактов с анимацией контейнера
        binding.swipeRefreshLayout.setRefreshing(true);
        updateContactList();
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

    public void onClearButtonClick() {
        clearSearchQuery();

        // Загружаем контакты
        loadContactList();
    }

    private void clearSearchQuery() {
        // Очищаем поисковый запрос
        model.saveSearchQuery(null);
        binding.search.setText(null);
    }

    private void showLoading(boolean isShow) {
        binding.loading.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private void showClearButton(boolean isShow) {
        binding.clear.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}
