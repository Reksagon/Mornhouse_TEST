package com.denys.korniienko.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.denys.korniienko.R;
import com.denys.korniienko.adapter.HistoryAdapter;
import com.denys.korniienko.dagger.AppModule;
import com.denys.korniienko.dagger.DaggerMyComponent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.denys.korniienko.dagger.MyComponent;
import com.denys.korniienko.databinding.FragmentMainBinding;
import com.denys.korniienko.room.History;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainFragmentViewModel mainFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainFragmentViewModel =
                new ViewModelProvider(this).get(MainFragmentViewModel.class);
        binding = FragmentMainBinding.inflate(inflater, container, false);

        MyComponent component = DaggerMyComponent.builder()
                .appModule(new AppModule(getActivity().getApplication()))
                .build();
        component.inject(mainFragmentViewModel);

        getData();

        binding.buttonGetFact.setOnClickListener(v ->
        {
            if (binding.editTextNumber.getText().length() > 0) {
                int num = Integer.parseInt(binding.editTextNumber.getText().toString());
                mainFragmentViewModel.getFact(num);
            }
            else
                Snackbar.make(binding.getRoot(), getString(R.string.error_enter), Snackbar.LENGTH_SHORT).show();

        });
        binding.buttonGetRandomFact.setOnClickListener(v ->
        {
            mainFragmentViewModel.getRandomFact();
        });
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();
        mainFragmentViewModel.getAllHistory();
        binding.historyRecycler.setAdapter(null);
    }

    private void getData()
    {
        mainFragmentViewModel.getAllHistory();
        mainFragmentViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("fact", s);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_main_to_navigation_detail, bundle);
                }

            }
        });

        mainFragmentViewModel.getHistoryMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                binding.historyRecycler.setLayoutManager(layoutManager);
                HistoryAdapter historyAdapter = new HistoryAdapter(histories);
                binding.historyRecycler.setAdapter(historyAdapter);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mainFragmentViewModel.clearData();
    }
}
