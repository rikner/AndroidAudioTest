//
// Created by flowing erik on 18.11.15.
//

#include "NativeAudioEngine.h"

#define  LOG_TAG    "NATIVE_AUDIO_ENGINE"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

static SuperpoweredAndroidAudioIO *audioIO;
float *inputBufferFloat;

// JNI related stuff
JNIEnv *jenv;
jmethodID java_callback;
jclass main_activity;
jobject thiz;
JavaVM* vm;

float calculateRMS(float *audioFrame, int size) {
    float total = 0.0f;
    for (int i=0; i<size; i++) {
        total += audioFrame[i] * audioFrame[i];
    }
    return sqrt(total / size);
}

float calculateRMS(short int *audioFrame, int size){
    float total = 0.0f;
    for (int i=0; i<size; i++) {
        total += (float)(audioFrame[i] * audioFrame[i]);
    }
    return sqrt(total / size);
}

float linearToDecibel(float value, float ref){
    return 20*log10(value / ref);
}


float decibelToPercentage(float decibel){
    float value = 1 - (decibel / -72);
    return fmax(0.1f, value) * 100;
}

static bool audioProcessing(void *clientdata, short int *audioInputOutput, int numberOfSamples, int samplerate) {

    vm->AttachCurrentThread(&jenv, 0);

    // convert to float with superpowered and calculate volume
    // SuperpoweredShortIntToFloat(audioInputOutput, inputBufferFloat, numberOfSamples); // Converting the 16-bit integer samples to 32-bit floating point.
    // float volume = decibelToPercentage(linearToDecibel(calculateRMS(inputBufferFloat, numberOfSamples), 1));

    // calculate volume from short int buffer
    float volume = decibelToPercentage(linearToDecibel(calculateRMS(audioInputOutput, numberOfSamples), SHRT_MAX));

    // LOGI("audioProcessing , volume: %f", volume);

    jenv->CallVoidMethod(thiz, java_callback, volume);

    return true;
}

extern "C" {
    void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize);
}

JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize) {

    audioIO = new SuperpoweredAndroidAudioIO(samplerate, buffersize, true, false, audioProcessing, NULL, SL_ANDROID_STREAM_MEDIA, buffersize * 2); // Start audio input/output.
    inputBufferFloat = (float *)malloc(buffersize * sizeof(float) * 2 + 128);

    //catch jni environment for later use
    jenv = env;
    thiz = env->NewGlobalRef(self);

    env->GetJavaVM(&vm);

    // First get the class that contains the method you need to call
    main_activity = jenv->GetObjectClass(self);

    if (main_activity == 0) {
        LOGI("GetObjectClass error");
        return;
    }

    // Get the method that you want to call
    java_callback = jenv->GetMethodID(main_activity, "callbackFromNative", "(F)V");

    // Call the method on the objecte
    jenv->CallVoidMethod(self, java_callback, 0);
}


JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_startNativeAudioEngine(JNIEnv *env, jobject self){
    audioIO->start();
}

JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_stopNativeAudioEngine(JNIEnv *env, jobject self){
    audioIO->stop();
}