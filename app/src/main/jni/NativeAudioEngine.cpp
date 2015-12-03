//
// Created by flowing erik on 18.11.15.
//
#include <jni.h>
#include "Superpowered/SuperpoweredAndroidAudioIO.h"
#include <stdlib.h>
#include <stdio.h>
#include <android/log.h>
#include "NativeAudioEngine.h"

#define  LOG_TAG    "NATIVE_AUDIO_ENGINE"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

static SuperpoweredAndroidAudioIO *audioIO;
static float *inputBufferFloat;


static double calculateRMS(float *input, int numberOfSamples){

    return 0.01;
}

static bool audioProcessing(void *clientdata, short int *audioInputOutput, int numberOfSamples, int samplerate) {
    // SuperpoweredShortIntToFloat(audioInputOutput, inputBufferFloat, numberOfSamples); // Converting the 16-bit integer samples to 32-bit floating point.
    // double rms = calculateRMS(audioInputOutput, numberOfSamples);
    return true;
}

extern "C" {
    void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize);
}

JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize) {
    __android_log_write(ANDROID_LOG_INFO, " JNI CODE ", " Executing Native Audio Engine");
    audioIO = new SuperpoweredAndroidAudioIO(samplerate, buffersize, true, true, audioProcessing, NULL, buffersize * 2); // Start audio input/output.

    // First get the class that contains the method you need to call
    // jclass main_activity = env->FindClass("com/flowkey/flowaudiolab/MainActivity");
    jclass main_activity = env->GetObjectClass(self);

    if (main_activity == 0) {
        LOGI("FindClass error");
        return;
    }

    // Get the method that you want to call
    jmethodID javaCallback = env->GetMethodID(main_activity, "callbackFromNative", "(D)V");

    // Construct a String
    // jstring jstr = env->NewStringUTF("This string comes from JNI");

    // Call the method on the objecte
    env->CallVoidMethod(self, javaCallback, 123.123);
    // jdouble rms = 123.123;
    // env->CallVoidMethod(self, javaCallback, rms);

    // const char* str = env->GetStringUTFChars((jstring) result, NULL); // should be released but what a heck, it's a tutorial :)
    // printf("%s\n", str);
}