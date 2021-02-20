package com.example.moneytracker;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecordViewHolder> {
    private static final String TAG = "ItemsAdapter";

    private List<Item> data = new ArrayList<>();
    private ItemsAdapterListener listener = null;

    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    private ItemsFragment itemsFragment;

    public ItemsAdapter(ItemsFragment itemsFragment) {
        this.itemsFragment = itemsFragment;
        //createData();
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
//        notifyItemChanged(2);       //только для 2 item
    }

    /**
     * Получение Item по позиции в List
     * @param position
     * @return
     */
    public Item getItemByPosition(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemsAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.RecordViewHolder holder, int position) {
        Item item = data.get(position);
        holder.bind(item, position, listener, selections.get(position, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(Item item) {
        data.add(0, item);
        notifyItemInserted(0);
    }

    /**
     * хранит соответствие позиций и значений true/false (нажато/ненажато)
     * тоже самое, что и private HashMap<Integer, Boolean> selectionH = new HashMap<>();
     */
    private SparseBooleanArray selections = new SparseBooleanArray();

    public void toggleSelection(int position) {
        if (selections.get(position, false)) {
            selections.delete(position);
        } else {
            selections.put(position, true);
        }
        notifyItemChanged(position);
    }

    void clearSelections() {
        selections.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selections.size();
    }

    List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selections.size());
        for(int i = 0; i < selections.size(); i++) {
            items.add(selections.keyAt(i));
        }
        return items;
    }

    Item remove(int pos) {
        final Item item = data.remove(pos);
        notifyItemRemoved(pos);
        return item;
    }



    /*public void createData() {
        data.add(new Item("Молоко", 30));
        data.add(new Item("хрень", 100000000));
        data.add(new Item("Indicates whether this View is currently in edit mode. A View is usually in edit mode when displayed within a developer tool. For instance, if this View is being drawn by a", 0));
        data.add(new Item("уж", 50));
        data.add(new Item("устрица", 0));
        data.add(new Item("Молоко", 80));
        data.add(new Item("тб", 999));
        data.add(new Item("апельсин", 1568));
        data.add(new Item("Молоко22", 80));
        data.add(new Item("тб333", 999));
        data.add(new Item("апельсин222", 156822));
    }*/



    class RecordViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView price;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }

        public void bind(final Item item, final int position, final ItemsAdapterListener listener, boolean selected) {
            Log.d(TAG, "applyData: " + itemsFragment.mRecycleView.getChildLayoutPosition(itemView) + " " + item.name);

            String priceRaw = itemsFragment.getResources().getString(R.string.price_record);
            String recValPrice = String.valueOf(item.price);
            String priceFinal = String.format(priceRaw, recValPrice);
            title.setText(String.valueOf(item.name));
            //price.setText(priceFinal);

            //для декорирования знака валюты
            SpannableString spannableString = new SpannableString(priceFinal);
            spannableString.setSpan(
                    new ForegroundColorSpan(itemsFragment.getResources().getColor(R.color.uah_znak_color)),
                    recValPrice.length(),
                    priceFinal.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            price.setText(spannableString);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(item, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemLongClick(item, position);
                    }
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}
