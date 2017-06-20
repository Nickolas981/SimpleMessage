package com.example.nickolas.simplemessage;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CustomDialogAdapter extends RecyclerView.Adapter<CustomDialogAdapter.ViewHolder> {

    DialogModel dialogModel;

    public CustomDialogAdapter(DialogModel dialogModel) {
        this.dialogModel = dialogModel;
    }

    public void addItem() {
        notifyItemInserted(this.dialogModel.getSize() - 1);
    }

    public void removeItem(int i){
        notifyItemRemoved(i);
    }

    @Override
    public int getItemViewType(int position) {
        if (dialogModel.get(position).getUid().equals(Firebasse.getuId())){
            return 1;
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v;
        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_message_in_view, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_message_out_view, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.body = (TextView) v.findViewById(R.id.message);
        viewHolder.time = (TextView) v.findViewById(R.id.message_time);
//        viewHolder.name = (TextView) v.findViewById(R.id.sender_name);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.name.setText(dialogModel.get(position).getName());
        holder.body.setText(dialogModel.get(position).getBody());
        holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", dialogModel.get(position).getTimeMessage()));
    }

    @Override
    public int getItemCount() {
        return dialogModel.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, body, time;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }
}
