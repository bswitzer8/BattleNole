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

public class BoatCanvas extends ImageView  implements View.OnTouchListener {

    // boat art
    public Bitmap boat;

    // length of boat
    protected int boatLength;

    // are we vertical or horizontal?
    boolean isHorizontal = true;

    // x, y of boat
    public int[] coordinates;

    // how long we should wait
    final long Double_Click_Interval = 150; // ms.

    // last touch
    long lastTime = 0;

    int tileLength;

    // touch coordinates
    float x = 0.0f, y = 0.0f;

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


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean onTouch(View view, MotionEvent event)
    {
        /*love you */ long time = System.currentTimeMillis();


        float dx = 0, dy = 0;

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

                x = event.getRawX();
                y = event.getRawY();

                dx = view.getX();
                dy = event.getRawY() - this.getHeight();

                float xx = x - dx; // this.getWidth();
                float yy = y - dy; //this.getHeight();

                //Log.d("(xx,yy)", "(" + xx + ", " +  yy + ")");


                if(!isHorizontal)
                {
                    this.setX(event.getRawX() + this.getHeight()/2);
                    this.setY(event.getRawY() - ((tileLength*boatLength) + boatLength*5));
                }
                else
                {
                    this.setX(event.getRawX() - tileLength*(boatLength - 3));
                    this.setY(event.getRawY() - ((tileLength*boatLength) + (boatLength*5)));
                }


              //  Log.d("(x,y)", "(" + dx + ", " + dy + ")");
                coordinates = placeOnBoard(this.getX(), this.getY());
               // Log.d("place at", "[" + coordinates[0] + "][" + coordinates[1] + "]");
                break;
            case MotionEvent.ACTION_UP:

                Log.d("(x,y)", "(" + this.getX() + ", " + this.getY() + ")");

                // double click time stuff
                lastTime = System.currentTimeMillis();

                // this is where we might up date the board
                // and will need to add some boat repositioning

                //valid check
                if(coordinates == null || coordinates.length < 2) break;
                // hey let's generate the boat coordinates

                if(isHorizontal)
                {
                    int cx = coordinates[0];
                    for(int i = cx ; i < cx + boatLength; ++i)
                    {
                        Log.d("coor: ", "(" + i + ", " + coordinates[1] + ")");
                    }
                }
                else
                {
                    int cy = coordinates[1];
                    for(int i = cy ; i < cy + boatLength; ++i)
                    {
                        Log.d("coor: ", "(" + coordinates[0] + ", " + i + ")");
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

        boolean change = boat.getHeight() > boat.getWidth();

        isHorizontal = change;
    }


    // Rotaes the boat when it gets double clicked
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotate()
    {
        rotateBitmap();

        this.setImageBitmap(boat);

        // switch the toggle

    }


    // (-50.95703, 315.44818) what's this mean?
    private int[] placeOnBoard(float x, float y)
    {
        if(!isHorizontal && y < 200) return new int[]{ 0 };

        int offset = boatLength % 2 == 0 ? (tileLength/2) : 0;

        // coordinates
        int[] xy = new int[]{ 0, 0 };

        int halfBoat = (boatLength*tileLength)/2;

        int yMin = halfBoat;


        if(isHorizontal)
        {
            if(x < halfBoat) xy[0] = 0;
            else // do you know what you're doing?
            {
                xy[0] = (int)((x - halfBoat) / (tileLength + (5/2)));
                xy[0] += boatLength/2;
            }

            if(y < yMin) xy[1] = 0;
            else
            {
                int t = (int)y - yMin;
                xy[1] = (t / (tileLength + (5/2)));
            }
        }
        else
        { // vertical
            if(x < tileLength) xy[0] = 0;
            else // do you know what you're doing?
            {
                xy[0] = (int) x / (tileLength + (5/2));
                xy[0] -= 4;

            }
            if(y < yMin) xy[1] = 0;
            else    // if you understand this far I congratulate you.
            {
                int t = (int)y - yMin;
                xy[1] = (t) / (tileLength + (5/2));
            }
        }



        int newX;
        int newY;

        if(isHorizontal)
        {
            if(xy[0] < 2) xy[0] = boatLength/2;
            if(xy[0] > 9) xy[0] = 9;

            newX = (xy[0] * tileLength - offset);
            newY = yMin + ((xy[1]) * tileLength) - (tileLength/3);

            xy[0] -= boatLength / 2;
            xy[1] -= (5 - boatLength);
        }
        else
        {
            if(xy[1] < 0) xy[1] = 0;
            if(xy[1] > 9) xy[1] = 9 - boatLength;

            newX = (xy[0] * tileLength - offset);
            newY = yMin + ((xy[1]) * tileLength);

            xy[0] += boatLength / 2;
            xy[1] -= 4;
        }

        this.setX(newX);
        this.setY(newY);

        return xy;
    }
}
