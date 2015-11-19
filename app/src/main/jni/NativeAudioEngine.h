//
// Created by flowing erik on 18.11.15.
//

#ifndef FLOWAUDIOLAB_NATIVEAUDIOENGINE_H
#define FLOWAUDIOLAB_NATIVEAUDIOENGINE_H

static bool audioProcessing(void *clientdata, short int *audioInputOutput, int numberOfSamples, int samplerate);
// void Java_com_flowkey_flowaudiolab_MainActivity_NativeAudioEngine(JNIEnv *javaEnvironment, jobject self, jlong samplerate, jlong buffersize);

#endif //FLOWAUDIOLAB_NATIVEAUDIOENGINE_H
