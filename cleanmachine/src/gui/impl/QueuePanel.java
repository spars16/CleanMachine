package gui.impl;

import gui.MainPanel;
import gui.SubPanel;
import javafx.embed.swing.SwingFXUtils;
import player.Player;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QueuePanel extends SubPanel {

    public QueuePanel(MainPanel listener, final Player player) {
        super(listener, player);
        setPreferredSize(new Dimension(150, 400));
        add(new JLabel());

        final JLabel queue = new JLabel("Queued Songs");

        final JPanel queuedPanel = new JPanel();

        queuedPanel.setLayout(new BoxLayout(queuedPanel, BoxLayout.Y_AXIS));

        queuedPanel.add(queue);

        add(queuedPanel, BorderLayout.EAST);

        JList list = new JList();

        //for(SongDefinition definition : player.getCurrentPlayerlist()) {

        //}

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);


    }
    private void mouseClicked(java.awt.event.MouseEvent mouse) {
        JList list = (JList)mouse.getSource();
        if (mouse.getClickCount() == 2) {
            int index = list.locationToIndex(mouse.getPoint());
            System.out.println("index: "+index);
            list.ensureIndexIsVisible(index);
        }
    }

    private class QueueItem extends JPanel {
        public QueueItem(SongDefinition definition) {
            final QueueItem item = QueueItem.this;
            item.setLayout(new BorderLayout());

            Image image = SwingFXUtils.fromFXImage(definition.getImage(), null);

            //item.add();
        }
    }
}
