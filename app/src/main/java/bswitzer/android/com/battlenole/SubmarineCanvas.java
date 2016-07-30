package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class SubmarineCanvas extends BoatCanvas{
        public SubmarineCanvas(Context context, AttributeSet attrs) {
                super(context, attrs);

                boatLength = 3;

                boat = BitmapFactory.decodeResource(getResources(), R.drawable.submarine);
                boat = Bitmap.createScaledBitmap(boat, tileLength, boatLength * tileLength, false);

                this.setX(160f);
                this.setY(200f);
        }

        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                this.setImageBitmap(boat);
        }
}
