package gui.impl;

import gui.MainPanel;
import gui.SubPanel;
import player.Player;
import player.music.Playlist;

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
        final JLabel playlists = new JLabel("PlayLists");
        final JButton add = new JButton("+");
        final JButton addPlaylist = new JButton("add playlist");

        JList<String> playlistList = new JList();
        final java.util.List<player.music.Playlist> tempList = player.getPlaylists();
        String playlistArray[] = new String[tempList.size()];
        for(int i=0;i < tempList.size();i++)
        {
           // playlistList.addElement((tempList.get(i)).getName());
            playlistArray[i] = tempList.get(i).getName();
        }
        playlistList.setListData(playlistArray);

        final JPanel selectPanel = new JPanel();
        JScrollPane scrollSelect = new JScrollPane(selectPanel);

        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(add);
        selectPanel.add(songs);
        selectPanel.add(artists);
        selectPanel.add(albums);
        selectPanel.add(playlists);
        selectPanel.add(playlistList);

        add(selectPanel, BorderLayout.WEST);
        add(new CenterPanel(listener, player, player.getCurrentPlayerlist()), BorderLayout.CENTER);

        add.addActionListener((e) -> player.addNewSong(choose()));

        addPlaylist.addActionListener((e) -> player.addNewPlaylist(JOptionPane.showInputDialog("Please Input PlaylistName")));


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