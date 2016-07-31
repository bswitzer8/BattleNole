package bswitzer.android.com.battlenole;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ben on 7/23/2016.
 */


public class BoatCanvas extends ImageView implements View.OnTouchListener {


    // References to ship objects
    public ShipM ship_;
    public ShipM patrol_;
    public ShipM sub_;
    public ShipM destroyer_;
    public ShipM battleship_;
    public ShipM carrier_;
    public BoardM.Type shipType_;


    // boat art
    public Bitmap boat;

    // length of boat
    protected int boatLength;

    // are we vertical or horizontal?
    boolean isHorizontal = true;

    // x, y of boat
    public int[] coordinates;

    public String[][] pairs = new String[5][2];

    boolean enable = true;

    // how long we should wait
    final long Double_Click_Interval = 150; // ms.

    // last touch
    long lastTime = 0;

    int tileLength;

    // neo knows this well
    Matrix matrix = new Matrix();


    // ms paint
    Paint paint = new Paint();

    public BoatCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );

        DisplayMetrics dm = getResources().getDisplayMetrics();

        tileLength = dm.widthPixels/10;

        paint = new Paint();

        setOnTouchListener(this);
    }

    public String[][] getPairs() {
        return pairs;
    }

    ///
    /// Prevents the boats from moving.
    ///
    public void disable()
    {
        enable = false;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean onTouch(View view, MotionEvent event)
    {
        if(!enable) return false;

        ShipM currentShip = null;

        BoardM.Type shipType = shipType_;
        // Determine which object is needed to get data from
        switch (shipType) {
            case PATROL:
                currentShip = patrol_;
                break;
            case SUB:
                currentShip = sub_;
                break;
            case DESTROYER:
                currentShip = destroyer_;
                break;
            case BATTLESHIP:
                currentShip = battleship_;
                break;
            case CARRIER:
                currentShip = carrier_;
                break;
        }



        /*love you */ long time = System.currentTimeMillis();


        switch(event.getAction())
        {

            case MotionEvent.ACTION_DOWN:

                // hey let's double tap
                if((time - lastTime) <= Double_Click_Interval) {
                    Log.d("rotate", "rotating... " + boatLength);
                    rotate();
                }

                break;
            case MotionEvent.ACTION_MOVE:

                coordinates = placeOnBoard(event.getRawX(), event.getRawY());

                break;
            case MotionEvent.ACTION_UP:

                // double click time stuff
                lastTime = System.currentTimeMillis();

                // this is where we might up date the board
                // and will need to add some boat repositioning

                // valid check

                if(!valid()) break;
                // hey let's generate the boat coordinates

                int c = 0;
                Log.d("Current Ship", currentShip.GetShipName());
                if(isHorizontal)
                {
                    int cx = coordinates[0];

                    //currentShip.SetFrontPosition("" + (char)(65 + cx) + coordinates[1] + coordinates[1]);
                    for(int i = cx ; i < cx + boatLength; ++i)
                    {
                        // convert X to Alphabet
                        pairs[c][0] = "" + (char)(65 + i);

                        Log.d("coor: ", "(" +  pairs[c][0] + ", " + pairs[c][1] + ")");
                        if (i == cx)
                            currentShip.SetFrontPosition(pairs[c][0] + pairs[c][1]); //
                        else
                            currentShip.SetBackPosition(pairs[c][0] + pairs[c][1]); // sorry, too lazy to just get the last, will increment through to get last :)
                        ++c;
                    }

                }
                else
                {
                    int cy = coordinates[1];
                    //currentShip.SetFrontPosition("" + (char)(65 + cy) + coordinates[0] + coordinates[1]);
                    for(int i = cy ; i < cy + boatLength; ++i)
                    {
                        pairs[c][0] = "" + (char)(65 + coordinates[0]);


                        Log.d("coor: ", "(" + pairs[c][0] + ", " + pairs[c][1] + ")");

                        if (i == cy)
                            currentShip.SetFrontPosition(pairs[c][0] + pairs[c][1]); //
                        else
                            currentShip.SetBackPosition(pairs[c][0] + pairs[c][1]); // sorry, too lazy to just get the last, will increment through to get last :)
                        ++c;
                    }

                }
                break;
        }


        return true;
    }

    // this just makes the boat vertical to start.
    public void rotateBitmap()
    {
        // toggle back and forth between the two.
        matrix.postRotate(isHorizontal ? 90 : -90);

        // rotate that bitmap
        boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);

        // maybe this will work?
        boolean change = boat.getHeight() > boat.getWidth();

        isHorizontal = change;
    }


    // Rotaes the boat when it gets double clicked
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotate()
    {
        rotateBitmap();

        this.setImageBitmap(boat);
    }




    // (-50.95703, 315.44818) what's this mean?
    // this function will place the board on the board
    // and return the corrosponding x,y coordinates.
    private int[] placeOnBoard(float x, float y)
    {

        if(!isHorizontal && y < 200) return new int[]{ 0 };

        // coordinates
        int[] xy = new int[]{ 0, 0 };

        int halfBoat = boatLength/2;

        float newX;
        float newY;

        // do you know what you're doing (getting x)
        xy[0] = ((int)(( x ) / (tileLength + (5/2))));

        // get the y value
        int t = (int)y - this.getHeight() + tileLength;            // don't rock the boat
        xy[1] = (t / (tileLength + (5/2)));

        // we offset the x if the boat is even length, else offset the y.
        int xOffset = boatLength % 2 == 0 ? tileLength/2 : 0;
        int yOffset = xOffset == 0 ? tileLength/2 : 0;

        if(isHorizontal)  // ------
        {
           
            // x fixes
            if(xy[0] < halfBoat) xy[0] = halfBoat;

            if(xy[0] + boatLength > 11) xy[0] = (10 - halfBoat) - (boatLength % 2 == 1 ? 1 : 0);

            // y fixes

            int yAdjust = boatLength > 3 ? 3 : 4;
            if((xy[1] - yAdjust) < 0) xy[1] = yAdjust;     // srsly what is this
            if((xy[1] - yAdjust) > 9) xy[1] = 9 + yAdjust;


            newX = ((xy[0] * tileLength) - xOffset);
            newY = (xy[1] * tileLength) - (tileLength/3) - yOffset;


            // life hacks
            xy[0] -= halfBoat;

            xy[1] -= yAdjust;
        }
        else  // vertical |
        {
           
            // x fixes
            int even = boatLength % 2 == 0 ? 1 : 0;

            if(xy[0] < 0) xy[0] = 0;
            if(xy[0] > 9) xy[0] = 9;

            // y fixes
           
            int yAdjust = halfBoat + 4 - (even - 1);

            if(xy[1] - yAdjust < 0) xy[1] = yAdjust;     // srsly what is this
            if((xy[1] - yAdjust) + boatLength > 10) xy[1] = (10 + yAdjust) - boatLength;

            newX = (((xy[0] - halfBoat) * tileLength) + xOffset);
            newY = ((xy[1]) * tileLength) - (tileLength/3) - yOffset;

            // life hacks

            xy[1] -= yAdjust;
        }

        this.setX(newX);
        this.setY(newY);

        return xy;
    }


    public boolean valid()
    {
        if(coordinates == null || coordinates.length != 2) return false;


        return coordinates[0] > -1 && coordinates[0] < 10 && coordinates[1] > -1 && coordinates[1] < 10 ;
    }

    public void debug()
    {
        Log.d("Boat", " This boat is length " + boatLength);
        for(int i = 0; i < boatLength; ++i)
        {
            Log.d("coords: ", "(" + pairs[i][0] + ", " + pairs[i][1] + ")");
        }

    }

    public void SetShip(ShipM ship) {
        ship_ = ship;
    }

    public void SetPatrol(ShipM ship) {
        patrol_ = ship;
    }
    public void SetSub(ShipM ship) {
        sub_ = ship;
    }
    public void SetDestroyer(ShipM ship) {
        destroyer_ = ship;
    }
    public void SetBattleShip(ShipM ship) {
        battleship_ = ship;
    }
    public void SetCarrier(ShipM ship) {
        carrier_ = ship;
    }



}
