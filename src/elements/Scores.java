package elements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import values.Settings;

/**
 * This pane will display scores info.
 * 
 * @author X-Stranger
 */
@SuppressWarnings("serial")
public class Scores extends Corner {

    /**
     * Default constructor.
     * 
     * @param settings - game settings
     */
    public Scores(Settings settings) {
        super(settings);
    }
    
    /**
     * Method draws component`s content.
     * 
     * @param g - Graphics instance to use for draw.
     */
    public void paintComponent(Graphics g) {
        if (this.getBuf() == null) { this.setBuf(createImage(getWidth(), getHeight())); }
        
        // preparing variables
        String scores = this.getSettings().getScores().toString();
        
        // preparing black rectangle
        Graphics bufg = this.getBuf().getGraphics();
        bufg.setColor(Color.black);
        bufg.fillRect(0, 0, getWidth(), getHeight());
        
        // fill rectangle with scores
        Graphics2D g2 = (Graphics2D) bufg;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufg.setColor(Color.yellow);
        bufg.setFont(this.getSettings().getFont());
        FontMetrics fm = bufg.getFontMetrics();
        Rectangle2D area = fm.getStringBounds(scores, bufg);
        bufg.drawString(scores, (int) (getWidth() - area.getWidth()) / 2, 
                (int) (getHeight() + area.getHeight()) / 5 * 3);
        area = fm.getStringBounds(getSettings().getString("SCORES"), bufg);
        bufg.drawString(getSettings().getString("SCORES"), (int) (getWidth() - area.getWidth()) / 2, 
                (int) (getHeight() + area.getHeight()) / 5 * 2);
        
        // draw it at right upper corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }
}