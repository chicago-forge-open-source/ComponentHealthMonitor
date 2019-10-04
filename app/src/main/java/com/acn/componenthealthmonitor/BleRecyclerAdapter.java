package com.acn.componenthealthmonitor;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BleRecyclerAdapter extends RecyclerView.Adapter<BleRecyclerAdapter.BleViewHolder> {


    @NonNull
    @Override
    public BleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BleViewHolder extends RecyclerView.ViewHolder {
        private View view;

        BleViewHolder(View view) {
            super(view);
        }
    }

}

