package edu.eci.arsw.game.persistence.room.impl;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.persistence.room.RoomPersistence;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * @author KevinMendieta
 */
@Service
public class InMemoryRoomPersistence implements RoomPersistence{

    private final Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    
    public InMemoryRoomPersistence() {
        Player firstPlayer = new Player("Sebastian", "sebastian@mail.com", "k457");
        Player secondPlayer = new Player("Esteban", "esteban@mail.com", "j785");
        Room room = new Room(0);
        room.addPlayer(firstPlayer);
        room.addPlayer(secondPlayer);
        rooms.put(0, room);
    }
    
    @Override
    public void registerNewRoom(Room room) throws RoomPersistenceException {
        if (rooms.containsKey(room.getId())) {
            throw new RoomPersistenceException("The room already exists.");
        }else {
            rooms.put(room.getId(), room);
        }
    }

    @Override
    public void registerPlayerInRoom(int id, Player player) throws RoomPersistenceException {
        if (!rooms.containsKey(id)) {
            throw new RoomPersistenceException("The room " + id + " does not exist.");
        }else {
            rooms.get(id).addPlayer(player);
        }
    }

    @Override
    public void registerWinnerOfRoom(int id, Player player) throws RoomPersistenceException {
        if (!rooms.containsKey(id)) {
            throw new RoomPersistenceException("The room " + id + " does not exist.");
        }else {
            rooms.get(id).setWinner(player);
        }
    }

    @Override
    public Room getRoom(int id) throws RoomPersistenceException {
        if (!rooms.containsKey(id)) throw new RoomPersistenceException("The room " + id + " does not exist.");
        return rooms.get(id);
    }

    @Override
    public Player getWinnerOfRoom(int id) throws RoomPersistenceException {
        if (!rooms.containsKey(id)) throw new RoomPersistenceException("The room " + id + " does not exist.");
        return rooms.get(id).getWinner();
    }

    @Override
    public ArrayList<Player> getPlayersOfRoom(int id) throws RoomPersistenceException {
        if (!rooms.containsKey(id)) throw new RoomPersistenceException("The room " + id + " does not exist.");
        return rooms.get(id).getPlayers();
    }

}
