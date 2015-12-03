//
// Created by flowing erik on 25.11.15.
//

#ifndef FLOWAUDIOLAB_NATIVEAUDIOENGINE_H
#define FLOWAUDIOLAB_NATIVEAUDIOENGINE_H

#endif //FLOWAUDIOLAB_NATIVEAUDIOENGINE_H


//
// Created by flowing erik on 18.11.15.
//
#include <jni.h>
#include "Superpowered/SuperpoweredAndroidAudioIO.h"
#include <stdlib.h>
#include <stdio.h>
#include <android/log.h>
#include <android/log.h>


// static SuperpoweredAndroidAudioIO *audioIO;
// static float *inputBufferFloat;

static bool audioProcessing(void *clientdata, short int *audioInputOutput, int numberOfSamples, int samplerate);

extern "C" {
    void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize);
}

JNIEXPORT void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *env, jobject self, jlong samplerate, jlong buffersize);

static double calculateRMS(float *input, int numberOfSamples);