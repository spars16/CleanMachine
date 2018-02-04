package player.music;

/**
 * Song object that will be changed depending on what the user wants with the song;
 */
public class Song {

    private final SongDefinition definition;

    //current running time of the song
    private int currentTime;

    public Song(SongDefinition definition, int currentTime) {
        this.definition = definition;
        this.currentTime = currentTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }


}
