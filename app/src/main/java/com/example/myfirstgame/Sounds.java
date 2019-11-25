package com.example.myfirstgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds {

    private static SoundPool soundPool;
    private static int sonidoAcierto;
    private static int sonidoDesacierto;

    public Sounds(Context context){

        //SoundPool (int maxStreams,int streamsType,int srcQuality)
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        sonidoAcierto = soundPool.load(context,R.raw.barrido,1);
        sonidoDesacierto = soundPool.load(context,R.raw.over,1);
    }

    public void playSonidoAcierto(){

        soundPool.play(sonidoAcierto,1.0f,1.0f,1,0,1.0f);

    }

    public void playSonidoDesacierto(){

        soundPool.play(sonidoDesacierto,1.0f,1.0f,1,0,1.0f);
    }

}
