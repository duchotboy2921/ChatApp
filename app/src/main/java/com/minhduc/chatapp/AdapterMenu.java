package com.minhduc.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder> {
    List<MenuItem> list;
    Context mcontext;

    public AdapterMenu(List<MenuItem> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = list.get(position);
        holder.item_image.setImageResource(menuItem.getImageMenu());
        holder.item_name.setText(menuItem.getMenuName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuItem.getMenuName().equals("Sign out")){
                    DatabaseReference sttRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    sttRef.child("status").setValue("offline");
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(v.getContext(),StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ((Activity)v.getContext()).finish();
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_image;
        private TextView item_name;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            item_image = itemView.findViewById(R.id.image_item_menu);
            item_name = itemView.findViewById(R.id.txt_menu_item);
//            itemView.setOnClickListener(this);
        }

    }
}
