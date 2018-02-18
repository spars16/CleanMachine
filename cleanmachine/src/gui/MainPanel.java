package gui;

import gui.impl.PlayerPanel;
import gui.impl.QueuePanel;
import gui.impl.SelectionPanel;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel {

    private final PlayerPanel playerPanel;
    private final SelectionPanel selectionPanel;
    private final QueuePanel queuePanel;

    public MainPanel(Player player) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(850, 500));
        add(playerPanel = new PlayerPanel(this, player), BorderLayout.SOUTH);
        add(selectionPanel = new SelectionPanel(this, player), BorderLayout.CENTER);
        add(queuePanel = new QueuePanel(this, player), BorderLayout.EAST);


    }

    public void sendPropertyChange(EventType type, Object object, SubPanel executor) {
        for(SubPanel panel : new SubPanel[]{playerPanel, selectionPanel, queuePanel}) {
            panel.propertyChange(type, object, executor);
        }
    }

}
