package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class CarrierCanvas extends BoatCanvas {

    public CarrierCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        boatLength = 5;

        boat = BitmapFactory.decodeResource(getResources(), R.drawable.carrier);
        boat = Bitmap.createScaledBitmap(boat, tileLength, boatLength * (tileLength), false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.setImageBitmap(boat);
    }
}
