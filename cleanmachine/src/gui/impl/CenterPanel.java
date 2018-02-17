package gui.impl;

import gui.MainPanel;
import gui.SubPanel;
import player.Player;
import player.music.Playlist;
import player.music.SongDefinition;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends SubPanel {
    private final Playlist playlist;

    public CenterPanel(MainPanel listener, Player player, Playlist clickedPlaylist) {
        super(listener, player);
        setLayout(new BorderLayout());
        this.playlist = clickedPlaylist;
        add(new JLabel());
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
