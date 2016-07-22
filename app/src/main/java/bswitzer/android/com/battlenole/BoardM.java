package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/21/2016.

 Log:

 7/21/2016

 * Here is my idea of how to the Board combined with yours Ben, I think it's just easier to do the
   state and the board in one object. Rather than each position be a separate object

 * I think we also have to set the


 */
public class BoardM {

    // Ben's ENUM type
    public enum Type {
        BLANK,
        WHITE_MISSILE,
        RED_MISSILE,
        BOAT
    }

    int size_;                 // used to determine the size of the board
    private Type[][] board_; // variable to hold the board multi dimensional array, initialize it later.

    // Constructor
    public BoardM(int size) {
        // Set Size
        SetSize(size);
        // set Board
        CreateBoard(size);
    }

    // Create Board
    public void CreateBoard(int size) {
        board_ = new Type[size][size]; // Create the board
        SetBoardBlank();               // Make the board Enum Type Blank.
    }

    // SIZE **********************************************
    public int GetSize() {
        return size_;
    }

    public void SetSize(int size) {
        this.size_ = size;
    }
    // ***************************************************/


    // BOARD **********************************************

    // Get Board
    public Type[][] GetBoard() {
        return board_;
    }
    // Set as blank, used for Reset too
    public void SetBoardBlank() {
        for (int i = 0; i < GetSize(); ++i) {
            for (int j = 0; j < GetSize(); ++j) {
                GetBoard()[i][j] = Type.BLANK;
            }

        }
    }

    // Set Board position
    public void SetBoardPosition(Type selection, String x, int y) {
        // Can only set the position if the board piece is blank
        if (GetBoard()[ReturnIntFromLetter(x)][y] == Type.BLANK)
            GetBoard()[ReturnIntFromLetter(x)][y] = selection;
    }

    // Get Board Full Status, if all are blank, return true
    public boolean IsBoardFull() {
        for (int i = 0; i < GetSize(); ++i) {
            for (int j = 0; j < GetSize(); ++j) {
                if (GetBoard()[i][j] != Type.BLANK)
                    return false;
            }
        }
        return true;
    }

    // ADDITIONAL FUNCTIONS **************************

    // Hard coded letter return for number, not sure if any class built in has this feature already
    // used for the Board multi dimensional array
    public int ReturnIntFromLetter(String s1) {
        int t1 = 0;
        switch (s1) {
            case "A":
                t1 = 0;
                break;
            case "B":
                t1 = 1;
                break;
            case "C":
                t1 = 2;
                break;
            case "D":
                t1 = 3;
                break;
            case "E":
                t1 = 4;
                break;
            case "F":
                t1 = 5;
                break;
            case "G":
                t1 = 6;
                break;
            case "H":
                t1 = 7;
                break;
            case "I":
                t1 = 8;
                break;
            case "J":
                t1 = 9;
                break;
            default:
                t1 = 0;
                break;
        }
        return t1;

    }
}