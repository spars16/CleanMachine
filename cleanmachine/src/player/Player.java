package player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import player.music.Playlist;
import player.music.Song;
import player.music.SongDefinition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Player implements Constants {


    private RepeatState repeatState = RepeatState.NO_REPEAT;
    private Stack<SongDefinition> previous;
    private Song currentSong;
    private Playlist mainPlaylist;
    private LinkedList<SongDefinition> songQueue;
    private final List<Playlist> playlists;
    private Playlist currentPlaylist;
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

    public List<SongDefinition> getQueue() {
        return songQueue;
    }

    public void init() {
        for(SongDefinition definition : currentPlaylist.getSongDefinitionList()) {
            songQueue.add(definition);
        }
    }

    public boolean next() {
        if(currentSong != null) {
            previous.push(currentSong.getDefinition());
        }

        if(repeatState.equals(RepeatState.REPEAT_SONG)) {
            setNewSong(currentSong.getDefinition());
            return true;
        }
        SongDefinition definition = songQueue.poll();
        if(definition == null) {
            return false;
        }

        if(repeatState.equals(RepeatState.REPEAT_PLAYLIST)) {
            songQueue.add(currentSong.getDefinition());
        }
        setNewSong(definition);
        return true;
    }

    public boolean previous() {
        if(currentSong != null) {
            songQueue.push(currentSong.getDefinition());
        }

        try {
            final SongDefinition definition = previous.pop();
            setNewSong(definition);
        } catch (EmptyStackException exp) {
            return false;
        }
        return true;

    }

    public void setNewSong(SongDefinition def) {
        if(currentSong != null) {
            currentSong.stop();
        }
        final Song song = new Song(def);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentSong = song;
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

    public RepeatState nextRepeatState() {
        return (repeatState = repeatState.nextState());
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

    public Playlist addNewPlaylist(final String name) {
        Playlist list;
        playlists.add(list = new Playlist(name, new LinkedList<>()));
        save();
        return list;
    }

    public void switchToPlaylist(Playlist playlist) {
        reset();

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
            playlists_array.add(playlist_obj);
        }

        object.put(PLAYLISTS, playlists_array);

        object.put("defaultDirectory", defaultDirectory == null ? System.getProperty("user.home") : defaultDirectory);

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


    public enum RepeatState {
        NO_REPEAT("repeat.png"),
        REPEAT_PLAYLIST("repeatpressed.png"),
        REPEAT_SONG("repeatpressed2.png");

        public final String file;
        RepeatState(String file) {
            this.file = file;
        }

        private static final RepeatState[] values = values();

        public RepeatState nextState() {
            return values[(this.ordinal() + 1)%values.length];
        }
    }
}