package gui.impl;

import gui.*;
import gui.SubPanel;
import player.Player;
import player.music.Playlist;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends SubPanel {
    private final Playlist playlist;

    public CenterPanel(MainPanel listener, Player player, Playlist clickedPlaylist) {
        super(listener, player);
        setLayout(new BorderLayout());
        this.playlist = clickedPlaylist;

        java.util.List<SongDefinition> songDefList = playlist.getSongDefinitionList();

        for(int i=0; i < songDefList.size(); i++)
        {
            JPanel songPanel = songInfo(songDefList.get(i));
            add(songPanel);
            System.out.println("songpanel made");
        }

    }

    public JPanel songInfo(SongDefinition definition) {
        GridLayout gridLayout = new GridLayout(0,5);
        JButton playButt = new JButton();
        JPanel songDef = new JPanel(gridLayout);
        JLabel title = new JLabel(""+definition.getTitle());
        JLabel artist = new JLabel(""+definition.getArtist());
        JLabel album = new JLabel(""+definition.getArtist());
        JLabel duration = new JLabel(""+definition.getFormattedDuration());
        songDef.add(playButt);
        songDef.add(title);
        songDef.add(artist);
        songDef.add(album);
        songDef.add(duration);
        //  songDef.add(title, BorderLayout.WEST);
        //songDef.add(artist, BorderLayout.CENTER);
        return songDef;
    }




}
