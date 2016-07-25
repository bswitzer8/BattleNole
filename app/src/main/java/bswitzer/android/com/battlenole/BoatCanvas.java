package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Ben on 7/23/2016.
 */
public class BoatCanvas extends ImageView {

    public Bitmap boat;

    protected int boatLength;

    Matrix matrix = new Matrix();


    boolean boatRotated = false;

    Paint paint = new Paint();

    public BoatCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void resize(int width, int gap)
    {
        boat =  Bitmap.createScaledBitmap(boat, width - gap, (boatLength * width) - gap, false);
    }


    public void rotate()
    {
        // toggle back and forth between the two.
        matrix.postRotate(boatRotated ? 90 : -90);
        // rotate that bitmap
        boat = Bitmap.createBitmap(boat, 0, 0, boat.getWidth(), boat.getHeight(), matrix, true);

        // flip it
        boatRotated = !boatRotated;
    }

}
