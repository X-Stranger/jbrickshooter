package elements;

import values.Settings;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;

/**
 * This pane will contain scores and other useful elements that should be drawed over window components.
 * 
 * @author X-Stranger
 */
@SuppressWarnings("serial")
public class Corner extends JComponent {

    /** Settings instance. */
    private Settings settings;
    /** Double buffer image. */
    private Image buf = null;
    private Image backgr = null;

    /**
     * Default constructor.
     * 
     * @param settings - game settings
     */
    public Corner(Settings settings) {
        super();
        this.settings = settings;
    }
    
    /**
     * Getter for settings object.
     * 
     * @return Settings instance
     */
    protected Settings getSettings() {
        return settings;
    }

    /**
     * Getter for double-buffering image.
     * 
     * @return Image object
     */
    protected Image getBuf() {
        return buf;
    }

    /**
     * Setter for double-buffering image.
     * 
     * @param buf - new Image object
     */
    protected void setBuf(Image buf) {
        this.buf = buf;
    }

    /**
     * Method prepares double buffer for painting and returns buffer's Graphics object instance.
     *
     * @return double buffer Graphics instance to use for draw
     */
    protected Graphics prepareForPaint() {
        if (this.getBuf() == null) {
            this.setBuf(createImage(getWidth(), getHeight()));
        }
        if (this.backgr == null) {
            ImageIcon blackIcon = new ImageIcon(ClassLoader.getSystemResource("images/black.png"));
            Image black = blackIcon.getImage();
            int width = blackIcon.getIconWidth();
            int height = blackIcon.getIconHeight();
            this.backgr = createImage(width * 3, height * 3);
            Graphics g = this.backgr.getGraphics();
            g.drawImage(black, 0, 0, this);
            g.drawImage(black, 0, height, this);
            g.drawImage(black, 0, height * 2, this);
            g.drawImage(black, width, 0, this);
            g.drawImage(black, width * 2, 0, this);
            g.drawImage(black, width, height, this);
            g.drawImage(black, width * 2, height * 2, this);
            g.drawImage(black, width, height * 2, this);
            g.drawImage(black, width * 2, height, this);
        }

        // preparing black rectangle
        Graphics bufg = this.getBuf().getGraphics();
        bufg.drawImage(backgr, 0, 0, this);
        return bufg;
    }

    /**
     * Method draws component`s content.
     * 
     * @param g - Graphics instance to use for draw
     */
    public void paintComponent(Graphics g) {
        // draw background
        prepareForPaint();
        // draw it at right upper corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }
}