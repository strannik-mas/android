package com.student.realm3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import io.realm.RealmResults;

public class UserAdapter extends BaseAdapter {

    private RealmResults<User> users;

    public UserAdapter (RealmResults<User> users) {

        this.users = users;
    }

    public void update(RealmResults<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.user_item, viewGroup, false);

        TextView first = v.findViewById(R.id.firstname);
        TextView last = v.findViewById(R.id.lastname);

        User u = users.get(i);

        first.setText(u.getFirstName());
        last.setText(u.getLastName());

        return v;
    }
}
