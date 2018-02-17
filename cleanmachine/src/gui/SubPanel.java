package gui;

import player.Player;
import player.music.Playlist;

import javax.swing.*;

public abstract class SubPanel extends JPanel{

    protected MainPanel listener;
    protected Player player;

    public SubPanel(MainPanel listener, Player player) {
        this.listener = listener;
        this.player = player;
    }
}
