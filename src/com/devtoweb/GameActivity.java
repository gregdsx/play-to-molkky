package com.devtoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import com.controllers.ViewsMaker;
import com.object.Game;
import com.object.Player;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Greg27
 */
public class GameActivity extends Activity {

    private RelativeLayout generalWrapper;
    private LinearLayout wrapperMolkky;
    private final RelativeLayout.LayoutParams lpMatchParent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final RelativeLayout.LayoutParams sizeRows = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private Bundle bundle;
    private TextView displayScorePlayer, displayNamePlayer;
    private Button backPlayer;
    private Dialog popup;
    private final View.OnClickListener setScore = new View.OnClickListener() {

        public void onClick(View v) {

            if (v.isSelected()) {

                v.setBackgroundResource(R.drawable.quille);

            } else {

                v.setBackgroundResource(R.drawable.quille_touched);
            }

            getCurrentScore(v.getId(), v);
        }
    };

    //Objets de gestion de partie
    private Game game;
    private Player playerFocus;
    private ArrayList<Player> listJoueurs;
    private final ArrayList<Player> listJoueurOut = new ArrayList<Player>();
    private int playerIdFocus = 0;
    private int lastScorePlayer, lastCroixPlayer;
    private final ArrayList<Integer> listIdBtnPressed = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Récupération extras envoyés depuis SetGameActivity ou WinActivity
        bundle = getIntent().getExtras();
        String gameFromJson = bundle.getString("game");

        /**
         * Récupération de l'objet Game via Json
         */
        game = Game.jsonToGame(gameFromJson);
        listJoueurs = game.getListJoueurs();

        generalWrapper = new RelativeLayout(this);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setPadding(20, 20, 20, 20);

        setContentView(generalWrapper);

        /**
         * Conteneur de l'entete avec le nom du joueur et le bouton score
         */
        RelativeLayout.LayoutParams sizeHeaderWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeHeaderWrapper.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        LinearLayout headerWrapper = new LinearLayout(this);
        headerWrapper.setLayoutParams(sizeHeaderWrapper);
        headerWrapper.setGravity(Gravity.CENTER_VERTICAL);
        generalWrapper.addView(headerWrapper);

        LinearLayout.LayoutParams sizeNamePlayer = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.5);
        sizeNamePlayer.setMargins(20, 0, 10, 0);

        //Nom du joueur en cours + affichage de son nombre de croix via le drawable
        displayNamePlayer = new TextView(this);
        displayNamePlayer.setLayoutParams(sizeNamePlayer);
        displayNamePlayer.setTextColor(Color.BLACK);
        displayNamePlayer.setTextSize(25);
        displayNamePlayer.setGravity(Gravity.LEFT);
        displayNamePlayer.setText(game.getListJoueurs().get(0).getName());
        headerWrapper.addView(displayNamePlayer);

        //Affichage du score
        Button allScore = ViewsMaker.newButton(this, "Scores", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, true, false, 9999, 9999);
        allScore.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.bouton_touched);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.bouton);
                }
                return false;
            }
        });
        allScore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /**
                 * Affichage du score
                 */
                showScore(v);
            }
        });
        headerWrapper.addView(allScore);

        /**
         * MOLKKY-------------------------------------------------------------------------
         */
        wrapperMolkky = new LinearLayout(this);
        wrapperMolkky.setOrientation(LinearLayout.VERTICAL);
        wrapperMolkky.setBackgroundResource(R.drawable.grass_mollky);
        wrapperMolkky.setId(5648);

        RelativeLayout.LayoutParams sizeMolkkyWrapper = new RelativeLayout.LayoutParams(wrapperMolkky.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeMolkkyWrapper.addRule(RelativeLayout.CENTER_IN_PARENT);
        sizeMolkkyWrapper.setMargins(0, 0, 0, 10);

        generalWrapper.addView(wrapperMolkky, sizeMolkkyWrapper);

        /**
         * Première ligne
         */
        RelativeLayout firstRow = new RelativeLayout(this);
        firstRow.setLayoutParams(sizeRows);
        wrapperMolkky.addView(firstRow);

        Button q9 = ViewsMaker.newKeel(this, "9", 9, setScore, RelativeLayout.CENTER_IN_PARENT, 9999);
        firstRow.addView(q9);

        Button q7 = ViewsMaker.newKeel(this, "7", 7, setScore, RelativeLayout.LEFT_OF, q9.getId());
        firstRow.addView(q7);

        Button q8 = ViewsMaker.newKeel(this, "8", 8, setScore, RelativeLayout.RIGHT_OF, q9.getId());
        firstRow.addView(q8);

        /**
         * Seconde ligne
         */
        RelativeLayout secondRow = new RelativeLayout(this);
        secondRow.setLayoutParams(sizeRows);
        wrapperMolkky.addView(secondRow);

        Button q5 = ViewsMaker.newKeel(this, "5", 5, setScore, RelativeLayout.ALIGN_PARENT_LEFT, 9999);
        secondRow.addView(q5);

        Button q11 = ViewsMaker.newKeel(this, "11", 11, setScore, RelativeLayout.RIGHT_OF, q5.getId());
        secondRow.addView(q11);

        Button q12 = ViewsMaker.newKeel(this, "12", 12, setScore, RelativeLayout.RIGHT_OF, q11.getId());
        secondRow.addView(q12);

        Button q6 = ViewsMaker.newKeel(this, "6", 6, setScore, RelativeLayout.RIGHT_OF, q12.getId());
        secondRow.addView(q6);

        /**
         * Troisieme ligne
         */
        RelativeLayout thirdRow = new RelativeLayout(this);
        thirdRow.setLayoutParams(sizeRows);
        wrapperMolkky.addView(thirdRow);

        Button q10 = ViewsMaker.newKeel(this, "10", 10, setScore, RelativeLayout.CENTER_IN_PARENT, 9999);
        thirdRow.addView(q10);

        Button q3 = ViewsMaker.newKeel(this, "3", 3, setScore, RelativeLayout.LEFT_OF, q10.getId());
        thirdRow.addView(q3);

        Button q4 = ViewsMaker.newKeel(this, "4", 4, setScore, RelativeLayout.RIGHT_OF, q10.getId());
        thirdRow.addView(q4);

        /**
         * Quatrieme ligne
         */
        RelativeLayout fourthRow = new RelativeLayout(this);
        fourthRow.setLayoutParams(sizeRows);
        wrapperMolkky.addView(fourthRow);

        RelativeLayout.LayoutParams sizeSpace = new RelativeLayout.LayoutParams(1, 50);
        sizeSpace.addRule(RelativeLayout.CENTER_IN_PARENT);

        Space space = new Space(this);
        space.setLayoutParams(sizeSpace);
        space.setId(13);
        fourthRow.addView(space);

        Button q1 = ViewsMaker.newKeel(this, "1", 1, setScore, RelativeLayout.LEFT_OF, space.getId());
        fourthRow.addView(q1);

        Button q2 = ViewsMaker.newKeel(this, "2", 2, setScore, RelativeLayout.RIGHT_OF, space.getId());
        fourthRow.addView(q2);

        /**
         * FIN MOLKKY-------------------------------------------------------------------------
         */

        /**
         * Conteneur des boutons précédent, ok et l'affichage du score
         */
        RelativeLayout.LayoutParams sizeFooterWrapper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeFooterWrapper.addRule(RelativeLayout.BELOW, wrapperMolkky.getId());

        RelativeLayout footerWrapper = new RelativeLayout(this);
        footerWrapper.setLayoutParams(sizeFooterWrapper);
        generalWrapper.addView(footerWrapper);

        RelativeLayout.LayoutParams sizeScorePlayer = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeScorePlayer.addRule(RelativeLayout.CENTER_HORIZONTAL);
        sizeScorePlayer.setMargins(10, 5, 10, 5);

        displayScorePlayer = new TextView(this);
        displayScorePlayer.setText("0");
        displayScorePlayer.setGravity(Gravity.CENTER);
        displayScorePlayer.setTextSize(20);
        displayScorePlayer.setBackgroundResource(R.drawable.quille_touched);
        displayScorePlayer.setLayoutParams(sizeScorePlayer);
        displayScorePlayer.setId(new Random().nextInt(100000));
        footerWrapper.addView(displayScorePlayer);

        backPlayer = ViewsMaker.newButton(this, " < ", displayScorePlayer.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, true, RelativeLayout.LEFT_OF, displayScorePlayer.getId());
        backPlayer.setBackgroundResource(R.drawable.quille_touched);
        backPlayer.setVisibility(View.INVISIBLE);
        backPlayer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.quille);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.quille_touched);
                }
                return false;
            }
        });
        backPlayer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Retour joueur précédent
                getLastPlayer(playerFocus);
            }
        });
        footerWrapper.addView(backPlayer);

        Button setScorePlayer = ViewsMaker.newButton(this, "Ok", displayScorePlayer.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, true, RelativeLayout.RIGHT_OF, displayScorePlayer.getId());
        setScorePlayer.setBackgroundResource(R.drawable.quille_touched);
        setScorePlayer.setGravity(Gravity.CENTER);
        setScorePlayer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(R.drawable.quille);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.quille_touched);
                }
                return false;
            }
        });
        setScorePlayer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Récupération du joueur en cours + du score affiché
                setScorePlayerFocus(v, getPlayerFocus(), Integer.valueOf(displayScorePlayer.getText().toString()));
            }
        });
        footerWrapper.addView(setScorePlayer);

    }

    @Override
    public void onBackPressed() {

        //Boite de dialogue
        final Dialog dial = new Dialog(this);
        dial.setTitle("Plat to Mölkky");

        LinearLayout.LayoutParams sizeDial = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout dialWrapper = new LinearLayout(this);
        dialWrapper.setOrientation(LinearLayout.VERTICAL);
        dial.setContentView(dialWrapper, sizeDial);

        //Texte d'avertissement
        TextView warning = new TextView(this);
        warning.setLayoutParams(sizeRows);
        warning.setText("Voulez vous vraiment quitter cette partie ? Votre partie en cours sera supprimée.");
        warning.setTextColor(Color.WHITE);
        warning.setGravity(Gravity.CENTER_HORIZONTAL);
        dialWrapper.addView(warning);

        LinearLayout btnsWrapper = new LinearLayout(this);
        btnsWrapper.setLayoutParams(sizeRows);
        btnsWrapper.setOrientation(LinearLayout.HORIZONTAL);
        dialWrapper.addView(btnsWrapper);

        LinearLayout.LayoutParams sizeBtns = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5);

        //Retour menu et fin partie
        Button yes = new Button(this);
        yes.setLayoutParams(sizeBtns);
        yes.setText("Oui");
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(GameActivity.this, StartActivity.class);
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
     * Détermine le joueur en cours
     *
     * @return playerFocus = joueur en cours
     */
    private Player getPlayerFocus() {

        int nbrJoueursPartie = game.getNbrJoueurs();

        //Si l'id du joueur suivant n'existe pas, retour au premier joueur
        if (playerIdFocus >= nbrJoueursPartie) {
            playerIdFocus = 0;
        }

        listJoueurs = game.getListJoueurs();
        playerFocus = listJoueurs.get(playerIdFocus);

        return playerFocus;
    }

    /**
     * Affichage de la bonne image correspondant au bon nombre de croix du joueur
     *
     * @param player : joueur en cours
     */
    private void displayNbrCroixPlayerFocus(Player player) {

        int nbrCroixPLayer = player.getNbrCroix();

        switch (nbrCroixPLayer) {

            case 0:
                displayNamePlayer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;

            case 1:
                displayNamePlayer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.croix, 0);
                break;

            case 2:
                displayNamePlayer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.croix2, 0);
                break;
        }
    }

    /**
     * Click sur une des quilles | Récupération et affichage du score en cours pour le joueur en cours
     *
     * @param scoreToAdd = identifiant quille touchée
     * @param v = quille touchée
     */
    private void getCurrentScore(int scoreToAdd, View v) {

        //Si quille deja touchée
        if (v.isSelected()) {

            //Démarquage de la quille (déselectionnée)
            v.setSelected(false);

            //Récupération de la quille touchée dans la liste des quilles
            for (int i = 0; i < listIdBtnPressed.size(); i++) {

                int idQuille = listIdBtnPressed.get(i);

                if (idQuille == scoreToAdd) {

                    //Suppression de la quille de la liste
                    listIdBtnPressed.remove(i);
                }
            }

            //Et si la liste de quilles n'en contient qu'une seule
            if (listIdBtnPressed.size() == 1) {

                //On affiche le score de la dernière quille dans la liste
                int newScore = listIdBtnPressed.get(0);
                displayScorePlayer.setText(String.valueOf(newScore));

                //Et sinon, on affiche le nombre de quilles restantes dans la liste
            } else {

                int newScore = listIdBtnPressed.size();
                displayScorePlayer.setText(String.valueOf(newScore));
            }

            //Si la quille n'est pas sélectionnée 
        } else {

            //Marquage de la quille comme séléctionnée
            v.setSelected(true);

            //Ajout de la quille dans la liste des quilles touchées
            listIdBtnPressed.add(scoreToAdd);

            //Si première quille touchée : le score est égal au numéro de la quille
            if (listIdBtnPressed.size() == 1) {

                displayScorePlayer.setText(String.valueOf(scoreToAdd));

                //Si plusieurs quilles touchées : on additionne les quilles une par une
            } else {

                int newScore = listIdBtnPressed.size();

                displayScorePlayer.setText(String.valueOf(newScore));
            }
        }
    }

    /**
     * Click sur Btn Ok | Gestion du tir du joueur en cours et du passage au joueur suivant | Enregistrement du nouveau score et du nombre de croix | Vérification si joueur gagne, rate ou perd et est elliminé
     *
     * @param v = bouton clické
     * @param playerFocus = joueur en cours
     * @param scorePlayerFocus = score récupéré depuis l'affichage du score
     */
    private void setScorePlayerFocus(View v, Player playerFocus, int scorePlayerFocus) {

        //Vérification si joueur en cours à gagné ou non
        boolean flag = false;

        //Enregistrement du score du joueur pour une éventuelle suppression (soustraction du score enregistré à tors) avec le btn BackPlayer
        lastScorePlayer = scorePlayerFocus;

        //Enregistrement du score du joueur en cours
        playerFocus.setScore(playerFocus.getScore() + scorePlayerFocus);

        //Si lancé manqué on ajoute une croix en plus au joueur en cours
        if (scorePlayerFocus == 0) {

            //Récupération dernier nombre de croix pour une éventuelle suppression avec le btn BackPlayer
            lastCroixPlayer = playerFocus.getNbrCroix();

            //Ajout de la croix au joueur
            playerFocus.setNbrCroix(playerFocus.getNbrCroix() + 1);

            //Si le joueur en cours obtient 3 croix il est supprimé du jeu
            if (playerFocus.getNbrCroix() == game.getNbrCroixMax()) {

                //Ajout du joueur supprimé à la liste des perdants
                listJoueurOut.add(playerFocus);

                //Suppression du joueur de la liste dynamique
                listJoueurs.remove(playerFocus);

                //Suppression du joueur de la partie en cours (objet Game)
                game.setListJoueurs(listJoueurs);
                game.setNbrJoueurs(game.getListJoueurs().size());

                //Si suppression du dernier adversaire, le gagnant est désigné
                if (game.getNbrJoueurs() < 2) {

                    //Récupération du dernier joueur
                    Player playerWinnner = listJoueurs.get(0);

                    //Victoire du dernier joueur en lice
                    flag = true;
                    playerWinGame(playerWinnner);

                } else {
                    //Gestion de l'id du joueur à prendre en focus après suppression d'un joueur dans la liste
                    //si joueur 3 supprimé -> joueur 4 passe joueur 3, 5 passe 4...
                    playerIdFocus--;
                }
            }

            //Si le joueur en cours dépasse le score max, le score du joueur en cours retombe à 25
        } else if (playerFocus.getScore() > game.getScoreMax()) {

            playerFocus.setScore(25);

            //Récupération dernier nombre de croix
            lastCroixPlayer = playerFocus.getNbrCroix();

            playerFocus.setNbrCroix(0);

            //Si le joueur en cours arrive à 50, le jeu est terminé
        } else if (playerFocus.getScore() == game.getScoreMax()) {

            playerFocus.setNbrCroix(0);

            //Victoire du joueur en cours
            flag = true;
            playerWinGame(playerFocus);

            //Sinon remise à 0 des croix
        } else {

            //Récupération dernier nombre de croix
            lastCroixPlayer = playerFocus.getNbrCroix();

            playerFocus.setNbrCroix(0);
        }

        //flag = false : jeu en cours
        //flag = true : fin de partie, un gagnant a été déterminé, plus de modifications sur l'activité
        if (!flag) {

            v.setBackgroundResource(R.drawable.quille_touched);

            //Remise à zéro du molkky (boutons touchés), de la liste de quilles touchées et de l'affichage du score
            resetMolkky(wrapperMolkky);

            //Passage au joueur suivant
            playerIdFocus++;

            //Récupération du joueur suivant le joueur focus
            Player playerNext = getPlayerFocus();

            //Nom du nouveau joueur
            displayNamePlayer.setText(playerNext.getName());

            //Affichage de l'image correspondant au nombre de croix du joueur
            displayNbrCroixPlayerFocus(playerNext);

            //Apparition du bouton de retour au joueur précédent
            backPlayer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Un joueur a gagné la partie, fin de la partie, passage WinActivity
     *
     * @param playerWinner
     */
    private void playerWinGame(Player playerWinner) {

        //Liste dynamique des joueurs éliminés sous forme de json à envoyer à WinActivity
        ArrayList<CharSequence> listJsonJoueurOut = new ArrayList<CharSequence>();

        //Récupération de tous les éliminés
        for (int i = 0; i < listJoueurOut.size(); i++) {

            //Récupération du joueur éliminé
            Player joueurOut = listJoueurOut.get(i);

            //Ajout du joueur éliminé sous forme de Json
            listJsonJoueurOut.add(Player.playerToJson(joueurOut));
        }

        //Liste dynamique des joueurs encore dans la partie sous forme de json à envoyer à WinActivity
        ArrayList<CharSequence> listJsonJoueur = new ArrayList<CharSequence>();

        //Récupération de tous les joueurs encore en jeu
        for (int i = 0; i < listJoueurs.size(); i++) {

            //Récupération du joueur
            Player joueur = listJoueurs.get(i);

            //Ajout du joueur sous forme de Json
            listJsonJoueur.add(Player.playerToJson(joueur));
        }

        Intent intent = new Intent(GameActivity.this, WinActivity.class);

        //Nom du gagnant
        intent.putExtra("nameWinner", playerWinner.getName());
        //Ajout des éliminés
        intent.putCharSequenceArrayListExtra("listJoueurOut", listJsonJoueurOut);
        //Ajout des joueurs encore en jeu
        intent.putCharSequenceArrayListExtra("listJoueurs", listJsonJoueur);
        //Ajout du type de partie
        intent.putExtra("typePartie", game.getKindGame());
        startActivity(intent);
        finish();
    }

    /**
     * Click sur Btn Score | Affichage du score dans une popup gérée par le joueur (Dialog)
     *
     * @param v = bouton score
     */
    private void showScore(View v) {

        //Création popup
        popup = new Dialog(v.getContext());
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Barre bleue de séparation du popup entre titre et contenu
        int divierId = popup.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = popup.findViewById(divierId);
        if (divider != null) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        LinearLayout.LayoutParams lpPopupWrapper = new LinearLayout.LayoutParams(generalWrapper.getWidth(), generalWrapper.getHeight());

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        popup.setContentView(scroll);

        //Conteneur du popup
        LinearLayout popupWrapper = new LinearLayout(popup.getContext());
        popupWrapper.setOrientation(LinearLayout.VERTICAL);
        popupWrapper.setGravity(Gravity.CENTER);
        popupWrapper.setBackgroundResource(R.drawable.panneau_score);
        scroll.addView(popupWrapper, lpPopupWrapper);

        //Taille conteneur ligne header et joueur
        LinearLayout.LayoutParams lpTxtViewWrapper = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //Taille des champs nom croix et score dans header et joueurs
        LinearLayout.LayoutParams lpTxtView = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.3);
        lpTxtView.setMargins(0, 2, 0, 2);

        //Header (entete pour nom, croix et score)
        LinearLayout headerWrapper = new LinearLayout(popup.getContext());
        popupWrapper.addView(headerWrapper, lpTxtViewWrapper);

        //Header Nom
        TextView name = new TextView(popup.getContext());
        name.setLayoutParams(lpTxtView);
        name.setText("Nom");
        name.setGravity(Gravity.CENTER_HORIZONTAL);
        headerWrapper.addView(name);

        //Header Croix
        TextView croix = new TextView(popup.getContext());
        croix.setLayoutParams(lpTxtView);
        croix.setText("Croix");
        croix.setGravity(Gravity.CENTER_HORIZONTAL);
        headerWrapper.addView(croix);

        //Header Score
        TextView score = new TextView(popup.getContext());
        score.setLayoutParams(lpTxtView);
        score.setText("Score");
        score.setGravity(Gravity.CENTER_HORIZONTAL);
        headerWrapper.addView(score);

        //Ajout d'un textview pour chaque joueurs encore en jeu
        for (int i = 0; i < listJoueurs.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout txtViewWrapper = new LinearLayout(popup.getContext());
            popupWrapper.addView(txtViewWrapper, lpTxtViewWrapper);

            //Nom du joueur
            TextView newJoueur = new TextView(popup.getContext());
            newJoueur.setLayoutParams(lpTxtView);
            newJoueur.setText(listJoueurs.get(i).getName());
            newJoueur.setGravity(Gravity.CENTER_HORIZONTAL);
            txtViewWrapper.addView(newJoueur);

            //Conteneur de l'image des croix
            LinearLayout imgWrapper = new LinearLayout(popup.getContext());
            imgWrapper.setLayoutParams(lpTxtView);
            imgWrapper.setGravity(Gravity.CENTER_HORIZONTAL);
            txtViewWrapper.addView(imgWrapper);

            //Taille de l'image des croix
            LinearLayout.LayoutParams lpImgViewWrapper = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //Croix du joueur
            ImageView croixJoueur = new ImageView(popup.getContext());
            croixJoueur.setLayoutParams(lpImgViewWrapper);
            //Controle du nombre de croix
            int nbrCroixPLayer = listJoueurs.get(i).getNbrCroix();

            switch (nbrCroixPLayer) {

                case 0:
                    croixJoueur.setBackground(null);
                    break;

                case 1:
                    croixJoueur.setBackgroundResource(R.drawable.croix);
                    break;

                case 2:
                    croixJoueur.setBackgroundResource(R.drawable.croix2);
                    break;
            }

            imgWrapper.addView(croixJoueur);

            //Score du joueur
            TextView scoreJoueur = new TextView(popup.getContext());
            scoreJoueur.setLayoutParams(lpTxtView);
            scoreJoueur.setText(String.valueOf(listJoueurs.get(i).getScore()));

            if (Game.getPlayerMaxScore(listJoueurs) == listJoueurs.get(i).getScore()) {
                scoreJoueur.setTextColor(Color.YELLOW);
            }

            scoreJoueur.setGravity(Gravity.CENTER_HORIZONTAL);
            txtViewWrapper.addView(scoreJoueur);
        }
        popup.show();
    }

    /**
     * Déclenchement dans setScorePlayerFocus | Remise à zéro des quilles pour nouveau joueur
     *
     * @param molkkyWrapper = conteneur des quilles
     */
    private void resetMolkky(LinearLayout molkkyWrapper) {

        Button quille;

        //Suppression des quilles dans la liste dynamique
        listIdBtnPressed.clear();

        //Récupération des enfants du conteneur molkky
        for (int i = 0; i < molkkyWrapper.getChildCount(); i++) {

            //Rangée de quilles
            RelativeLayout rl = (RelativeLayout) molkkyWrapper.getChildAt(i);

            //Pour chaque quilles dans la rangée
            for (int j = 0; j < rl.getChildCount(); j++) {

                //Si c'est bien une quille (pas un Space)
                if (!rl.getChildAt(j).getClass().toString().equals("class android.widget.Space")) {

                    //Marquage de la quille comme non touchée et changement background
                    quille = (Button) rl.getChildAt(j);
                    quille.setSelected(false);
                    quille.setBackgroundResource(R.drawable.quille);
                }
            }
        }
        //Remise à zéro de l'affichage du score
        displayScorePlayer.setText("0");
    }

    /**
     * Click sur BackPlayer | Revenir au joueur précédent le joueur en cours
     *
     * @param playerFocusOff = joueur en cours voulant revenir au joueur précédent
     */
    private void getLastPlayer(Player playerFocusOff) {

        //Id du dernier joueur dans la liste
        int lastIdInGameList = game.getListJoueurs().size() - 1;

        //Récupération du dernier joueur à avoir joué
        Player lastPlayerFocus;

        //Si le joueur en cours est le premier de la liste
        if (playerFocusOff.getId() == 0) {

            //On récupère le dernier de cette liste
            lastPlayerFocus = game.getListJoueurs().get(lastIdInGameList);

        } else {

            //On récupère le joueur avec l'id précédent
            lastPlayerFocus = game.getListJoueurs().get(playerFocusOff.getId() - 1);
        }

        //Le joueur précédent deviens le joueur en cours
        playerFocus = lastPlayerFocus;

        //Soustraction du dernier score enregistré par le nouveau joueur en cours
        playerFocus.setScore(playerFocus.getScore() - lastScorePlayer);

        //Récupération du dernier nombre de croix
        playerFocus.setNbrCroix(lastCroixPlayer);

        //Id du joueur en cours deviens celui du nouveau joueur
        playerIdFocus = playerFocus.getId();

        //Modification du Nom affiché
        displayNamePlayer.setText(playerFocus.getName());

        //Disparition du bouton de retour au joueur précédent (empeche les multiples retours en arriere)
        backPlayer.setVisibility(View.INVISIBLE);

        //Remise à zéro du molkky
        resetMolkky(wrapperMolkky);
    }
}
