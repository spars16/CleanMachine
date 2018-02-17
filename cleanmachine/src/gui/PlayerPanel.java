package gui;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import player.Player;
import player.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {

    private static final int X_BUTTON_WIDTH = 35;
    private static final int Y_BUTTON_HEIGHT = 20;

    private final Player player;
    /**
     * Create the main panel that contains the Player
     */
    public PlayerPanel(final Player player) {
        this.player = player;

        setLayout(new BorderLayout());

        final JButton next = createLayoutButton("forward.png", "forpressed.png");
        final JButton back = createLayoutButton("rewind.png", "rewindpressed.png");
        final JButton play = createLayoutButton("play.png", "playhover.png", 50, 50);

        final JPanel topPanel = new JPanel();

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        topPanel.add(back);
        topPanel.add(play);
        topPanel.add(next);


        add(topPanel);
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
