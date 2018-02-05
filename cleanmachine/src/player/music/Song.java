package player.music;

/**
 * Song object that will be changed depending on what the user wants with the song;
 */
public class Song {

    private final SongDefinition definition;

    private int currentTime; //current running time of the song

    /**
     * constructor
     * @param def    info about song
     * @param currentTime   running time
     */
    public Song(SongDefinition def, int currentTime) {
        this.definition = def;
        this.currentTime = currentTime;
    }

    /**
     * getter current run-time and timestamp of song
     * @return currentTime running time
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * setter, to play songs at certain times
     * @param currentTime   running time
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * volume method
     */
    public void setVolume() {
        //
    }

    /**
     * pause method
     */
    public void pause() {
        //
    }

    /**
     * play method
     */
    public void play() {
        //
    }

    /**
     * forward method, increase
     * time stamp 2X faster than usual
     */
    public void forward() {
        //
    }

    /**
     * rewind method, decrease
     * time stamp 2X faster than usual
     */
    public void rewind() {
        //
    }
}