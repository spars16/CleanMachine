package gui.impl;

import com.sun.jmx.remote.security.JMXPluggableAuthenticator;
import gui.MainPanel;
import gui.SubPanel;
import javafx.embed.swing.SwingFXUtils;
import player.Player;
import player.Resource;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QueuePanel extends SubPanel {

    public QueuePanel(MainPanel listener, final Player player) {
        super(listener, player);
        setPreferredSize(new Dimension(300, 600));
        add(new JLabel());

        final JLabel queue = new JLabel("Queued Songs");

        final JPanel queuedPanel = new JPanel();

        queuedPanel.setLayout(new BoxLayout(queuedPanel, BoxLayout.Y_AXIS));

        queuedPanel.add(queue);



        for(SongDefinition definition : player.getQueue()) {
            queuedPanel.add(new QueueItem(definition));
        }

        final JScrollPane pane = new JScrollPane(queuedPanel);
        //queuedPanel.add(list);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(new JScrollPane(queuedPanel), BorderLayout.EAST);



    }

    private class QueueItem extends JPanel {
        public QueueItem(SongDefinition definition) {
            final QueueItem item = QueueItem.this;
            item.setLayout(new BorderLayout());

            Image image = null;
            if(definition.getImage() != null) {
                image = SwingFXUtils.fromFXImage(definition.getImage(), null);
                //item.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
            } else {
                image = Resource.loadImage(50, 50, "CleanMachineIcon.png");
            }

            final JLabel imageLabel =  new JLabel(new ImageIcon(image));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10,15));
            item.add(imageLabel, BorderLayout.WEST);

            final JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            System.out.println(definition.getArtist());
            infoPanel.add(new JLabel(definition.getTitle()));
            infoPanel.add(new JLabel(definition.getArtist()));
            infoPanel.add(new JLabel(definition.getFormattedDuration()));
            //item.add();
            item.add(infoPanel);
        }
    }
}
