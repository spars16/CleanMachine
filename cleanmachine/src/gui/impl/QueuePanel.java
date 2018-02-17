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



        for(SongDefinition definition : player.getQueue()) {
            queuedPanel.add(new QueueItem(definition));
        }


        //queuedPanel.add(list);
        add(queuedPanel, BorderLayout.EAST);



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

            if(definition.getImage() != null) {
                Image image = SwingFXUtils.fromFXImage(definition.getImage(), null);
                //item.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
            }

            System.out.println(definition.getArtist());
            item.add(new JLabel(definition.getTitle()));
            item.add(new JLabel(definition.getArtist()), BorderLayout.EAST);
            //item.add();
        }
    }
}
