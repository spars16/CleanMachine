package player.music;
import java.util.ArrayList;

public class Playlist {
    public String name;
    private final ArrayList<Song> songs;

    /**
     * constructor
     * @param name  name of playlist
     */
    public Playlist(String name) {
        this.name = name;
        songs = new ArrayList<Song>();
    }

    /**
     * adds to internal song queue of the playlist
     * @param s     song to be added
     */
    public void add(Song s) {
        songs.add(s);
    }

    /**
     * name field not final bc it should be able
     * to be changed
     * @param name  playlist name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * next method
     */
    public void next() {
        //
    }

    /**
     * previous method
     */
    public void previous() {
        //
    }

    /**
     * shuffle method
     */
    public ArrayList shuffle() {
        // edit this method
        return songs;
    }
}