package values;

import basic.Layout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Class generates and stores color for brick.
 * 
 * @author X-Stranger
 */
public class BrickColor {
    
    private static final ImageIcon BLACK_IMAGE = new ImageIcon(ClassLoader.getSystemResource("images/black.png"));
    private static final ImageIcon GRAY_IMAGE = new ImageIcon(ClassLoader.getSystemResource("images/gray.png"));

    /** Static value. */
    public static final BrickColor BLACK = new BrickColor() {

        public ImageIcon getColor(Orientation orientation) {
            return BLACK_IMAGE;
        }

        public ImageIcon getColor() {
            return BLACK_IMAGE;
        }
    }; 

    /** Static value. */
    public static final BrickColor GRAY = new BrickColor() {

        public ImageIcon getColor(Orientation orientation) {
            return GRAY_IMAGE;
        }

        public ImageIcon getColor() {
            return GRAY_IMAGE;
        }
    }; 

    /** Possible colors array. */
    private static final List<Map<Orientation, ImageIcon>> COLORS = new ArrayList<Map<Orientation, ImageIcon>>();
    
    static {
        for (int i = 0; i < Layout.FIELD; i++) {
            Map<Orientation, ImageIcon> map = new HashMap<Orientation, ImageIcon>();
            map.put(Orientation.NONE, new ImageIcon(ClassLoader.getSystemResource("images/" + i + "0.png")));
            map.put(Orientation.TOP, new ImageIcon(ClassLoader.getSystemResource("images/" + i + "1.png")));
            map.put(Orientation.RIGHT, new ImageIcon(ClassLoader.getSystemResource("images/" + i + "2.png")));
            map.put(Orientation.BOTTOM, new ImageIcon(ClassLoader.getSystemResource("images/" + i + "3.png")));
            map.put(Orientation.LEFT, new ImageIcon(ClassLoader.getSystemResource("images/" + i + "4.png")));
            COLORS.add(map);
        }
    }

    /** Random number generator to select color value. */
    private static Random generator = new Random(System.currentTimeMillis());
    
    /**
     * Private constructor.
     */
    private BrickColor() {
        // do nothing
    }

    /**
     * Default constructor.
     * 
     * @param index - colors map index to initialize
     */
    public BrickColor(int index) {
        this.colors = COLORS.get(index);
    }

    /**
     * Parameterized constructor.
     * 
     * @param colors - colors map to initialize
     */
    public BrickColor(Map<Orientation, ImageIcon> colors) {
        this.colors = colors;
    }

    /** Brick colors object. */
    private Map<Orientation, ImageIcon> colors;
    
    /**
     * Returns color according to orientation.
     * 
     * @param orientation - brick orientation
     * @return brick color (ImageIcon object)
     */
    public ImageIcon getColor(Orientation orientation) {
        return colors.get(orientation);
    }
    
    /**
     * Returns color according to orientation.
     * 
     * @return brick color (ImageIcon object)
     */
    public ImageIcon getColor() {
        return colors.get(Orientation.NONE);
    }
    
    /**
     * Returns color index.
     * 
     * @return int index value or -1 if none
     */
    public int getIndex() {
        for (int i = 0; i < COLORS.size(); i++) {
            if (COLORS.get(i) == colors) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Static method which creates a new BrickColor instance.
     * 
     * @param level - max color index
     * @return new color instance
     */
    public static BrickColor generate(int level) {
        return new BrickColor(COLORS.get(generator.nextInt(level)));
    }

    /**
     * Compares object to another object of the same type.
     * 
     * @param brickColor - object to compare to
     * @return boolean value
     */
    public boolean compareTo(BrickColor brickColor) {
        return this.getColor().equals(brickColor.getColor());
    }
}
