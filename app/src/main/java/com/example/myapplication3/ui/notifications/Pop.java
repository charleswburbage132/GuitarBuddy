package com.example.myapplication3.ui.notifications;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication3.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Pop extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView lvUsers;
    ListAdapter adapter;
    TextView text1;
    TextView text2;
    ImageView image;
    RatingBar rating;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width* .9),(int)(height* 1.2));


        Bundle bundle = getIntent().getExtras();
        String selected = bundle.getString("selected");
        Long id = bundle.getLong("id");

        dbHelper = new DatabaseHelper(getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lvUsers = findViewById(R.id.lvUsers);
        List<String> listUsers = dbHelper.getUser(selected);
        byte[] sound;
        text1 = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        image = findViewById(R.id.imageView3);
        rating = findViewById(R.id.ratingBar);



        Bitmap bitmap = dbHelper.getUserPicture(selected);



        if(listUsers != null){

            text1.setText(listUsers.get(0));
            text2.setText(listUsers.get(1));
            image.setImageBitmap(bitmap);
            rating.setRating(Float.parseFloat(listUsers.get(2)));
            if (bitmap == null){
                text2.setText("Empty");
            }

            /**adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    listUsers);
            lvUsers.setAdapter(adapter);*/
        }



    }
}
