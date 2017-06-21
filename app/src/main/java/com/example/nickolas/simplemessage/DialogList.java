package com.example.nickolas.simplemessage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class DialogList extends Fragment implements DialogListModel.DialogListListner{

    CustomDialogListAdapter mAdapter;
    RecyclerView dialogList;
    DialogListModel dialogListModel;
    ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_list, container, false);

        dialogList = (RecyclerView) v.findViewById(R.id.user_list);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        dialogListModel = new DialogListModel(this);
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

    @Override
    public void add() {
        mAdapter.add();
    }

    @Override
    public void success() {
        mAdapter = new CustomDialogListAdapter(dialogListModel);
        dialogList.setLayoutManager(new mManager(MainActivity.activity));
        dialogList.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);
        dialogList.setVisibility(View.VISIBLE);
    }
}
