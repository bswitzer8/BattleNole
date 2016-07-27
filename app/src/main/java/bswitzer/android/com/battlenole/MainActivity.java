package bswitzer.android.com.battlenole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // boardSize
    private static final int boardSize = 10;
    private static final int shipCount = 5;

    // Create Game Logic ---------------------
    GameLogic gameLogic = new GameLogic(1);    // set user moves to 1, can create Salvo Version

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

        //    2,   3,         4,          5,       6
        // Boat, Sub, Destroyer, BattleShip, Carrier

        // Create ships here
        // Player 1
        shipsPlayer1[0] = new ShipM("USS Teddy",   BoardM.Type.PATROL,       board1, player1);
        shipsPlayer1[1] = new ShipM("USS Anthony", BoardM.Type.SUB,          board1, player1);
        shipsPlayer1[2] = new ShipM("USS Bill",    BoardM.Type.DESTROYER,    board1, player1);
        shipsPlayer1[3] = new ShipM("USS Don",     BoardM.Type.BATTLESHIP,   board1, player1);
        shipsPlayer1[4] = new ShipM("USS Hillary", BoardM.Type.CARRIER,      board1,  player1);

        // Player 2
        shipsPlayer2[0] = new ShipM("RSS Rummy", BoardM.Type.PATROL,     board2, player2);
        shipsPlayer2[1] = new ShipM("RSS Aris",  BoardM.Type.SUB,        board2, player2);
        shipsPlayer2[2] = new ShipM("RSS Hun",   BoardM.Type.DESTROYER,  board2, player2);
        shipsPlayer2[3] = new ShipM("RSS Dee",   BoardM.Type.BATTLESHIP, board2, player2);
        shipsPlayer2[4] = new ShipM("RSS Yorry", BoardM.Type.CARRIER,    board2, player2);






    }
}
