package bswitzer.android.com.battlenole;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/20/2016.
 *
 * source: http://www.androiddom.com/2012/03/creating-simple-custom-android-view.html
 */
public class BoardCanvas extends ImageView {

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

        int height = this.getMeasuredHeight();
        int width = this.getMeasuredWidth();

        // prepare for double nested loop to place images
    }
}
