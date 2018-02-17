package player;

import player.music.Playlist;
import player.music.Song;
import player.music.SongDefinition;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player {

    private Song currentSong;
    private Queue<SongDefinition> songQueue;
    private final List<Playlist> playlists;
    private Playlist currentPlaylist;
    private boolean loop, shuffle;


    public Player( final Playlist currentPlaylist, final List<Playlist> playlists) {
        this.playlists = playlists;
        this.currentPlaylist = currentPlaylist;

    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getCurrentPlayerlist() {
        return currentPlaylist;
    }

    public boolean next() {
        if(currentSong != null)
            currentSong.stop();
        final SongDefinition definition = songQueue.poll();
        if(definition == null) {
            return false;
        }
        final Song song = new Song(definition);
        song.start();
        //song.getMediaPlayer().setOnEndOfMedia(this::next);

        return true;
    }

    public void pause() {
        currentSong.pause();
    }

    public Song getCurrentSong() {
        return currentSong;
    }











}
