package edu.eci.arsw.game.test.persistence.impl;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.persistence.room.RoomPersistence;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import edu.eci.arsw.game.persistence.room.impl.InMemoryRoomPersistence;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Test;

/**
 * @author KevinMendieta
 */
public class InMemoryRoomPersistenceTest {

    @Test
    public void registerNewAndGetRoomTest() throws RoomPersistenceException {
        RoomPersistence roomPersistence = new InMemoryRoomPersistence();
        Room firstRoom = new Room(1, 3);
        Player firstPlayer = new Player("kevin");
        firstRoom.addPlayer(firstPlayer);
        firstRoom.setWinner(firstPlayer);
        roomPersistence.registerNewRoom(firstRoom);
        assertEquals("The previously stored room is different", firstRoom, roomPersistence.getRoom(1));
    }

    @Test
    public void registerExistingRoom() {
        RoomPersistence roomPersistence = new InMemoryRoomPersistence();
        Room firstRoom = new Room(1, 3);
        Player firstPlayer = new Player("kevin");
        firstRoom.addPlayer(firstPlayer);
        firstRoom.setWinner(firstPlayer);
        try {
            roomPersistence.registerNewRoom(firstRoom);
        } catch(RoomPersistenceException e) {
            fail("Room persistence faild creating saving the room");
        }
        try {
            roomPersistence.registerNewRoom(firstRoom);
            fail("Should not save a duplicated room");
        } catch(RoomPersistenceException e) {
            
        }
    }

    @Test
    public void registerPlayerAndGetPlayersTest() throws RoomPersistenceException {
        RoomPersistence roomPersistence = new InMemoryRoomPersistence();
        Room firstRoom = new Room(1,3);
        Player firstPlayer = new Player("kevin");
        firstRoom.addPlayer(firstPlayer);
        roomPersistence.registerNewRoom(firstRoom);
        Player secondPlayer = new Player("sebastian");
        roomPersistence.registerPlayerInRoom(1, secondPlayer);
        assertEquals("The previously stored player is different", firstPlayer, roomPersistence.getPlayersOfRoom(1).get(0));
        assertEquals("The previously stored player is different", secondPlayer, roomPersistence.getPlayersOfRoom(1).get(1));
    }

    @Test
    public void registerWinnerAndGetWinnerTest() throws RoomPersistenceException {
        RoomPersistence roomPersistence = new InMemoryRoomPersistence();
        Room firstRoom = new Room(1, 3);
        Player firstPlayer = new Player("kevin");
        firstRoom.addPlayer(firstPlayer);
        roomPersistence.registerNewRoom(firstRoom);
        roomPersistence.registerWinnerOfRoom(1, firstPlayer);
        assertEquals("The previously stored room is different", firstRoom.getWinner(), roomPersistence.getWinnerOfRoom(1));
    }

}
