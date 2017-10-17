package edu.eci.arsw.movecatchandwin.model;

import java.util.ArrayList;

/**
 *
 * @author KevinMendieta
 */
public class Room {

    private int id;
    private Player winner;
    private ArrayList<Player> players;

    public Room(int id) {
        this.players = new ArrayList<Player>();
        this.id = id;
    }

    /**
     * Sets the winner of the room.
     * @param winner The player who is a winner.
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Add a player to the room.
     * @param player New player of the room.
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * @return The id of the room.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The winner of the room.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * @return players of the room.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
