package com.flowkey.flowaudiolab.AudioEngine;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.flowkey.flowaudiolab.R;
import com.flowkey.flowaudiolab.VolumeMeter.VolumeBar;

/**
 * Created by erik on 13.11.15.
 */
public class AudioEngine/* extends Thread */{

    private static final String TAG = "AudioEngine";

    private final int SAMPLE_RATE;
    private final int CHANNEL_CONFIG;
    private final int AUDIO_FORMAT;
    private final int BUFFER_SIZE;
    private final int BUFFER_SIZE_IN_BYTES;
    private short[] audioBuffer;

    private double currentRMS = 0;

    public double getCurrentRMS() {
        return currentRMS;
    }

    private VolumeBar volumeBar;

    private AudioRecord recorder;

    public AudioEngine(VolumeBar volumeBar) {

        this.AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
        this.CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
        this.SAMPLE_RATE = 44100;
        // this.BUFFER_SIZE_IN_BYTES = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        this.BUFFER_SIZE_IN_BYTES = 4096;
        this.BUFFER_SIZE = BUFFER_SIZE_IN_BYTES / 2;

        //ToDo: for higher api versions, also float[] can be used for higher precision (maybe not that important)
        this.audioBuffer = new short[this.BUFFER_SIZE];

        // this.setPriority(MIN_PRIORITY);

        this.initRecorder();


        this.volumeBar = volumeBar;

    }

    private void initRecorder(){
        try {
            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_IN_BYTES);
            if (recorder.getState() != AudioRecord.STATE_INITIALIZED) {
                throw new Exception("AudioRecord initialization failed");
            }
            recorder.setPositionNotificationPeriod(this.BUFFER_SIZE);
            recorder.setRecordPositionUpdateListener(this.updateListener);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.d(TAG, e.getMessage());
            } else {
                Log.d(TAG, "Unknown error occured while initializing recording");
            }
        }
    }


//    @Override
//    public void run(){
//        recorder.startRecording();
//        while (isRecording()) {
//            dspCallback();
//        }
//    }

    private boolean isRecording(){
        return recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING;
    }

    private void dspCallback(){
        recorder.read(audioBuffer, 0, BUFFER_SIZE);
        currentRMS = calculateRMS(audioBuffer);
        Log.d(TAG, Double.toString(currentRMS));
        volumeBar.setVolume((float)currentRMS);
                volumeBar.invalidate();
    }

    public void startRunning(){
        this.recorder.startRecording();
        Log.d(TAG, "started running, state: " + Integer.toString(this.recorder.getRecordingState()));
    }

    public void stopRunning(){
        this.recorder.stop();
        Log.d(TAG, "stopped running, state: " + Integer.toString(this.recorder.getRecordingState()));
    }

/*    private void publishProgress(int randNum) {
        Log.v(TAG, "reporting back from the Random Number Thread");
        final String text = String.format(getString(R.string.service_msg), randNum);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateResults(text);
            }
        });
    }*/



    //implement interface: AudioRecord.OnRecordPositionUpdateListener
    private AudioRecord.OnRecordPositionUpdateListener updateListener = new AudioRecord.OnRecordPositionUpdateListener() {

        public void onPeriodicNotification(AudioRecord recorder) {
            dspCallback();
        }

        public void onMarkerReached(AudioRecord recorder) {
            Log.d(TAG, "onMarkerReached");
        }
    };

    private double calculateRMS(short[] numbers){
        double t = 0;
        for (int i = 0; i < numbers.length; i++) {
            t = t + Math.pow(numbers[i],2);
        }
        return Math.sqrt(t / BUFFER_SIZE);
    }
}



