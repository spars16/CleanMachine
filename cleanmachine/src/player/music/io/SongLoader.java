package player.music.io;

import player.music.SongDefinition;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SongLoader implements FileLoader<SongDefinition> {

    private static final List<String> EXTENTIONS = Arrays.asList(".mp4", ".mp3", ".wav");
    private static final String DEFAULT_LOCATION = CACHE_LOCATION + "songs/";


    /**
     * @inheritDoc
     */
    @Override
    public SongDefinition load(String fileName) throws IOException {
        final File file = new File(DEFAULT_LOCATION + fileName);

        //load data via song library

        return new SongDefinition("", "", 0);
    }

}
