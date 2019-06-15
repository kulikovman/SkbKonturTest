package ru.kulikovman.skbkonturtest;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {

    private ContactViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Подключаем макет меню
        inflater.inflate(R.menu.search_menu, menu);

        // Обработчик поисковых запросов
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                /*preparingForLoadPhoto(searchQuery);

                // Получение фото
                showProgressBar();
                loadSearchPhoto(mSearchQuery, 1, true);

                // Свертывание поиска
                searchView.clearFocus();
                searchView.onActionViewCollapsed();*/

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}
