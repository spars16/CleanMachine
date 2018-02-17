package player.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Song object that will be changed depending on what the user wants with the song;
 */
public class Song {

    private final SongDefinition definition;

    private final Media media;
    private final MediaPlayer mediaPlayer;


    public Song(SongDefinition definition) {
        this.definition = definition;

        media = new Media((new File(definition.getLocation()).toURI().toString()));
        mediaPlayer = new MediaPlayer(media);
    }

    public void start() {
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> stop());
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public double getCurrentTime() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    public void setCurrentTime(double currentTime) {
        mediaPlayer.seek(Duration.seconds(currentTime));
    }

    public void stop() {
        mediaPlayer.dispose();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public SongDefinition getDefinition() {
        return definition;
    }
}
