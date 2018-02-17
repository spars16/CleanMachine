package player.music;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.File;
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

        Executors.newSingleThreadExecutor().submit(() -> {
            final Media media = new Media((new File(location)).toURI().toString());
            media.getMetadata().addListener((MapChangeListener<String, Object>) (listener) -> {
                if (listener.wasAdded()) {
                    System.out.println(listener.getKey() + " " + listener.getValueAdded());
                    SongDefinition sd = SongDefinition.this;
                    switch (listener.getKey().toLowerCase()) {
                        case "raw metadata":
                            break;
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
            final MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> duration = media.getDuration().toSeconds());


        });
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
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
}