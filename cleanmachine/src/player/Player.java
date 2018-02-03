package player;

import java.util.LinkedList;
import java.util.List;

public class Player {


    public class Person {
        public final String name = "hello";

        public String getName() {
            return name;
        }
    }
    private final List<Playlist> list;


    public Player() {
        list = new LinkedList<>();

        list.add(new Playlist());


    }

    public List<Playlist> getList() {
        return list.subList(0, list.size());
    }

    public void newPlaylist(String s) {
        final Playlist playlist = new Playlist();

    }


}
