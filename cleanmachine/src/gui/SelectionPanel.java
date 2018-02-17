package gui;

import player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SelectionPanel extends JPanel {
    private CenterPanel centerPanel;

    /**
     *
     * @param player
     */
    public SelectionPanel(Player player) {
        //   this.player = player;
        setLayout(new BorderLayout());

        final JLabel songs = new JLabel("Songs");
        final JLabel artists = new JLabel("Artists");
        final JLabel albums = new JLabel("Albums");
        //NOTE: need to add mouselisteners to these in order to access their lists

        final JPanel selectPanel = new JPanel();

        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(songs);
        selectPanel.add(artists);
        selectPanel.add(albums);

        add(selectPanel, BorderLayout.EAST);
        add(new CenterPanel(player, player.getCurrentPlayerlist()), BorderLayout.CENTER);
    }

/*
    private JButton makeLayoutButton(string buttName)
    {
        final JButton next = new JButton(buttName);
        imageUpdate(next, 0, 0, 40, 10);
        return next;

    }
*/

}