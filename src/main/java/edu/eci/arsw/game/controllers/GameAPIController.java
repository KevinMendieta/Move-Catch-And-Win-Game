package edu.eci.arsw.game.controllers;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.room.RoomPersistenceException;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import edu.eci.arsw.game.services.GameServices;
import edu.eci.arsw.game.services.LoginServices;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KevinMendieta
 */
@Controller
@RestController
@Service
public class GameAPIController {

    private GameServices gameServices;
    private LoginServices loginServices;

    private SimpMessagingTemplate msgt;
    
    /* GET METHODS */
    
    @RequestMapping(method = RequestMethod.GET, path = "/rooms")
    public ResponseEntity<?> getRooms() {
        return new ResponseEntity<>(gameServices.getRooms(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rooms/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable int roomId) {
        Room result;
        try {
            result = gameServices.getRoom(roomId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
    
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(loginServices.getAllUsers(), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/loggedUser")
    public ResponseEntity<?> getLoggedUser(){
        try {
            return new ResponseEntity<>(loginServices.getCurrentUser(), HttpStatus.ACCEPTED);
        } catch (UserPersistenceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    
    /*POST METHODS*/

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            loginServices.login(user);
            System.out.println(loginServices.getCurrentUser());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserPersistenceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
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

    @RequestMapping(method = RequestMethod.POST, path = "/rooms/{roomId}/players")
    public ResponseEntity<?> registerPlayer(@PathVariable int roomId, @RequestBody Player player) {
        try {
            gameServices.registerPlayerInRoom(roomId, player);
            newPlayer(roomId);
            Room currentRoom = gameServices.getRoom(roomId);
            if(currentRoom.getPlayers().size() >= currentRoom.getCapacity()) sendStartMessage(roomId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
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
    
    @RequestMapping(method = RequestMethod.POST, path = "/user")
    public ResponseEntity<?> addNewUser(@RequestBody User usr){
        
        try {
            usr.setId(loginServices.getNextId());
            loginServices.saveUser(usr);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (UserPersistenceException ex) {

            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN); 
        }
        
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable int roomId) {
        try {
            gameServices.deleteRoom(roomId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(RoomPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    public void newPlayer(int id) {
        msgt.convertAndSend("/topic/players." +  id, "New player.");
    }
    
    public void sendStartMessage(int id) {
        msgt.convertAndSend("/topic/start." +  id, "Enough Players to Play.");
    }
    
    /* DEPENDENCY INJECTION */

    @Autowired
    public void setGameServices(GameServices gameServices) {
        this.gameServices = gameServices;
    }

    @Autowired
    public void setMsgt(SimpMessagingTemplate msgt) {
        this.msgt = msgt;
    }
    
    @Autowired
    public void setLoginServices(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

}
