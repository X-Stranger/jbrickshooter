package basic;

import elements.Brick;
import elements.Corner;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import values.BrickColor;

/**
 * Implements custom layout functionality for this game.
 * 
 * @author X-Stranger
 */
public class Layout implements LayoutManager {
    
    /** Corner size. */
    public static final int CORNER = 3; 
    /** Field size. */
    public static final int FIELD = 10; 
    /** Window size. */
    public static final int MAX = CORNER * 2 + FIELD;

    private Brick[][] bricks = new Brick[MAX][MAX];
    private Corner[][] corners = new Corner[2][2];

    /**
     * Default constructor.
     */
    public Layout() {
        // adding bricks to panel
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                bricks[i][j] = null; 
            }
        }
    }

    /**
     * {@inheritDoc}
     * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(String name, Component comp) {
        String[] coordinates = name.split(",");
        if ("corner".equals(coordinates[0])) {
            int x = Integer.parseInt(coordinates[1]);
            int y = Integer.parseInt(coordinates[2]);
            corners[x][y] = (Corner) comp;
        } else {
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            bricks[x][y] = (Brick) comp;
        }
    }

    /**
     * {@inheritDoc}
     * @see java.awt.LayoutManager#removeLayoutComponent(Component)
     */
    public void removeLayoutComponent(Component comp) {
        Point pos = this.getPosition((Brick) comp);
        if (pos != null) {
            bricks[pos.x][pos.y] = null;
        }
    }
    
    /**
     * {@inheritDoc}
     * @see java.awt.LayoutManager#minimumLayoutSize(Container)
     */
    public Dimension minimumLayoutSize(Container target) {
        int w = Layout.MAX * BrickColor.BLACK.getColor().getIconWidth();
        int h = Layout.MAX * BrickColor.BLACK.getColor().getIconHeight();
        return new Dimension(w, h);
    }
    
    /**
     * {@inheritDoc}
     * @see java.awt.LayoutManager#preferredLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container target) {
        return minimumLayoutSize(target);
    }
    
    /**
     * {@inheritDoc}
     * @see java.awt.LayoutManager#layoutContainer(Container)
     */
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            
            int w = BrickColor.BLACK.getColor().getIconWidth();
            int h = BrickColor.BLACK.getColor().getIconHeight();
            Insets insets = parent.getInsets();
            
            int ncomponents = parent.getComponentCount();
            if (ncomponents == 0) {
                return;
            }
            
            for (int c = 0, x = insets.left; c < Layout.MAX; c++) {
                for (int r = 0, y = insets.top; r < Layout.MAX; r++) {
                    if (bricks[c][r] != null) {
                        bricks[c][r].setBounds(x, y, w, h);
                    }
                    y += w;
                }
                x += h;
            }
            
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    corners[x][y].setBounds(
                            (Layout.CORNER + Layout.FIELD) * w * x,
                            (Layout.CORNER + Layout.FIELD) * h * y,
                            w * 3,
                            h * 3);
                }
            }
        }
    }

    /**
     * Returns position of the selected brick at the window.
     * 
     * @param brick - selected brick to check
     * @return Point object
     */
    public Point getPosition(Brick brick) {
        for (int i = 0; i < Layout.MAX; i++) {
            for (int j = 0; j < Layout.MAX; j++) {
                if (bricks[i][j] == brick) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
    
    /**
     * Method returns true if brick is a border brick.
     * 
     * @param brick - Brick to check
     * @return boolean value
     */
    public boolean isBorder(Brick brick) {
        Point pos = this.getPosition(brick);
        if ((pos.x == Layout.CORNER - 1) 
                || (pos.x == Layout.CORNER + Layout.FIELD) 
                || (pos.y == Layout.CORNER - 1) 
                || (pos.y == Layout.CORNER + Layout.FIELD)) {
            return true; 
        } 
        return false;
    }

    /**
     * Getter for bricks.
     * 
     * @param x - x-position
     * @param y - y-position
     * @return Brick element
     */
    public Brick getBrick(int x, int y) {
        return bricks[x][y];
    }
}
