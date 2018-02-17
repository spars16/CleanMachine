package gui;

import player.Player;
import player.music.Playlist;
import player.music.SongDefinition;

import javax.swing.*;

public abstract class SubPanel extends JPanel {

    protected MainPanel listener;
    protected Player player;

    public SubPanel(MainPanel listener, Player player) {
        this.listener = listener;
        this.player = player;
    }

    public void clickedPlay(SongDefinition definition) {
        propertyChange(EventType.PLAY, definition, this);
    }
    public <T extends SubPanel> void propertyChange(EventType type, Object object, T executor) {}
}
