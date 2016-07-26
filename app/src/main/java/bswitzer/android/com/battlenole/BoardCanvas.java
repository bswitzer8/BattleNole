package bswitzer.android.com.battlenole;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
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
public class BoardCanvas extends ImageView implements View.OnTouchListener {

    private Bitmap ocean;

    private static Paint paint;

    public BoardCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        setOnTouchListener(this);
    }


    int height = 0, width =0;
    Canvas c;
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        c = canvas;
        // make the dimensions 1/10th of the board.
        height = this.getMeasuredHeight() / 10;
        width = this.getMeasuredWidth() / 10;
        // gap size is 5.
        int gap = 5;


        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        ocean = Bitmap.createScaledBitmap(ocean, width - gap, width - gap, false);

        canvas.drawColor(Color.BLACK);

        // just logging things.
        Log.d("height", "" + height);
        Log.d("width", "" + width);


        // prepare for double nested loop to place ocean tiles
        for(int i = 0; i < 10; ++i)
            for(int j = 0; j < 10; ++j)
                canvas.drawBitmap(ocean, (gap / 2 ) + (i * width), 455 + (gap / 2) + (j * width), paint);

    }


    float x = 0.0f, y = 0.0f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View view, MotionEvent event)
    {

        return true;
    }

 /*
    private void placeBoat(Canvas c, Bitmap boat, int i, int j, int width, int gap)
    {
        --i;
        --j;

        c.drawBitmap(boat, (gap / 2 ) + (i * width), 455 + (gap / 2) + (j * width), paint);
    }    */

    private void placeMissile(Canvas c, boolean hit, int i, int j, int width, int gap)
    {
       // i = (i * 2) - 1;
       // j = (j * 2) - 1;

        paint.setColor(hit ? Color.RED : Color.WHITE);

        paint.setStrokeWidth((width-10)/2);

        int halfSize = width/2;
        int gapSize = gap/2;

       // c.drawPoint(gapSize + (i * halfSize), 455 + gapSize + (j * halfSize), paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }


}
