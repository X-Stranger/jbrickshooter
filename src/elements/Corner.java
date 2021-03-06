package elements;

import basic.Layout;
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
    private int width = 0;
    private int height = 0;

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
        if ((getWidth() != this.width) || (getHeight() != this.height)) {
            this.setBuf(null);
            this.backgr = null;
            this.width = getWidth();
            this.height = getHeight();
        }

        if (this.getBuf() == null) {
            this.setBuf(createImage(getWidth(), getHeight()));
        }
        if (this.backgr == null) {
            int width = Settings.getBrickSize();
            int height = Settings.getBrickSize();

            Image black = new ImageIcon(this.getClass().getClassLoader().getResource("images/black.png")).getImage();
            this.backgr = createImage(width * Layout.CORNER, height * Layout.CORNER);

            Graphics g = this.backgr.getGraphics();
            for (int i = 0; i < Layout.CORNER; i++) {
                for (int j = 0; j < Layout.CORNER; j++) {
                    g.drawImage(black, i * width, j * height, width, height, this);
                }
            }
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
        // draw it at an appropriate corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }
}