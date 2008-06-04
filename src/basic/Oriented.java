package basic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import values.Orientation;

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
}
