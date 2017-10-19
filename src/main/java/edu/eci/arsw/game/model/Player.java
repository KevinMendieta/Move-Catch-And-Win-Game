package edu.eci.arsw.game.model;

/**
 * @author KevinMendieta
 */
public class Player {

    private String nickName;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public Player() {}

    /**
     * @return The nickname of the player.
     */
    public String getNickName() {
        return nickName;
    }

}
