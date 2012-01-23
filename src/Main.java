import elements.Frame;

/**
 * Main class to start application.
 * 
 * @author X-Stranger
 */
public final class Main {
    private Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Game(new Frame("JBrickShooter"));
    }
}
