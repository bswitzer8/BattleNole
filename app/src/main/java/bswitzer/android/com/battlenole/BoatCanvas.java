package bswitzer.android.com.battlenole;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

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
    boolean boatRotated = false;

    // x, y of boat
    public int[] coordinates;

    // how long we should wiat
    final long Double_Click_Interval = 120; // ms.

    // last touch
    long lastTime = 0;

    int tileLength = 98;

    // touch coordinates
    float x = 0.0f, y = 0.0f;

    // neo knows this well
    Matrix matrix = new Matrix();

    // ms paint
    Paint paint = new Paint();

    public BoatCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );

        paint = new Paint();
        paint.setStrokeWidth(10);

        this.setMaxWidth(boatLength * tileLength);
        this.setMaxHeight(tileLength);

        this.setMinimumHeight(tileLength);
        this.setMinimumWidth(boatLength * tileLength);



        setOnTouchListener(this);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onTouch(View view, MotionEvent event)
    {
        /*love you */ long time = System.currentTimeMillis();

        switch(event.getAction())
        {

            case MotionEvent.ACTION_DOWN:

                // hey let's double tap
                if((time - lastTime) <= Double_Click_Interval) {
                    rotate();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                x = event.getRawX();
                y = event.getRawY();

                float xx = (5 / 2) + this.getWidth();
                float yy = (5 / 2) + this.getHeight();

                Log.d("(xx,yy)", "(" + xx + ", " +  yy + ")");

                float dx = x - xx/2;
                float dy = y - yy/2;

                this.setX(dx);
                this.setY(dy);


                Log.d("(x,y)", "(" + dx + ", " + dy + ")");
                coordinates = placeOnBoard(dx, dy);
                Log.d("place at", "[" + coordinates[0] + "][" + coordinates[1] + "]");
                break;
            case MotionEvent.ACTION_UP:
                // double click time stuff
                lastTime = System.currentTimeMillis();

                // this is where we might up date the board
                // and will need to add some boat repositioning

                //valid check
                if(coordinates == null) break;
                // hey let's generate the boat coordinates

                if(boatRotated)
                {
                    int cy = coordinates[1];
                    for(int i = cy ; i < cy + boatLength; ++i)
                    {
                        Log.d("coor: ", "(" + coordinates[0] + ", " + i + ")");
                    }
                }
                else
                {
                    int cx = coordinates[0];
                    for(int i = cx ; i < cx + boatLength; ++i)
                    {
                        Log.d("coor: ", "(" + i + ", " + coordinates[1] + ")");
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
        matrix.postRotate(boatRotated ? -90 : 90);
        // rotate that bitmap
        boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);

    }


    // Rotaes the boat when it gets double clicked
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotate()
    {
        rotateBitmap();

        this.setImageBitmap(boat);

        // switch the toggle
        boatRotated = !boatRotated;
    }


    // (-50.95703, 315.44818) what's this mean?
    private int[] placeOnBoard(float x, float y)
    {
        // coordinates
        int[] xy = new int[]{ 0, 0 };


        if(x < 0) xy[0] = 0;
        else // do you know what you're doing?
        {
            xy[0] = (int)((x + 200) / (tileLength + (5/2)));
        }
        if(y < 290) xy[1] = 0;
        else    // if you understand this far I congratulate you.
        {
            int t = (int)y - 620;
            xy[1] = (t / (tileLength + (5/2)));
        }

        // I should probably validate this, shouldn't i?
        return xy;
    }
}
