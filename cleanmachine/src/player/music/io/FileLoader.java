package player.music.io;

import org.json.simple.JSONObject;
import player.Constants;
import java.io.IOException;

public interface FileLoader<T> extends Constants {

    /**
     * @param fileName - file name relative to location inside of /CleanMachine
     * @return - contents of file parsed into an object
     * @throws IOException - loading the file, should be handled by the upper layer - unexpected
     */
    T load(final String fileName) throws IOException;


}
