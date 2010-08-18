package values;

/**
 * Enumeration containing game types.
 *
 * @author X-Stranger
 */
public enum GameType {
    /** Strategy alike game type. */
    STRATEGY(0, "MESSAGE_GAME_NEW_TYPE_STRATEGY"),
    /** Arcade alike game type. */
    ARCADE(1, "MESSAGE_GAME_NEW_TYPE_ARCADE"),
    /** Puzzle alike game type. */
    PUZZLE(2, "MESSAGE_GAME_NEW_TYPE_PUZZLE");

    /** Private value storing game type index. */
    private int gameType;
    /** Private value storing game type title id. */
    private String titleId;

    /**
     * Private constructor for creating GameType object from int.
     *
     * @param type - int value to select game type
     */
    private GameType(int type, String id) {
        gameType = type;
        titleId = id;
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
     * Returns game type title id.
     *
     * @return String value
     */
    public String getTitleId() {
        return titleId;
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
