package com.example.aavax.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.aavax.R;
import model.Vaccine;
import com.example.aavax.ui.homepage.VaccineAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomePageFragment extends Fragment {

    private static final String TAG = "MyVaccinations";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private VaccineAdapter adapter;
    private ArrayList<Vaccine> vaccineArrayList;
    private String uId;
    private ImageButton addEntry;
    private FragmentActivity myContext;
    private FirebaseManager firebaseManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
        firebaseManager  = new FirebaseManager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        vaccineArrayList = new ArrayList<>();
        final ImageButton addVaccineButton = view.findViewById(R.id.add_vaccine_button);
        addVaccineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new VaccineEntryFragment();
                doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, "");
            }
        });
        // initialise vaccines
        createListData();
        //firebaseManager.retrieveVaccines();




        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.vaccine_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each vaccine row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new VaccineAdapter(getActivity(), vaccineArrayList);
        recyclerView.setAdapter(adapter);
    }

    //TODO: call profile method to get list of vaccine log entries
    private void createListData() {
        //

        /*
        Vaccine vac1 = new Vaccine("Hepatitis A", "detail 1");
        vaccineArrayList.add(vac1);
        Vaccine vac2 = new Vaccine("Measles", "detail 1");
        vaccineArrayList.add(vac2);
        Vaccine vac3 = new Vaccine("Rubella", "detail 1");
        vaccineArrayList.add(vac3);
        Vaccine vac4 = new Vaccine("Td Booster", "detail 1");
        vaccineArrayList.add(vac4);
        Vaccine vac5 = new Vaccine("Varicella", "detail 1");
        vaccineArrayList.add(vac5);
        Vaccine vac6 = new Vaccine("Malaria", "detail 1");
        vaccineArrayList.add(vac6);
        Vaccine vac7 = new Vaccine("Vaccine", "detail 1");
        vaccineArrayList.add(vac7);
        vaccineArrayList.add(vac7);
        vaccineArrayList.add(vac7);*/
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
        if(!message.equals("")){
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.intent_message), message);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, fragment, tag);
        if(addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    /**
     * On fragment start, it will register for EventBus, a subscription Mechanism
     */
    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * On stop, it will stop getting updates from EventBus
     */
    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CustomMessageEvent event) {
        Log.d("HOMEFRAG EB RECEIVER", "Username :\"" + event.getCustomMessage() + "\" Successfully Received!");
        uId = event.getCustomMessage();
        //DisplayName.setText(usernameImported);

    }

}