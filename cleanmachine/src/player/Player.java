package player;

import player.music.Playlist;
import java.util.LinkedList;
import java.util.List;

public class Player {

    private final List<Playlist> list;

    /**
     * constructor for player
     */
    public Player() {
        list = new LinkedList<>();
    }

    /**
     * getter
     * @return list     list of playlists
     */
    public List<Playlist> getList() {
        return list;
    }

    /**
     *  returns the playlist that is currently
     *  being played in the player.
     */
    public Playlist getCurrentPlaylist() {
        //edit this method
        return new Playlist("s");
    }
}
