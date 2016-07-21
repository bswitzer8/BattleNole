


package bswitzer.android.com.battlenole;


/**
 * Created by Ben on 7/7/2016.
 *
 * This will be used to manage the position on the board
 * The ENUM Type has 4 options:
 * BLANK (Default), WHITE_MISSILE (MISS), RED_MISSILE (HIT), BOAT
 *
 * clear() - sets the _tile to BLANK
 *
 * hit() - checks if there is a boat at this position
 * If so, let's put a RED_MISSISLE here, else WHITE_MISSILE
 *
 * getTile() - returns the value of _tile.
 *
 * setBoat() - Puts a boat piece at this position.
 *
 *
 *
 */
public class Board {

    public enum Type
    {
        BLANK,
        WHITE_MISSILE,
        RED_MISSILE,
        BOAT
    }

    // What is occupying this tile?
    private Type _tile;


    /*
     * Public Constructor
     */
    public Board()
    {
        _tile = Type.BLANK;
    }

    /*
     * Sets position to BLANK
     */
    public void clear()
    {
        _tile = Type.BLANK;
    }

    /*
     * Call this method when the position has been hit.
     * If there is a boat on this piece, change the tile to Red Missile
     * else it was a miss, so make it white.
     */
    public boolean hit()
    {
        if(_tile == Type.BOAT)
        {
            _tile = Type.RED_MISSILE;
            return true;
        }
        else
        {
            _tile = Type.WHITE_MISSILE;
            return false;
        }
    }


    /*
     * Sets this position to a boat.
     */
    public void setBoat()
    {
        _tile = Type.BOAT;
    }


    /*
     * Returns the tile of this position.
     */
    public Type getTile()
    {
        return _tile;
    }
}
