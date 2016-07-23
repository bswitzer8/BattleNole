package bswitzer.android.com.battlenole;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Ben on 7/23/2016.
 */
public class BattleshipCanvas extends BoatCanvas {

    public BattleshipCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        boatLength = 4;
    }
}