package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/21/2016.
 */
public class ShipM {

    private int shipLength_;
    private String shipName_;
    private String shipClass_;
    private String frontPosition_;
    private String backPosition_;



    public ShipM(int length, String name, String sClass, String fp, String bp ) {
        SetShipLength(length);
        SetShipName(name);
        SetShipClass(sClass);
        SetFrontPosition(fp);
        SetBackPosition(bp);
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


}
