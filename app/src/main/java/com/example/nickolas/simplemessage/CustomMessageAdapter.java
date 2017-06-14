//package com.example.nickolas.simplemessage;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by Nickolas on 14.06.2017.
// */
//
//public class CustomMessageAdapter extends RecyclerView.Adapter<CustomMessageAdapter.ViewHolder> {
//
//    private ArrayList<MessageModel> messageModel;
//    private Context context;
//
//    public CustomMessageAdapter(ArrayList<MessageModel> messageModel, Context context) {
//        this.messageModel = messageModel;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v;
//
//        v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.custom_message_in_view, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(v);
//
//        viewHolder.avatar = (ImageView) v.findViewById(R.id.sender_avatar);
//        viewHolder.body = (TextView) v.findViewById(R.id.message);
//        viewHolder.time = (TextView) v.findViewById(R.id.message_time);
//        viewHolder.name = (TextView) v.findViewById(R.id.sender_name);
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.name = MessageModel.
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageModel.size();
//    }
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView name, body, time;
//        ImageView avatar;
//        View item;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            item = itemView;
//        }
//    }
//
//}
