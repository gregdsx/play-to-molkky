/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devtoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.devtoweb.factory.GameFactory;
import com.devtoweb.factory.Player;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Greg27
 */
public class WinActivity extends Activity {

    private RelativeLayout generalWrapper;
    private LinearLayout scoreWrapper;
    private final RelativeLayout.LayoutParams lpMatchParent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final LinearLayout.LayoutParams lpWrapContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private String kindGame;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        setContentView(scroll);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new RelativeLayout(this);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);
        scroll.addView(generalWrapper);

        /**
         * Conteneur du logo
         */
        LinearLayout logoWrapper = new LinearLayout(this);
        logoWrapper.setLayoutParams(lpWrapContent);
        logoWrapper.setLayoutDirection(LinearLayout.HORIZONTAL);
        logoWrapper.setGravity(Gravity.CENTER);
        logoWrapper.setId(804512);
        generalWrapper.addView(logoWrapper);

        LinearLayout.LayoutParams lpLogo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginBottomLogo = ViewsMaker.getDpFromPixel(this, 10);
        lpLogo.setMargins(0, 0, 0, marginBottomLogo);

        ImageView logo = new ImageView(this);
        logo.setLayoutParams(lpLogo);
        logo.setBackgroundResource(R.drawable.logo_fin);
        logoWrapper.addView(logo);

        /**
         * Conteneur du tableau des scores
         */
        RelativeLayout.LayoutParams sizeScoreWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginLeftRightScoreWrapper = ViewsMaker.getDpFromPixel(this, 20);
        int marginTopScoreWrapper = ViewsMaker.getDpFromPixel(this, 5);
        int marginBottomScoreWrapper = ViewsMaker.getDpFromPixel(this, 15);
        sizeScoreWrapper.setMargins(marginLeftRightScoreWrapper, marginTopScoreWrapper, marginLeftRightScoreWrapper, marginBottomScoreWrapper);
        sizeScoreWrapper.addRule(RelativeLayout.BELOW, logoWrapper.getId());

        scoreWrapper = new LinearLayout(this);
        scoreWrapper.setOrientation(LinearLayout.VERTICAL);
        scoreWrapper.setLayoutParams(sizeScoreWrapper);
        scoreWrapper.setGravity(Gravity.CENTER_VERTICAL);
        scoreWrapper.setBackgroundResource(R.drawable.panneau_score);
        scoreWrapper.setId(325410);
        generalWrapper.addView(scoreWrapper);

        //Création du tableau des scrores
        setAndDisplayScoresTable();

        /**
         * Conteneur des boutons
         */
        RelativeLayout.LayoutParams sizeBtnWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeBtnWrapper.addRule(RelativeLayout.BELOW, scoreWrapper.getId());

        LinearLayout btnWrapper = new LinearLayout(this);
        btnWrapper.setLayoutParams(sizeBtnWrapper);
        btnWrapper.setOrientation(LinearLayout.HORIZONTAL);
        btnWrapper.setId(937);
        generalWrapper.addView(btnWrapper);

        //Recommencer la partie
        Button back = ViewsMaker.newButton(this, "Play", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, false, false, 9999, 9999);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                v.setBackgroundResource(R.drawable.bouton_touched);

                //Début nouvelle partie
                restartNewGame();
            }
        });
        btnWrapper.addView(back);

        //Revenir au menu principal
        Button menu = ViewsMaker.newButton(this, "Menu", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, false, false, 9999, 9999);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                v.setBackgroundResource(R.drawable.bouton_touched);

                Intent intent = new Intent(WinActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnWrapper.addView(menu);
    }

    @Override
    public void onBackPressed() {

        //Boite de dialogue
        final Dialog dial = new Dialog(this);
        dial.setTitle("Play to Mölkky");

        LinearLayout.LayoutParams sizeDial = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout dialWrapper = new LinearLayout(this);
        dialWrapper.setOrientation(LinearLayout.VERTICAL);
        dial.setContentView(dialWrapper, sizeDial);

        //Texte d'avertissement
        TextView warning = ViewsMaker.newTextView(this,
                "Voulez vous vraiment quitter l'application ?",
                ViewsMaker.getFontSizeWithScreenWidth(this),
                Color.WHITE,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                0,
                0,
                0,
                0,
                Gravity.CENTER_HORIZONTAL,
                false,
                false,
                9999,
                9999);
        warning.setSingleLine(false);
        dialWrapper.addView(warning);

        LinearLayout btnsWrapper = new LinearLayout(this);
        btnsWrapper.setLayoutParams(lpWrapContent);
        btnsWrapper.setOrientation(LinearLayout.HORIZONTAL);
        dialWrapper.addView(btnsWrapper);

        //Retour menu et fin partie
        Button yes = ViewsMaker.newButton(this, "Oui", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, false, false, 9999, 9999);
        yes.setBackgroundResource(android.R.drawable.btn_default);
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Sortie de l'application
                System.exit(0);

            }
        });
        btnsWrapper.addView(yes);

        //Reprise de la partie en cours 
        Button no = ViewsMaker.newButton(this, "Non", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, false, false, 9999, 9999);
        no.setBackgroundResource(android.R.drawable.btn_default);
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dial.dismiss();
            }
        });
        btnsWrapper.addView(no);

        dial.show();
    }

    /**
     * Affichage de la bonne image correspondant au bon nombre de croix du joueur
     *
     * @param player : joueur en cours
     * @return nbrCroixPlayer : image correspondante au nombre de croix
     */
    private Drawable displayNbrCroixPlayerFocus(Player player) {

        int croixPLayer = player.getNbrCroix();

        Drawable nbrCroixPlayer = null;

        switch (croixPLayer) {

            case 0:
                nbrCroixPlayer = null;
                break;

            case 1:
                nbrCroixPlayer = getResources().getDrawable(R.drawable.croix);
                break;

            case 2:
                nbrCroixPlayer = getResources().getDrawable(R.drawable.croix2);
                break;

            case 3:
                nbrCroixPlayer = getResources().getDrawable(R.drawable.croix3);
                break;
        }
        return nbrCroixPlayer;
    }

    /**
     * Affichage et paramétrage du tableau des scores | Récupération des joueurs en cours et des joueurs éliminés
     *
     * @param listJoueursJson
     * @param listJoueursOutJson
     */
    private void setAndDisplayScoresTable() {

        //Joueurs en cours
        ArrayList<Player> listJoueurs = GameFactory.getGameServiceImpl().getGame().getListJoueurs();
        //Joueurs éliminés
        ArrayList<Player> listJoueursOut = GameFactory.getGameServiceImpl().getGame().getListJoueursOut();

        //Tri des joueurs encore en jeu par score croissant
        Collections.sort(listJoueurs);

        //Taille police pour tableau
        int fontSize = ViewsMaker.getFontSizeWithScreenWidth(this) - 4;

        //Ajout d'un textview pour chaque joueurs en cours
        for (int i = 0; i < listJoueurs.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout playerWrapper = new LinearLayout(this);
            playerWrapper.setLayoutParams(lpWrapContent);
            playerWrapper.setGravity(Gravity.CENTER_VERTICAL);
            scoreWrapper.addView(playerWrapper);

            //Nom du joueur
            TextView nameJoueur = ViewsMaker.newTextView(this,
                    listJoueurs.get(i).getName(),
                    fontSize,
                    Color.WHITE,
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (float) 0.5,
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    Gravity.LEFT,
                    false,
                    false,
                    9999,
                    9999);
            playerWrapper.addView(nameJoueur);

            LinearLayout imgWrapper = new LinearLayout(this);
            imgWrapper.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25));
            imgWrapper.setGravity(Gravity.CENTER);
            playerWrapper.addView(imgWrapper);

            //Croix du joueur
            ImageView croixJoueur = new ImageView(this);
            croixJoueur.setBackground(displayNbrCroixPlayerFocus(listJoueurs.get(i)));
            imgWrapper.addView(croixJoueur);

            //Score du joueur
            TextView scoreJoueur = ViewsMaker.newTextView(this,
                    String.valueOf(listJoueurs.get(i).getScore()),
                    fontSize,
                    Color.WHITE,
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (float) 0.25,
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    Gravity.RIGHT,
                    false,
                    false,
                    9999,
                    9999);
            playerWrapper.addView(scoreJoueur);
        }

        //Ajout d'un textview pour chaque joueurs éliminés
        for (int i = 0; i < listJoueursOut.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout playerOutWrapper = new LinearLayout(this);
            playerOutWrapper.setLayoutParams(lpWrapContent);
            playerOutWrapper.setGravity(Gravity.CENTER_VERTICAL);
            scoreWrapper.addView(playerOutWrapper);

            //Nom du joueur
            TextView nameJoueurOut = ViewsMaker.newTextView(this,
                    listJoueursOut.get(i).getName(),
                    fontSize,
                    Color.WHITE,
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (float) 0.5,
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    Gravity.LEFT,
                    false,
                    false,
                    9999,
                    9999);
            playerOutWrapper.addView(nameJoueurOut);

            LinearLayout imgWrapper = new LinearLayout(this);
            imgWrapper.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25));
            imgWrapper.setGravity(Gravity.CENTER);
            playerOutWrapper.addView(imgWrapper);

            //Croix du joueur
            ImageView croixJoueurOut = new ImageView(this);
            croixJoueurOut.setBackground(displayNbrCroixPlayerFocus(listJoueursOut.get(i)));
            imgWrapper.addView(croixJoueurOut);

            //Score du joueur
            TextView scoreJoueurOut = ViewsMaker.newTextView(this,
                    String.valueOf(listJoueursOut.get(i).getScore()),
                    fontSize,
                    Color.WHITE,
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (float) 0.25,
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    Gravity.RIGHT,
                    false,
                    false,
                    9999,
                    9999);
            playerOutWrapper.addView(scoreJoueurOut);
        }
    }

    /**
     * Click sur btn Recommencer | Définition de la nouvelle liste de joueurs à envoyer à GameActivity, Début nouvelle partie
     *
     * @param listJoueur
     * @param listJoueurOut
     */
    private void restartNewGame() {

        //Nouvelle liste pour un nouvel ordre de jeu, le premier à jouer est le gagnant
        ArrayList<Player> listJoueurNewGame = new ArrayList<Player>();

        //Récupération des joueurs non éliminés
        for (Player player : GameFactory.getGameServiceImpl().getGame().getListJoueurs()) {
            listJoueurNewGame.add(player);
        }

        //Récupérations des joueurs éliminés
        for (Player player : GameFactory.getGameServiceImpl().getGame().getListJoueursOut()) {
            listJoueurNewGame.add(player);
        }

        //Création nouvelle partie : nombre de joueurs, liste des joueurs, type de partie
        GameFactory.getGameServiceImpl().setNewGame(listJoueurNewGame.size(), listJoueurNewGame, kindGame);

        //Remise à zéro de la partie
        for (int i = 0; i < GameFactory.getGameServiceImpl().getGame().getListJoueurs().size(); i++) {

            Player player = GameFactory.getGameServiceImpl().getGame().getListJoueurs().get(i);

            player.setId(i);
            player.setScore(0);
            player.setNbrCroix(0);
        }

        Intent intent = new Intent(WinActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

}
