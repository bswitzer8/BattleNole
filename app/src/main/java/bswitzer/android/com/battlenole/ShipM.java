package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/21/2016.
 */
public class ShipM {

    private int      shipLength_;
    private String   shipName_;
    private String   shipClass_;
    private String   frontPosition_;
    private String   backPosition_;
    private boolean  isAlive_;

    public ShipM(int length, String name, String sClass, String fp, String bp, boolean alive  ) {
        SetShipLength(length);   // Create the length of the ship object
        SetShipName(name);       // Player can name their ships
        SetShipClass(sClass);    // Ship class can be determined by length
        SetFrontPosition(fp);    // Front of ship position
        SetBackPosition(bp);     // Back of ship position
        SetAlive(alive);         // Determine if ship is alive
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

    public void SetShipClass(String sc) {
        this.shipClass_ = sc;
    }

    public String GetShipClass() {
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

    // Horizontal Check of Ship

    // Vertical Check of Ship




    // Helper Functions to conver string of player position


}
