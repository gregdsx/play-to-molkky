/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devtoweb.factory;

import java.util.ArrayList;

/**
 *
 * @author Greg27
 */
public interface GameService {

    void setNewGame(int nbrJoueurs, ArrayList<Player> listJoueurs, String kindGame);

    Game getGame();

    Player getPlayerFocus();

    Player getLastPlayer(int lastScore, int lastNbrCroix);

    boolean setScorePlayerFocus(Player playerFocus, int score);

    void playerWinGame();

}
