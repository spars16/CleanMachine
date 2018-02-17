package gui.impl;

import gui.MainPanel;
import gui.SubPanel;
import player.Player;

import javax.swing.*;
import java.awt.*;

public class QueuePanel extends SubPanel {

    public QueuePanel(MainPanel listener, final Player player) {
        super(listener, player);
        setPreferredSize(new Dimension(150, 400));
        add(new JLabel());
    }
}
