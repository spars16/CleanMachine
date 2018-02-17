package gui;

import player.Player;
import player.music.Playlist;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private final Player player;
    private final Playlist playlist;

    public CenterPanel(Player player, Playlist clickedPlaylist) {
        this.player = player;
        this.playlist = clickedPlaylist;
    }

    public JPanel songInfo(SongDefinition definition) {
        JPanel songDef = new JPanel();
        JLabel title = new JLabel(""+definition.getTitle());
        JLabel artist = new JLabel(""+definition.getArtist());
        songDef.add(title, BorderLayout.WEST);
        songDef.add(artist, BorderLayout.CENTER);
        return songDef;
    }


}
