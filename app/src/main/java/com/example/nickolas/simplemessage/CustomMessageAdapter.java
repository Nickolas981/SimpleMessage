package com.example.nickolas.simplemessage;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class CustomMessageAdapter extends RecyclerView.Adapter<CustomMessageAdapter.ViewHolder> {

    private ArrayList<MessageModel> messageModel;
    private Context context;

    public CustomMessageAdapter(ArrayList<MessageModel> messageModel, Context context) {
        this.messageModel = messageModel;
        this.context = context;
    }

    public void addItems(ArrayList<MessageModel> message){
        messageModel = message;
        notifyItemInserted(message.size()-1);
    }

    public  void addItem(MessageModel messageModel){
        if (messageModel != null) {
            this.messageModel.add(messageModel);
            notifyItemInserted(this.messageModel.size() - 1);
        }
    }

    public void removeItem(MessageModel m){
        for(int i = 0; i < getItemCount(); i++){
            if (messageModel.get(i).equals(m)){
                messageModel.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_message_in_view, parent, false);



        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.avatar = (ImageView) v.findViewById(R.id.sender_avatar);
        viewHolder.body = (TextView) v.findViewById(R.id.message);
        viewHolder.time = (TextView) v.findViewById(R.id.message_time);
        viewHolder.name = (TextView) v.findViewById(R.id.sender_name);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(messageModel.get(position).getName());
        holder.body.setText(messageModel.get(position).getBody());
        holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", messageModel.get(position).getTimeMessage()));
//        Glide.with(context)
//                .load(FirebaseStorage.getInstance().getReferenceFromUrl("gs://simplemessage-abdee.appspot.com/avatars").child(messageModel.get(position).getUid() + ".jpg"))
//                .into(holder.avatar);
        new DownloadImageTask(holder.avatar).downloadAvatar(messageModel.get(position).getUid());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(context, holder.itemView);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.message);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.delete:
//                                //handle menu1 click
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return messageModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, body, time;
        ImageView avatar;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }

}
