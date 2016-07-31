package bswitzer.android.com.battlenole;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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


        if(boats.length == 5 && boats[0] != null) {
            battleship = createBoat(battleship, R.drawable.battleship, true, width, gap);
            carrier = createBoat(carrier, R.drawable.ocean, true, width, gap);
            cruiser = createBoat(cruiser, R.drawable.cruiser, true, width, gap);
            patrol = createBoat(patrol, R.drawable.patrol, true, width, gap);
            submarine = createBoat(submarine, R.drawable.submarine, true, width, gap);
        }


        // prepare for double nested loop to place ocean tiles
        for (int i = 0; i < 10; ++i)
            for (int j = 0; j < 10; ++j)
                canvas.drawBitmap(ocean, 10 + gap + (i * width), 10 + gap + (j * width), paint);

    }





    public Bitmap createBoat(Bitmap boat, int resource, boolean isHorizontal, int width, int gap)
    {
        boat = BitmapFactory.decodeResource(getResources(), resource);
        boat = Bitmap.createScaledBitmap(boat, width - gap*2, width - gap*2, false);

        return boat;
    }




}