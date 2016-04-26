package com.devtoweb.factory;

import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Objet principal de l'application | Gestion de la partie
 *
 * @author Greg27
 */
public class Game{

    private int nbrJoueurs;
    private ArrayList<Player> listJoueurs;
    private ArrayList<Player> listJoueursOut = new ArrayList<Player>();
    private String kindGame;
    private final int scoreMax = 50;
    private final int nbrCroixMax = 3;

    public Game(int nbrJoueurs, ArrayList<Player> listJoueurs, String kindGame) {
        this.nbrJoueurs = nbrJoueurs;
        this.listJoueurs = listJoueurs;
        this.kindGame = kindGame;
    }

    public int getNbrJoueurs() {
        return nbrJoueurs;
    }

    public void setNbrJoueurs(int nbrJoueurs) {
        this.nbrJoueurs = nbrJoueurs;
    }

    public ArrayList<Player> getListJoueurs() {
        return listJoueurs;
    }

    public void setListJoueurs(ArrayList<Player> listJoueurs) {
        this.listJoueurs = listJoueurs;
    }

    public ArrayList<Player> getListJoueursOut() {
        return listJoueursOut;
    }

    public void setListJoueursOut(ArrayList<Player> listJoueursOut) {
        this.listJoueursOut = listJoueursOut;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public int getNbrCroixMax() {
        return nbrCroixMax;
    }

    public String getKindGame() {
        return kindGame;
    }

    public void setKindGame(String kindGame) {
        this.kindGame = kindGame;
    }

    @Override
    public String toString() {
        String rule = "La partie aura " + getNbrJoueurs() + " joueurs, avec " + getListJoueurs() + ". /n Le score max sera de " + getScoreMax() + " et le nombre de croix autorisé est de " + getNbrCroixMax();
        return rule;
    }

    /**
     * Retourne le score le plus élevé de la partie en cours
     *
     * @param listPlayers
     * @return
     */
    public int getPlayerMaxScore(ArrayList<Player> listPlayers) {

        int maxScore = 0;

        for (int i = 0; i < listPlayers.size(); i++) {

            int scoreFocus = listPlayers.get(i).getScore();

            if (scoreFocus > maxScore) {
                maxScore = scoreFocus;
            }
        }
        return maxScore;
    }

    /**
     * Objet game retourné en String via Json
     *
     * @param game
     * @return
     */
    public static String gameToJson(Game game) {

        Gson json = new Gson();

        String str = json.toJson(game);

        return str;
    }

    /**
     * String contenant l'objet game en json, retourné en objet Game
     *
     * @param gameStr
     * @return
     */
    public static Game jsonToGame(String gameStr) {

        Gson json = new Gson();

        Game game = json.fromJson(gameStr, Game.class);

        return game;
    }

}
