package values;

/**
 * Orientation enumeration.
 * 
 * @author X-Stranger
 */
public enum Orientation {
    /**
     *  Orientation directions.
     */
    TOP, BOTTOM, LEFT, RIGHT, NONE;
    
    /**
     * Switch orientation direction.
     * 
     * @param orientation - orientation to switch
     * @return new orientation value
     */
    public static Orientation switchOrientation(Orientation orientation) {
        if (orientation == Orientation.BOTTOM) { return Orientation.TOP; }
        if (orientation == Orientation.TOP) { return Orientation.BOTTOM; }
        if (orientation == Orientation.LEFT) { return Orientation.RIGHT; }
        if (orientation == Orientation.RIGHT) { return Orientation.LEFT; }
        return Orientation.NONE;
    }
    
    /**
     * Returns true if orientation value is horizontal.
     * 
     * @return boolean value
     */
    public boolean isHorizontal() {
        if (this == Orientation.LEFT) { 
            return true; 
        } else if (this == Orientation.RIGHT) {
            return true; 
        }
        return false;
    }
    
    /**
     * Returns true if orientation value is vertical.
     * 
     * @return boolean value
     */
    public boolean isVertical() {
        if (this == Orientation.TOP) { 
            return true; 
        } else if (this == Orientation.BOTTOM) { 
            return true; 
        }
        return false;
    }
}
