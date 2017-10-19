package edu.eci.arsw.game.model;

/**
 * @author KevinMendieta
 */
public class Player {

    private String nickName, email, password;

    public Player(String nickName, String email, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
    
    public Player() {}

    /**
     * @return The nickname of the player.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @return The email of the player.
     */
    public String getEmail() {
        return email;
    }

}