package bswitzer.android.com.battlenole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Initialize the network client
    private NetworkClient client = new NetworkClient(this);

    // boardSize
    private static final int boardSize = 10;
    private static final int shipCount = 5;

    private ArrayList<String> al = new ArrayList<String>();

    // Create Game Logic ---------------------
    GameLogic gameLogic = new GameLogic(0,1);    // (UserMoves, MaxMoves) Salvo version just increase maxMoves

    // ---------------------------------------

    // Create Player Objects -----------------
    // Player 1
    Player player1 = new Player("player 1");
    // Player 2
    Player player2 = new Player("player 2");

    // ----------------------------------------


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
        // Create Board Objects -------------------
        // Player 1 Board, Player 2 attacks this board
        final BoardM board1 = new BoardM(boardSize, player1);
        // Player 2 Board, Player 1 attacks this board
        final BoardM board2 = new BoardM(boardSize, player2);



        // ----------------------------------------

        // Create Ship array
        // Player 1 Ships
        final ShipM[] shipsPlayer1 = new ShipM[shipCount];

        // Player 2 Ships
        final ShipM[] shipsPlayer2 = new ShipM[shipCount];


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

        client.start();
        client.sendMove("Test");

        pc = (PatrolCanvas) findViewById(R.id.patrol);
        sc = (SubmarineCanvas)findViewById(R.id.submarine);
        cr = (CruiserCanvas) findViewById(R.id.cruiser);
        bc = (BattleshipCanvas) findViewById(R.id.battleship);
        cc = (CarrierCanvas) findViewById(R.id.carrier);

        // Set references to ship objects and canvas HERE!!!! woot! ---<--@
        pc.SetPatrol(shipsPlayer1[0]);
        sc.SetSub(shipsPlayer1[1]);
        cr.SetDestroyer(shipsPlayer1[2]);
        bc.SetBattleShip(shipsPlayer1[3]);
        cc.SetCarrier(shipsPlayer1[4]);



        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set ship positions on board
                board1.SetShipBoardTile(shipsPlayer1,5);

                // IS THIS VALID? (meaning all the coordinates are proper.
                boolean valid = bc.valid() && cc.valid() && cr.valid() && pc.valid() && sc.valid();

                // check for overlapping ( if it's valid, otherwise don't bother)
                boolean determine = valid && AppendCoord(bc) && AppendCoord(cc) && AppendCoord(cr)
                        && AppendCoord(pc) && AppendCoord(sc);

                if(determine){ // determined will be valid

                    bc.disable();
                    cc.disable();
                    cr.disable();
                    pc.disable();
                    sc.disable();

                    // we don't need them hitting save anymore
                    save.setVisibility(View.INVISIBLE);

                    spam("commence the game !! all your boats are belong to my ?? ");
                }
                else if(!valid) // if it's not determined, then it could be invalid?
                {
                    // trash the array list.
                    al.clear();

                    spam("Error : One or more of the boats is not positioned properly on the board, please reevaluate. ");
                }
                else // obviously if it is valid, then it must be indetermined.
                {
                    // trash the array list.
                    al.clear();

                    spam("Error : Invalid boat placement. Boats cannot overlap each other. ");
                }

            }
        });


        //    2,   3,         4,          5,       6
        // Boat, Sub, Destroyer, BattleShip, Carrier




    }

    /*
     * Spam and toast go quite well together.
     * This just sends a toasty message to the user.
     */
    public void spam(String message)
    {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }


    /*
     * This purpose of this function is to gather all the coordinates of the boats
     * And determine if any boats are overlapping. This is done by converting each coordinate
     * Into a string pair, like x,y and then it sees if that string is already in the arraylist,
     * if not it proceeds to add it to there and continues loops.
     *
     * If it is there, it'll return false (meaning invalid placement).
     *
     */
    private boolean AppendCoord(BoatCanvas b)
    {
        // you have to do boat length, cause I was lazy and did length 5 (carrier) on pairs.
        for(int i = 0; i < b.boatLength; ++i)
        {
            String pair  =  b.pairs[i][0] + "," + b.pairs[i][1];

            if(al.contains(pair)){
                Log.d("This pair is contained", pair);
                return false;
            }

            al.add(pair);
        }

        return true;
    }


}
