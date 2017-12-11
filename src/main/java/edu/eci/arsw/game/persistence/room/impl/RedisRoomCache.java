/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.room.impl;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.persistence.room.RoomPersistence;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2106897
 */
@Service
public class RedisRoomCache implements RoomPersistence {

    private StringRedisTemplate template;

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    private String game = "room:";

    private void chargeRooms() {
        template.opsForHash().put("room:0", "id", "0");
        template.opsForHash().put("room:0", "capacity", "1");
        template.opsForHash().put("room:0", "winner", "");
        template.opsForHash().put("room:11", "id", "11");
        template.opsForHash().put("room:11", "capacity", "2");
        template.opsForHash().put("room:11", "winner", "");
        template.opsForHash().put("room:22", "id", "22");
        template.opsForHash().put("room:22", "capacity", "3");
        template.opsForHash().put("room:22", "winner", "");
    }

    public RedisRoomCache() throws RoomPersistenceException {
        //chargeRooms();
    }

    @Override
    public void registerNewRoom(Room room) throws RoomPersistenceException {
        String actGame = game + room.getId();
        if (template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala ya se encuentra creada.");
        } else {
            template.opsForHash().put(actGame, "id", room.getId() + "");
            template.opsForHash().put(actGame, "capacity", room.getCapacity() + "");
            template.opsForHash().put(actGame, "winner", "");
        }
    }

    @Override
    public void registerPlayerInRoom(int id, Player player) throws RoomPersistenceException {
        String actGame = game + id;
        if (!template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala no se encuentra creada.");
        } else {
            template.opsForList().leftPush(actGame + ":players", player.getNickName());
        }
    }

    @Override
    public void registerWinnerOfRoom(int id, Player player) throws RoomPersistenceException {
        String actGame = game + id;
        if (!template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala no se encuentra creada.");
        } else {
            template.opsForHash().put(actGame, "winner", player.getNickName());
        }
    }

    @Override
    public ArrayList<Room> getRooms() {
        Set<String> keys = template.keys("room:*");
        ArrayList<Room> rooms = new ArrayList<>();
        for (String key : keys) {
            try {
                int id = Integer.parseInt(key.substring(5));
                rooms.add(getRoom(id));
            } catch (NumberFormatException ex) {
            } catch (RoomPersistenceException ex) {
                Logger.getLogger(RedisRoomCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rooms;
    }

    @Override
    public Room getRoom(int id) throws RoomPersistenceException {
        String actGame = game + id;
        if (!template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala no se encuentra creada.");
        } else {
            ArrayList<Player> players = getPlayersOfRoom(id);
            int capacity = Integer.parseInt((String) template.opsForHash().get(actGame, "capacity"));
            String winner = (String) template.opsForHash().get(actGame, "winner");
            Room tmp = new Room(id, capacity);
            tmp.setPlayers(players);
            return tmp;
        }
    }

    @Override
    public Player getWinnerOfRoom(int id) throws RoomPersistenceException {
        String actGame = game + id;
        if (!template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala no se encuentra creada.");
        } else {
            String name = (String) template.opsForHash().get(actGame, "winner");
            return new Player(name);
        }
    }

    @Override
    public ArrayList<Player> getPlayersOfRoom(int id) throws RoomPersistenceException {
        String actGame = game + id;
        if (!template.hasKey(actGame)) {
            throw new RoomPersistenceException("La sala no se encuentra creada.");
        } else {
            List<String> lst = template.opsForList().range(actGame + ":players", 0, 30);
            ArrayList<Player> playrs = new ArrayList<>();
            for (String ply : lst) {
                playrs.add(new Player(ply));
            }
            return playrs;
        }
    }

    @Override
    public void deleteRoom(int id) throws RoomPersistenceException {
        template.delete(game + id);
    }

}
