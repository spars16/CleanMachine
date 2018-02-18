import com.sun.javafx.application.PlatformImpl;
import gui.MainPanel;
import javafx.embed.swing.JFXPanel;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.*;
import player.Constants;
import player.Player;
import player.Resource;
import player.music.io.PlaylistLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Toolkit.getDefaultToolkit();
        new JFXPanel();
        Thread.sleep(3000);
        PlatformImpl.startup(() -> {
            final PlaylistLoader loader = new PlaylistLoader();
            try {
                final Optional<Player> player = loader.load(Constants.CONFIG_FILE_PATH);
                player.ifPresent((p) -> {
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
                            frame.setPreferredSize(new Dimension(1100, 550));
                            frame.setContentPane(new MainPanel(p));
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }
                    });
                });
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
    }

}
