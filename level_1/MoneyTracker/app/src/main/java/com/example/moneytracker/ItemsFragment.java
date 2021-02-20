package com.example.moneytracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moneytracker.api.AddItemResult;
import com.example.moneytracker.api.RemoveResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsFragment extends Fragment {
    /*private static final int TYPE_UNCNOWN = -1;
    public static final int TYPE_INCOMES = 1;
    public static final int TYPE_EXPENSES = 2;*/
    private static final String TYPE_KEY = "type";
    public static final int ADD_ITEM_REQUEST_CODE = 123;

    private  String type;

    private static final String TAG = "ItemsFragment";

    public RecyclerView mRecycleView;
//    private FloatingActionButton fab;

    public List<Item> mData = new ArrayList<>();
    private ItemsAdapter adapter;

    private Api api;
    private App app;

    private static final int DATA_LOADED = 123;

    SwipeRefreshLayout refresh;

    public static ItemsFragment createItemsFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mRecycleView = findViewById(R.id.list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new ItemsAdapter(this);
        mAdapter.createData();
        mRecycleView.setAdapter(mAdapter);

        mData.add(new Item("Апельсин", 15680));
        mAdapter.notifyDataSetChanged();

        mRecycleView.addItemDecoration(new MarginItemDecoration(10));
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter(this);
        adapter.setListener(new AdapterListener());

        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(TYPE_KEY, Item.TYPE_UNCNOWN);
        }

        if (type.equals(Item.TYPE_UNCNOWN)) {
            throw new IllegalArgumentException("Unknown type");
        }

        app = (App) getActivity().getApplication();
        api = app.getApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = view.findViewById(R.id.list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(adapter);

        /*fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(getContext(), AddItemActivity.class);
                intent.putExtra(AddItemActivity.TYPE_KEY, type);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);

                //неявный интент
                *//*Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://pikabu.ru"));
                startActivity(intent);*//*
            }
        });*/

        refresh = view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        loadItems();

        mRecycleView.addItemDecoration(new MarginItemDecoration(10));
    }

    /**
     * ACTION MODE
     */

    /*private void removeSelectedItems() {
        //для удаления первого и еще какого-то проходим массив с конца
        for (int i = adapter.getSelectedItemCount() -1; i >= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }
        actionMode.finish();
    }*/

    private ActionMode actionMode = null;

    private class AdapterListener implements ItemsAdapterListener {

        @Override
        public void onItemClick(Item item, int position) {
            if (isInActionMode()) {
                toggleSelection(position);
            }
        }

        @Override
        public void onItemLongClick(Item item, int position) {
            if (isInActionMode()){
                return;
            }
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
            toggleSelection(position);
        }

        private boolean isInActionMode() {
            return actionMode != null;
        }

        /**
         * установка выбранного itema
         */
        private void toggleSelection(int position) {
            adapter.toggleSelection(position);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.remove:
                    showDialog();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

    private void showDialog() {
        /*AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Вы уверены?")
                .setTitle("Удаление")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeSelectedItems();
                    }
                })
                .setNegativeButton("Heт", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actionMode.finish();
                        adapter.clearSelections();
                    }
                })
                .create();
        dialog.show();*/

        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setListener(new DeleteDialogListener());
        dialog.show(getFragmentManager(), "ConfirmationDialog");
    }

    class DeleteDialogListener implements DialogListener {

        @Override
        public void onPositiveClick() {
            deleteItem();
        }

        @Override
        public void onNegativeClick() {
            actionMode.finish();
            adapter.clearSelections();
        }
    }

    private void loadItems() {
        Call<List<Item>> call = api.getItems(type);

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                adapter.setData(response.body());
                refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                refresh.setRefreshing(false);
            }
        });
    }

    private void addItem(final Item item) {
        Call<AddItemResult> call = api.addItem(item.price, item.name, item.type);
        call.enqueue(new Callback<AddItemResult>() {
            @Override
            public void onResponse(Call<AddItemResult> call, Response<AddItemResult> response) {
                AddItemResult result = response.body();
                if (result.status.equals("success")) {
                    adapter.addItem(item);
                }
            }

            @Override
            public void onFailure(Call<AddItemResult> call, Throwable t) {

            }
        });
    }

    private void deleteItem() {
        //для удаления первого и еще какого-то проходим массив с конца
        for (int i = adapter.getSelectedItemCount() -1; i >= 0; i--) {
            final int pos = adapter.getSelectedItems().get(i);
            Item item = adapter.getItemByPosition(pos);
            Call<RemoveResult> call = api.deleteItem(item.id);
            call.enqueue(new Callback<RemoveResult>() {
                @Override
                public void onResponse(Call<RemoveResult> call, Response<RemoveResult> response) {
                    RemoveResult result = response.body();
                    if (result.status.equals("success")) {
                        adapter.remove(pos);
                    }
                }

                @Override
                public void onFailure(Call<RemoveResult> call, Throwable t) {

                }
            });
        }
        actionMode.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            /*String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");
            Item item = (Item) data.getSerializableExtra("item");*/
            Item item = (Item) data.getParcelableExtra("item");
            if (item.type.equals(type)) {
//                adapter.addItem(item);
                addItem(item);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /*@SuppressLint("StaticFieldLeak")
    private void loadItems() {
        AsyncTask<Void, Void, List<Item>> task = new AsyncTask<Void, Void, List<Item>>() {

            @Override
            protected void onPreExecute() {
                Log.d(TAG, "onPreExecute: thread name = " + Thread.currentThread().getName());
            }

            @Override
            protected List<Item> doInBackground(Void... voids) {
                Log.d(TAG, "doInBackground: thread name = " + Thread.currentThread().getName());
                Call<List<Item>> call = api.getItems(type);

                try {
                    List<Item> items = call.execute().body();
                    return items;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                Log.d(TAG, "onPostExecute: thread name = " + Thread.currentThread().getName());
                if (items != null) {
                    adapter.setData(items);
                }
            }
        };

        task.execute();
    }*/


/*    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == DATA_LOADED) {
                List<Item> items = (List<Item>) msg.obj;
                adapter.setData(items);
                return true;
            }
            return false;
        }
    };

    private void loadItems() {
        Log.d(TAG, "loadItems: current thread " + Thread.currentThread().getName());
        new LoadItemsTask(new Handler(callback)).start();
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item("устрица", 0));
//        items.add(new Item("Молоко", 80));
//        items.add(new Item("тб", 999));
//        adapter.setData(items);
    }

    private class LoadItemsTask implements Runnable {
        private Thread thread;
        private Handler handler;

        public LoadItemsTask(Handler handler) {
            //разделяем потоки , т.к. нельзя использовать главный поток
            thread = new Thread(this);
            this.handler = handler;
        }

        public void start() {
            thread.start();
        }

        @Override
        public void run() {
            Log.d(TAG, "run: current thread " + Thread.currentThread().getName());

            Call<List<Item>> call = api.getItems(type);

            try {
                List<Item> items = call.execute().body();
//                adapter.setData(items);     //не работает, т.к. данные в другом потоке
                handler.obtainMessage(DATA_LOADED, items).sendToTarget();   //отправка на гл. поток
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


    private class MarginItemDecoration extends RecyclerView.ItemDecoration {
        //для отступов между item_record (вместо margin)
        private final int offset;

        public MarginItemDecoration(int offset) {
            this.offset = offset;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.top = offset*2;
            outRect.left = offset*2;
            outRect.right = offset*2;
        }
    }
}
