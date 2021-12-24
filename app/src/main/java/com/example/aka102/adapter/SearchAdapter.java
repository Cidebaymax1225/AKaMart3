package com.example.aka102.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aka102.MainActivity;
import com.example.aka102.R;
import com.example.aka102.model.User;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean ischat;






    public SearchAdapter(Context mContext, List<User> mUsers, boolean ischat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cari, parent, false);

        return new SearchAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.user_name_search.setText(user.getUsername());
        holder.user_full_name_search.setText(user.getBio());

        if (user.getImage().equals("default")){
            holder.user_profile_image_search.setImageResource(R.drawable.profile);
        } else {

            try {

                Glide
                        .with(mContext)
                        .load(user.getImage())
                        .centerCrop()
                        .into(holder.user_profile_image_search);

            }
            catch (Exception e){

            }

        }


        if (ischat) {


            if (user.getStatus().equals("online")) {
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);

            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        } else {

            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("visit_user_id", user.getUID());
                mContext.startActivity(intent);

            }
        });




    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView user_name_search;
        public TextView user_full_name_search;
        public ImageView user_profile_image_search;
        private ImageView img_on;
        private ImageView img_off;
        public ImageView user_image_chat;




        public ViewHolder(View itemView){
            super(itemView);

            user_full_name_search = itemView.findViewById(R.id.user_full_name_search);
            user_name_search = itemView.findViewById(R.id.user_name_search);
            user_profile_image_search = itemView.findViewById(R.id.user_profile_image_search);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            user_image_chat = itemView.findViewById(R.id.profile_cari);





        }
    }
}


