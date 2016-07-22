package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/21/2016.

 7/21/2016
 * This class is used to take an array of Ships, and a Board and analyze player input throughout the game.
     * Analyze valid ship positions then set ship positions
     * Analyze ongoing battle between players
        * If a ship has been hit
        * If a ship has been sunk
        * Current Game Score
        * End of game status
        * Player status (resign... )

 * Maybe we can change the game play and create a Salvo version too...


 */
public class GameLogic {

    // Ben's ENUM type

    public GameLogic() {

    }

    // Check if Ship is Sunk
    public boolean IsShipSunk(ShipM ship, BoardM board) {

        // Example, Carrier Front A1 Back A5
        String fp = ship.GetFrontPosition();
        String bp = ship.GetBackPosition();

        // Front of Ship position (X,Y)
        int FrontOfShipX = board.ReturnIntFromLetter(Character.toString(fp.charAt(0)));
        int FrontOfShipY = board.ReturnIntFromString(Character.toString(fp.charAt(1)));
        int BackOfShipX = board.ReturnIntFromLetter(Character.toString(bp.charAt(0)));
        int BackOfShipY = board.ReturnIntFromString(Character.toString(bp.charAt(1)));

        int length = ship.GetShipLength();

        // Horizontal Check of ship towards the right --------------------------------------------------------//
        if (fp.charAt(1) == bp.charAt(1)) {                                                                   //
            // Front of ship is to left and back towards right  < F * * B >                                   //
            if (FrontOfShipX < BackOfShipX) {                                                                 //
                for (int i = 0; i < length; ++i) {                                                            //
                    // Letter | Number | TYPE . Check the position if it compares                             //
                    if (!board.CompareBoardPosition(FrontOfShipX, FrontOfShipY, BoardM.Type.RED_MISSILE)) {   //
                        return false; // it is not sunk yet.                                                  //
                    }                                                                                         //
                    // increment to the right to check rest of ship                                           //
                    ++FrontOfShipX;                                                                           //
                }                                                                                             //
                ship.SetAlive(false); // Set ship to false indicating dead                                    //
                return true;          // all positions are Red, SHIP is SUNK!                                 //
            }                                                                                                 //
            // Front of ship is right and back towards left  < B * * F >                                      //
            else {                                                                                            //
                for (int i = 0; i < length; ++i ) {                                                           //
                    if (!board.CompareBoardPosition(BackOfShipX, BackOfShipY, BoardM.Type.RED_MISSILE)) {     //
                        return false; // not sunk yet                                                         //
                    }                                                                                         //
                    ++BackOfShipX;                                                                            //
                }                                                                                             //
                ship.SetAlive(false); // Set ship to false indicating dead                                    //
                return true;          // all positions are Red, SHIP is SUNK!                                 //
            }                                                                                                 //
        }                                                                                                     //
        // ---------------------------------------------------------------------------------------------------//

        // Example: Ship at D2 - D5 size 3
        // Vertical Check of Ship towards the bottom ************************************************************
        if (fp.charAt(0) == bp.charAt(0)) {
            // Front of ship is at top and back towards bottom
            if (FrontOfShipY < BackOfShipY) {
                for (int j = 0; j < length; ++j) {
                    // Go through tiles and check if it is hit
                    if (!board.CompareBoardPosition(FrontOfShipX, FrontOfShipY, BoardM.Type.RED_MISSILE)) {
                        return false;
                    }
                    // increment ship check to bottom of grid, check rest of ship
                    ++FrontOfShipY;
                }

                ship.SetAlive(false); // set ship to false indicating
                return true;          // All positions are Red, Ship is SUNK
            }
            // Front of ship at bottom and back at top
            else{
                for (int i = 0; i < length; ++i) {
                    if (!board.CompareBoardPosition(BackOfShipX, BackOfShipY, BoardM.Type.RED_MISSILE)) {
                        return false; // not sunk yet
                    }
                    ++BackOfShipY;
                }
                ship.SetAlive(false); // Set ship to false indicating it is sunk
                return true;          // All positions are Red, SHIP IS SUNK!!
            }
        }
        // *******************************************************************************************************

        // Default case
        return false;
    }

    public int HowManyShipsSunk(ShipM[] ship, int shipTotal) {

        int shipsSunk = 0;

        for (int i = 0; i < shipTotal; ++i) {
            if (!ship[i].GetAlive()) {          // if ship is dead increment counter
                ++shipsSunk;
            }
        }
        return shipsSunk;
    }


}
