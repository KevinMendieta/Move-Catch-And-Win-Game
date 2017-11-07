package edu.eci.arsw.game.persistence.room;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;

import java.util.ArrayList;

/**
 * @author KevinMendieta
 */
public interface RoomPersistence {

    /**
     * Register a new room in memory
     * @param room the new Room.
     * @throws RoomPersistenceException if the Room already exists.
     */
    public void registerNewRoom(Room room) throws RoomPersistenceException;

    /**
     * Register a player in a room.
     * @param id The id of the room.
     * @param player The player to register.
     * @throws RoomPersistenceException if the room does not exists, the capacity of the room is exceed,
     * the player already is on the room.
     */
    public void registerPlayerInRoom(int id, Player player) throws RoomPersistenceException;

    /**
     * Register a winner for a room.
     * @param id The id of the room.
     * @param player The winner player.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public void registerWinnerOfRoom(int id, Player player) throws RoomPersistenceException;
    
    /**
     * @return Return all the current rooms.
     */
    public ArrayList<Room> getRooms();

    /**
     * Return a room given a id room.
     * @param id The id of the room.
     * @return The room of the id.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public Room getRoom(int id) throws RoomPersistenceException;

    /**
     * Returns a winner of a room.
     * @param id The id of the room.
     * @return The player of the id.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public Player getWinnerOfRoom(int id) throws RoomPersistenceException;

    /**
     * Return a list of all the player of a room.
     * @param id the id of the room.
     * @return A list with all the players of the room.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public ArrayList<Player> getPlayersOfRoom(int id) throws RoomPersistenceException;

}
