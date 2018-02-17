package gui;

import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionPanel extends JPanel {

    public SelectionPanel(Player player) {

        //   this.player = player;
        setLayout(new BorderLayout());
        final JButton songs = makeLayoutButton("Songs");
        final JButton artists = makeLayoutButton("Artists");
        final JButton albums = makeLayoutButton("Albums");
        final JButton playlists = makeLayoutButton("Playlists");

        Action songListToggle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               songs.setText("yuh");
            }
        };

        songs.setAction(songListToggle);
/*
        final JLabel songs = new JLabel("Songs");
        final JLabel artists = new JLabel("Artists");
        final JLabel albums = new JLabel("Albums");
        final JLabel playlists = new JLabel("Playlists");
        //NOTE: need to add mouselisteners to these in order to access their lists
       (
*/
        final JPanel selectPanel = new JPanel();

        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(songs);
        selectPanel.add(artists);
        selectPanel.add(albums);
        selectPanel.add(playlists);

        add(selectPanel);
    }

    private JButton makeLayoutButton(String buttName)
    {
        final JButton next = new JButton(buttName);
        next.setBorderPainted(false);
        //imageUpdate(next, 0, 0, 40, 10);
        return next;

    }

}