package com.devtoweb.factory;

import java.util.ArrayList;

/**
 * Définition des fonctions métiers abstraites de l'objet Game
 *
 * @author Greg27
 */
public interface GameService {

    void setNewGame(int nbrJoueurs, ArrayList<Player> listJoueurs, String kindGame);

    Game getGame();

    Player getPlayerFocus();

    Player getLastPlayer(int lastScore, int lastNbrCroix);

    boolean setScorePlayerFocus(Player playerFocus, int score);

    int getPlayerMaxScore(ArrayList<Player> listPlayers);

    Player getWinner();

    void restartNewGame();

}
