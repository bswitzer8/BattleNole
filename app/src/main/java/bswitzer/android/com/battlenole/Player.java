package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/22/2016.
 */
public class Player {

    String playerName_;
    int    gamesWon_;
    int    gamesLost_;

    Player() {

    }

    Player(String name) {
        SetPlayerName(name);
    }

    void SetPlayerName(String name) {
        this.playerName_ = name;
    }

    String GetPlayerName() {
        return playerName_;
    }


    void IncrementWinCount() {
        this.gamesWon_++;
    }

    int GetWinCount(){
        return gamesWon_;
    }

    void IncrementLostCount() {
        this.gamesLost_++;
    }

    int GetLostCount(){
        return gamesLost_;
    }

}
