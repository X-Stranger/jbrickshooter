package elements;

import values.Settings;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

/**
 * This pane will display level info.
 * 
 * @author X-Stranger
 */
@SuppressWarnings("serial")
public class Level extends Corner {

    /**
     * Default constructor.
     * 
     * @param settings - game settings
     */
    public Level(Settings settings) {
        super(settings);
    }
    
    /**
     * Method draws component`s content.
     * 
     * @param g - Graphics instance to use for draw.
     */
    public void paintComponent(Graphics g) {
        // prepare double buffer
        Graphics bufg = prepareForPaint();
        
        // preparing variables
        String level = this.getSettings().getLevel().toString();
        
        // fill rectangle with scores
        Graphics2D g2 = (Graphics2D) bufg;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufg.setColor(Color.yellow);
        bufg.setFont(this.getSettings().getFont());
        FontMetrics fm = bufg.getFontMetrics();
        Rectangle2D area = fm.getStringBounds(level, bufg);
        bufg.drawString(level, (int) (getWidth() - area.getWidth()) / 2, 
                (int) (getHeight() + area.getHeight()) / 5 * 3);
        area = fm.getStringBounds(getSettings().getString("LEVEL"), bufg);
        bufg.drawString(getSettings().getString("LEVEL"), (int) (getWidth() - area.getWidth()) / 2, 
                (int) (getHeight() + area.getHeight()) / 5 * 2);
        
        // draw it at right upper corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }
}