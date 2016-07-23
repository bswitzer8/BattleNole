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
        ENEMY_PLAYER
    }

    int              size_;         // used to determine the size of the board
    int              turn_;
    int              maxTurns_;     // maximum amount of turns
    String           boardPlayer_;  //
    private Type[][] board_;        // multidimensional array to hold the board values of each tile, initialize it later.

    // Constructor
    public BoardM(int size, Player player) {
        // Set Size
        SetSize(size);
        // set Board
        CreateBoard(size);
        // Set Turn
        SetTurn(0); // initial value
        // Set Max Turns
        SetMaxTurns(size);
        // Set who owns this board player
        this.boardPlayer_ = player.GetPlayerName();
    }

    // Create Board
    public void CreateBoard(int size) {
        // Horizontal Values: "A" starts at int value of 0    A - J  = 0 - 9
        // Vertical Values:  "1" starts at in value of 0      1 - 10 = 0 - 9

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

    // TURN ------------------------------------------------
    // Just need to compare if the move is valid before using turn, can be done
    // in main, or it can be done here, but I think it's simpler to keep like this.
    public int GetTurn() {
        return turn_;
    }

    public void SetTurn(int turn) {
        this.turn_ = turn;
    }

    public void IncrementTurn() {
        turn_++;
    }

    public void DecrementTurn() {
        turn_--;
    }

    public void ResetTurn() {
        turn_ = 0;
    }

    // ---------------------------------------------------

    public void SetMaxTurns(int size) {
        this.maxTurns_ = size * size;
    }

    public int GetMaxTurns() {
        return maxTurns_;
    }


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


    // Set Board position, return false if player does not hit, return true if ship is hit
    // Example: SetBoardPosition("A1"); SetBoardPosition("B2");
    public boolean SetBoardPosition(String position) {

        int xPos = ReturnIntFromLetter(Character.toString(position.charAt(0)));  // get X position
        int yPos = ReturnIntFromString(Character.toString(position.charAt(1)));  // get Y Position

        Type selection = GetBoard()[xPos][yPos];

        switch (selection) {
            case BLANK:                // Player selects open seas, miss
                IncrementTurn();       // Increment player turn
                GetBoard()[xPos][yPos] = Type.WHITE_MISSILE;
                return false;
            case WHITE_MISSILE:        // Player selects already selected space
            case RED_MISSILE:          // Player slects already selected space
                return false;
            case ENEMY_PLAYER:               // Player hits ship.
                IncrementTurn();       // Increment player turn
                GetBoard()[xPos][yPos] = Type.RED_MISSILE;
                return true;
            default:
                break;
        }

        /*
        if (GetBoard()[ReturnIntFromLetter(x)][y] == Type.BLANK) {
            GetBoard()[ReturnIntFromLetter(x)][y] = selection;
            return true;
        }
        else {
            return false;
        }
        */
        return false;
    }

    public Type GetBoardPositionValue(String position) {
        int xPos = ReturnIntFromLetter(Character.toString(position.charAt(0)));  // get X position
        int yPos = ReturnIntFromString(Character.toString(position.charAt(1)));  // get Y Position
        Type selection = GetBoard()[xPos][yPos];
        return selection;
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

    // No more moves .... most likely will never be reached as most games end before max
    public boolean IsBoardFullCount() {
        if (GetTurn() == (GetMaxTurns() - 1)) // I think 99 is 100, since count starts at 0??
            return true;
        else
            return false;
    }

    // Set Ship tile positions. Takes an array of ships and applies their positions on the board
    public void SetShipBoardTile(ShipM[] ship, int shipCount) {

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
            FrontOfShipX = ReturnIntFromLetter(Character.toString(fp.charAt(0)));
            FrontOfShipY = ReturnIntFromString(Character.toString(fp.charAt(1)));
            BackOfShipX = ReturnIntFromLetter(Character.toString(bp.charAt(0)));
            BackOfShipY = ReturnIntFromString(Character.toString(bp.charAt(1)));

            // Horizontal Set ----------------------------------------------------------------------
            // Example A2 - C2, ship of size 3
            if (fp.charAt(1) == bp.charAt(1)) {
                // Front of ship is on left
                if (FrontOfShipX < BackOfShipX) {
                    for (int j = 0; j < shipLength; ++j) {
                        GetBoard()[FrontOfShipX][FrontOfShipY] = Type.ENEMY_PLAYER;
                        ++FrontOfShipX;
                    }
                }
                // Front fo ship is on right
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        GetBoard()[BackOfShipX][BackOfShipY] = Type.ENEMY_PLAYER;
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
                        GetBoard()[FrontOfShipX][FrontOfShipY] = Type.ENEMY_PLAYER;
                        ++FrontOfShipY;
                    }

                }
                // Front of ship is at bottom and back at top
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        GetBoard()[BackOfShipX][BackOfShipY] = Type.ENEMY_PLAYER;
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
    // For us in multidimensional array, one off
    public int ReturnIntFromString(String yVal) {
        int num;
        switch (yVal) {
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