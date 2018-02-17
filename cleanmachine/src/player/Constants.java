package player;

public interface Constants {

    String CACHE_LOCATION = System.getProperty("user.home") + "/CleanMachine/";
    String CONFIG_FILE_NAME = "config.json";
    String CONFIG_FILE_PATH = CACHE_LOCATION + CONFIG_FILE_NAME;

    String SONG_LIBRARY = "songlibrary";
    String PLAYLISTS = "playlists";
}
