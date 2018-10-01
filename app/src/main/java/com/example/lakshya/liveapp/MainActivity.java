package com.example.lakshya.liveapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button play;
    Boolean prepared = false;
    Boolean started = false;
    String stream = "http://stream.radioreklama.bg:80/radio1rock128";
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button)findViewById(R.id.play);
        play.setEnabled(false);
        play.setText("Loading");
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        new PlayerTask().execute(stream);
    }

    public void stream1(View view)
    {
        if(started)
        {
            started = false;
            mp.pause();
            play.setText("Play");
        }
        else{
            started = true;
            mp.start();
            play.setText("Pause");
        }
    }

    public void stream2(View view)
    {

    }

    public void stream3(View view)
    {

    }

    class PlayerTask extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(String... strings)
        {
            try {
                mp.setDataSource(strings[0]);
                mp.prepare();
                prepared = true;
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //mp.start();
            play.setEnabled(true);
            play.setText("Play");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(started)
        {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(started)
        {
            mp.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prepared)
        {
            mp.release();
        }
    }
}

