package com.denys.korniienko.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.denys.korniienko.databinding.LayoutHistoryBinding;
import com.denys.korniienko.room.History;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<History> data;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final LayoutHistoryBinding binding;

        public MyViewHolder(LayoutHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(History item) {
            binding.txtNumberLayout.setText(item.getNum());
            binding.txtFactLayout.setText(item.getFact());
        }
    }

    public HistoryAdapter(List<History> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutHistoryBinding binding = LayoutHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
