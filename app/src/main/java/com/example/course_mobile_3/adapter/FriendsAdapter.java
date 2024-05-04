//package com.example.course_mobile_3.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.course_mobile_3.modelUI.Friend;
//
//import java.util.List;
//
//public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
//
//    private List<Friend> friendsList;
//
//    public FriendsAdapter(List<Friend> friendsList) {
//        this.friendsList = friendsList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_friend, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Friend friend = friendsList.get(position);
//        holder.nicknameTextView.setText(friend.getNickname());
//        holder.fullNameTextView.setText(friend.getFullName());
////        holder.iconImageButton.setImageResource(friend.getIconResId());
//    }
//
//    @Override
//    public int getItemCount() {
//        return friendsList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView nicknameTextView;
//        public TextView fullNameTextView;
//        public ImageButton iconImageButton;
//
//        public ViewHolder(View view) {
//            super(view);
//            nicknameTextView = view.findViewById(R.id.text_nickname);
//            fullNameTextView = view.findViewById(R.id.text_full_name);
//            iconImageButton = view.findViewById(R.id.image_button_icon);
//        }
//    }
//}