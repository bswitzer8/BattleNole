package bswitzer.android.com.battlenole;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/20/2016.
 *
 * source: http://www.androiddom.com/2012/03/creating-simple-custom-android-view.html
 */
public class BoardCanvas extends ImageView {

    private Bitmap ocean;
    private Bitmap battleship, carrier, cruiser, patrol, submarine;


    private static Paint paint;


    public BoardCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }



    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // make the dimensions 1/10th of the board.
        int height = this.getMeasuredHeight() / 10;
        int width = this.getMeasuredWidth() / 10;
        // gap size is 5.
        int gap = 5;

        battleship = BitmapFactory.decodeResource(getResources(), R.drawable.battleship);
        int battleship_len = 4;
        battleship = Bitmap.createScaledBitmap(battleship, width - gap, (battleship_len * width) - gap, false);

        carrier = BitmapFactory.decodeResource(getResources(), R.drawable.carrier);
        int carrier_len = 5;
        carrier = Bitmap.createScaledBitmap(carrier, width - gap, (carrier_len * width) - gap, false);

        cruiser = BitmapFactory.decodeResource(getResources(), R.drawable.cruiser);
        int cruiser_len = 4;
        cruiser = Bitmap.createScaledBitmap(cruiser, width - gap, (cruiser_len * width) - gap, false);

        submarine = BitmapFactory.decodeResource(getResources(), R.drawable.submarine);
        int submarine_len = 4;
        submarine = Bitmap.createScaledBitmap(submarine, width - gap, (submarine_len * width) - gap, false);

        patrol = BitmapFactory.decodeResource(getResources(), R.drawable.patrol);
        int patrol_len = 2;
        patrol = Bitmap.createScaledBitmap(patrol, width - gap, (patrol_len * width) - gap, false);


        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        ocean = Bitmap.createScaledBitmap(ocean, width - gap, width - gap, false);

        canvas.drawColor(Color.BLACK);

        // just logging things.
        Log.d("height", "" + height);
        Log.d("width", "" + width);


        // prepare for double nested loop to place ocean tiles
        for(int i = 0; i < 10; ++i)
        {
            for(int j = 0; j < 10; ++j)
            {
                canvas.drawBitmap(ocean, (gap / 2 ) + (i * width), 455 + (gap / 2) + (j * width), paint);
            }
        }


        // enter the matrix
        Matrix matrix = new Matrix();

        // rotate 90 degrees
        matrix.postRotate(90);

        // place boats
        carrier = Bitmap.createBitmap(carrier, 0, 0, carrier.getWidth(), carrier.getHeight(), matrix, true);
        placeBoat(canvas, carrier, 1, 1, width, gap);

        placeBoat(canvas, cruiser, 3, 3, width, gap);

        placeBoat(canvas, battleship, 9, 6, width, gap);

        placeBoat(canvas, patrol, 10, 2, width, gap);

        placeBoat(canvas, submarine, 7, 5, width, gap);

        // missile test!
        for(int k = 1; k < 11; ++k)
            placeMissile(canvas, k % 2 == 1, k, k, width, gap);

    }

    private void placeBoat(Canvas c, Bitmap boat, int i, int j, int width, int gap)
    {
        --i;
        --j;

        c.drawBitmap(boat, (gap / 2 ) + (i * width), 455 + (gap / 2) + (j * width), paint);
    }

    private void placeMissile(Canvas c, boolean hit, int i, int j, int width, int gap)
    {
        i = (i * 2) - 1;
        j = (j * 2) - 1;

        paint.setColor(hit ? Color.RED : Color.WHITE);

        paint.setStrokeWidth((width-10)/2);

        int halfSize = width/2;
        int gapSize = gap/2;

        c.drawPoint(gapSize + (i * halfSize), 455 + gapSize + (j * halfSize), paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }


}
