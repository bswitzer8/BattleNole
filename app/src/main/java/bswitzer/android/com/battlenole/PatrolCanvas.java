package bswitzer.android.com.battlenole;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class PatrolCanvas extends BoatCanvas{

    public PatrolCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        boatLength = 2;
        shipType_ = BoardM.Type.PATROL;

        boat = BitmapFactory.decodeResource(getResources(), R.drawable.patrol);
        boat = Bitmap.createScaledBitmap(boat, tileLength, boatLength * tileLength, false);

        this.setX(400f);
        this.setY(250f);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.setImageBitmap(boat);
    }


}
