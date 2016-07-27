package bswitzer.android.com.battlenole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Initialize the network client
    private NetworkClient client = new NetworkClient(this);

    // boardSize
    private static final int boardSize = 10;
    private static final int shipCount = 5;

    // Create Game Logic ---------------------
    GameLogic gameLogic = new GameLogic();

    // ---------------------------------------

    // Create Player Objects -----------------
    // Player 1
    Player player1 = new Player("player 1");
    // Player 2
    Player player2 = new Player("player 2");

    // ----------------------------------------


    // Create Board Objects -------------------
    // Player 1 Board, Player 2 attacks this board
    BoardM board1 = new BoardM(boardSize, player1);
    // Player 2 Board, Player 1 attacks this board
    BoardM board2 = new BoardM(boardSize, player2);

    // ----------------------------------------

    // Create Ship array
    // Player 1 Ships
    ShipM[] shipsPlayer1 = new ShipM[shipCount];

    // Player 2 Ships
    ShipM[] shipsPlayer2 = new ShipM[shipCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client.start();

        client.sendMove("Test");

        //    2,   3,         4,          5,       6
        // Boat, Sub, Destroyer, BattleShip, Carrier

        // Create ships here
        // Player 1
        shipsPlayer1[0] = new ShipM(2, "USS Teddy",   "Boat",       true, player1);
        shipsPlayer1[1] = new ShipM(3, "USS Anthony", "Sub",        true, player1);
        shipsPlayer1[2] = new ShipM(4, "USS Bill",    "Destroyer",  true, player1);
        shipsPlayer1[3] = new ShipM(5, "USS Don",     "BattleShip", true, player1);
        shipsPlayer1[4] = new ShipM(6, "USS Hillary", "Carrier",    true, player1);

        // Player 2
        shipsPlayer2[0] = new ShipM(2, "RSS Rummy", "Boat",       true, player2);
        shipsPlayer2[1] = new ShipM(3, "RSS Aris",  "Sub",        true, player2);
        shipsPlayer2[2] = new ShipM(4, "RSS Hun",   "Destroyer",  true, player2);
        shipsPlayer2[3] = new ShipM(5, "RSS Dee",   "BattleShip", true, player2);
        shipsPlayer2[4] = new ShipM(6, "RSS Yorry", "Carrier",    true, player2);






    }
}
