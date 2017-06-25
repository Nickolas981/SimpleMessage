//package com.example.nickolas.simplemessage;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.text.format.DateFormat;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class CustomMessageAdapter extends RecyclerView.Adapter<CustomMessageAdapter.ViewHolder> implements View.OnCreateContextMenuListener{
//
//    private ArrayList<MessageModel> messageModel;
//
//    public CustomMessageAdapter(ArrayList<MessageModel> messageModel, Context context) {
//        this.messageModel = messageModel;
//    }
//
//    public void addItems(ArrayList<MessageModel> message) {
//        messageModel = message;
//        notifyItemInserted(message.size() - 1);
//    }
//
//    public void addItem(MessageModel messageModel) {
//        if (messageModel != null) {
//            this.messageModel.add(messageModel);
//            notifyItemInserted(this.messageModel.size() - 1);
//        }
//    }
//
//    public void removeItem(MessageModel m) {
//        for (int i = 0; i < getItemCount(); i++) {
//            if (messageModel.get(i).equals(m)) {
//                messageModel.remove(i);
//                notifyItemRemoved(i);
//                return;
//            }
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v;
//
//        v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.custom_message_in_view, parent, false);
//
//
//        ViewHolder viewHolder = new ViewHolder(v);
//
////        viewHolder.avatar = (ImageView) v.findViewById(R.id.sender_avatar);
//        viewHolder.body = (TextView) v.findViewById(R.id.message);
//        viewHolder.time = (TextView) v.findViewById(R.id.message_time);
//        viewHolder.name = (TextView) v.findViewById(R.id.sender_name);
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.name.setText(messageModel.get(position).getName());
//        holder.body.setText(messageModel.get(position).getBody());
//        holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", messageModel.get(position).getTimeMessage()));
//        holder.item.setOnCreateContextMenuListener(this);
////        new DownloadImageTask(holder.avatar).downloadAvatar(messageModel.get(position).getUid());
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return messageModel.size();
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//
//        menu.setHeaderTitle("Select The Action");
//        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
//        menu.add(0, v.getId(), 0, "SMS");
//    }
//
//
//    protected static class ViewHolder extends RecyclerView.ViewHolder   implements View.OnCreateContextMenuListener, OnClickListener{
//        TextView name, body, time;
//        ImageView avatar;
//        View item;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            item = itemView;
//            itemView.setOnCreateContextMenuListener(this);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle("Select Action");
//            MenuItem edit = menu.add(Menu.NONE,1,1,"Edit");
//            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");
//
//
//            edit.setOnMenuItemClickListener(onChange);
//            delete.setOnMenuItemClickListener(onChange);
//        }
//
//        private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case 1:
//                        Toast.makeText(MainActivity.activity,"Edit", Toast.LENGTH_LONG).show();
//                        return true;
//                    case 2:
//                        Toast.makeText(MainActivity.activity,"Delete",Toast.LENGTH_LONG).show();
//                        return true;
//                }
//                return false;
//            }
//        };
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(MainActivity.activity, "asdasd ", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
