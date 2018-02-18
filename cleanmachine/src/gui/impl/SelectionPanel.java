package gui.impl;

import gui.EventType;
import gui.MainPanel;
import gui.SubPanel;
import player.Player;
import player.music.Playlist;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.function.Function;

public class SelectionPanel extends SubPanel {
    private JScrollPane centerPanel;

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
        final JLabel playlists = new JLabel("PlayLists");
        final JButton add = new JButton("Add Song");
        final JButton addPlaylist = new JButton("Add Playlist");


        final DefaultListModel<Playlist> playlistsData = new DefaultListModel<>();

        player.getPlaylists().stream().forEach(playlistsData::addElement);


        JList<Playlist> playlistList = new JList<>(playlistsData);


        final JPanel selectPanel = new JPanel();
        JScrollPane scrollSelect = new JScrollPane(selectPanel);

        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(add);
       // selectPanel.add(songs);
       // selectPanel.add(artists);
       // selectPanel.add(albums);
        selectPanel.add(addPlaylist);
        selectPanel.add(playlists);
        selectPanel.add(playlistList);

        add(selectPanel, BorderLayout.WEST);
        add(new CenterPanel(listener, player, player.getCurrentPlayerlist()), BorderLayout.CENTER);

        add.addActionListener((e) -> {
            player.addNewSong(choose());
        });

        addPlaylist.addActionListener((ActionEvent e) ->
        {
            Playlist list = player.addNewPlaylist(JOptionPane.showInputDialog("Please Input Playlist Name"));
            playlistsData.addElement(list);
            playlistList.updateUI();

        });
        centerPanel = new JScrollPane(new CenterPanel(listener, player, player.getCurrentPlayerlist()));

        playlistList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Playlist change = playlistList.getSelectedValue();
                remove(centerPanel);
                centerPanel = new JScrollPane(new CenterPanel(listener, player, change));
                add(centerPanel);
                SelectionPanel.this.revalidate();
                SelectionPanel.this.repaint();
            }
        });
        add(centerPanel, BorderLayout.CENTER);

    }

    public File[] choose() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "MP3 & MP4 Audio", "mp3", "mp4");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        if(player.getDefaultDirectory() != null)
            chooser.setCurrentDirectory(
                new File(player.getDefaultDirectory()));
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            player.setDefaultDirectory(chooser.getCurrentDirectory().getAbsolutePath());
            return chooser.getSelectedFiles();
        }

        return new File[0];
    }
/*
    public boolean addPlaylistGui()
    {
        String newName = JOptionPane.showInputDialog("Please Input Playlist Name");
        //player.addNewPlayList(newName);
        return true;
    }
*/

}