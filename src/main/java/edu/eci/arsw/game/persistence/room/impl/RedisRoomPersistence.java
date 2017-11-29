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
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.embedded.RedisServer;

/**
 *
 * @author 2106897
 */
public class RedisRoomPersistence implements RoomPersistence{
    
    private RedisServer rd;
    
    public RedisRoomPersistence() throws RoomPersistenceException{
        try {
            rd = new RedisServer(6379);
        } catch (IOException ex) {
            Logger.getLogger(RedisRoomPersistence.class.getName()).log(Level.SEVERE, null, ex);
            throw new RoomPersistenceException("NO se ha podido crear el servidor redis embebido correctamente.");
        }
    }

    @Override
    public void registerNewRoom(Room room) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerPlayerInRoom(int id, Player player) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerWinnerOfRoom(int id, Player player) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Room> getRooms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room getRoom(int id) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getWinnerOfRoom(int id) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Player> getPlayersOfRoom(int id) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoom(int id) throws RoomPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
