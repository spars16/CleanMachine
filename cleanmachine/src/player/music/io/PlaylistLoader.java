package player.music.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import player.Player;
import player.music.Album;
import player.music.Playlist;
import player.music.SongDefinition;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PlaylistLoader implements FileLoader<Optional<Player>> {

    private static final Set<String> ACCEPTED_EXTENTIONS = new HashSet<>(Arrays.asList("mp4", "mp3", "wav"));
    private static final String DEFAULT_LOCATION = CACHE_LOCATION + "playlist/";


    /**
     * @inheritDoc
     */
    @Override
    public Optional<Player> load(String location) throws IOException {
        final File file = new File(location);
        if(!file.exists()) {
            return Optional.of(Player.empty());
        }

        final FileReader fileReader = new FileReader(file);
        final JSONParser parser = new JSONParser();
        final List<Playlist> playlistsList = new LinkedList<>();

        final Map<String, SongDefinition> locationToSong = new HashMap<>();

        Playlist currentPlaylist = null;
        try {

            //LOAD ALL SONGS
            JSONObject obj = (JSONObject)parser.parse(fileReader);
            JSONArray songs = (JSONArray)obj.get(SONG_LIBRARY);
            if(songs == null) {
                return Optional.of(Player.empty());
            }
            final List<SongDefinition> definitions = new LinkedList<>();

            for(int i = 0; i < songs.size(); i++) {
                String songFile = (String) songs.get(i);
                final SongDefinition definition = new SongDefinition(songFile);
                locationToSong.put(songFile, definition);
                definitions.add(definition);
            }

            final Playlist fullPlaylist = new Playlist("Song Library", definitions);
            currentPlaylist = fullPlaylist;
            playlistsList.add(fullPlaylist);

            //LOAD ALL STATIC PLAYLISTS
            JSONArray playlists = (JSONArray)obj.get(PLAYLISTS);
            if(playlists != null) {
                for (int i = 0; i < playlists.size(); i++) {
                    JSONObject playlistObj = (JSONObject) playlists.get(i);
                    final String name = playlistObj.get("name").toString();
                    JSONArray songDefsForPlaylist = (JSONArray) playlistObj.get("songfiles");

                    final List<SongDefinition> playlistSongDefs = new LinkedList<>();

                    for (int j = 0; j < songDefsForPlaylist.size(); j++) {
                        final String songFile = playlistSongDefs.get(j).toString();
                        playlistSongDefs.add(locationToSong.get(songFile));
                    }

                    final Playlist playlist = new Playlist(name, playlistSongDefs);
                    playlistsList.add(playlist);
                }
            }

            final String defaultDirectory = obj.getOrDefault("defaultDirectory", System.getProperty("user.home")).toString();


                //CREATE ALBUMS

            final Map<String, List<SongDefinition>> songsToAlbum = new HashMap<>();

            for (SongDefinition def : definitions) {
                final List<SongDefinition> list = songsToAlbum.getOrDefault(def.getAlbum(), new LinkedList<>());
                list.add(def);
            }

            for (Map.Entry<String, List<SongDefinition>> entry : songsToAlbum.entrySet()) {
                final Album album = new Album(entry.getKey(), entry.getValue());
                playlistsList.add(album);
            }

            final Player player = new Player(currentPlaylist, playlistsList);
            player.setDefaultDirectory(defaultDirectory);
            return Optional.of(player);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    private boolean isAcceptableExtention(final String fileName) {
        final String extention = fileName.substring(fileName.lastIndexOf("."));
        return ACCEPTED_EXTENTIONS.contains(fileName);
    }

}
