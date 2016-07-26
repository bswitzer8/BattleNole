package bswitzer.android.com.battlenole;

import java.util.Enumeration;

/**
 * Created by marv972228 on 7/21/2016.
 */
public class ShipM {

    public enum ShipType {
        PATROAL,
        SUB,
        DESTROYER,
        BATTLESHIP,
        CARRIER
    };

    static public final int max_length = 6;
    static public final int minimum_length = 1;

    private int        shipLength_;
    private String     shipName_;
    private ShipType   shipClass_;
    private String     frontPosition_;
    private String     backPosition_;
    private boolean    isAlive_;
    private String     playerName_;

    public ShipM(int length, String name, ShipType sClass, boolean alive, Player player  ) {

        // Quick check to put ship within bounds of appropriate size
        if (length > max_length || length < minimum_length)
            SetShipLength(1);        // Default ship value
        else
            SetShipLength(length);   // Create the length of the ship object

        SetShipName(name);       // Player can name their ships
        SetShipClass(sClass);    // Ship class can be determined by length
        SetAlive(alive);         // Determine if ship is alive
        SetPlayerOfShip(player); // Associate ship to player
    }


    public void SetShipLength(int length) {
        this.shipLength_ = length;
    }

    public int GetShipLength() {
        return shipLength_;
    }

    public void SetShipName(String n) {
        this.shipName_ = n;
    }

    public String GetShipName(){
        return shipName_;
    }

    public void SetShipClass(ShipType sc) {
        this.shipClass_ = sc;
    }

    public ShipType GetShipClass() {
        return shipClass_;
    }

    public void SetFrontPosition(String fp) {
        this.frontPosition_ = fp;
    }

    public String GetFrontPosition(){
        return frontPosition_;
    }

    public void SetBackPosition(String bp) {
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

}
