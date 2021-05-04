package com.example.myapplication3.ui.home;

import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.media.SoundPool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.myapplication3.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });

        /**-----------------------START----------------------------*/
        Button button = root.findViewById(R.id.button);
        Button buttonUp = root.findViewById(R.id.buttonUp);
        Button buttonDown = root.findViewById(R.id.buttonDown);
        Button buttonStop = root.findViewById(R.id.button2);
        TextView txtOut = root.findViewById(R.id.txtOutput);
        SeekBar inBPM = root.findViewById(R.id.seekBar);
        SoundPool sp = new SoundPool.Builder() .build();
        String f = "tick";
        int soundId = sp.load(getActivity(), R.raw.tick , 1);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                /** float Bpm = Float.parseFloat(editBPM.getEditableText().toString());*/
                float Bpm = Float.parseFloat(String.valueOf(inBPM.getProgress()));
                float out = 60 / Bpm;
                float timeDelay = 1000 * out;
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        sp.play(soundId, 1, 1, 1, 0, 2);
                    }
                };
                /**txtOut.setText(String.valueOf(timeDelay));*/
                timer.scheduleAtFixedRate(task,0, (long) timeDelay);

                buttonStop.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
                buttonStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setVisibility(View.VISIBLE);
                        buttonStop.setVisibility(View.INVISIBLE);
                        timer.cancel();
                    }
                });
            }
        });

        inBPM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtOut.setText(String.valueOf(progress) + " bpm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inBPM.setProgress(inBPM.getProgress()-1);
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inBPM.setProgress(inBPM.getProgress()+1);
            }
        });




        return root;
    }
}