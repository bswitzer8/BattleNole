package bswitzer.android.com.battlenole;

/**
 * Created by Ben on 7/7/2016.
 *
 *
 *
 * reset() - used to clear the board and set all states to
 * be game ready.
 *
 */



public class BoardManager {

    // Board Properties.
    final int HEIGHT = 10;
    final int WIDTH = 10;

    // the board array
    private Board[][] _board = new Board[HEIGHT][WIDTH];






    /*
     * I don't know what this does.
     */
    public void reset()
    {
        // reset the board array.
        for (Board b[] : _board)
            for (Board p: b)
                p.clear();

    }

}
