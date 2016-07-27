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
        BLANK,              // used to indicate open seas
        WHITE_MISSILE,      // represents a miss by enemy player
        RED_MISSILE,        // represents a hit by enemy player
        PLAYER,             // represents player ship in tile space
        PATROL,             // size 2
        SUB,                // size 3
        DESTROYER,          // size 3
        BATTLESHIP,         // size 4
        CARRIER             // size 5
    }

    // Variables ...................................................................................
    int              size_;         // used to determine the size of the board
    int              turn_;         // also represents tiles taken as each turn is one tile
    int              maxTurns_;     // maximum amount of turns

    String           boardPlayer_;  // name of player who owns this board
    private Type[][] board_;        // multidimensional array to hold the board values of each tile, initialize it later.
    private Type[][] boardEnemyDisplay_; // used to display board to enemy


    // ...........................................................................................*/

    // Constructor
    public BoardM(int size, Player player) {
        SetSize(size);          // Set Size
        CreateBoard(size);      // set Board
        SetTurn(0);             // Set Turn
        SetMaxTurns(size);      // Set Max Turns
        this.boardPlayer_ = player.GetPlayerName(); // Set who owns this board player
    }

    // Create Board
    private void CreateBoard(int size) {
        // Horizontal Values: "A" starts at int value of 0    A - J  = 0 - 9
        // Vertical Values:  "1" starts at in value of 0      1 - 10 = 0 - 9

        board_ = new Type[size][size];             // Create the board
        boardEnemyDisplay_ = new Type[size][size]; // Display board to enemy
        SetBoardBlank();                           // Make the board Enum Type Blank.
    }

    // SIZE **********************************************
    public int GetSize() {
        return size_;
    }

    private void SetSize(int size) {
        this.size_ = size;
    }
    // ***************************************************/

    // TURNS ------------------------------------------------
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

    private void SetMaxTurns(int size) {
        this.maxTurns_ = size * size;
    }

    public int GetMaxTurns() {
        return maxTurns_;
    }

    public boolean IsMaxTurnsReached(){
        if (GetTurn() == GetMaxTurns() - 1)
            return true;
        else
            return false;
    }
    // ---------------------------------------------------

    // BOARD **********************************************

    // Get Board
    private Type[][] GetBoard() {
        return board_;
    }

    public void SetBoardByTypePos(int xPos, int yPos, Type selection) {
        GetBoard()[xPos][yPos] = selection;
    }

    // Get enemy board
    private Type[][] GetEnemyBoardDisplay() {
        return boardEnemyDisplay_;
    }

    public void SetBoardEnemyByTypePos(int xPos, int yPos, Type selection) {
        GetEnemyBoardDisplay()[xPos][yPos] = selection;
    }
    // Set as blank for both boards, used for Reset too
    public void SetBoardBlank() {
        for (int i = 0; i < GetSize(); ++i) {
            for (int j = 0; j < GetSize(); ++j) {
                GetBoard()[i][j] = Type.BLANK;
                GetEnemyBoardDisplay()[i][j] = Type.BLANK;
            }
        }
    }
    // Set Ship tile positions. Takes an array of ships and applies their positions on the board
    public void SetShipBoardTile(ShipM[] ship, int shipCount) {

        String fp;
        String bp;
        Type shipType;
        int FrontOfShipX;
        int FrontOfShipY;
        int BackOfShipX;
        int BackOfShipY;
        int shipLength;

        // Sequence through ship array and set board to player positions
        for (int i = 0; i < shipCount; ++i ) {
            shipType = ship[i].GetShipClass();
            shipLength = ship[i].ReturnSizeOfShip(shipType);
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
                        SetTileOfBoard(ship[i], FrontOfShipX, FrontOfShipY); // Set tile to ship type
                        IncrementTurn();                                     // Need to acknowledge space being taken by ships
                        ++FrontOfShipX;
                    }
                }
                // Front fo ship is on right
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        SetTileOfBoard(ship[i], FrontOfShipX, FrontOfShipY); // Set tile to ship type
                        IncrementTurn();                                     // Need to acknowledge space being taken by ships
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
                        SetTileOfBoard(ship[i], FrontOfShipX, FrontOfShipY); // Set tile to ship type
                        IncrementTurn(); // Need to acknowledge space being taken by ships
                        ++FrontOfShipY;
                    }
                }
                // Front of ship is at bottom and back at top
                else {
                    for (int j = 0; j < shipLength; ++j) {
                        SetTileOfBoard(ship[i], FrontOfShipX, FrontOfShipY); // Set tile to ship type
                        IncrementTurn(); // Need to acknowledge space being taken by ships
                        ++BackOfShipY;
                    }
                }
            }
            // *************************************************************************************
        }
    }

    // Set tile to appropriate enum value on board.
    public void SetTileOfBoard(ShipM ship, int FrontOfShipX, int FrontOfShipY) {
        Type shipType = ship.GetShipClass();
        switch(shipType) {
            case PATROL:
                GetBoard()[FrontOfShipX][FrontOfShipY] = Type.PATROL;
                break;
            case SUB:
                GetBoard()[FrontOfShipX][FrontOfShipY] = Type.SUB;
                break;
            case DESTROYER:
                GetBoard()[FrontOfShipX][FrontOfShipY] = Type.DESTROYER;
                break;
            case BATTLESHIP:
                GetBoard()[FrontOfShipX][FrontOfShipY] = Type.BATTLESHIP;
                break;
            case CARRIER:
                GetBoard()[FrontOfShipX][FrontOfShipY] = Type.CARRIER;
                break;
            default:
                break;
        }

    }

    // Set Board position of user selection, return false if player does not hit, return true if ship is hit
    // Example: SetBoardPosition("A1", player1ships); SetBoardPosition("B2", player2ships);
    // Used for game play action
    public boolean SetBoardPosition(String position, ShipM[] ships) {

        int xPos = ReturnIntFromLetter(Character.toString(position.charAt(0)));  // get X position
        int yPos = ReturnIntFromString(Character.toString(position.charAt(1)));  // get Y Position
        Type selection = GetBoardPositionValue(position);               // get tile value

        switch (selection) {
            case BLANK:                                                 // Player selects open seas, miss
                IncrementTurn();                                        // Increment player turn
                SetBoardByTypePos(xPos, yPos, Type.WHITE_MISSILE);      // Set tile to white missile
                SetBoardEnemyByTypePos(xPos, yPos, Type.WHITE_MISSILE); // Set tile to white missile
                return false;
            case WHITE_MISSILE:                                         // Player selects already selected space
            case RED_MISSILE:                                           // Player selects already selected space
                return false;

            // Increase ship count hits, turns, and set tile to red missile
            case PATROL:
                UpdateShipTileInfo(ships[0], xPos, yPos);
                return true;
            case SUB:
                UpdateShipTileInfo(ships[1], xPos, yPos);
                return true;
            case DESTROYER:
                UpdateShipTileInfo(ships[2], xPos, yPos);
                return true;
            case BATTLESHIP:
                UpdateShipTileInfo(ships[3], xPos, yPos);
                return true;
            case CARRIER:
                UpdateShipTileInfo(ships[4], xPos, yPos);
                return true;
            default:
                break;
        }

        return false; // some random tile not set is selected, should not happen
    }

    public void UpdateShipTileInfo(ShipM ship, int xPos, int yPos) {
        if (ship.GetAlive())                                    // only update if alive
            ship.IncrementShipHits();                           // increase the ship count hits
        ship.SetShipAliveStatus();                              // Check if ship is dead
        IncrementTurn();                                        // Increment board turn
        SetBoardByTypePos(xPos, yPos, Type.RED_MISSILE);        // set Tile To red missile
        SetBoardEnemyByTypePos(xPos, yPos, Type.RED_MISSILE);   // set to red missile
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


    // No more moves .... most likely will never be reached as most games end before max
    public boolean IsBoardFullCount() {
        if (GetTurn() == (GetMaxTurns() - 1)) // I think 99 is 100, since count starts at 0??
            return true;
        else
            return false;
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