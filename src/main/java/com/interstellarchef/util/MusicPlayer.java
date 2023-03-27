package com.interstellarchef.util;

import java.io.BufferedInputStream;
import javax.sound.sampled.*;

public class MusicPlayer {
  private Clip clip;
  private FloatControl volume;
  private boolean musicPlaying;
  private String scarySong = "scarysong.wav";
  private String happySong = "happysong.wav";
  private String currentSong = happySong;

  public void play() {
    try {
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(currentSong)));
      clip = AudioSystem.getClip();
      clip.open(audioInput);
      clip.start();
      volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      volume.setValue(-5f);
      musicPlaying = true;
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    if (clip != null) {
      clip.stop();
      clip.close();
    }
  }

  public void toggleMusic(){
    if(musicPlaying){
      stop();
      musicPlaying = false;
    }else{
      play();
    }
  }

  public void volumeDown() {
    if (volume != null) {
      volume.setValue(-10f);
    }
  }

  public void volumeUp(){
    float newVolume = volume.getValue()+ 10f;
    volume.setValue(Math.min(newVolume, volume.getMaximum()));
  }

  public String getScarySong() {
    return scarySong;
  }

  public void setScarySong(String scarySong) {
    this.scarySong = scarySong;
  }

  public String getHappySong() {
    return happySong;
  }

  public void setHappySong(String happySong) {
    this.happySong = happySong;
  }

  public String getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(String currentSong) {
    this.currentSong = currentSong;
  }
}
