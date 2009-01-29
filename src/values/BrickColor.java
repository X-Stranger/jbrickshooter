package values;

import basic.Layout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.Image;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Graphics;
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
        ImageIcon upImage = new ImageIcon(ClassLoader.getSystemResource("images/d_up.png"));
        ImageIcon downImage = new ImageIcon(ClassLoader.getSystemResource("images/d_down.png"));
        ImageIcon leftImage = new ImageIcon(ClassLoader.getSystemResource("images/d_left.png"));
        ImageIcon rightImage = new ImageIcon(ClassLoader.getSystemResource("images/d_right.png"));

        BufferedImage image;
        for (int i = 0; i < Layout.FIELD; i++) {
            Map<Orientation, ImageIcon> map = new HashMap<Orientation, ImageIcon>();

            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/" + i + ".png"));
            map.put(Orientation.NONE, icon);

            image = toBufferedImage(icon.getImage());
            image.getGraphics().drawImage(upImage.getImage(), 0, 0, null);
            map.put(Orientation.TOP, new ImageIcon(image));

            image = toBufferedImage(icon.getImage());
            image.getGraphics().drawImage(rightImage.getImage(), 0, 0, null);
            map.put(Orientation.RIGHT, new ImageIcon(image));

            image = toBufferedImage(icon.getImage());
            image.getGraphics().drawImage(downImage.getImage(), 0, 0, null);
            map.put(Orientation.BOTTOM, new ImageIcon(image));

            image = toBufferedImage(icon.getImage());
            image.getGraphics().drawImage(leftImage.getImage(), 0, 0, null);
            map.put(Orientation.LEFT, new ImageIcon(image));

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

    /**
     * This method returns true if the specified image has transparent pixels.
     * Source: http://www.exampledepot.com/egs/java.awt.image/HasAlpha.html
     *
     * @param image - the Image object to check
     * @return true if image has Alpha-channel
     */
    private static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    /**
     * This method returns a buffered image with the contents of an image.
     * Source: http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
     *
     * @param image - the Image object to convert
     * @return new BufferedImage object created from image
     */
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }
}
