package gui.impl;

import gui.EventType;
import gui.MainPanel;
import gui.SubPanel;
import player.Player;
import player.Resource;
import player.music.Song;
import player.music.SongDefinition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class PlayerPanel extends SubPanel {

    private boolean paused = true;

    private static final int X_BUTTON_WIDTH = 20;
    private static final int Y_BUTTON_HEIGHT = 15;
    private final JButton play = createLayoutButton("play.png", "playpressed.png", 40, 40);
    private final JSlider songSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);


    /**
     * Create the main panel that contains the Player
     */
    public PlayerPanel(MainPanel listener, final Player player) {
        super(listener, player);

        setLayout(new BorderLayout());

        // TOP PANEL

        final JButton next = createLayoutButton("forward.png", "forpressed.png");
        final JButton back = createLayoutButton("rewind.png", "rewindpressed.png");
        final JButton shuffle = createLayoutButton("shuffle.png", "shufflepressed.png");
        final JButton repeat = createLayoutButton("repeat.png", "repeatpressed.png", 20, 20);
        final JPanel topPanel = new JPanel();

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        topPanel.add(shuffle);
        topPanel.add(back);
        topPanel.add(play);
        topPanel.add(next);
        topPanel.add(repeat);
        //topPanel.add(Box.createVerticalBox());
        //add(topPanel);

        //BOTTOM PANEL
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        songSlider.setPreferredSize(new Dimension(65, 20));

        final JLabel label = new JLabel("0:00");
        bottomPanel.add(label, BorderLayout.WEST);
        bottomPanel.add(songSlider, BorderLayout.CENTER);
        if(player.getCurrentSong() != null)
            bottomPanel.add(new JLabel(player.getCurrentSong().getDefinition().getFormattedDuration()), BorderLayout.EAST);
        else
            bottomPanel.add(new JLabel("0:00"), BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);


        //BUTTON FUNCTIONALITY

        play.addActionListener((e) -> {
            if(player.getCurrentSong() == null) { //if no current song, load next one
                if(!player.next())
                    return;
                else {//play next song
                    player.getCurrentSong().play();
                    paused = false;
                    return;
                }
            }
            if(paused) {
                listener.sendPropertyChange(EventType.PLAY, player.getCurrentSong(), this);
            } else {
                listener.sendPropertyChange(EventType.PAUSE, player.getCurrentSong(), this);
            }

            paused = !paused;
        });

        next.addActionListener((e) -> {
            if(player.next())
                listener.sendPropertyChange(EventType.PLAY, player.getCurrentSong(), this);
        });

        back.addActionListener((e) -> {
            if(player.previous())
                listener.sendPropertyChange(EventType.PLAY, player.getCurrentSong(), this);
        });

        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(topPanel);
        add(panel, BorderLayout.CENTER);

    }



    private JButton createLayoutButton(String icon, String hoverIcon, int width, int height) {
        final ImageIcon original;
        final JButton next = new JButton(original = Resource.loadImageIcon(width, height, icon));
        final ImageIcon hover = Resource.loadImageIcon(width, height, hoverIcon);
        next.setBorderPainted(false);
        //next.setBorder(null);
        next.setMargin(new Insets(0, 0, 0, 0));
        next.setContentAreaFilled(false);
        next.setRolloverIcon(hover);
        next.setPressedIcon(original);
        return next;
    }

    private JButton createLayoutButton(String icon, String hoverIcon) {
        return createLayoutButton(icon, hoverIcon, X_BUTTON_WIDTH, Y_BUTTON_HEIGHT);
    }

    @Override
    public <T extends SubPanel> void propertyChange(EventType type, Object obj, T t) {
        switch (type) {
            case PLAY:
                //CHANGE TO PAUSE BUTTON
                player.getCurrentSong().play();
                break;
            case PAUSE:
                //CHANGE TO PLAY BUTTON
                player.getCurrentSong().pause();
                break;
        }
    }

}