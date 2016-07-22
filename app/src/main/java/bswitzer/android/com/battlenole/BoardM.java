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
        PLAYER
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


    // Set Board position, return false if player selects position not blank
    public boolean SetBoardPosition(Type selection, String x, int y) {
        // Can only set the position if the board piece is blank
        if (GetBoard()[ReturnIntFromLetter(x)][y] == Type.BLANK) {
            GetBoard()[ReturnIntFromLetter(x)][y] = selection;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean CompareBoardPosition(int x, int y, Type t1) {
        if (GetBoard()[x][y] == t1)
            return true;
        return false;
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

    // Set Ship tile positions. Takes an array of ships and applies their positions on the board
    public void SetShipBoardTile(ShipM[] ship, int shipCount, BoardM board) {

        String fp;
        String bp;
        int FrontOfShipX;
        int FrontOfShipY;
        int BackOfShipX;
        int BackOfShipY;
        int shipLength;

        // Sequence through ship array and set board to player positions
        for (int i = 0; i < shipCount; ++i ) {
            shipLength = ship[i].GetShipLength();
            fp = ship[i].GetFrontPosition();
            bp = ship[i].GetBackPosition();
            FrontOfShipX = board.ReturnIntFromLetter(Character.toString(fp.charAt(0)));
            FrontOfShipY = board.ReturnIntFromString(Character.toString(fp.charAt(1)));
            BackOfShipX = board.ReturnIntFromLetter(Character.toString(bp.charAt(0)));
            BackOfShipY = board.ReturnIntFromString(Character.toString(bp.charAt(1)));

            // Horizontal Set ----------------------------------------------------------------------
            // Example A2 - C2, ship of size 3
            if (fp.charAt(1) == bp.charAt(1)) {
                // Front of ship is on left
                if (FrontOfShipX < BackOfShipX) {
                    for (int j = 0; j < shipLength; ++j) {
                        board.GetBoard()[FrontOfShipX][FrontOfShipY] = Type.PLAYER;
                        ++FrontOfShipX;
                    }

                }
                // Front fo ship is on right
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        board.GetBoard()[BackOfShipX][BackOfShipY] = Type.PLAYER;
                        ++BackOfShipX;
                    }
                }
            }
            // -------------------------------------------------------------------------------------

            // Vertical Check of Ship towards the bottom *******************************************
            if (fp.charAt(0) == bp.charAt(1)) {
                // Front of ship is at top and back towards bottom
                if (FrontOfShipY < BackOfShipY ){
                    for (int j = 0; j < shipLength; ++j) {
                        board.GetBoard()[FrontOfShipX][FrontOfShipY] = Type.PLAYER;
                        ++FrontOfShipY;
                    }

                }
                // Front of ship is at bottom and back at top
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        board.GetBoard()[BackOfShipX][BackOfShipY] = Type.PLAYER;
                        ++BackOfShipY;
                    }
                }
            }
            // *************************************************************************************
        }
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

    public int ReturnIntFromString(String x) {
        int num;
        switch (x) {
            case "0":
                num = 0;
                break;
            case "1":
                num = 0;
                break;
            case "2":
                num = 1;
                break;
            case "3":
                num = 2;
                break;
            case "4":
                num = 3;
                break;
            case "5":
                num = 4;
                break;
            case "6":
                num = 5;
                break;
            case "7":
                num = 6;
                break;
            case "8":
                num = 7;
                break;
            case "9":
                num = 8;
                break;
            case "10":
                num = 9;
            default:
                num = 0;

        }

        return num;
    }
}