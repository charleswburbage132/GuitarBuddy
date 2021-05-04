package com.example.myapplication3.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication3.MainActivity;
import com.example.myapplication3.R;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    DatabaseHelper dbHelper;
    ListView lvUsers;
    ListAdapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        dbHelper = new DatabaseHelper(getContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lvUsers = (ListView)root.findViewById(R.id.lvUsers);
        TextView listHead = new TextView(getContext());
        Spinner filters = new Spinner(getContext());

        String[] items = new String[]{"No Filter", "Open Chords", "Barre Chords", "Power Chords"};

        final ArrayAdapter[] adapter = {new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items)};

        filters.setAdapter(adapter[0]);
        filters.setPadding(580,20,10,20);


        listHead.setTextSize(30);
        listHead.setPadding(260,0,0,20);
        listHead.setText("All Chords");

        lvUsers.addHeaderView(listHead);
        lvUsers.addHeaderView(filters);
        List<String> listUsers = dbHelper.getAllUsers();
        String selectedFilter;

        if(listUsers != null){
            adapter[0] = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    listUsers);
            lvUsers.setAdapter(adapter[0]);
        }
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**-----------TEST-------------------*/
                Intent i = new Intent(NotificationsFragment.this.getActivity(),Pop.class);
                String selected = (String) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("selected",selected);
                i.putExtras(bundle);

                startActivity(i);
            }
        });
        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                if (selected == "Open Chords"){
                    List<String> listUsersF = dbHelper.getFilteredUsers(selected);

                    if(listUsersF != null){
                        adapter[0] = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1,
                                listUsersF);
                        lvUsers.setAdapter(adapter[0]);
                    }

                    listHead.setText("Open Chords");
                }
                else if (selected == "Barre Chords"){
                    List<String> listUsersF = dbHelper.getFilteredUsers(selected);

                    if(listUsersF != null){
                        adapter[0] = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1,
                                listUsersF);
                        lvUsers.setAdapter(adapter[0]);
                    }

                    listHead.setText("Barre Chords");
                }
                else if (selected == "Power Chords"){
                    List<String> listUsersF = dbHelper.getFilteredUsers(selected);

                    if(listUsersF != null){
                        adapter[0] = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1,
                                listUsersF);
                        lvUsers.setAdapter(adapter[0]);
                    }

                    listHead.setText("Power Chords");
                }

                else if (selected == "No Filter"){
                    List<String> listUsersF = dbHelper.getAllUsers();

                    if(listUsersF != null){
                        adapter[0] = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1,
                                listUsersF);
                        lvUsers.setAdapter(adapter[0]);
                    }

                    listHead.setText("All Chords");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return root;
    }
}