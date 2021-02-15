package com.bol.interview.kalahaweb.constants;

public class TestConstants {
    public static final String EXPECTED_RESPONSE_STRING = "{\"id\":\"9edff32b-7aa2-427a-ab98-14c4482bf86e\",\"board\":{\"pits\":[{\"stones\":4,\"id\":1},{\"stones\":4,\"id\":2},{\"stones\":4,\"id\":3},{\"stones\":4,\"id\":4},{\"stones\":4,\"id\":5},{\"stones\":4,\"id\":6},{\"stones\":0,\"id\":7},{\"stones\":4,\"id\":8},{\"stones\":4,\"id\":9},{\"stones\":4,\"id\":10},{\"stones\":4,\"id\":11},{\"stones\":4,\"id\":12},{\"stones\":4,\"id\":13},{\"stones\":0,\"id\":14}]},\"activePlayer\":null,\"currentPit\":-1,\"switchTurn\":\"\",\"gameResult\":{\"messageUI\":\"\",\"gameStatus\":\"STARTED\"},\"captureIfOppositeEmpty\":false}";
    public static final String KALAHA_CREATE_API_URL_STRING = "http://localhost:8011/kalaha/create-game";
    public static final String KALAHA_SOW_API_URL_STRING = "http://localhost:8011/kalaha/9edff32b-7aa2-427a-ab98-14c4482bf86e/pits/1";
    public static final String KALAHA_DELETE_API_URL_STRING = "http://localhost:8011/kalaha/delete-game/9edff32b-7aa2-427a-ab98-14c4482bf86e";
    public static final boolean SHOULD_CAPTURE_STONES_IF_OPPOSITE_PIT_EMPTY = true;
    public static final String GAME_ID = "9edff32b-7aa2-427a-ab98-14c4482bf86e";
    public static final String DELETED_FROM_DATABASE = "Deleted from database!";

}
