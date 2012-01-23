package elements;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Class holds game frame and its configuration.
 *
 * @author X-Stranger
 */
public class Frame extends JFrame {
    /**
     * Default constructor.
     *
     * @param title - frame title
     */
   public Frame(String title) {
        super(title);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

