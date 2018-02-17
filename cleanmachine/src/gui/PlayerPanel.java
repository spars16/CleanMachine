package gui;

import player.Player;
import player.Resource;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {

    private static final int X_BUTTON_WIDTH = 20;
    private static final int Y_BUTTON_HEIGHT = 15;
    private final Player player;
    /**
     * Create the main panel that contains the Player
     */
    public PlayerPanel(final Player player) {
        this.player = player;

        setLayout(new BorderLayout());

        // TOP PANEL
        final JButton next = createLayoutButton("forward.png", "forpressed.png");
        final JButton back = createLayoutButton("rewind.png", "rewindpressed.png");
        final JButton shuffle = createLayoutButton("shuffle.png", "shufflepressed.png");
        final JButton play = createLayoutButton("play.png", "playpressed.png", 40, 40);
        final JButton repeat = createLayoutButton("repeat.png", "repeatpressed.png", 20, 20);

        final JPanel topPanel = new JPanel();

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        topPanel.add(shuffle);
        topPanel.add(back);
        topPanel.add(play);
        topPanel.add(next);
        topPanel.add(repeat);

        add(topPanel);

        //BOTTOM PANEL
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        final JSlider songSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        songSlider.setPreferredSize(new Dimension(65, 20));

        final JLabel label = new JLabel("0:00");
        bottomPanel.add(label, BorderLayout.WEST);
        bottomPanel.add(songSlider, BorderLayout.CENTER);
        //bottomPanel.add(new JLabel(durationMinutes(player.getCurrentSong().getDefinition().getDuration()), BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private String durationMinutes(double d) {
        int p = (int)d;
        String str = (p/60) + ":" + (p%60);
        return str;
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

}
