//
// Created by flowing erik on 10.12.15.
//

#ifndef FLOWAUDIOLAB_FLOWAUDIOENGINE_H
#define FLOWAUDIOLAB_FLOWAUDIOENGINE_H

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_AndroidConfiguration.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <android/log.h>
#include <climits>
#include "../Superpowered/SuperpoweredAndroidAudioIO.h"
#include "Filterbank.h"


class FlowAudioEngine {

private:
    SuperpoweredAndroidAudioIO audioIO;
    audioProcessingCallback audioProcessing();
    Filterbank filterbank;
    NoteDetection noteDetection;
    OnsetDetection onsetDetection;
public:
    FlowAudioEngine();
    ~FlowAudioEngine();
    void start();
    void stop();
};


#endif //FLOWAUDIOLAB_FLOWAUDIOENGINE_H
