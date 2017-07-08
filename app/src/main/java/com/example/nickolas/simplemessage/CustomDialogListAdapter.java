package com.example.nickolas.simplemessage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomDialogListAdapter extends RecyclerView.Adapter<CustomDialogListAdapter.ViewHolder> {

    DialogListModel dialogs;


    public CustomDialogListAdapter(DialogListModel dialogs) {
        this.dialogs = dialogs;
    }


    @Override
    public int getItemViewType(int position) {
        String me, sender;
        me = Firebasse.getuId();
        sender = dialogs.get(position).last.getUid();
//        me = dialogs.get(position).last.getUid();

//        if (you.equals(me)){
//            return 1;
//        }
//        return 0;
        return sender.equals(me) ? 1 : 0;
    }

    void add() {
        notifyDataSetChanged();
    }

    void change(int i) {
        notifyItemChanged(i);
    }

    @Override
    public CustomDialogListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog_list_element_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        vh.avatar = (ImageView) v.findViewById(R.id.user_avatar);
        vh.name = (TextView) v.findViewById(R.id.user_name);
        vh.lastMessageBody = (TextView) v.findViewById(R.id.last_message);
        vh.lastMessageContainer = v.findViewById(R.id.last_message_container);
        vh.lastMessageTime = (TextView) v.findViewById(R.id.last_message_time);
        vh.myAvatar = (ImageView) v.findViewById(R.id.my_avatar);


        return vh;
    }

    @Override
    public void onBindViewHolder(CustomDialogListAdapter.ViewHolder vh, final int position) {
        new DownloadImageTask(vh.avatar, dialogs.get(position).photo).downloadAvatar();
        vh.name.setText(dialogs.get(position).name);
        vh.lastMessageBody.setText(dialogs.get(position).last.getBody());
//        vh.lastMessageContainer.
        vh.lastMessageTime.setText(DateFormat.format("(HH:mm:ss)", dialogs.get(position).last.getTimeMessage()));
//        vh.lastMessageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", dialogs.get(position).last.getTimeMessage()));

        if (getItemViewType(position) == 1) {
            new DownloadImageTask(vh.myAvatar, Firebasse.getuId()).downloadAvatar();
            vh.myAvatar.setVisibility(View.VISIBLE);
        }
        vh.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.activity, Dialog.class);
                intent.putExtra("id", dialogs.get(position).photo);
                intent.putExtra("name", dialogs.get(position).name);
                MainActivity.activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dialogs.getSize();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, lastMessageBody, lastMessageTime;
        View lastMessageContainer;
        ImageView avatar, myAvatar;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }
}
