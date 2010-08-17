package values;

/**
 * Enumeration containing game types.
 *
 * @author X-Stranger
 */
public enum GameType {
    /** Strategy alike game type. */
    STRATEGY(0),
    /** Arcade alike game type. */
    ARCADE(1),
    /** Puzzle alike game type. */
    PUZZLE(2);

    /** Private value storing game type index. */
    private int gameType;

    /**
     * Private constructor for creating GameType object from int.
     *
     * @param type - int value to select game type
     */
    private GameType(int type) {
        gameType = type;
    }

    /**
     * Method that creates GameType object from game type index value.
     *
     * @param value - int value to select game type
     * @return GameType object
     */
    public static GameType fromInt(int value) {
        switch (value) {
            default:
            case 0: return STRATEGY;
            case 1: return ARCADE;
            case 2: return PUZZLE;
        }
    }

    /**
     * Method that returns game type index.
     *
     * @return int value
     */
    public int toInt() {
        return gameType;
    }

    /**
     * Checker for gameType property.
     *
     * @return true if game is strategy alike
     */
    public Boolean isStrategy() {
        return (gameType == GameType.STRATEGY.toInt());
    }

    /**
     * Checker for gameType property.
     *
     * @return true if game is arcade alike
     */
    public Boolean isArcade() {
        return (gameType == GameType.ARCADE.toInt());
    }

    /**
     * Checker for gameType property.
     *
     * @return true if game is puzzle alike
     */
    public Boolean isPuzzle() {
        return (gameType == GameType.PUZZLE.toInt());
    }

    /**
     * Generates string interpretation of GameType object.
     *
     * @return String object
     */
    @Override
    public String toString() {
        return "["  + gameType + "]";
    }
}
