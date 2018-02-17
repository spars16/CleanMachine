package gui;

import player.Player;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel(Player player) {
        setLayout(new BorderLayout());

        add(new PlayerPanel(player), BorderLayout.SOUTH);
        add(new SelectionPanel(player), BorderLayout.CENTER);
        add(new QueuePanel(player), BorderLayout.WEST);



    }

}
