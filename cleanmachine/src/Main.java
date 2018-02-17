import gui.PlayerPanel;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.*;
import player.Resource;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);

                try {
                    SubstanceLookAndFeel.setSkin(new RavenSkin());
                } catch (Exception e) {
                    System.out.println("Substance Graphite failed to initialize");
                }

                final JFrame frame = new JFrame("Player");

                frame.setUndecorated(true);

                frame.setIconImage(Resource.loadImage("CleanMachineIcon.png"));
                frame.setContentPane(new PlayerPanel(null));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });


    }
}
