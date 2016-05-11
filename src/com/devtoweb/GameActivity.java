package com.devtoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
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
import com.devtoweb.factory.GameFactory;
import com.devtoweb.factory.Player;
import java.util.ArrayList;

/**
 *
 * @author Greg27
 */
public class GameActivity extends Activity{

    private RelativeLayout generalWrapper;
    private LinearLayout wrapperMolkky;
    private final RelativeLayout.LayoutParams lpMatchParent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final RelativeLayout.LayoutParams sizeRows = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private TextView displayScorePlayer, displayNamePlayer;
    private Button backPlayer;
    private Dialog popup;
    private GestureListener gestureListener;
    private GestureDetector gDetector;
    private final View.OnClickListener setScore = new View.OnClickListener() {

        public void onClick(View v) {

            if (v.isSelected()) {

                Button keel = (Button) v;
                keel.setBackgroundResource(R.drawable.quille);
                keel.setTextColor(Color.argb(255, 51, 204, 51));

            } else {

                Button keel = (Button) v;
                keel.setBackgroundResource(R.drawable.quille_touched);
                keel.setTextColor(Color.argb(255, 255, 255, 255));
            }

            //TEST
            DisplayMetrics dis = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dis);

            System.out.println("taille molkky : " + wrapperMolkky.getWidth());
            System.out.println(displayNamePlayer.getTextSize());
            //FIN TEST
            getCurrentScore(v.getId(), v);
        }
    };

    //Objets de gestion de partie
    private Player playerFocus;
    private int lastScorePlayer, lastCroixPlayer;
    private final ArrayList<Integer> listIdBtnPressed = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Gestion des events Swipe pour afficher score
        gestureListener = new GestureListener(this);
        gDetector = new GestureDetector(this, gestureListener);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new RelativeLayout(this);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);
        generalWrapper.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (gDetector.onTouchEvent(event)) {

                    //Affichage score sur swipe 
                    showScore(v);
                    return true;
                }
                return true;
            }
        });
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

        //Nom du joueur en cours
        displayNamePlayer = ViewsMaker.newTextView(this,
                getPlayerFocus().getName(),
                28,
                Color.BLACK,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                10,
                0,
                10,
                0,
                Gravity.LEFT,
                false,
                false,
                9999,
                9999);
        displayNamePlayer.setMaxLines(1);
        headerWrapper.addView(displayNamePlayer);

        /**
         * MOLKKY-------------------------------------------------------------------------
         */
        wrapperMolkky = new LinearLayout(this);
        wrapperMolkky.setOrientation(LinearLayout.VERTICAL);
        wrapperMolkky.setBackgroundResource(R.drawable.grass_mollky);
        wrapperMolkky.setId(5648);

        RelativeLayout.LayoutParams sizeMolkkyWrapper = new RelativeLayout.LayoutParams(wrapperMolkky.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeMolkkyWrapper.addRule(RelativeLayout.CENTER_IN_PARENT);
        int marginBottomMolkky = ViewsMaker.getDpFromPixel(this, 10);
        sizeMolkkyWrapper.setMargins(0, 0, 0, marginBottomMolkky);

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
        footerWrapper.setGravity(Gravity.CENTER);
        generalWrapper.addView(footerWrapper);

        displayScorePlayer = ViewsMaker.newTextView(this,
                "0",
                ViewsMaker.getFontSizeWithScreenWidth(this),
                Color.argb(255, 255, 255, 255),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                5,
                10,
                5,
                0,
                Gravity.CENTER,
                true,
                true,
                RelativeLayout.CENTER_IN_PARENT,
                9999);
        displayScorePlayer.setBackgroundResource(R.drawable.quille_touched);
        displayScorePlayer.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/" + "snaket.ttf"));
        footerWrapper.addView(displayScorePlayer);

        backPlayer = ViewsMaker.newButton(this, " < ", displayScorePlayer.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, true, RelativeLayout.LEFT_OF, displayScorePlayer.getId());
        backPlayer.setBackgroundResource(R.drawable.quille_touched);
        backPlayer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        backPlayer.setTextColor(Color.argb(255, 255, 255, 255));
        backPlayer.setVisibility(View.INVISIBLE);
        backPlayer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                Button btn = (Button) v;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btn.setBackgroundResource(R.drawable.quille);
                    btn.setTextColor(Color.argb(255, 51, 204, 51));

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    btn.setBackgroundResource(R.drawable.quille_touched);
                    btn.setTextColor(Color.argb(255, 255, 255, 255));
                }
                return false;
            }
        });
        backPlayer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Retour joueur précédent
                getLastPlayer();
            }
        });
        footerWrapper.addView(backPlayer);

        Button setScorePlayer = ViewsMaker.newButton(this, "OK", displayScorePlayer.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, true, RelativeLayout.RIGHT_OF, displayScorePlayer.getId());
        setScorePlayer.setBackgroundResource(R.drawable.quille_touched);
        setScorePlayer.setTextColor(Color.argb(255, 255, 255, 255));
        setScorePlayer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                Button btn = (Button) v;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btn.setBackgroundResource(R.drawable.quille);
                    btn.setTextColor(Color.argb(255, 51, 204, 51));

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    btn.setBackgroundResource(R.drawable.quille_touched);
                    btn.setTextColor(Color.argb(255, 255, 255, 255));
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
        dial.setTitle("Play to Mölkky");

        LinearLayout.LayoutParams sizeDial = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout dialWrapper = new LinearLayout(this);
        dialWrapper.setOrientation(LinearLayout.VERTICAL);
        dial.setContentView(dialWrapper, sizeDial);

        //Texte d'avertissement
        TextView warning = ViewsMaker.newTextView(this,
                "Voulez vous vraiment quitter cette partie ? Votre partie en cours sera supprimée.",
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
        btnsWrapper.setLayoutParams(sizeRows);
        btnsWrapper.setOrientation(LinearLayout.HORIZONTAL);
        dialWrapper.addView(btnsWrapper);

        //Retour menu et fin partie
        Button yes = ViewsMaker.newButton(this, "Oui", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, false, false, 9999, 9999);
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(GameActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        yes.setBackgroundResource(android.R.drawable.btn_default);
        btnsWrapper.addView(yes);

        //Reprise de la partie en cours 
        Button no = ViewsMaker.newButton(this, "Non", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, false, false, 9999, 9999);
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dial.dismiss();
            }
        });
        no.setBackgroundResource(android.R.drawable.btn_default);
        btnsWrapper.addView(no);

        dial.show();
    }

    /**
     * Détermine le joueur en cours
     *
     * @return playerFocus = joueur en cours
     */
    private Player getPlayerFocus() {

        playerFocus = GameFactory.getGameServiceImpl().getPlayerFocus();

        return playerFocus;
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
    private void setScorePlayerFocus(View v, Player playerFocus, int score) {

        //Vérification si il y a un gagnant ou non
        boolean flag;

        //Enregistrement du score du joueur pour une éventuelle suppression (soustraction du score enregistré à tors) avec le btn BackPlayer
        lastScorePlayer = score;

        //Récupération dernier nombre de croix pour une éventuelle suppression avec le btn BackPlayer
        lastCroixPlayer = playerFocus.getNbrCroix();

        //Check nombre de joueurs avant enregistrement score
        int nbrJoueursInGame = GameFactory.getGameServiceImpl().getGame().getNbrJoueurs();

        //Gestion de l'enregistrement du score, retour du flag false joueur suivant, true fin de partie
        flag = GameFactory.getGameServiceImpl().setScorePlayerFocus(playerFocus, score);

        //Check nombre de joueur après enregistrement score
        int nbrJoueursInGameAfterSetScore = GameFactory.getGameServiceImpl().getGame().getNbrJoueurs();

        //si flag = false : pas de gagnant, la partie continue
        if (!flag) {

            v.setBackgroundResource(R.drawable.quille_touched);

            //Remise à zéro du molkky (boutons touchés), de la liste de quilles touchées et de l'affichage du score
            resetMolkky(wrapperMolkky);

            //Nom du nouveau joueur
            displayNamePlayer.setText(GameFactory.getGameServiceImpl().getPlayerFocus().getName());

            //Affichage de l'image correspondant au nombre de croix du joueur
            displayNbrCroixPlayerFocus(GameFactory.getGameServiceImpl().getPlayerFocus());

            //Si il n'y a pas eu de suppression de joueurs, backPlayer deviens disponible
            //Si un joueur est supprimé, pas de retour en arrière possible
            if (nbrJoueursInGame == nbrJoueursInGameAfterSetScore) {

                backPlayer.setVisibility(View.VISIBLE);

            } else {
                backPlayer.setVisibility(View.INVISIBLE);
            }

            //si flag = true : fin de partie, un gagnant a été déterminé, pas de modifications sur l'activité
        } else {
            playerWinGame();
        }
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
     * Remise à zéro des quilles pour nouveau joueur
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
                    quille.setTextColor(Color.argb(255, 51, 204, 51));
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
    private void getLastPlayer() {

        //Le joueur précédent deviens le joueur en cours
        playerFocus = GameFactory.getGameServiceImpl().getLastPlayer(lastScorePlayer, lastCroixPlayer);

        //Modification du Nom affiché
        displayNamePlayer.setText(playerFocus.getName());

        //Disparition du bouton de retour au joueur précédent (empeche les multiples retours en arriere)
        backPlayer.setVisibility(View.INVISIBLE);

        //Modificcation du nombre de croix affichée
        displayNbrCroixPlayerFocus(playerFocus);

        //Remise à zéro du molkky
        resetMolkky(wrapperMolkky);
    }

    /**
     * Click sur Btn Score | Affichage du score dans une popup gérée par le joueur (Dialog)
     *
     * @param v = bouton score
     */
    public void showScore(View v) {

        //Création popup
        popup = new Dialog(v.getContext());
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Barre bleue de séparation du popup entre titre et contenu à rendre trnsparent
        int divierId = popup.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = popup.findViewById(divierId);
        if (divider != null) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (gDetector.onTouchEvent(event)) {

                    //Fermeture popup
                    popup.dismiss();
                    return true;
                }
                return true;
            }
        });
        popup.setContentView(scroll);

        LinearLayout.LayoutParams lpPopupWrapper = new LinearLayout.LayoutParams(generalWrapper.getWidth() - generalWrapper.getPaddingLeft() * 2, generalWrapper.getHeight());
        lpPopupWrapper.leftMargin = generalWrapper.getPaddingLeft();

        //Taille police pour dialog
        int fontSizeDialog = ViewsMaker.getFontSizeWithScreenWidth(popup.getContext()) - 4;

        //Conteneur du popup
        LinearLayout popupWrapper = new LinearLayout(popup.getContext());
        popupWrapper.setOrientation(LinearLayout.VERTICAL);
        popupWrapper.setGravity(Gravity.CENTER);
        popupWrapper.setBackgroundResource(R.drawable.panneau_score);
        scroll.addView(popupWrapper, lpPopupWrapper);

        //Taille conteneur ligne header et joueur
        LinearLayout.LayoutParams lpTxtViewWrapper = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Header (entete pour nom, croix et score)
        LinearLayout headerWrapper = new LinearLayout(popup.getContext());
        popupWrapper.addView(headerWrapper, lpTxtViewWrapper);

        //Header Nom
        TextView name = ViewsMaker.newTextView(popup.getContext(),
                "Nom",
                fontSizeDialog,
                Color.WHITE,
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (float) 0.5,
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                Gravity.CENTER_HORIZONTAL,
                false,
                false,
                9999,
                9999);
        headerWrapper.addView(name);

        //Header Croix
        TextView croix = ViewsMaker.newTextView(popup.getContext(),
                "Croix",
                fontSizeDialog,
                Color.WHITE,
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (float) 0.25,
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                Gravity.CENTER_HORIZONTAL,
                false,
                false,
                9999,
                9999);
        headerWrapper.addView(croix);

        //Header Score
        TextView score = ViewsMaker.newTextView(popup.getContext(),
                "Score",
                fontSizeDialog,
                Color.WHITE,
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (float) 0.25,
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                0,
                ViewsMaker.getDpFromPixel(this, 2),
                Gravity.CENTER_HORIZONTAL,
                false,
                false,
                9999,
                9999);
        headerWrapper.addView(score);

        ArrayList<Player> listJoueurs = GameFactory.getGameServiceImpl().getGame().getListJoueurs();

        //Ajout d'un textview pour chaque joueurs encore en jeu
        for (int i = 0; i < listJoueurs.size(); i++) {

            //Conteneur ligne joueur
            LinearLayout txtViewWrapper = new LinearLayout(popup.getContext());
            txtViewWrapper.setGravity(Gravity.CENTER_VERTICAL);
            popupWrapper.addView(txtViewWrapper, lpTxtViewWrapper);

            //Nom du joueur
            TextView newJoueur = ViewsMaker.newTextView(popup.getContext(),
                    listJoueurs.get(i).getName(),
                    fontSizeDialog,
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
            txtViewWrapper.addView(newJoueur);

            //Conteneur de l'image des croix
            LinearLayout imgWrapper = new LinearLayout(popup.getContext());
            imgWrapper.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25));
            imgWrapper.setGravity(Gravity.CENTER);
            txtViewWrapper.addView(imgWrapper);

            //Croix du joueur
            ImageView croixJoueur = new ImageView(popup.getContext());
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
            TextView scoreJoueur = ViewsMaker.newTextView(popup.getContext(),
                    String.valueOf(listJoueurs.get(i).getScore()),
                    fontSizeDialog,
                    Color.WHITE,
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (float) 0.25,
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    0,
                    ViewsMaker.getDpFromPixel(this, 2),
                    Gravity.CENTER_HORIZONTAL,
                    false,
                    false,
                    9999,
                    9999);

            if (GameFactory.getGameServiceImpl().getGame().getPlayerMaxScore(listJoueurs) == listJoueurs.get(i).getScore()) {
                scoreJoueur.setTextColor(Color.YELLOW);
            }

            txtViewWrapper.addView(scoreJoueur);
        }
        popup.show();
    }

    /**
     * Un joueur a gagné la partie, fin de la partie, passage WinActivity
     *
     * @param playerWinner
     */
    private void playerWinGame() {

        Intent intent = new Intent(GameActivity.this, TrophyActivity.class);
        startActivity(intent);
        finish();
    }

}
