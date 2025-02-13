package com.Communication;

import com.sun.speech.freetts.*;

public class Speach {

    Voice spk;
    public static String KEVIN = "kevin16";
    String speech;

    public Speach(String s) {
        this.speech = s;
    }

    public void speak(String voice) {
        VoiceManager vm = VoiceManager.getInstance();
        spk = vm.getVoice(voice);
        spk.allocate();
        spk.speak(speech);
    }
    public static void main(String[] args) {
        new Speach("Welcome to student management system").speak(KEVIN);
    }

}
