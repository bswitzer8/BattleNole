package bswitzer.android.com.battlenole;

/**
 * Created by marv972228 on 7/22/2016.
 */
public class Player {

    String playerName_;
    int    gamesWon_;
    int    gamesLost_;

    // Empty Constructor
    Player() {
        SetPlayerName("NO NAME!");
        gamesLost_ = 0;
        gamesWon_  = 0;
    }

    // Constructor with name
    Player(String name) {
        SetPlayerName(name);
        gamesLost_ = 0;
        gamesWon_  = 0;
    }

    void SetPlayerName(String name) {
        this.playerName_ = name;
    }

    String GetPlayerName() {
        return playerName_;
    }

    void IncrementWinCount() {
        gamesWon_++;
    }

    int GetWinCount(){
        return gamesWon_;
    }

    void IncrementLostCount() {
        gamesLost_++;
    }

    int GetLostCount(){
        return gamesLost_;
    }

    void ResetCount() {
        gamesWon_ = 0;
        gamesLost_ = 0;
    }

}
