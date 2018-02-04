package player.music;

/**
 * Static definition of loaded song
 * Immutable class
 */
public class SongDefinition {

    //private AudioSomething audio
    public final String title;
    public final String artist;
    public final int duration;

    public SongDefinition(final String title, final String artist, final int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }



}
