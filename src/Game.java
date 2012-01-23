import basic.Layout;
import elements.Window;
import values.BrickColor;
import values.Settings;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class holds game window and implements game management actions.
 * 
 * @author X-Stranger
 */
@SuppressWarnings("serial")
public class Game extends JDialog implements ActionListener {
    private Settings settings;
    private Window window;

    /**
     * Default constructor.
     *
     * @param frame - JFrame to be used for having a button at the task bar
     */
    public Game(JFrame frame) {
        super(frame);
        this.printJvmDetails();
        this.settings = new Settings(); 
        BrickColor.init(this.settings.getThemeIndex());
        this.window = new Window(settings);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                window.windowClosing(evt);
                System.exit(0);
            }
        });

        this.setLAF();
        this.setMenu();
        this.start();        
    }
    
    private void printJvmDetails() {
        String ver = System.getProperty("java.version");
        String ven = System.getProperty("java.vendor");
        System.out.println("JVM v" + ver + " by " + ven);
    }

    /**
     * Method configures game window and shows it at the screen.
     */
    private void start() {
        // activate savegame slots 
        window.activateSlots();

        // displaying window at the screen
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(settings.getString("TITLE"));
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.png")).getImage());
        ((JFrame) getParent()).setIconImages(getIconImages());

        setBackground(Settings.BACKGROUND);
        setContentPane(window);
        addWindowListener(window);
        pack();

        // allow resize-able frame
        this.addComponentListener(new ComponentAdapter() {
            private int width = getWidth();
            private int height = getHeight();
            private int size;
            private Insets insets;

            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();

                insets = getInsets();
                width = width - insets.right - insets.left;
                height = height - insets.bottom - insets.top - getJMenuBar().getHeight();
                if (height < width) { size = height; } else { size = width; }

                Settings.setBrickSize(size / Layout.MAX);
                window.doResize();
            }
        });

        // fix game window size (workaround for OpenJDK)
        this.fixWindowSize();

        // set minimum frame size
        this.setMinimumSize(this.getSize());

        // center game frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

        try {
            window.loadFromFile();
            window.updateTitle();
        } catch (Exception e) {
            System.out.println(settings.getString("ERROR_LOAD") + " (" + e.getMessage() + ")");
        }

        resize(((double) settings.getSavedBrickSize()) / ((double) Settings.DEFAULT_BRICK_SIZE));
        setVisible(true);
        toFront();
    }

    /**
     * Method sets game window size.
     */
    public void fixWindowSize() {
        Insets insets = this.getInsets();
        int w = Settings.getBrickSize() * Layout.MAX;
        int h = Settings.getBrickSize() * Layout.MAX;
        this.setSize(w + insets.left + insets.right,
            h  + insets.top + insets.bottom + getJMenuBar().getHeight());
    }

    /**
     * Methos configures Look-And-Feel.
     */
    private void setLAF() {
        // configuring LAF
        try {
            String laf = settings.getLaF();
            UIManager.setLookAndFeel(laf);
            
        } catch (Exception e) {
            System.out.println(settings.getString("ERROR_LAF"));
        }
    }
    
    /**
     * Method creates game menu.
     */
    private void setMenu() {
        // configuring menu
        setJMenuBar(settings.getMenu());
        settings.getMenu().setBackground(Settings.BACKGROUND);
        settings.getMenu().setBorderPainted(false);
        
        JMenu gameMenu = new JMenu(settings.getString("MENU_GAME"));
        gameMenu.setForeground(Color.lightGray);
        settings.getMenu().add(gameMenu);

        JMenuItem menuItem = new JMenuItem(settings.getString("MENU_GAME_NEW"), KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);

        menuItem = new JMenuItem(settings.getString("MENU_GAME_UNDO"), KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        menuItem.setEnabled(false);
        gameMenu.add(menuItem);
        settings.setUndo(menuItem);

        gameMenu.addSeparator();

        menuItem = new JMenuItem(settings.getString("MENU_GAME_SAVE"), KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);

        menuItem = new JMenu(settings.getString("MENU_GAME_SAVE_SLOT"));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);
        for (Integer i = 1; i <= 5; i++) {
            JMenuItem slot = new JMenuItem(settings.getString("MENU_GAME_SLOT") + " " + i);
            slot.setName(settings.getString("MENU_GAME_SAVE_SLOT") + " " + i);
            slot.addActionListener(window);
            menuItem.add(slot);
            window.addSaveSlot(slot);
        }

        gameMenu.addSeparator();

        menuItem = new JMenuItem(settings.getString("MENU_GAME_LOAD"), KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);

        menuItem = new JMenu(settings.getString("MENU_GAME_LOAD_SLOT"));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);
        for (Integer i = 1; i <= 5; i++) {
            JMenuItem slot = new JMenuItem(settings.getString("MENU_GAME_SLOT") + " " + i);
            slot.setName(settings.getString("MENU_GAME_LOAD_SLOT") + " " + i);
            slot.addActionListener(window);
            menuItem.add(slot);
            window.addLoadSlot(slot);
        }

        gameMenu.addSeparator();

        menuItem = new JMenuItem(settings.getString("MENU_GAME_OPTIONS"), KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);
        
        gameMenu.addSeparator();

        menuItem = new JMenuItem(settings.getString("MENU_GAME_SIZE1"), KeyEvent.VK_1);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        gameMenu.add(menuItem);

        menuItem = new JMenuItem(settings.getString("MENU_GAME_SIZE2"), KeyEvent.VK_2);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        gameMenu.add(menuItem);

        menuItem = new JMenuItem(settings.getString("MENU_GAME_SIZE3"), KeyEvent.VK_3);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        gameMenu.add(menuItem);

        gameMenu.addSeparator();
        
        menuItem = new JMenuItem(settings.getString("MENU_GAME_EXIT"), KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(window);
        gameMenu.add(menuItem);

        JMenu aboutMenu = new JMenu(settings.getString("MENU_ABOUT"));
        aboutMenu.setForeground(Color.lightGray);
        settings.getMenu().add(aboutMenu);

        menuItem = new JMenuItem(settings.getString("MENU_ABOUT_HIGHSCORES"), KeyEvent.VK_H);
        menuItem.addActionListener(window);
        aboutMenu.add(menuItem);

        menuItem = new JMenuItem(settings.getString("MENU_ABOUT_GAME"), KeyEvent.VK_G);
        menuItem.addActionListener(window);
        aboutMenu.add(menuItem);
    }

    /**
     * Method performs game window resize based of default brick size multiplied by value presented.
     *
     * @param mult - multiplier to increase default brick size
     */
    private void resize(double mult) {
        Insets insets = this.getInsets();
        int x = (int) this.getBounds().getX();
        int y = (int) this.getBounds().getY();
        int size = Layout.MAX * Settings.DEFAULT_BRICK_SIZE;
        size = (int) (((double) size) * mult);
        this.setBounds(x, y, size + insets.left + insets.right,
                size + insets.top + insets.bottom + getJMenuBar().getHeight());
    }

    /**
     * Method called when menu action performed.
     *
     * @param e - ActionEvent object
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass().isAssignableFrom(JMenuItem.class)) {
            JMenuItem item = (JMenuItem) e.getSource();
            String text = item.getText();

            if (text.equals(settings.getString("MENU_GAME_SIZE1"))) {
                resize(1.0d);
                return;
            }

            if (text.equals(settings.getString("MENU_GAME_SIZE2"))) {
                resize(5.0d / 4.0d);
                return;
            }

            if (text.equals(settings.getString("MENU_GAME_SIZE3"))) {
                resize(3.0d / 2.0d);
            }
        }
    }
}
