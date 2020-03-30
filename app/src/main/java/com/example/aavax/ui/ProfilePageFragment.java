package com.example.aavax.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.aavax.R;
import com.example.aavax.ui.ProfileRVAdapter;
import java.util.ArrayList;

import model.ProfileRV;


public class ProfilePageFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";
    //widgets

    //vars
    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private ProfileRVAdapter adapter;
    private ArrayList<ProfileRV> profileRVArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        profileRVArrayList = new ArrayList<>();

        createListData();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new ProfileRVAdapter(getActivity(), profileRVArrayList);
        recyclerView.setAdapter(adapter);
    }
    private void createListData() {
        ProfileRV profileRV1 = new ProfileRV("Other Profiles");
        profileRVArrayList.add(profileRV1);
        ProfileRV profileRV2 = new ProfileRV("About Us");
        profileRVArrayList.add(profileRV2);
        ProfileRV profileRV3 = new ProfileRV("Sign Out");
        profileRVArrayList.add(profileRV3);

    }
}
