package bswitzer.android.com.battlenole;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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

    private int x = 0, y = 0;

    public int height = 0, width = 0;

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

        // gap size is 5.
        int gap = 2;


        width = 440 / 10;

        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        ocean = Bitmap.createScaledBitmap(ocean, width - gap, width - gap, false);

        // prepare for double nested loop to place ocean tiles
        for (int i = 0; i < 10; ++i)
            for (int j = 0; j < 10; ++j)
                canvas.drawBitmap(ocean, 10 + gap + (i * width), 10 + gap + (j * width), paint);

        if(boats.length == 5 && boats[0] != null) {
            Log.d("boat length", "" + boats.length);

            createBoat(canvas, battleship, R.drawable.battleship, boats[0], width, gap);
            createBoat(canvas, carrier, R.drawable.carrier, boats[1], width, gap);
            createBoat(canvas, cruiser, R.drawable.cruiser, boats[2], width, gap);
            createBoat(canvas, patrol, R.drawable.patrol, boats[3], width, gap);
            createBoat(canvas, submarine, R.drawable.submarine, boats[4], width, gap);
        }

    }



    public void createBoat(Canvas canvas, Bitmap boat, int resource, ShipM ship, int width,  int gap)
    {
        boat = BitmapFactory.decodeResource(getResources(), resource);
        boat = Bitmap.createScaledBitmap(boat, width - gap*2, (width * ship.GetLength()) - gap*2, false);

        if(ship.isHorizontal())
        {
            matrix.postRotate(90);

            // rotate that bitmap
            boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);
        }

        int i = (int)ship.GetFrontPosition().charAt(0) - 65; // life hack
        int j = Integer.parseInt(ship.GetFrontPosition().substring(1));

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





}