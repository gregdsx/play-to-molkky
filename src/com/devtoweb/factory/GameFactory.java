package com.devtoweb.factory;

/**
 * Factory : Générateur des fonctions métiers de l'objet Game
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
