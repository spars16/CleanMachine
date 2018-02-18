package player.music;

import player.Player;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Playlist {

    private List<SongDefinition> songDefinitionList;
    private String name;

    public Playlist(String name, List<SongDefinition> songDefinitionList) {
        this.name = name;
        this.songDefinitionList = songDefinitionList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SongDefinition> getSongDefinitionList() {
        return songDefinitionList;
    }

    public void setSongDefinitionList(List<SongDefinition> songDefinitionList) {
        this.songDefinitionList = songDefinitionList;
    }

    @Override
    public String toString() {
        return name;
    }
}
