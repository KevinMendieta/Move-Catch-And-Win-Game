package edu.eci.arsw.game.controllers;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import edu.eci.arsw.game.services.GameServices;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KevinMendieta
 */
@RestController
@Service
public class GameAPIController {

    private GameServices gameServices;

    @RequestMapping(method = RequestMethod.GET, path = "/rooms/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable int roomId) {
        //obtener datos que se enviarán a través del API
        Room result;
        try {
            result = gameServices.getRoom(roomId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/rooms")
    public ResponseEntity<?> registerNewRoom(@RequestBody Room room) {
        try {
            gameServices.registerNewRoom(room);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rooms/{roomId}/players")
    public ResponseEntity<?> getPlayers(@PathVariable int roomId) {
        ArrayList<Player> result;
        try {
            result = gameServices.getPlayersOfRoom(roomId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/rooms/{roomId}/players")
    public ResponseEntity<?> registerPlayer(@RequestBody int roomId, @RequestBody Player player) {
        try {
            gameServices.registerPlayerInRoom(roomId, player);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rooms/{roomId}/winner")
    public ResponseEntity<?> getWinner(@PathVariable int roomId) {
        Player result;
        try {
            result = gameServices.getWinnerOfRoom(roomId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/rooms/{roomId}/winner")
    public ResponseEntity<?> registerWinner(@PathVariable int roomId, @RequestBody Player player) {
        try {
            gameServices.registerPlayerInRoom(roomId, player);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @Autowired
    public void setGameServices(GameServices gameServices) {
        this.gameServices = gameServices;
    }

}
