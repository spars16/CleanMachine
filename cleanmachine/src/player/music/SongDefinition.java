package player.music;

/**
 * Static definition of loaded song
 * Immutable class
 */
public class SongDefinition {

    //private AudioSomething audio
    public final String title;
    public final String artist;
    public final String album;
    public final int duration;

    /**
     * Information of song
     * @param title     song title
     * @param artist    artist of song
     * @param album     album of song
     * @param duration  duration of song
     */
    public SongDefinition(final String title, final String artist, final String album, final int duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
}