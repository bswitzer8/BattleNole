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

import java.util.Random;

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

    private Random brain = new Random();

    private BoardM board1_;
    private BoardM board2_;
    private ShipM[] shipPlayer1_;
    private ShipM[] shipPlayer2_;
    private GameLogic gameLogic_;

    private String winner = "";
    private String winningText = "Player 1 Wins!!";
    private String winningText2 = "Player 1 Wins!!";


    // please don't tell anyone i did this.
    private MiniMapCanvas minimap = null;

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
                        canvas.drawPoint(gapSize + ((i+1) * width) - halfSize, (455+width) + gapSize + (j * width) - halfSize, paint);

                }
            }



            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
        }

        if(!winner.isEmpty())
        {
            paint.setColor(Color.BLACK);
            paint.setTextSize(width);
            canvas.drawText(winner, this.getMeasuredWidth()/4, this.getMeasuredHeight()/2, paint);
        }

    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        if(!gameActive) return false;

        BoardM.Type tile; // need this for switch case

        switch(event.getAction()) {


            case MotionEvent.ACTION_UP:


                    x = -1;
                    for(int i = 0; i < this.getWidth()+width; i += width)
                    {
                        if(i >= event.getRawX() && event.getRawX() < (i + width))
                            break;

                        ++x;
                    }

                    y = -1;
                    for(int i = 700; i < this.getHeight() + width*1.5; i += width)
                    {
                        if(i >= event.getRawY() && event.getRawY() < (i + width))
                            break;

                        ++y;
                    }


                    //Log.d("x,y", "" + x + ", " + y + " : (" + event.getRawX() + ", " + event.getRawY() + ")");

                    if(x >= 0 && x < 10 && y >= 0 && y < 10)
                    {

                        // Determine if ship hit test for hitting your own board *******************
                        tile = board1_.GetBoard()[x][y];

                        String dumber = (char)(x + 65) + "" + y;

                        switch (tile) {
                            case PATROL:
                            case SUB:
                            case DESTROYER:
                            case BATTLESHIP:
                            case CARRIER:
                                hits[x][y] = 1;
                                break;
                            default:
                                hits[x][y] = -1;
                                break;
                        }
                        // *************************************************************************


                    }

                    boolean keepTrying = false;
                    do {
                        int compX = brain.nextInt(9);
                        int compY = brain.nextInt(9);

                        String dumb = (char)(compX + 65) + "" + compY;
                        Log.d("test", dumb);

                        tile = board2_.GetBoard()[compX][compY];
                        switch (tile) {
                            case WHITE_MISSILE:
                            case RED_MISSILE:
                                keepTrying = true;
                                break;
                            case PATROL:
                            case SUB:
                            case DESTROYER:
                            case BATTLESHIP:
                            case CARRIER:

                                minimap.hit(compY, compX, 1);
                                board2_.SetBoardPosition(dumb, shipPlayer2_);





                                break;
                            default:

                                minimap.hit(compY, compX, -1);
                                break;
                        }
                    } while(keepTrying);


                    Log.d("player 1", "boats sank = " + gameLogic_.HowManyShipsSunk(shipPlayer1_));
                    Log.d("player 2", "boats sank = " + gameLogic_.HowManyShipsSunk(shipPlayer2_));

                    if(gameLogic_.HowManyShipsSunk(shipPlayer1_) == 5  )
                    {
                        Log.d("WINNER", "player 1 wins");

                        // game over
                        winner = winningText;
                        setEnabled(false);

                    }
                    if(gameLogic_.HowManyShipsSunk(shipPlayer2_) == 5)
                    {
                        Log.d("WINNER", "player 2 wins");

                        // game over
                        winner = winningText2;
                        setEnabled(false);

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


    public void SetBoardObjects(ShipM[] ship1, ShipM[] ship2, BoardM board1, BoardM board2, GameLogic gameLogic, MiniMapCanvas mini) {
        board1_ = board1;
        board2_ = board2;
        shipPlayer1_ = ship1;
        shipPlayer2_ = ship2;
        gameLogic_ = gameLogic;
        minimap = mini;

    }

}
