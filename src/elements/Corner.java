package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;
import values.Settings;

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
     * Method draws component`s content.
     * 
     * @param g - Graphics instance to use for draw.
     */
    public void paintComponent(Graphics g) {
        if (this.getBuf() == null) { this.setBuf(createImage(getWidth(), getHeight())); }
        
        // preparing black rectangle
        Graphics bufg = this.getBuf().getGraphics();
        bufg.setColor(Color.black);
        bufg.fillRect(0, 0, getWidth(), getHeight());
        
        // draw it at right upper corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }
}