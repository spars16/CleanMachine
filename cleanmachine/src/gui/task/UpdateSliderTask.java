package gui.task;

import player.Player;
import player.music.Song;
import player.music.SongDefinition;

import javax.swing.*;
import java.util.TimerTask;

public class UpdateSliderTask implements Runnable {

    private JLabel duration;
    private JSlider slider;
    private Player player;

    public UpdateSliderTask(Player player, JSlider slider, JLabel duration) {
        this.player = player;
        this.slider = slider;
        this.duration = duration;
    }

    @Override
    public void run() {
        while(true) {
            Song song = player.getCurrentSong();

            if (song == null)
                continue;

            if (slider.getValueIsAdjusting())
                continue;

            int i = (int) player.getCurrentSong().getMediaPlayer().getCurrentTime().toSeconds();

            slider.setValue(i);
            duration.setText(SongDefinition.durationMinutes(i));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
