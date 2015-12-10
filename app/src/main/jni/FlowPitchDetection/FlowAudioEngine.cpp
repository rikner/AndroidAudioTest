//
// Created by flowing erik on 10.12.15.
//

#include "FlowAudioEngine.h"

FlowAudioEngine::FlowAudioEngine(samplerate, buffersize) {

    audioIO = new SuperpoweredAndroidAudioIO(samplerate, buffersize, true, false, audioProcessing, NULL, SL_ANDROID_STREAM_MEDIA, buffersize * 2);
}