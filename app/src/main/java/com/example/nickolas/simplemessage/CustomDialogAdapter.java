package com.example.nickolas.simplemessage;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;


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
        holder.dialogKey = dialogModel.id;
        holder.key = dialogModel.get(position).getKey();
        holder.uid = dialogModel.get(position).getUid();
    }

    @Override
    public int getItemCount() {
        return dialogModel.getSize();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView name, body, time;
        String key, dialogKey, uid;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE,1,1,"Delete");
            delete.setOnMenuItemClickListener(onChange);
        }
        private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case 2:
                        Toast.makeText(MainActivity.activity,"Edit", Toast.LENGTH_LONG).show();
                        return true;
                    case 1:
                        String other = Firebasse.getuId();
                        if (uid.equals(other)) {
                            Toast.makeText(MainActivity.activity,"Delete",Toast.LENGTH_LONG).show();
                            DatabaseReference ref = Firebasse.getmDatebase().getReference();
                            ref.child("dialogs").child("dialogs").child(dialogKey).child(key).removeValue();
                        }else {
                            Toast.makeText(MainActivity.activity, "It`s not your message)", Toast.LENGTH_LONG).show();
                        }
                        return true;
                }
                return false;
            }
        };

        @Override
        public void onClick(View v) {

        }
    }
}
