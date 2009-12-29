package elements;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import javax.swing.ImageIcon;
import values.Settings;

/**
 * This pane will contain scores and other useful elements that should be drawed over window components.
 * 
 * @author X-Stranger
 */
@SuppressWarnings("serial")
public class Buttons extends Corner implements MouseListener {

    private Color color = Color.lightGray;
    private ImageIcon buttonNormal = new ImageIcon(this.getClass().getClassLoader().getResource("images/button.png"));
    private ImageIcon buttonOver = new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonover.png"));
    private ImageIcon button = buttonNormal;
    private Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private Cursor buttonCursor = new Cursor(Cursor.HAND_CURSOR);
    private int w, h, bw, bh;
    
    /**
     * Default constructor.
     * 
     * @param settings - game settings
     */
    public Buttons(Settings settings) {
        super(settings);
        this.addMouseListener(this);
    }
    
    /**
     * Method draws component`s content.
     * 
     * @param g - Graphics instance to use for draw.
     */
    public void paintComponent(Graphics g) {
        if (this.getBuf() == null) { 
            this.setBuf(createImage(getWidth(), getHeight())); 
            w = getWidth();
            h = getHeight();
            bw = button.getIconWidth();
            bh = button.getIconHeight();
        }
        
        // prepare double buffer
        Graphics bufg = prepareForPaint();

        // draw buttons 
        Graphics2D g2 = (Graphics2D) bufg;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        bufg.setFont(this.getSettings().getFont());
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout layout = new TextLayout(getSettings().getString("UNDO"), getSettings().getFont(), frc);

        bufg.setColor(this.color);
        Rectangle r = layout.getBounds().getBounds();
        bufg.drawImage(button.getImage(), (w - bw) / 2, (h - bh) / 2, this);
        bufg.drawString(getSettings().getString("UNDO"), (w - r.width) / 2, (h + r.height) / 2);

        // draw it at right upper corner
        g.drawImage(this.getBuf(), 0, 0, this);
    }

    /**
     * Called when mouse exited window.
     * 
     * @param e - mouse event object 
     */
    public void mouseExited(MouseEvent e) {
        setCursor(normalCursor);
        this.color = Color.lightGray;
        this.button = this.buttonNormal;
        this.repaint();
    }
     
    /**
     * Called when mouse entered window.
     * 
     * @param e - mouse event object 
     */
    public void mouseEntered(MouseEvent e) {
        setCursor(buttonCursor);
        this.color = Color.white;
        this.button = this.buttonOver;
        this.repaint();
    }
      
    /**
     * Called when mouse button clicked.
     * 
     * @param e - mouse event object 
     */
    public void mouseClicked(MouseEvent e) {
        getSettings().getUndo().doClick();
    }
     
    /**
     * Called when mouse button released.
     * 
     * @param e - mouse event object 
     */
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }
       
    /**
     * Called when mouse button pressed.
     * 
     * @param e - mouse event object 
     */
    public void mousePressed(MouseEvent e) {
        // do nothing
    }
}