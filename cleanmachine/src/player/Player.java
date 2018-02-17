package player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import player.music.Playlist;
import player.music.Song;
import player.music.SongDefinition;

import javax.management.RuntimeErrorException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Player implements Constants {

    private Stack<SongDefinition> previous;
    private Song currentSong;
    private Playlist mainPlaylist;
    private Queue<SongDefinition> songQueue;
    private final List<Playlist> playlists;
    private Playlist currentPlaylist;
    private boolean loop;
    private String defaultDirectory;


    public Player( final Playlist currentPlaylist, final List<Playlist> playlists) {
        this.playlists = playlists;
        this.currentPlaylist = currentPlaylist;
        this.mainPlaylist = currentPlaylist;
        this.songQueue = new LinkedList<>();
        this.previous = new Stack<>();

        init();

    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getCurrentPlayerlist() {
        return currentPlaylist;
    }

    public void init() {
        for(SongDefinition definition : currentPlaylist.getSongDefinitionList()) {
            songQueue.add(definition);
        }
    }

    public boolean next() {
        if(currentSong != null) {
            previous.push(currentSong.getDefinition());
            currentSong.stop();
        }
        final SongDefinition definition = songQueue.poll();
        if(definition == null) {
            return false;
        }
        final Song song = new Song(definition);
        song.start();
        //song.getMediaPlayer().setOnEndOfMedia(this::next);
        if(loop) {
            songQueue.add(currentSong.getDefinition());
        }

        currentSong = song;
        return true;
    }

    public boolean previous() {
        if(currentSong != null) {
            currentSong.stop();
            ((LinkedList<SongDefinition>)songQueue).push(currentSong.getDefinition());
        }

        return true;

    }

    public void pause() {
        if(currentSong != null)
            currentSong.pause();
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void shuffle() {
        reset();
        final List<SongDefinition> list = new LinkedList<>(currentPlaylist.getSongDefinitionList());
        Collections.shuffle(list);

        for(SongDefinition def : list) {
            songQueue.add(def);
        }
    }

    public void clickLoop() {
        loop = !loop;
    }

    public boolean isLooping() {
        return loop;
    }

    private void reset() {
        songQueue.clear();
        previous.clear();
    }

    public void addNewSong(final File[] files) {
        final File dir = new File(CACHE_LOCATION);
        if(!dir.exists())
            dir.mkdirs();

        for(File f : files) {
            final String newPath = CACHE_LOCATION + f.getName();
            final File file = new File(newPath);
            if(file.exists())
                continue;
            try {
                Files.copy(f.toPath(), (new File(newPath)).toPath());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            final SongDefinition definition = new SongDefinition(file.getAbsolutePath());
            mainPlaylist.getSongDefinitionList().add(definition);
            if (mainPlaylist.equals(currentPlaylist)) {
                songQueue.add(definition);
            }
        }

        save();
    }

    public void addSongToPlaylist(Playlist playlist, SongDefinition definition) {
        playlist.getSongDefinitionList().add(definition);
        save();
    }

    public void switchToPlaylist(Playlist playlist) {
        songQueue.clear();

        for(SongDefinition definition : playlist.getSongDefinitionList()) {
            songQueue.add(definition);
        }
        currentPlaylist = playlist;
    }

    private void save() {
        final JSONObject toSave = getSaveObject();
        BufferedWriter writer = null;
        final File config = new File(CONFIG_FILE_PATH);
        try {
            if(!config.exists()) {
                    config.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(config));
            writer.write(toSave.toJSONString());
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private JSONObject getSaveObject() {
        JSONObject object = new JSONObject();
        JSONArray song_array = new JSONArray();
        for(SongDefinition definition : mainPlaylist.getSongDefinitionList()) {
            song_array.add(definition.getLocation());
        }
        object.put(SONG_LIBRARY, song_array);

        JSONArray playlists_array = new JSONArray();
        for(Playlist playlist : playlists) {
            if(playlist.equals(mainPlaylist)) //we don't want to save main playlist, as it is generated by song_library
                continue;
            JSONObject playlist_obj = new JSONObject();
            playlist_obj.put("name",playlist.getName());
            JSONArray song_files = new JSONArray();
            for(SongDefinition definition : playlist.getSongDefinitionList()) {
                song_files.add(definition);
            }

            playlist_obj.put("songfiles", song_files);
        }

        object.put("defaultDirectory", defaultDirectory);

        return object;

    }

    public static Player empty() {
        final Playlist main = new Playlist("Song Library", new LinkedList<>());
        final LinkedList<Playlist> playlists = new LinkedList<>();
        playlists.add(main);
        return new Player(main, playlists);
    }

    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }
}