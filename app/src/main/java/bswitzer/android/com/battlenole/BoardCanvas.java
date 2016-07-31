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

    private boolean gameActive = false;

    private  int x = 0, y = 0;

    private boolean hit = false;

    public int height = 0, width = 0;

    private int[][] hits = new int[10][10];


    public BoardCanvas( Context context, AttributeSet attrs ) {
        super( context, attrs );



        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        setOnTouchListener(this);
    }




    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // gap size is 5.
        int gap = 5;


        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        ocean = Bitmap.createScaledBitmap(ocean, width - gap, width - gap, false);

        canvas.drawColor(Color.BLACK);

        height = this.getMeasuredHeight() / 10;
        width = this.getMeasuredWidth() / 10;


        // prepare for double nested loop to place ocean tiles
        for (int i = 0; i < 10; ++i)
            for (int j = 0; j < 10; ++j)
                canvas.drawBitmap(ocean, (gap / 2) + (i * width), 455 + (gap / 2) + (j * width), paint);

        if(gameActive)
        {

            int halfSize = width/2;
            int gapSize = 5/2;

            paint.setStrokeWidth((width-10)/2);
            for (int i = 0; i < 10; ++i)
            {
                for (int j = 0; j < 10; ++j)
                {
                    paint.setColor(hits[i][j] == 1 ? Color.RED : Color.WHITE);
                    if(hits[i][j] != 0)
                        canvas.drawPoint(gapSize + (i * width) - halfSize, 455 + gapSize + (j * width) - halfSize, paint);

                }
            }



            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
        }

    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        if(!gameActive) return false;


        switch(event.getAction()) {


            case MotionEvent.ACTION_UP:
                    ++x;
                    ++y;

                    x = (int)(event.getRawX() -5/2 + width/2)/width;
                    y = (int)((event.getRawY() - 455 - 5/2)/ width);

                    Log.d("x,y", "" + x + ", " + y);

                    if(x >= 0 && x < 10 && y >= 0 && y < 10)
                    {
                        hits[x][y] = x % 2 == 0 ? 1 : -1;
                    }

                    invalidate();
                break;
        }


        return true;
    }


    private void placeMissile(boolean h, int i, int j)
    {

        hits[i][j] = h ? 1 : -1;

        invalidate();
    }



    public void setGameActive(boolean b)
    {
        gameActive = b;
    }


}
