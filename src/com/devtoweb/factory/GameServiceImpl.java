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
public class GameServiceImpl implements GameService {

    private Game game;
    private int playerIdFocus = 0;
    private Player playerWinnner;

    public GameServiceImpl() {
    }

    /**
     * Création d'une nouvelle partie
     *
     * @param nbrJoueurs
     * @param listJoueurs
     * @param kindGame Type de jeu choisis, utile pour les prochaines versions de l'appli
     */
    public void setNewGame(int nbrJoueurs, ArrayList<Player> listJoueurs, String kindGame) {

        playerIdFocus = 0;

        game = new Game(nbrJoueurs, listJoueurs, kindGame);
    }

    /**
     * Récupération de la partie en cours
     *
     * @return objet game
     */
    public Game getGame() {

        return game;
    }

    /**
     * Récupération du joueur en cours
     *
     * @return objet player
     */
    public Player getPlayerFocus() {

        int nbrJoueursPartie = game.getNbrJoueurs();

        //Si l'id du joueur suivant n'existe pas, retour au premier joueur
        if (playerIdFocus >= nbrJoueursPartie) {
            playerIdFocus = 0;
        }

        Player player = game.getListJoueurs().get(playerIdFocus);

        return player;
    }

    /**
     * Enregistrement du score du joueur en cours | Gestion de l'élimination et de la victoire
     *
     * @param playerFocus
     * @param score
     * @return boolean flag : Avertisseur de victoire ou non pour déterminer l'action dans la vue
     */
    public boolean setScorePlayerFocus(Player playerFocus, int score) {

        //Flag == true quand un joueur gagne la partie
        boolean flag = false;

        playerFocus = game.getListJoueurs().get(playerIdFocus);

        //Enregistrement du score du joueur en cours
        playerFocus.setScore(playerFocus.getScore() + score);

        //Si lancé manqué on ajoute une croix en plus au joueur en cours
        if (score == 0) {

            //Ajout de la croix au joueur
            playerFocus.setNbrCroix(playerFocus.getNbrCroix() + 1);

            //Si le joueur en cours obtient 3 croix il est supprimé du jeu
            if (playerFocus.getNbrCroix() == game.getNbrCroixMax()) {

                //Ajout du joueur éliminé à la liste des joueurs out
                game.getListJoueursOut().add(playerFocus);

                //Suppression du joueur de la liste des joueurs encore en jeu
                game.getListJoueurs().remove(playerFocus);

                //Modification du nombre de joueur encore en jeu
                game.setNbrJoueurs(game.getListJoueurs().size());

                //Enregistrement des nouveaux id de la liste comme id de joueur
                for (int i = 0; i < game.getListJoueurs().size(); i++) {
                    game.getListJoueurs().get(i).setId(i);
                }

                //Si suppression du dernier adversaire, le gagnant est désigné
                if (game.getNbrJoueurs() < 2) {

                    //Récupération du dernier joueur en lice, le vainqueur
                    playerWinnner = game.getListJoueurs().get(0);

                    //Victoire à transmettre à la vue
                    flag = true;

                } else {
                    //Gestion de l'id du joueur à prendre en focus après suppression d'un joueur dans la liste
                    //si joueur 3 supprimé -> joueur 4 passe joueur 3, 5 passe 4...
                    playerIdFocus--;
                }
            }

            //Si le joueur en cours dépasse le score max, le score du joueur en cours retombe à 25
        } else if (playerFocus.getScore() > game.getScoreMax()) {

            playerFocus.setScore(25);

            playerFocus.setNbrCroix(0);

            //Si le joueur en cours arrive à 50, le jeu est terminé
        } else if (playerFocus.getScore() == game.getScoreMax()) {

            playerFocus.setNbrCroix(0);

            //Victoire du joueur en cours
            playerWinnner = playerFocus;

            //Victoire à transmettre à la vue
            flag = true;

            //Sinon remise à 0 des croix
        } else {

            playerFocus.setNbrCroix(0);
        }

        if (!flag) {
            //Passage au joueur suivant
            playerIdFocus++;
        }

        return flag;
    }

    /**
     * Récupération du joueur précédent le joueur focus pour une erreur de score | Modification du score du joueur précédent
     *
     * @param lastScore
     * @param lastNbrCroix
     * @return
     */
    public Player getLastPlayer(int lastScore, int lastNbrCroix) {

        //Id du dernier joueur dans la liste
        int lastIdInGameList = game.getListJoueurs().size() - 1;

        //Récupération du dernier joueur à avoir joué
        Player lastPlayerFocus;

        //Si le joueur en cours est le premier de la liste
        if (getPlayerFocus().getId() == 0) {

            //On récupère le dernier de cette liste
            lastPlayerFocus = game.getListJoueurs().get(lastIdInGameList);

        } else {

            //On récupère le joueur avec l'id précédent
            lastPlayerFocus = game.getListJoueurs().get(getPlayerFocus().getId() - 1);
        }


        //Soustraction du dernier score enregistré par le dernier joueur en cours
        lastPlayerFocus.setScore(lastPlayerFocus.getScore() - lastScore);

        //Récupération du dernier nombre de croix
        lastPlayerFocus.setNbrCroix(lastNbrCroix);

        //Id du joueur en cours deviens celui du dernier joueur
        playerIdFocus = lastPlayerFocus.getId();

        return lastPlayerFocus;
    }
}
