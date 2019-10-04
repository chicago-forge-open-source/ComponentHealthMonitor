package com.acn.componenthealthmonitor;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BleItemBinding {
    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<BleItem> items) {
        RecyclerView.Adapter<?> adapter = view.getAdapter();
        if (adapter instanceof BleRecyclerAdapter) {
            ((BleRecyclerAdapter) adapter).setItems(items);
        } else {
            throw new IllegalArgumentException("RecyclerView.Adapter is not a BleRecyclerAdapter");
        }
    }
}
