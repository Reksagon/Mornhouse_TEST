package com.denys.korniienko.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.denys.korniienko.dagger.AppModule;
import com.denys.korniienko.dagger.DaggerMyComponent;
import com.denys.korniienko.dagger.MyComponent;
import com.denys.korniienko.databinding.FragmentDetailBinding;
import com.denys.korniienko.room.History;
import com.denys.korniienko.room.HistoryDao;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailFragment extends Fragment {

    @Inject
    HistoryDao historyrDao;
    private FragmentDetailBinding binding;
    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        String fact = getArguments().getString("fact");
        String[] strings = fact.split(" ");
        binding.txtNumber.setText(strings[0]);
        String s = fact.replaceFirst(strings[0], " ");
        binding.txtFact.setText(s);

        MyComponent component = DaggerMyComponent.builder()
                .appModule(new AppModule(getActivity().getApplication()))
                .build();
        component.inject(this);


        Completable.fromAction(() -> historyrDao.inserHistory(new History(strings[0], s)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // Операція успішно виконана
                }, throwable -> {
                    // Виникла помилка
                });

        return binding.getRoot();
    }
}
