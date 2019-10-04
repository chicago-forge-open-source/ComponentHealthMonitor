package com.acn.componenthealthmonitor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.acn.componenthealthmonitor.databinding.ListBleItemBinding;

import java.util.List;

class BleRecyclerAdapter extends RecyclerView.Adapter<BleRecyclerAdapter.BleViewHolder> {

    static final String EXTRA_BLUETOOTH = "EXTRA_BLUETOOTH";
    private List<BleItem> items;
    private Activity parentActivity;

    public BleRecyclerAdapter(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

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
        setItemOnClickListener(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<BleItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private void setItemOnClickListener(@NonNull BleViewHolder holder, final BleItem item) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickReturnsToMain(item);
            }
        });
    }

    void handleClickReturnsToMain(BleItem item) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_BLUETOOTH, item);
        parentActivity.setResult(Activity.RESULT_OK, intent);
        parentActivity.finish();
    }

    class BleViewHolder extends RecyclerView.ViewHolder {
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

