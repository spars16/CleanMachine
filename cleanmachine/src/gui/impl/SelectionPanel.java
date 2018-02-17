package gui.impl;

import gui.MainPanel;
import gui.SubPanel;
import player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class SelectionPanel extends SubPanel {
    private CenterPanel centerPanel;

    /**
     *
     * @param player
     */
    public SelectionPanel(MainPanel listener, final Player player) {
        super(listener, player);
        //   this.player = player;
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(300, 400));

        final JLabel songs = new JLabel("Songs");
        final JLabel artists = new JLabel("Artists");
        final JLabel albums = new JLabel("Albums");
        final JButton add = new JButton("+");
        //NOTE: need to add mouselisteners to these in order to access their lists

        final JPanel selectPanel = new JPanel();

        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(add);
        selectPanel.add(songs);
        selectPanel.add(artists);
        selectPanel.add(albums);

        add(selectPanel, BorderLayout.WEST);
        add(new CenterPanel(listener, player, player.getCurrentPlayerlist()), BorderLayout.CENTER);

        add.addActionListener((e) -> player.addNewSong(choose()));
    }


    public File[] choose() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "MP3 & MP4 Audio", "mp3", "mp4");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new File(player.getDefaultDirectory()));
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            player.setDefaultDirectory(chooser.getCurrentDirectory().getAbsolutePath());
            return chooser.getSelectedFiles();
        }

        return new File[0];
    }

}