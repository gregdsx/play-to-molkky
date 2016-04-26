/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devtoweb.factory;

/**
 *
 * @author Greg27
 */
public class GameFactory {

    private static GameService gameService = null;

    public static GameService getGameServiceImpl() {

        if (gameService == null) {

            gameService = new GameServiceImpl();
        }

        return gameService;
    }

}
