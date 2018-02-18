package gui.impl;

import gui.EventType;
import gui.MainPanel;
import gui.SubPanel;
import gui.task.TaskManager;
import gui.task.UpdateSliderTask;
import player.Player;
import player.Resource;
import player.music.Song;
import player.music.SongDefinition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;

public class PlayerPanel extends SubPanel {

    private boolean paused = true;

    private java.util.Timer schedule;
    private static final int X_BUTTON_WIDTH = 20;
    private static final int Y_BUTTON_HEIGHT = 15;
    private final JButton play = createLayoutButton("play.png", "playpressed.png", 40, 40);
    private final JSlider songSlider = new JSlider(JSlider.HORIZONTAL, 0, 100,0);
    private final JLabel timeRunning = new JLabel("0:00");
    private final JLabel durationLabel = new JLabel("0:00");


    /**
     * Create the main panel that contains the Player
     */
    public PlayerPanel(MainPanel listener, final Player player) {
        super(listener, player);

        setLayout(new BorderLayout());

        // TOP PANEL

        final JButton next = createLayoutButton("forward.png", "forpressed.png");
        final JButton back = createLayoutButton("rewind.png", "rewindpressed.png");
        final JButton shuffle = createLayoutButton("shuffle.png", "shufflepressed.png", 20, 20);
        final JButton repeat = repeatButton(20, 20);
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

        bottomPanel.add(timeRunning, BorderLayout.WEST);
        bottomPanel.add(songSlider, BorderLayout.CENTER);
        bottomPanel.add(durationLabel, BorderLayout.EAST);
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

        });

        next.addActionListener((e) -> {
            if(player.next()) {
                listener.sendPropertyChange(EventType.NEXT, player.getCurrentSong(), this);
                listener.sendPropertyChange(EventType.PLAY_NEW, player.getCurrentSong(), this);
            }
        });

        back.addActionListener((e) -> {
            if(player.previous()) {
                listener.sendPropertyChange(EventType.PREVIOUS, player.getCurrentSong(), this);
                listener.sendPropertyChange(EventType.PLAY_NEW, player.getCurrentSong(), this);
            }
        });

        songSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int seconds = songSlider.getValue();
                Song song = player.getCurrentSong();
                if(song != null) {
                    song.setCurrentTime(seconds);
                }
            }
        });

        shuffle.addActionListener((e) -> {
            player.shuffle();
            listener.sendPropertyChange(EventType.SHUFFLE, player.getCurrentSong(), this);
        });


        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(topPanel);
        add(panel, BorderLayout.CENTER);

        TaskManager.getManager().submitTask(new UpdateSliderTask(player, songSlider, timeRunning));

    }



    public static JButton createLayoutButton(String icon, String hoverIcon, int width, int height) {
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

    private JButton repeatButton(int width, int height) {
        final ImageIcon original = Resource.loadImageIcon(width, height, Player.RepeatState.NO_REPEAT.file);
        final ImageIcon hover1 = Resource.loadImageIcon(width, height, Player.RepeatState.REPEAT_PLAYLIST.file);
        final JButton repeat = new JButton(original);

        repeat.setBorderPainted(false);
        //next.setBorder(null);
        repeat.setMargin(new Insets(0, 0, 0, 0));
        repeat.setContentAreaFilled(false);
        repeat.setRolloverIcon(hover1);
        repeat.setPressedIcon(hover1);

        repeat.addActionListener((e) -> {
            Player.RepeatState r = player.nextRepeatState();
            repeat.setIcon(Resource.loadImageIcon(width, height, r.file));
            ImageIcon hover;
            repeat.setRolloverIcon(hover = Resource.loadImageIcon(width, height, r.nextState().file));
            repeat.setPressedIcon(hover);
        });

        return repeat;
    }

    private JButton createLayoutButton(String icon, String hoverIcon) {
        return createLayoutButton(icon, hoverIcon, X_BUTTON_WIDTH, Y_BUTTON_HEIGHT);
    }

    private void recalibrateSlider() {
        int seconds = (int)player.getCurrentSong().getDefinition().getDuration();
        durationLabel.setText(SongDefinition.durationMinutes(seconds));
        songSlider.setMaximum(seconds);
        songSlider.setValue(0);



    }

    @Override
    public <T extends SubPanel> void propertyChange(EventType type, Object obj, T t) {
        switch (type) {
            case PLAY:
                //CHANGE TO PAUSE BUTTON
                player.getCurrentSong().play();
                paused = false;
                break;
            case PAUSE:
                //CHANGE TO PLAY BUTTON
                player.getCurrentSong().pause();
                paused = true;
                break;
            case PLAY_NEW:
                recalibrateSlider();
                player.getCurrentSong().play();
                paused = false;
                break;
        }
    }

}