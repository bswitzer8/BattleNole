package bswitzer.android.com.battlenole;

import android.util.Log;

import java.util.Enumeration;

/**
 * Created by marv972228 on 7/21/2016.
 */
public class ShipM {

    private int         shipHits_;
    private String      shipName_;
    private BoardM.Type shipClass_;
    private String      frontPosition_;
    private String      backPosition_;
    private boolean     isAlive_;
    private String      playerName_;

    // Constructor
    public ShipM(String name, BoardM.Type sClass, BoardM board, Player player  ) {
        SetShipName(name);              // Player can name their ships
        SetShipHits(0);                 // initialize to 0
        SetShipClass(sClass);           // Ship class can be determined by length
        SetAlive(true);                 // Set to true initially
        SetPlayerOfShip(player);        // Associate ship to player
    }

    public void SetShipHits(int hits) {
        this.shipHits_ = hits;
    }

    public int GetShipHits() {
        return shipHits_;
    }

    public void IncrementShipHits () {
        ++shipHits_;
    }

    public void SetShipAliveStatus( ) {
        // if the size of the ship is equal to the hits, it means it's sunk!!!
        if (ReturnSizeOfShip(GetShipClass()) == GetShipHits()) {
            SetAlive(false);
        }
    }

    public void SetShipName(String n) {
        this.shipName_ = n;
    }

    public String GetShipName(){
        return shipName_;
    }

    public void SetShipClass(BoardM.Type sc) {
        this.shipClass_ = sc;
    }

    public BoardM.Type GetShipClass() {
        return shipClass_;
    }

    public void SetFrontPosition(String fp) {
        Log.d("FP: ", fp);
        this.frontPosition_ = fp;
    }

    public String GetFrontPosition(){
        return frontPosition_;
    }

    public void SetBackPosition(String bp) {
        Log.d("BP: ", bp);
        this.backPosition_ = bp;
    }

    public String GetBackPosition() {
        return backPosition_;
    }

    public void SetAlive(boolean status) {
        this.isAlive_ = status;
    }
    public boolean GetAlive() {
        return isAlive_;
    }

    public void SetPlayerOfShip(Player player) {
        this.playerName_ = player.GetPlayerName();
    }

    public String GetPlayerOfShip() {
        return playerName_;
    }

    public int ReturnSizeOfShip(BoardM.Type ship) {

        switch (ship) {
            case PATROL:
                return 2;
            case SUB:
            case DESTROYER:
                return 3;
            case BATTLESHIP:
                return 4;
            case CARRIER:
                return 5;
            default:
                return 1;
        }
    }

}
