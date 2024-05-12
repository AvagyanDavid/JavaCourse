package com.example.course_mobile_3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_mobile_3.R;
import com.example.course_mobile_3.modelUI.Friend;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

    Context context;
    List<Friend> friendList;

    public FriendsAdapter(Context context, List<Friend> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendsItem = LayoutInflater.from(context).inflate(R.layout.friend_item,parent,false);
        return new FriendViewHolder(friendsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.fullName.setText(friendList.get(position).getFullName());
        holder.nickname.setText(friendList.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public static final class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView fullName,nickname;


        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.fullname);
            nickname = itemView.findViewById(R.id.nickname);
        }
    }
}