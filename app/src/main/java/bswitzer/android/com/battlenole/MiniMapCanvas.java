package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ben on 7/31/2016.
 */
public class MiniMapCanvas extends ImageView  {

    private Bitmap ocean,
            battleship,
            carrier,
            cruiser,
            patrol,
            submarine;

    private ShipM[] boats = new ShipM[5];

    private static Paint paint;

    private Matrix matrix = new Matrix();

    public int width = 0;

    private int[][] hits = new int[10][10];


    public MiniMapCanvas(Context context, AttributeSet attrs ) {
        super( context, attrs );

        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        this.setVisibility(View.INVISIBLE);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(5);

        // gap size is 2.
        int gap = 2;


        width = 440 / 10;

        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        ocean = Bitmap.createScaledBitmap(ocean, width - gap, width - gap, false);

        // prepare for double nested loop to place ocean tiles
        for (int i = 0; i < 10; ++i)
            for (int j = 0; j < 10; ++j)
                canvas.drawBitmap(ocean, 10 + gap + (i * width), 10 + gap + (j * width), paint);

        if(boats.length == 5 && boats[0] != null) {


            createBoat(canvas, battleship, R.drawable.battleship, boats[0], width, gap);
            createBoat(canvas, carrier, R.drawable.carrier, boats[1], width, gap);
            createBoat(canvas, cruiser, R.drawable.cruiser, boats[2], width, gap);
            createBoat(canvas, patrol, R.drawable.patrol, boats[3], width, gap);
            createBoat(canvas, submarine, R.drawable.submarine, boats[4], width, gap);
        }

        int halfSize = width/2;


        for(int x = 0; x < hits.length; ++x)
        {
            for(int y = 0; y < hits[x].length; y++)
            {
                paint.setColor(hits[x][y] == 1 ? Color.RED : Color.WHITE);
                paint.setStrokeWidth((int)(width*.5));
                if(hits[x][y] != 0)
                    canvas.drawPoint(10 + gap + (x * width) + halfSize, 10 + gap + (y * width) + halfSize, paint);
            }
        }
    }



    public void createBoat(Canvas canvas, Bitmap boat, int resource, ShipM ship, int width,  int gap)
    {

        boat = BitmapFactory.decodeResource(getResources(), resource);
        boat = Bitmap.createScaledBitmap(boat, width - gap * 2, (width * ship.GetLength()) - gap * 2, false);

        if (ship.isHorizontal()) {

            matrix.postRotate(90);

            // hopefully this works. this takes awhile.

            // rotate that bitmap
            boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);

            // maybe this will work?
            boolean change = boat.getHeight() > boat.getWidth();

            // i don't even know. this is dumb but man it has to be done.
            int attempts = 40;
            while (change && attempts > 0) {
                matrix.postRotate(90);
                boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);

                change = boat.getHeight() > boat.getWidth();
                --attempts;
            }


        }

        int i = (int) ship.GetFrontPosition().charAt(0) - 65; // life hack
        int j = Integer.parseInt(ship.GetFrontPosition().substring(1));

        // going to try giving it time to change the bitmaps
        // i hate to do this, but i need to give the bitmap a little more time to catch up.
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Log.d("Exception", "thread didn't sleep");
        }


        canvas.drawBitmap(boat, 10 + gap + (i * width), 10 + gap + (j * width), paint);

    }


    public void setShips(ShipM[] bts)
    {
        // i hate this, but it must be done.
        for(ShipM ship : bts)
        {
            if(ship.GetShipClass() == BoardM.Type.BATTLESHIP)
                boats[0] = ship;
            else if (ship.GetShipClass() == BoardM.Type.CARRIER)
                boats[1] = ship;
            else if (ship.GetShipClass() == BoardM.Type.DESTROYER)
                boats[2] = ship;
            else if (ship.GetShipClass() == BoardM.Type.PATROL)
                boats[3] = ship;
            else if (ship.GetShipClass() == BoardM.Type.SUB)
                boats[4] = ship;

        }

    }


    public boolean hit(int x, int y, int hit)
    {
        if(x < 0 || x > 9 || y < 0 || y > 9) return false;

        Log.d("comput hit", "(" + x + ", " + y + ")");


        hits[x][y] = hit;

        invalidate();
        return true;
    }


}