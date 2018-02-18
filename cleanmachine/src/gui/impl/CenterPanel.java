package gui.impl;

import gui.*;
import gui.SubPanel;
import player.Player;
import player.music.Playlist;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CenterPanel extends SubPanel {
    private final Playlist playlist;

    public CenterPanel(MainPanel listener, Player player, Playlist clickedPlaylist) {
        super(listener, player);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playlist = clickedPlaylist;

        java.util.List<SongDefinition> songDefList = playlist.getSongDefinitionList();

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 7));

        for(String s : new String[]{"", "Title", "Artist", "Album", "Length", ""}) {
            final JLabel label = new JLabel(s);
            titlePanel.add(label);
        }

        add(titlePanel);
        for(int i=0; i < songDefList.size(); i++)
        {
            JPanel songPanel = songInfo(songDefList.get(i));
            add(songPanel);
            songPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    songPanel.setBackground(Color.WHITE);
                    songPanel.setForeground(Color.BLACK);
                    songPanel.revalidate();
                    songPanel.repaint();
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    songPanel.setBackground(Color.BLACK);
                    songPanel.setForeground(Color.WHITE);
                    songPanel.revalidate();
                    songPanel.repaint();
                    super.mouseExited(e);
                }
            });
            System.out.println("songpanel made");
        }

    }

    public JPanel songInfo(SongDefinition definition) {
        JButton playButt = PlayerPanel.createLayoutButton("play.png", "playpressed.png", 15, 15);
        JPanel songDef = new JPanel();
        songDef.setLayout(new GridLayout(1, 7));
        songDef.setPreferredSize(new Dimension(300, 15));

        JLabel title = new JLabel(""+definition.getTitle());
        JLabel artist = new JLabel(""+definition.getArtist());
        JLabel album = new JLabel(""+definition.getAlbum());
        JLabel duration = new JLabel(""+definition.getFormattedDuration());

        final GridBagConstraints constraints = new GridBagConstraints();


        duration.setSize(30, 15);

        final JButton add2Playlist = new JButton("Add");
        JComboBox<Playlist> box = new JComboBox<>();

        for(Playlist playlist : player.getPlaylists()) {
            box.addItem(playlist);
        }



        constraints.gridx = 0;
        constraints.gridy = 0;

        constraints.gridheight = 15;

        constraints.gridwidth = 25;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_END;
        songDef.add(playButt, constraints);
        constraints.gridwidth = 90;
        songDef.add(title, constraints);
        constraints.gridwidth = 60;
        songDef.add(artist, constraints);
        constraints.gridwidth = 50;
        songDef.add(album, constraints);
        constraints.gridwidth = 30;
        songDef.add(duration, constraints);
        constraints.gridwidth = 150;

        songDef.add(add2Playlist, constraints);
        songDef.add(box);

        //  songDef.add(title, BorderLayout.WEST);
        //songDef.add(artist, BorderLayout.CENTER);

        playButt.addActionListener((e) -> {
            player.setNewSong(definition);
            if(!player.getCurrentPlayerlist().equals(playlist)) {
                player.switchToPlaylist(playlist);
                listener.sendPropertyChange(EventType.SHUFFLE, player.getCurrentSong(), this);
            }
            listener.sendPropertyChange(EventType.PLAY_NEW, player.getCurrentSong(), this);


        });

        add2Playlist.addActionListener((e) -> {
            Playlist p = (Playlist)box.getSelectedItem();
            System.out.println("u are cool");
            if(!p.getSongDefinitionList().contains(definition))
                p.getSongDefinitionList().add(definition);
        });
        return songDef;
    }




}
