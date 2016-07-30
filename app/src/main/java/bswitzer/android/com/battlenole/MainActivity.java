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
