package player.music.io;

import player.music.SongDefinition;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SongLoader implements FileLoader<SongDefinition> {

    private static final Set<String> ACCEPTED_EXTENTIONS = new HashSet<>(Arrays.asList("mp4", "mp3", "wav"));
    private static final String DEFAULT_LOCATION = CACHE_LOCATION + "songs/";


    /**
     * @inheritDoc
     */
    @Override
    public SongDefinition load(String fileName) throws IOException {

        if(!isAcceptableExtention(fileName)) {
            throw new IllegalArgumentException("Illegal extention - it should be filtered before it gets to this point!");
        }

        final File file = new File(DEFAULT_LOCATION + fileName);

        //load data via song library

        return new SongDefinition("", "", 0);
    }

    private boolean isAcceptableExtention(final String fileName) {
        final String extention = fileName.substring(fileName.lastIndexOf("."));
        return ACCEPTED_EXTENTIONS.contains(fileName);
    }

}
