package com.acn.componenthealthmonitor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.acn.componenthealthmonitor.databinding.ListBleItemBinding;

import java.util.List;

class BleRecyclerAdapter extends RecyclerView.Adapter<BleRecyclerAdapter.BleViewHolder> {

    private List<BleItem> items;

    @NonNull
    @Override
    public BleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListBleItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_ble_item, parent, false);
        return new BleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BleViewHolder holder, int position) {
        BleItem item = items.get(position);

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<BleItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class BleViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ListBleItemBinding binding;

        BleViewHolder(ListBleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BleItem item) {
            binding.setItem(item);
        }
    }

}

