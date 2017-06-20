package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nickolas on 20.06.2017.
 */

public class CustomUserListAdapter extends RecyclerView.Adapter<CustomUserListAdapter.ViewHolder>{


     UserListModel userListModel;

    public CustomUserListAdapter(UserListModel listModel){
        userListModel = listModel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_view,parent, false);

        ViewHolder vh = new ViewHolder(v);

        vh.name = (TextView) v.findViewById(R.id.user_name);
        vh.email = (TextView) v.findViewById(R.id.user_email);

        vh.avatar = (ImageView) v.findViewById(R.id.user_photo);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        new DownloadImageTask(holder.avatar).downloadAvatar(userListModel.get(position).id);
        holder.email.setText(userListModel.get(position).email);
        holder.name.setText(userListModel.get(position).name);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogListModel(MainActivity.activity);

                Intent intent = new Intent(MainActivity.activity, Dialog.class);
                intent.putExtra("id", userListModel.get(position).getId());
                intent.putExtra("name", userListModel.get(position).getName());
                intent.putExtra("email", userListModel.get(position).getEmail());
                MainActivity.activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userListModel.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        ImageView avatar;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }
}
