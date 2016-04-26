package com.devtoweb.factory;

import com.google.gson.Gson;

/**
 * Objet utilisé à la fois pour les match en solo ou en équipe
 *
 * @author Greg27
 */
public class Player implements Comparable<Player> {

    private final String name;
    private int id;
    private int score;
    private int nbrCroix;

    public Player(String name, int id, int score, int nbrCroix) {
        this.name = name;
        this.id = id;
        this.score = score;
        this.nbrCroix = nbrCroix;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNbrCroix() {
        return nbrCroix;
    }

    public void setNbrCroix(int nbrCroix) {
        this.nbrCroix = nbrCroix;
    }

    @Override
    public String toString() {
        String player = getName() + "|" + getScore() + "|" + getNbrCroix();
        return player;
    }

    /**
     * Objet player retourné en String via Json
     *
     * @param player
     * @return
     */
    public static String playerToJson(Player player) {

        Gson json = new Gson();

        String str = json.toJson(player);

        return str;
    }

    /**
     * String contenant l'objet player en json, retourné en objet Player
     *
     * @param playerStr
     * @return
     */
    public static Player jsonToPlayer(String playerStr) {

        Gson json = new Gson();

        Player player = json.fromJson(playerStr, Player.class);

        return player;
    }

    /**
     * Comparaison des joueurs de la liste pour classement croissant selon score
     * @param another
     * @return 
     */
    public int compareTo(Player another) {

        int compareScore1 = this.getScore();
        int compareScore2 = another.getScore();

        if (compareScore1 > compareScore2) {
            return -1;

        } else if (compareScore1 < compareScore2) {
            return 1;

        } else {
            return 0;
        }
    }

}
