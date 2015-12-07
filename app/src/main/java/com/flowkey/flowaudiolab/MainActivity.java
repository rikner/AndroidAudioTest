package com.flowkey.flowaudiolab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import com.flowkey.flowaudiolab.AudioEngine.AudioEngine;
import com.flowkey.flowaudiolab.VolumeMeter.VolumeBar;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("NativeAudioEngine"); // Load native library at runtime
    }

    private static final String TAG = "MainActivity";

    Button startButton = null;
    Button stopButton = null;
    VolumeBar volumeBar = null;

    // private AudioEngine audioEngine = null;

    private native void NativeAudioEngine(long samplerate, long buffersize);
    private native void startNativeAudioEngine();
    private native void stopNativeAudioEngine();

    Handler audioHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            volumeBar.updateVolume((float) msg.arg1);
        }
    };

    private void callbackFromNative(float volume){
        Log.d(TAG, "native code executing callback with volume: " + Float.toString(volume));
        Message volumeMsg = audioHandler.obtainMessage(0, (int)Math.round(volume), 0);
        audioHandler.sendMessage(volumeMsg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        // audioEngine = new AudioEngine(audioHandler);

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "App starting.");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "App resuming.");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "App pausing.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        // audioEngine.stopRunning();
        Log.d(TAG, "App stopping,");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "App destroying.");
    }



    private void initUI(){
        // Set the user interface layout for this Activity (defined in the project under res/layout/activity_main.xml file)
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // audioEngine.startRunning();

                long sampleRate = 44100;
                long bufferSize = 1024;

                Log.d(TAG, "About to execute Native Audio Engine");
                NativeAudioEngine(sampleRate, bufferSize);

                startNativeAudioEngine();
            }
        });


        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopNativeAudioEngine();
                // audioEngine.stopRunning();
            }
        });

        volumeBar = (VolumeBar) findViewById(R.id.volumeBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
