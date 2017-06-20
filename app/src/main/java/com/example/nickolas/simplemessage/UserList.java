package com.example.nickolas.simplemessage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


public class UserList extends Fragment implements UserListModel.UserListModelListner {

    CustomUserListAdapter mAdapter;
    RecyclerView userList;
    UserListModel userListModel;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);
        userList = (RecyclerView) v.findViewById(R.id.user_list);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        userListModel = new UserListModel(this);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    public void userListSuccess() {
        mAdapter = new CustomUserListAdapter(userListModel);
        userList.setLayoutManager(new mManager(MainActivity.activity));
        userList.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        userList.setAdapter(mAdapter);
    }
}
