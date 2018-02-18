package gui.impl;

import gui.EventType;
import gui.MainPanel;
import gui.SubPanel;
import javafx.embed.swing.SwingFXUtils;
import player.Player;
import player.Resource;
import player.music.Song;
import player.music.SongDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class QueuePanel extends SubPanel {


    private java.util.List<QueueItem> items = new LinkedList<>();
    private JPanel queuedPanel;

    public QueuePanel(MainPanel listener, final Player player) {
        super(listener, player);
        setPreferredSize(new Dimension(300, 200));
        add(new JLabel());

        queuedPanel = queuePanel();

        this.add(queuedPanel);




    }

    public JPanel queuePanel() {
        final JPanel queuedPanel = new JPanel();
        queuedPanel.setLayout(new BoxLayout(queuedPanel, BoxLayout.Y_AXIS));

        queuedPanel.add(new JLabel("Queued Songs"));



        for(SongDefinition definition : player.getQueue()) {
            QueueItem item;
            queuedPanel.add(item = new QueueItem(definition));
            items.add(item);

        }

        final JScrollPane pane = new JScrollPane(queuedPanel);
        //queuedPanel.add(list);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(pane, BorderLayout.EAST);
        return queuedPanel;
    }

    @Override
    public <T extends SubPanel> void propertyChange(EventType type, Object object, T t) {
        switch(type) {
            case PLAY_NEW:
                for(QueueItem item : items) {
                    if(item.definition.equals(((Song)(object)).getDefinition())) {
                        item.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                    } else {
                        item.setBorder(null);
                    }

                    item.revalidate();
                    item.repaint();
                }
                break;
            case SHUFFLE:
                items.clear();
                this.remove(queuedPanel);
                this.add(queuedPanel = queuePanel());
                this.revalidate();
                this.repaint();
                break;
        }

    }

    private class QueueItem extends JPanel {

        private SongDefinition definition;

        public QueueItem(SongDefinition definition) {
            this.definition = definition;
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

            final JPanel exit = new JPanel();
            exit.setPreferredSize(new Dimension(25, 50));
            exit.setLayout(new GridLayout(3,1));
            final JButton exitButton = new JButton("x");
            exitButton.setBorderPainted(false);
            exitButton.setOpaque(false);
            exitButton.setContentAreaFilled(false);
            exit.add(exitButton);
            exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    exitButton.setForeground(Color.RED);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    exitButton.setForeground(Color.WHITE);
                }
            });
            exitButton.addActionListener((e) -> {
                player.getQueue().remove(definition);
                remove();
            });

            item.add(exit, BorderLayout.EAST);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2) {
                        player.setNewSong(definition);
                        listener.sendPropertyChange(EventType.PLAY_NEW, player.getCurrentSong(), QueuePanel.this);
                    }
                }
            });

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    for(Component item : new Component[]{item, infoPanel}) {

                        item.setBackground(Color.WHITE);
                        item.setForeground(Color.BLACK);
                        item.revalidate();
                        item.repaint();
                    }
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    for(Component item : new Component[]{item, infoPanel}) {
                        item.setBackground(Color.BLACK);
                        item.setForeground(Color.WHITE);
                        item.revalidate();
                        item.repaint();
                    }
                }
            });
        }

        public void remove() {
            final Container parent = this.getParent();
            parent.remove(this);
            parent.repaint();
            parent.revalidate();
            QueuePanel.this.revalidate();
            QueuePanel.this.repaint();
        }
    }
}
