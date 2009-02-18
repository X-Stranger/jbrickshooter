package values;

import basic.Layout;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Class containing global variables.
 * 
 * @author X-Stranger
 */
public final class Settings {
    
    /** Game home folder to store settings. */
    public static final String HOME = System.getProperty("user.home") + "/.jbrickshooter/";
    /** Level up scores delta. */
    public static final int LEVEL_UP_SCORES = 100;
    
    /** Brick move delay value. */
    private static final String DELAY_MOVE = "5";
    /** Brick remove (fire) delay value. */
    private static final String DELAY_FIRE = "100";
    
    private Integer scores;
    private Integer scoresBackup;
    private Integer level;
    private Integer difficulty;
    private Font font;
    private Font bigFont;
    private Boolean gameOver;
    private final float fontSize = 18f;
    private final float bigFontSize = 60f;
    private ResourceBundle resources;
    private JMenuBar menu;
    private JMenuItem undo = null;
    private Properties highscores = new Properties();
    private Properties configuration = new Properties();
    
    /**
     * Method stores settings state to stream.
     * 
     * @param out - stream to store state to
     * @throws IOException if any occurs
     */
    public void saveToStream(OutputStream out) throws IOException {
        for (int i = 0; i < 4; i++) {
            out.write(scores >> (i * 8));
        }
        out.write(level);
        out.write(difficulty);
        out.write(gameOver ? 1 : 0);
    }
    
    /**
     * Method loads settings state from stream.
     * 
     * @param in - stream to load state ftom
     * @throws IOException if any occurs
     */
    public void loadFromStream(InputStream in) throws IOException {
        scores = 0;
        for (int i = 0; i < 4; i++) {
            scores += in.read() << (i * 8);
        }
        level = in.read();
        difficulty = in.read();
        gameOver = in.read() == 1 ? true : false;
    }

    /**
     * Method to load high scores from properties file.
     */
    public void loadHighScores() {
        try {
            File file = new File(Settings.HOME + "highscores");
            if (file.exists()) {
                if (file.isFile() && file.canRead()) {
                    InputStream in = new FileInputStream(file);
                    highscores.load(in);
                    in.close();
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException ie) {
            System.out.println(this.getString("ERROR_HIGHSCORES_LOAD") + " (" + ie.getMessage() + ")");
        }
        
        // initialize empty or broken high scores
        if (highscores.size() < 6 * Layout.FIELD) {
            for (Integer i = 5 * Layout.FIELD; i < (Layout.FIELD + 1) * Layout.FIELD; i++) {
                highscores.setProperty(i.toString(), "0,Unknown");
            }
        }
    }
    
    /**
     * Method to save high scores to the file.
     */
    public void saveHighScores() {
        try {
            File home = new File(Settings.HOME);
            home.mkdir();
            File file = new File(Settings.HOME + "highscores");
            file.createNewFile();
            if (file.isFile() && file.canWrite()) {
                FileOutputStream out = new FileOutputStream(Settings.HOME + "highscores");
                highscores.store(out, this.getString("TITLE"));
                out.flush();
                out.close();
            } else {
                throw new IOException();
            }
        } catch (IOException ie) {
            System.out.println(this.getString("ERROR_HIGHSCORES_SAVE") + " (" + ie.getMessage() + ")");
        }
    }
    
    /**
     * Method to load configuration from properties file.
     */
    public void loadConfiguration() {
        try {
            File file = new File(Settings.HOME + "configuration");
            if (file.exists()) {
                if (file.isFile() && file.canRead()) {
                    InputStream in = new FileInputStream(file);
                    configuration.load(in);
                    in.close();
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException ie) {
            System.out.println(this.getString("ERROR_CONF_LOAD") + " (" + ie.getMessage() + ")");
        }
    }
    
    /**
     * Method to save configuration to the file.
     */
    public void saveConfiguration() {
        try {
            File home = new File(Settings.HOME);
            home.mkdir();
            File file = new File(Settings.HOME + "configuration");
            file.createNewFile();
            if (file.isFile() && file.canWrite()) {
                FileOutputStream out = new FileOutputStream(Settings.HOME + "configuration");
                configuration.store(out, this.getString("TITLE"));
                out.flush();
                out.close();
            } else {
                throw new IOException();
            }
        } catch (IOException ie) {
            System.out.println(this.getString("ERROR_CONF_SAVE") + " (" + ie.getMessage() + ")");
        }
    }
    
    /**
     * Default constructor.
     */
    public Settings() {
        this.setScores(0);
        this.setLevel(1);
        this.setDifficulty(7);
        this.setMenu(new JMenuBar());
        this.setGameOver(false);

        this.loadHighScores();
        this.loadConfiguration();
        
        String[] values = this.getLocale().split("_");
        Locale locale;
        switch (values.length) {
            default:
            case 1: locale = new Locale(values[0]); break;
            case 2: locale = new Locale(values[0], values[1]); break;
            case 3: locale = new Locale(values[0], values[1], values[2]); break;
        }
        resources = ResourceBundle.getBundle("i18n.Messages", locale);
        UIManager.put("OptionPane.yesButtonText", this.getString("YES"));
        UIManager.put("OptionPane.noButtonText", this.getString("NO"));
        UIManager.put("OptionPane.cancelButtonText", this.getString("CANCEL"));

        try {
            InputStream font;
            String name = "images/" + locale.getLanguage() + "_" + locale.getCountry() + ".ttf";
            URL url = ClassLoader.getSystemResource(name);
            
            if (url == null) {
                url = ClassLoader.getSystemResource("images/font.ttf");
            }
            
            font = url.openStream(); 
            this.font = Font.createFont(Font.TRUETYPE_FONT, font);
            this.font = this.font.deriveFont(fontSize);
            font.close();
            
            font = url.openStream(); 
            this.bigFont = Font.createFont(Font.TRUETYPE_FONT, font);
            this.bigFont = this.bigFont.deriveFont(bigFontSize);
            font.close();
            
        } catch (Exception e) {
            System.out.println(this.getString("ERROR_FONT"));
        }
    }
    
    /**
     * Method returns string identified by key from resources bundle. 
     * 
     * @param key - String value for identification
     * @return String value
     */
    public String getString(String key) {
        return resources.getString(key);
    }
    
    /**
     * Getter for level.
     * 
     * @return Integer value
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * Setter for level.
     * 
     * @param level - new Integer value
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * Inc for level.
     */
    public void upLevel() {
        this.level = this.level + 1;
    }

    /**
     * Creates a copy of scores value. 
     */
    public void saveScores() {
        this.scoresBackup = this.scores;
    }
    
    /**
     * Restores scores value from copy.
     */
    public void restoreScores() {
        this.scores = this.scoresBackup;
    }
    
    /**
     * Getter for scores.
     * 
     * @return Integer value
     */
    public Integer getScores() {
        return this.scores;
    }

    /**
     * Setter for scores.
     * 
     * @param scores - new Integer value
     */
    public void setScores(Integer scores) {
        this.scores = scores;
    }

    /**
     * Modifier for scores.
     * 
     * @param scores - Integer value for addition
     */
    public void addScores(Integer scores) {
        this.scores = this.scores + scores;
    }

    /**
     * Getter for difficulty value.
     * 
     * @return Difficulty object
     */
    public Integer getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for difficulty value.
     * 
     * @param difficulty - new value
     */
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter for game font instance.
     * 
     * @param big - should be true if you need big font
     * @return Font object
     */
    public Font getFont(boolean big) {
        if (big) {
            return this.bigFont;
        } else {
            return this.font;
        }
    }

    /**
     * Getter for game font instance.
     * 
     * @return Font object
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Getter for menu.
     * 
     * @return JMenuBar object
     */    
    public JMenuBar getMenu() {
        return menu;
    }

    /**
     * Setter for menu.
     * 
     * @param menu - JMenuBar object
     */    
    public void setMenu(JMenuBar menu) {
        this.menu = menu;
    }

    /**
     * Getter for undo item.
     * 
     * @return JMenuItem object
     */
    public JMenuItem getUndo() {
        return undo;
    }

    /**
     * Setter for undo item.
     * 
     * @param undo  - JMenuItem object
     */
    public void setUndo(JMenuItem undo) {
        this.undo = undo;
    }
    
    /**
     * Getter for gameOver property.
     * 
     * @return the gameOver boolean flag
     */
    public Boolean isGameOver() {
        return gameOver;
    }

    /**
     * Setter fot gameOver propety.
     * 
     * @param gameOver - the boolean flag to be set
     */
    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Returns string interpretation of high scores.
     * 
     * @return String value
     */
    public String getHighScores() {
        String result = "";
        for (Integer i = 0; i < Layout.FIELD; i++) {
            Integer pos = i + difficulty * Layout.FIELD; 
            String[] values = highscores.getProperty(pos.toString()).split(",");
            result += (i + 1) + ". " + values[1] + "   -   " + values[0] + "\n";
        }
        return result;
    }
    
    /**
     * Checks possibility to add new high score.
     * 
     * @return int position to add new high score to or -1 if this is not a high score
     */
    public Integer checkHighScores() {
        for (Integer i = 0; i < Layout.FIELD; i++) {
            Integer pos = i + difficulty * Layout.FIELD; 
            String[] values = highscores.getProperty(pos.toString()).split(",");
            int scores = Integer.parseInt(values[0]);
            if (this.scores >= scores) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Adds new high score to selected position and shifts all the rest to down.
     * 
     * @param to - position to add to. Can be obtained from checkHighScores method
     * @param name - player name to store
     */
    public void setHighScores(Integer to, String name) {
        for (Integer i = Layout.FIELD - 1, tmp = Layout.FIELD - 2; i > to; i--, tmp--) {
            Integer pos1 = i + difficulty * Layout.FIELD;
            Integer pos2 = tmp + difficulty * Layout.FIELD;
            highscores.setProperty(pos1.toString(), highscores.getProperty(pos2.toString()));
        }
        Integer pos = to + difficulty * Layout.FIELD;
        highscores.setProperty(pos.toString(), scores.toString() + "," + name);
    }
    
    /**
     * Getter for brick removal delay value.
     * 
     * @return int delay value
     */
    public int getFireDelay() {
        return Integer.parseInt(configuration.getProperty("DELAY_FIRE", Settings.DELAY_FIRE));
    }
    
    /**
     * Getter for brick move delay value.
     * 
     * @return int delay value
     */
    public int getMoveDelay() {
        return Integer.parseInt(configuration.getProperty("DELAY_MOVE", Settings.DELAY_MOVE));
    }

    /**
     * Getter for locale name.
     * 
     * @return String value
     */
    public String getLocale() {
        return configuration.getProperty("LOCALE", Locale.getDefault().toString());
    }

    /**
     * Setter for brick removal delay value.
     * 
     * @param delay - new int value
     */
    public void setFireDelay(int delay) {
        configuration.setProperty("DELAY_FIRE", "" + delay);
    }
    
    /**
     * Setter for brick move delay value.
     * 
     * @param delay - new int value
     */
    public void setMoveDelay(int delay) {
        configuration.setProperty("DELAY_MOVE", "" + delay);
    }

    /**
     * Setter for locale name.
     * 
     * @param value - new String value
     */
    public void setLocale(String value) {
        configuration.setProperty("LOCALE", value);
    }

    /**
     * Method removes locale property.
     */
    public void cleanLocale() {
        configuration.remove("LOCALE");
    }

    /**
     * Getter for LaF name.
     *
     * @return String value
     */
    public String getLaF() {
        return configuration.getProperty("LAF", getDefaultLaF());
    }

    /**
     * Getter for default LaF name.
     *
     * @return String value
     */
    public String getDefaultLaF() {
        String laf = UIManager.getSystemLookAndFeelClassName();
        String os = System.getProperty("os.name");

        if (os.contains("Linux")) {
            laf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        }

        if (os.contains("Windows")) {
            laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        }
        return laf;
    }

    /**
     * Setter for LaF name.
     *
     * @param value - new String value
     */
    public void setLaF(String value) {
        configuration.setProperty("LAF", value);
    }

    /**
     * Method removes LaF property.
     */
    public void cleanLaF() {
        configuration.remove("LAF");
    }
}
