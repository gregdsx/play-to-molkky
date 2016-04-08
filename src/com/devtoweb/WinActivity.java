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
import android.graphics.drawable.GradientDrawable;
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
import com.controllers.ViewsMaker;
import com.object.Game;
import com.object.Player;
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
    private ArrayList<CharSequence> listJoueursOutJson, listJoueursJson;
    private ArrayList<Player> listJoueurs, listJoueursOut;
    private String kindGame;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getIntent().getExtras();

        //Liste des joueurs encore en jeu sous forme de json 
        listJoueursJson = bundle.getCharSequenceArrayList("listJoueurs");

        //Liste des joueurs éliminés sous forme de json 
        listJoueursOutJson = bundle.getCharSequenceArrayList("listJoueurOut");

        //Type de partie
        kindGame = bundle.getCharSequence("typePartie").toString();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        setContentView(scroll);

        generalWrapper = new RelativeLayout(this);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setPadding(20, 20, 20, 20);

        scroll.addView(generalWrapper);

        /**
         * Conteneur du logo
         */
        LinearLayout logoWrapper = new LinearLayout(this);
        logoWrapper.setLayoutParams(lpWrapContent);
        logoWrapper.setLayoutDirection(LinearLayout.HORIZONTAL);
        logoWrapper.setGravity(Gravity.CENTER);
        generalWrapper.addView(logoWrapper);

        LinearLayout.LayoutParams lpLogo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpLogo.setMargins(0, 0, 0, 10);

        ImageView logo = new ImageView(this);
        logo.setLayoutParams(lpLogo);
        logo.setBackgroundResource(R.drawable.logo_fin);
        logoWrapper.addView(logo);

        /**
         * Conteneur du tableau des scores
         */
        RelativeLayout.LayoutParams sizeScoreWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeScoreWrapper.setMargins(20, 5, 20, 15);
        sizeScoreWrapper.addRule(RelativeLayout.CENTER_IN_PARENT);

        scoreWrapper = new LinearLayout(this);
        scoreWrapper.setOrientation(LinearLayout.VERTICAL);
        scoreWrapper.setLayoutParams(sizeScoreWrapper);
        scoreWrapper.setGravity(Gravity.CENTER_VERTICAL);
        scoreWrapper.setBackgroundResource(R.drawable.panneau_score);
        scoreWrapper.setId(63985);
        generalWrapper.addView(scoreWrapper);

        //Création du tableau des scrores
        setAndDisplayScoresTable(listJoueursJson, listJoueursOutJson);

        /**
         * Conteneur des boutons
         */
        RelativeLayout.LayoutParams sizeBtnWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeBtnWrapper.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        LinearLayout btnWrapper = new LinearLayout(this);
        btnWrapper.setLayoutParams(sizeBtnWrapper);
        btnWrapper.setOrientation(LinearLayout.HORIZONTAL);
        btnWrapper.setBackgroundColor(Color.BLUE);
        btnWrapper.setId(937);
        generalWrapper.addView(btnWrapper);

        //Recommencer la partie
        Button back = ViewsMaker.newButton(this, "Recommencer", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, false, false, 9999, 9999);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                v.setBackgroundResource(R.drawable.bouton_touched);

                //Début nouvelle partie
                restartNewGame(listJoueurs, listJoueursOut);
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

        LinearLayout.LayoutParams sizeDial = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout dialWrapper = new LinearLayout(this);
        dialWrapper.setOrientation(LinearLayout.VERTICAL);
        dial.setContentView(dialWrapper, sizeDial);

        //Texte d'avertissement
        TextView warning = new TextView(this);
        warning.setLayoutParams(lpWrapContent);
        warning.setText("Voulez vous vraiment quitter cette partie et retourner au menu principal ?");
        warning.setTextColor(Color.WHITE);
        warning.setTextSize(12);
        warning.setGravity(Gravity.CENTER_HORIZONTAL);
        dialWrapper.addView(warning);

        LinearLayout btnsWrapper = new LinearLayout(this);
        btnsWrapper.setLayoutParams(lpWrapContent);
        btnsWrapper.setOrientation(LinearLayout.HORIZONTAL);
        dialWrapper.addView(btnsWrapper);

        LinearLayout.LayoutParams sizeBtns = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5);

        //Retour menu et fin partie
        Button yes = new Button(this);
        yes.setLayoutParams(sizeBtns);
        yes.setText("Oui");
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(WinActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnsWrapper.addView(yes);

        //Reprise de la partie en cours 
        Button no = new Button(this);
        no.setLayoutParams(sizeBtns);
        no.setText("Non");
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
    private void setAndDisplayScoresTable(ArrayList<CharSequence> listJoueursJson, ArrayList<CharSequence> listJoueursOutJson) {

        //Joueurs en cours
        listJoueurs = new ArrayList<Player>();
        //Joueurs éliminés
        listJoueursOut = new ArrayList<Player>();

        //Récupération des joueurs encore en jeu
        for (int i = 0; i < listJoueursJson.size(); i++) {

            //Conversion des joueurs Json en Objet Joueurs
            Player player = Player.jsonToPlayer(listJoueursJson.get(i).toString());

            //Ajout du joueur dans la liste
            listJoueurs.add(player);
        }

        //Récupération des joueurs éliminés
        for (int i = 0; i < listJoueursOutJson.size(); i++) {

            //Conversion des joueurs Json en Objet Joueurs
            Player playerOut = Player.jsonToPlayer(listJoueursOutJson.get(i).toString());

            //Ajout du joueur dans la liste
            listJoueursOut.add(playerOut);
        }

        //Tri des joueurs encore en jeu par score croissant
        Collections.sort(listJoueurs);

        //Bordure de séparation entre les lignes du tableau
        GradientDrawable border = new GradientDrawable();
        border.setShape(GradientDrawable.LINE);
        border.setStroke(1, Color.WHITE);

        //Taille des champs nom croix et score dans tableau score
        LinearLayout.LayoutParams lpTxtView = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.3);
        lpTxtView.setMargins(0, 2, 0, 2);

        //Ajout d'un textview pour chaque joueurs en cours
        for (int i = 0; i < listJoueurs.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout playerWrapper = new LinearLayout(this);
            playerWrapper.setLayoutParams(lpWrapContent);
            playerWrapper.setGravity(Gravity.CENTER_VERTICAL);
            playerWrapper.setBackground(border);
            scoreWrapper.addView(playerWrapper);

            //Nom du joueur
            TextView nameJoueur = new TextView(this);
            nameJoueur.setLayoutParams(lpTxtView);
            nameJoueur.setText(listJoueurs.get(i).getName());
            nameJoueur.setTextColor(Color.WHITE);
            nameJoueur.setGravity(Gravity.LEFT);
            playerWrapper.addView(nameJoueur);

            //Croix du joueur
            ImageView croixJoueur = new ImageView(this);
            croixJoueur.setBackground(displayNbrCroixPlayerFocus(listJoueurs.get(i)));
            playerWrapper.addView(croixJoueur);

            //Score du joueur
            TextView scoreJoueur = new TextView(this);
            scoreJoueur.setLayoutParams(lpTxtView);
            scoreJoueur.setText(String.valueOf(listJoueurs.get(i).getScore()));
            scoreJoueur.setTextColor(Color.WHITE);
            scoreJoueur.setGravity(Gravity.RIGHT);
            playerWrapper.addView(scoreJoueur);
        }

        //Ajout d'un textview pour chaque joueurs éliminés
        for (int i = 0; i < listJoueursOut.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout playerOutWrapper = new LinearLayout(this);
            playerOutWrapper.setLayoutParams(lpWrapContent);
            playerOutWrapper.setBackground(border);
            playerOutWrapper.setGravity(Gravity.CENTER_VERTICAL);
            scoreWrapper.addView(playerOutWrapper);

            //Nom du joueur
            TextView nameJoueurOut = new TextView(this);
            nameJoueurOut.setLayoutParams(lpTxtView);
            nameJoueurOut.setText(listJoueursOut.get(i).getName());
            nameJoueurOut.setTextColor(Color.WHITE);
            nameJoueurOut.setGravity(Gravity.LEFT);
            playerOutWrapper.addView(nameJoueurOut);

            //Croix du joueur
            ImageView croixJoueurOut = new ImageView(this);
            croixJoueurOut.setBackground(displayNbrCroixPlayerFocus(listJoueursOut.get(i)));
            playerOutWrapper.addView(croixJoueurOut);

            //Score du joueur
            TextView scoreJoueurOut = new TextView(this);
            scoreJoueurOut.setLayoutParams(lpTxtView);
            scoreJoueurOut.setText(String.valueOf(listJoueursOut.get(i).getScore()));
            scoreJoueurOut.setTextColor(Color.WHITE);
            scoreJoueurOut.setGravity(Gravity.RIGHT);
            playerOutWrapper.addView(scoreJoueurOut);
        }
    }

    /**
     * Click sur btn Recommencer | Définition de la nouvelle liste de joueurs à envoyer à GameActivity, Début nouvelle partie
     *
     * @param listJoueur
     * @param listJoueurOut
     */
    private void restartNewGame(ArrayList<Player> listJoueur, ArrayList<Player> listJoueurOut) {

        ArrayList<Player> listJoueurNewGame = new ArrayList<Player>();

        //Récupération des joueurs non éliminés
        for (Player player : listJoueur) {
            listJoueurNewGame.add(player);
        }

        //Récupérations des joueurs éliminés
        for (Player player : listJoueurOut) {
            listJoueurNewGame.add(player);
        }

        //Création nouvelle partie : nombre de joueurs, liste des joueurs, type de partie
        Game game = new Game(listJoueurNewGame.size(), listJoueurNewGame, kindGame);

        //Remise à zéro de la partie
        for (Player player : game.getListJoueurs()) {

            player.setScore(0);
            player.setNbrCroix(0);
        }

        //Objet Game transformé en String Json
        String gameToJson = Game.gameToJson(game);

        Intent intent = new Intent(WinActivity.this, GameActivity.class);
        intent.putExtra("game", gameToJson);
        startActivity(intent);
        finish();
    }

}
