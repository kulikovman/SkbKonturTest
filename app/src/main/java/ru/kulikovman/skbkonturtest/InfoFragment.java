package ru.kulikovman.skbkonturtest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import ru.kulikovman.skbkonturtest.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {

    private MainActivity activity;
    private FragmentInfoBinding binding;
    private ContactViewModel model;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Настройка экшен бара
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().show();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("");
        }

        model = ViewModelProviders.of(activity).get(ContactViewModel.class);

        initUI();
    }

    private void initUI() {
        binding.setContact(model.getSelectedContact());
        binding.setModel(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Переход назад
            NavHostFragment.findNavController(this).popBackStack();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
