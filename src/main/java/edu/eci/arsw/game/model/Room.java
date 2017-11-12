package edu.eci.arsw.game.model;

import java.util.ArrayList;

/**
 *
 * @author KevinMendieta
 */
public class Room {

    private int id, capacity;
    private Player winner;
    private ArrayList<Player> players;

    public Room(int id, int capacity) {
        this.players = new ArrayList<>();
        this.id = id;
        this.capacity = capacity;
    }
    
    public Room() {}

    /**
     * Checks if a player is already on the room
     * @param player the player
     * @return true if the player is on room otherwise false
     */
    public boolean containsPlayer(Player player) {
        boolean found = false;
        for (int i = 0; i < players.size() && !found; i++) {
            found = player.getNickName().equals(players.get(i).getNickName());
        }
        return found;
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
    
    /**
     * @return return the capacity of the room.
     */    
    public int getCapacity() {
        return capacity;
    }

}
