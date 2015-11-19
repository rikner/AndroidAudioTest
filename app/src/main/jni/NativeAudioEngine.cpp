//
// Created by flowing erik on 18.11.15.
//
#include <jni.h>
#include "Superpowered/SuperpoweredAndroidAudioIO.h"
#include <stdlib.h>
#include <stdio.h>
#include <android/log.h>

static SuperpoweredAndroidAudioIO *audioIO;

static bool audioProcessing(void *clientdata, short int *audioInputOutput, int numberOfSamples, int samplerate) {
    return true;
}

extern "C" {
    void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *javaEnvironment, jobject self, jlong samplerate, jlong buffersize);
}

JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *javaEnvironment, jobject self, jlong samplerate, jlong buffersize) {
    audioIO = new SuperpoweredAndroidAudioIO(samplerate, buffersize, true, true, audioProcessing, NULL, buffersize * 2); // Start audio input/output.
}