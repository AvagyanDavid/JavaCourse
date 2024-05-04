//package com.example.course_mobile_3.ui.friends;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.course_mobile_3.R;
//import com.example.course_mobile_3.modelUI.Friend;
//import com.example.course_mobile_3.adapter.FriendsAdapter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FriendsFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private FriendsAdapter friendsAdapter;
//    private List<Friend> friendsList;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_friends, container, false);
//
//        recyclerView = root.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        friendsList = new ArrayList<>();
//        friendsAdapter = new FriendsAdapter(friendsList);
//        recyclerView.setAdapter(friendsAdapter);
//
//        // Добавим примеры данных для отображения
//        friendsList.add(new Friend(1L,"John", "John Doe", "fullname"));
//        friendsList.add(new Friend(2L,"Alice", "Alice Smith", "fullname"));
//        friendsList.add(new Friend(3L,"Bob", "Bob Johnson", "fullname"));
//        friendsAdapter.notifyDataSetChanged();
//
//        return root;
//    }
//}