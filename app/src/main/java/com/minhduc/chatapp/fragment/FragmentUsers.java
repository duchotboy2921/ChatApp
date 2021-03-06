package com.minhduc.chatapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhduc.chatapp.R;
import com.minhduc.chatapp.adapter.UserAdapter;
import com.minhduc.chatapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class FragmentUsers extends Fragment {
    private List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_users);
        searchView = (SearchView)view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                userAdapter.filter(text);
                return false;
            }
        });
        userList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        readUser();
        return view;
    }
    private void readUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref = FirebaseDatabase.getInstance("https://chatapp-10fd5-default-rtdb.firebaseio.com/").getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot data:snapshot.getChildren()){
                    User user = data.getValue(User.class);
                    assert user!= null;
                    assert firebaseUser!= null;
                    if(!user.getId().equals(firebaseUser.getUid())){
                        userList.add(user);
                    }
                }
                userAdapter = new UserAdapter(userList,getContext());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("loi","loi r");
            }
        });
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu,menu);
//        MenuItem item = menu.findItem(R.id.menu_search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}
