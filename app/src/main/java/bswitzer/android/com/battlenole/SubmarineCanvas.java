package bswitzer.android.com.battlenole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class SubmarineCanvas extends BoatCanvas {



    public SubmarineCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        boatLength = 3;

        boat =  BitmapFactory.decodeResource(getResources(), R.drawable.submarine);
    }


}