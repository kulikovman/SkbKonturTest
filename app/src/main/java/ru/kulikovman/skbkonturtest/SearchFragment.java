package ru.kulikovman.skbkonturtest;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.kulikovman.skbkonturtest.databinding.FragmentSearchBinding;
import ru.kulikovman.skbkonturtest.ui.adapter.ContactAdapter;
import ru.kulikovman.skbkonturtest.util.sweet.SweetTextWatcher;

public class SearchFragment extends Fragment implements ContactAdapter.ContactClickListener {

    private MainActivity activity;
    private FragmentSearchBinding binding;
    private ContactViewModel model;

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
        // Скрыть ActionBar в этом фрагменте
        Objects.requireNonNull(activity.getSupportActionBar()).hide();

        // Подключение ViewModel
        model = ViewModelProviders.of(this).get(ContactViewModel.class);

        initUI();

        binding.setModel(this);
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
        ContactAdapter contactAdapter = new ContactAdapter(model);
        contactAdapter.setContactClickListener(this);

        RecyclerView contactList = binding.contactList;
        contactList.setLayoutManager(new LinearLayoutManager(activity));
        contactList.setAdapter(contactAdapter);
        contactList.setHasFixedSize(false);

        //model.getContactList();
    }

    public void clearSearchField() {
        binding.search.setText(null);
        binding.clear.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onContactClick() {
        // Передать контакт во вью модел
        // и открыть экран подробной информации о контакте
    }
}
