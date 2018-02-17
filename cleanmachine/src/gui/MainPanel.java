package gui;

import gui.impl.PlayerPanel;
import gui.impl.QueuePanel;
import gui.impl.SelectionPanel;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel implements PropertyChangeListener {

    public MainPanel(Player player) {
        setLayout(new BorderLayout());

        add(new PlayerPanel(this, player), BorderLayout.SOUTH);
        add(new SelectionPanel(this, player), BorderLayout.CENTER);
        add(new QueuePanel(this, player), BorderLayout.EAST);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


}
