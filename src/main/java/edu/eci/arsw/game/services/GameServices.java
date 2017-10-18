package edu.eci.arsw.game.services;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.persistence.room.RoomPersistence;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KevinMendieta
 */
@Service
public class GameServices {
    
    
    private RoomPersistence roomPersistence;
    
    /**
     * Register a new room in memory
     * @param room the new Room.
     * @throws RoomPersistenceException if the Room already exists.
     */
    public void registerNewRoom(Room room) throws RoomPersistenceException {
        roomPersistence.registerNewRoom(room);
    }

    /**
     * Register a player in a room.
     * @param id The id of the room.
     * @param player The player to register.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public void registerPlayerInRoom(int id, Player player) throws RoomPersistenceException {
        roomPersistence.registerPlayerInRoom(id, player);
    }

    /**
     * Register a winner for a room.
     * @param id The id of the room.
     * @param player The winner player.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public void registerWinnerOfRoom(int id, Player player) throws RoomPersistenceException {
        roomPersistence.registerWinnerOfRoom(id, player);
    }

    /**
     * Return a room given a id room.
     * @param id The id of the room.
     * @return The room of the id.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public Room getRoom(int id) throws RoomPersistenceException {
        return roomPersistence.getRoom(id);
    }

    /**
     * Returns a winner of a room.
     * @param id The id of the room.
     * @return The player of the id.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public Player getWinnerOfRoom(int id) throws RoomPersistenceException {
        return roomPersistence.getWinnerOfRoom(id);
    }
    
    /**
     * Return a list of all the player of a room.
     * @param id the id of the room.
     * @return A list with all the players of the room.
     * @throws RoomPersistenceException if the room does not exists.
     */
    public ArrayList<Player> getPlayersOfRoom(int id) throws RoomPersistenceException {
        return roomPersistence.getPlayersOfRoom(id);
    }
    
    @Autowired
    public void setRoomPersistence(RoomPersistence roomPersistence) {
        this.roomPersistence = roomPersistence;
    }
}
