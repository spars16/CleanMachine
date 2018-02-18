package player.music;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import player.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongDefinition {

    private String album, artist, title;
    private double duration;
    private int year;
    private String location;
    private Image image;

    public SongDefinition(final String location) {

        this.location = location;
        System.out.println("Loc: "+location);
        Executors.newSingleThreadExecutor().submit(() -> {
            final Media media = new Media((new File(location)).toURI().toString());
            media.getMetadata().addListener((MapChangeListener<String, Object>) (listener) -> {
                if (listener.wasAdded()) {
                    System.out.println(listener.getKey() + " " + listener.getValueAdded());
                    SongDefinition sd = SongDefinition.this;
                    switch (listener.getKey().toLowerCase()) {
                        case "album":
                            sd.album = listener.getValueAdded().toString();
                            break;
                        case "title":
                            sd.title = listener.getValueAdded().toString();
                            break;
                        case "artist":
                            sd.artist = listener.getValueAdded().toString();
                            break;
                        case "year":
                            sd.year = Integer.parseInt(listener.getValueAdded().toString());
                            break;
                        case "image":
                            sd.image = (Image) listener.getValueAdded();
                            break;
                    }
                }
                //mediaPlayer.dispose();

            });


        });
        try {
            AudioFile f = AudioFileIO.read(new File(location));
            this.duration = f.getAudioHeader().getTrackLength();
        } catch (IOException|TagException|ReadOnlyFileException|InvalidAudioFrameException|CannotReadException e) {
            e.printStackTrace();
        }


    }

    public String getAlbum() {
        return album == null ? "N/A" : album;
    }

    public String getArtist() {
        return artist == null ? "Unknown" : artist;
    }

    public String getTitle() {
        return title == null ? ((new File(location)).getName()) : title;
    }

    public double getDuration() {
        return duration;
    }

    public String getFormattedDuration() {
        return durationMinutes(duration);
    }

    public int getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }

    public Image getImage() {
        return image;
    }

    public static String durationMinutes(double d) {
        int p = (int)d;
        final StringBuilder builder = new StringBuilder();

        builder.append(p/60);
        final int seconds = p%60;
        builder.append(":");
        if(seconds < 10) {
            builder.append("0").append(seconds);
        } else
            builder.append(seconds);
        return builder.toString();
    }
}