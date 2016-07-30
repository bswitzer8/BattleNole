package bswitzer.android.com.battlenole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Initialize the network client
    private NetworkClient client = new NetworkClient(this);

    // boardSize
    private static final int boardSize = 10;
    private static final int shipCount = 5;

    // Create Game Logic ---------------------
    GameLogic gameLogic = new GameLogic(0,1);    // (UserMoves, MaxMoves) Salvo version just increase maxMoves

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


    BattleshipCanvas bc;
    CarrierCanvas cc;
    CruiserCanvas cr;
    PatrolCanvas pc;
    SubmarineCanvas sc;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client.start();

        client.sendMove("Test");

        bc = (BattleshipCanvas) findViewById(R.id.battleship);
        cc = (CarrierCanvas) findViewById(R.id.carrier);
        cr = (CruiserCanvas) findViewById(R.id.cruiser);
        pc = (PatrolCanvas) findViewById(R.id.patrol);
        sc = (SubmarineCanvas)findViewById(R.id.submarine);

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bc.valid() && cc.valid() && cr.valid() && pc.valid() && sc.valid())
                {
                    bc.disable();
                    cc.disable();
                    cr.disable();
                    pc.disable();
                    sc.disable();

                    spam("commence the game !! all your boats are belong to my ?? ");
                }
                else
                {
                   spam("All of the boats are don't belong to the board.. Set them up the board !!");
                }

            }
        });


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

    public void spam(String message)
    {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }

}
