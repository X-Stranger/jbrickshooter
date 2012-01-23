package basic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import values.Orientation;

import java.awt.Graphics;

/**
 * Class describes work with Orietation.
 * 
 * @author X-Stranger
 */
public class Oriented extends JLabel {
    private static final long serialVersionUID = 7942933490474531365L;
    
    /** Brick orientation. */
    private Orientation orientation;
    
    /**
     * Default constructor.
     */
    public Oriented() {
        super();
    }
    
    /**
     * Overridden constructor for compatibility with Brick.
     * 
     * @param icon - ImageIcon object
     */
    public Oriented(ImageIcon icon) {
        super(icon);
    }
    
    /**
     * Returns collection orientation.
     * 
     * @return Orientation value
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * Sets new brick orientation.
     * 
     * @param orientation - new Orientation value
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Changes brick orientation clockwise.
     */
    public void rotate() {
        switch (this.orientation) {
            case BOTTOM:
                this.setOrientation(Orientation.LEFT); break;
            case LEFT:
                this.setOrientation(Orientation.TOP); break;
            case TOP:
                this.setOrientation(Orientation.RIGHT); break;
            case RIGHT:
                this.setOrientation(Orientation.BOTTOM); break;
            default:
        }
    }

    /**
     * Method draws component`s content.
     *
     * @param g - Graphics instance to use for draw
     */
    public void paintComponent(Graphics g) {
        g.drawImage(((ImageIcon) this.getIcon()).getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
