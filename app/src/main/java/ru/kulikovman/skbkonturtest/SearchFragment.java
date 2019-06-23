package ru.kulikovman.skbkonturtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
import ru.kulikovman.skbkonturtest.databinding.FragmentSearchBinding;
import ru.kulikovman.skbkonturtest.repository.DataRepository;
import ru.kulikovman.skbkonturtest.ui.adapter.ContactAdapter;

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

    @SuppressLint("CheckResult")
    private void initUI() {
        // Инициализация поискового поля
        RxTextView.textChanges(binding.search)
                .debounce(800, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    Log.d("myLog", "Поисковый запрос: " + query);
                    showClearButton(!TextUtils.isEmpty(query));
                    model.saveSearchQuery(query);

                    // Загружаем контакты
                    loadContactList();
                }, Throwable::printStackTrace);

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
        connectionStatus.observe(this, status -> {
            Log.d("myLog", "Статус интернет соединения: " + status);
            if (status == DataRepository.NO_CONNECTION) {
                // Сообщение об отсутствии подключения
                Snackbar.make(binding.searchContainer, getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();

                // Отключение индикаторов загрузки
                binding.swipeRefreshLayout.setRefreshing(false);
                showLoading(false);
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
        contacts.observe(this, contacts1 -> {
            Log.d("myLog", "Контактов в списке: " + contacts1.size());
            contactAdapter.setContacts(contacts1);

            // Отключение индикаторов загрузки
            // Если список не пустой или есть поисковый запрос
            if (contacts1.size() > 0 || !TextUtils.isEmpty(model.getSearchQuery())) {
                binding.swipeRefreshLayout.setRefreshing(false);
                showLoading(false);
            }
        });
    }

    private void updateContactList() {
        LiveData<List<Contact>> contactsFromServer = model.getContactsFromServer();
        contactsFromServer.observe(this, contacts -> {
            model.updateContacts(contacts);
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
