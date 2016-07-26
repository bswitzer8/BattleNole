package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class BattleshipCanvas extends BoatCanvas {

    public BattleshipCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        boatLength = 4;

        boat = BitmapFactory.decodeResource(getResources(), R.drawable.battleship);
        boat = Bitmap.createScaledBitmap(boat, tileLength, boatLength * tileLength, false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.setImageBitmap(boat);
    }
}
