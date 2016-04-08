package com.devtoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.controllers.ViewsMaker;
import com.object.Player;
import com.object.Game;
import java.util.ArrayList;

/**
 *
 * @author Greg27
 */
public class SetGameActivity extends Activity implements TextWatcher {

    private ScrollView scrollCtn;
    private LinearLayout generalWrapper;
    private final LinearLayout.LayoutParams rlMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final LinearLayout.LayoutParams lpWrapContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private String kindPlayer;
    private Bundle bundle;
    private EditText champ2;
    private int flagPlayer = 2;
    private Game game;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Récupération du type de jeu depuis ChoiceActivity
        bundle = getIntent().getExtras();
        String typeDePartie = bundle.getString("typeDePartie");
        //Utilisation de kindPlayer dans les champs de saises
        kindPlayer = getKindGame(typeDePartie);

        scrollCtn = new ScrollView(this);
        scrollCtn.setFillViewport(true);
        setContentView(scrollCtn);

        generalWrapper = new LinearLayout(this);
        generalWrapper.setLayoutParams(rlMatchParent);
        generalWrapper.setOrientation(LinearLayout.VERTICAL);
        generalWrapper.setPadding(20, 20, 20, 20);
        generalWrapper.setBackgroundResource(R.drawable.background);
        scrollCtn.addView(generalWrapper);

        LinearLayout btnWrapper = new LinearLayout(this);
        btnWrapper.setLayoutParams(lpWrapContent);
        btnWrapper.setOrientation(LinearLayout.HORIZONTAL);
        generalWrapper.addView(btnWrapper);

        Button play = ViewsMaker.newButton(this, "PLAY", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, true, false, 9999, 9999);
        play.setOnTouchListener(new View.OnTouchListener() {

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
        play.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /**
                 * Récupération du type de jeu et du nombre de joueur Lancement de la partie Début GameActivity
                 */
                setGame(v);
            }
        });
        btnWrapper.addView(play);

        Button addPlayer = ViewsMaker.newButton(this, "+ 1 " + kindPlayer, 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, true, false, 9999, 9999);
        addPlayer.setOnTouchListener(new View.OnTouchListener() {

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
        addPlayer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /**
                 * Enregistrement nouveau joueur / équipe
                 */
                setNewPlayerEditText(v);
            }
        });
        btnWrapper.addView(addPlayer);

        EditText champ1 = ViewsMaker.newEditText(this, kindPlayer + " 1", false);
        champ1.addTextChangedListener(this);
        champ1.setTextColor(Color.BLACK);
        champ1.setHintTextColor(Color.BLACK);
        generalWrapper.addView(champ1);

        champ2 = ViewsMaker.newEditText(this, kindPlayer + " 2", false);
        champ2.addTextChangedListener(this);
        champ2.setTextColor(Color.BLACK);
        champ2.setHintTextColor(Color.BLACK);
        generalWrapper.addView(champ2);
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
        warning.setLayoutParams(lpWrapContent);
        warning.setText("Voulez vous vraiment quitter cette partie ? Votre partie en cours sera supprimée.");
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

                Intent intent = new Intent(SetGameActivity.this, ChoiceActivity.class);
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
     * Récupération du nom du bouton de l'activité précédente pour déterminé le type de partie
     *
     * @param typeDePartie
     * @return kindPlayer Retourne le type de partie
     */
    public String getKindGame(String typeDePartie) {
        String typePlayer = "Joueur";
        if (typeDePartie.equals("Solo")) {
            typePlayer = "Joueur";
        } else if (typeDePartie.equals("Equipe")) {
            typePlayer = "Equipe";
        }
        return typePlayer;
    }

    /**
     * Création d'un nouvel EditText pour enregistré un nouveau joueur ou une nouvelle équipe
     *
     * @param v Bouton + 1 joueur
     */
    public void setNewPlayerEditText(View v) {

        //Gestion du nombre max de joueur ou equipe : max 6
        if (flagPlayer != 6) {

            //Récupération d'un objet Button
            Button b = (Button) v;

            //hint à ajouter dans new champ
            //Récupéré depuis le texte du bouton +1 = joueur ou equipe
            String t = b.getText().toString();
            String txtToAdd = t.substring(4, t.length());

            //Création edittext
            EditText newChamp = ViewsMaker.newEditText(this, "New " + txtToAdd, true);
            newChamp.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v2, MotionEvent event) {

                    /**
                     * Gestion de la suppression de l'EditText concerné si click sur drawable supprimer
                     */
                    EditText et = (EditText) v2;

                    //Détection de l'action sur l'ecran
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        int paddingMax = et.getRight() - et.getTotalPaddingRight();

                        //Récupération de l'endroit du click (getRaw)
                        //Vérification si le click se trouve dans le padding de l'EditText crée par le Drawable
                        if (event.getRawX() <= et.getRight() && event.getRawX() >= paddingMax) {

                            //Changement background
                            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.poubelle_touched, 0);
                        }

                    }

                    //Détection de l'action sur l'ecran
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        int paddingMax = et.getRight() - et.getTotalPaddingRight();

                        //Récupération de l'endroit du click (getRaw)
                        //Vérification si le click se trouve dans le padding de l'EditText crée par le Drawable
                        if (event.getRawX() <= et.getRight() && event.getRawX() >= paddingMax) {

                            //Changement de visibilié de l'EditTxt concerné par le click du drawable supprimé
                            //Rendu invisible
                            et.setVisibility(View.GONE);

                            //Enleve 1 joueur au compteur
                            flagPlayer--;

                            return true;
                        }
                    }

                    return false;
                }
            });
            newChamp.setTextColor(Color.BLACK);
            newChamp.setHintTextColor(Color.BLACK);
            newChamp.addTextChangedListener(this);
            generalWrapper.addView(newChamp);

            //Ajoute un joueur au compteur
            flagPlayer++;

        } else {
            Toast.makeText(generalWrapper.getContext(), "Nombre de joueurs / équipes maximum atteint (6 max)", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Récupération joueurs et paramétrage partie Passage d'activité à GameActivity
     *
     * @param v Bouton Play
     */
    public void setGame(View v) {

        //Récupération du layout général (generalWrapper)
        LinearLayout parent = (LinearLayout) v.getParent().getParent();

        //Création de la liste des noms des joueurs
        ArrayList<String> listPlayerName = new ArrayList<String>();

        //Nombre d'enfant dans cette vue
        int nbrChild = parent.getChildCount();

        for (int i = 0; i < nbrChild; i++) {

            //Enfant ciblé par la boucle
            View childInLoop = parent.getChildAt(i);

            //Type de vue à récupérer (EditText)
            String editTxtClass = "android.widget.EditText";

            //Si cet enfant est visible, donc non supprimé,
            //Et si c'est une vue de type EditText
            if (childInLoop.isShown() && childInLoop.getClass().getName().equals(editTxtClass)) {

                //La vue childInLoop devient un EditText
                EditText etToSave = (EditText) childInLoop;

                //Nom du joueur tapé dans EditText
                String etGetTxt = etToSave.getText().toString();

                //Si pas de nom entré, récupération du Hint
                //Sinon récupération du texte tapé
                //Ajout du nom dans la liste de joueurs
                if (etGetTxt.isEmpty()) {

                    listPlayerName.add(etToSave.getHint().toString());

                } else {

                    listPlayerName.add(etGetTxt);
                }
            }
            //Fin Loop
        }

        //Création de la liste des joueurs
        ArrayList<Player> listPlayers = new ArrayList<Player>();

        //Récupération des joueurs
        for (int i = 0; i < listPlayerName.size(); i++) {

            //Création d'un nouveau joueur : nom du joueur, identifiant, score, croix
            Player newPlayer = new Player(listPlayerName.get(i).toString(), i, 0, 0);
            listPlayers.add(newPlayer);
        }

        //Création de la partie : nombre de joueurs, liste de joueurs, type de partie
        game = new Game(listPlayers.size(), listPlayers, kindPlayer);

        //Objet Game transformé en Json
        String gameToString = Game.gameToJson(game);

        //Envoi du game json à GameActivity
        //Lancement partie
        Intent intent = new Intent(SetGameActivity.this, GameActivity.class);
        intent.putExtra("game", gameToString);
        startActivity(intent);
        finish();
    }

    /**
     * Methodes override de TextWatcher | Verification de saisie
     */
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * Vérifiaction des textes saisient
     *
     * @param s
     */
    public void afterTextChanged(Editable s) {

        /**
         * Supprimer le dernier caractere ou interdire de le taper | Vérifier caracteres tapés
         */
        //Nombre caractère max
        int maxCar = 15;
        //Caractères interdits
        String blockCharacterSet = "\"'~#^|$%&*!<>&{}/[]^¨-()+@_=,;:?.§²";
        String sequence = s.toString();

        //Check caractères spéciaux
        for (int i = 0; i < blockCharacterSet.length(); i++) {

            if (!s.toString().isEmpty() && s.charAt(s.length() - 1) == blockCharacterSet.charAt(i)) {

                //Suppression caractère interdit
                s.delete(s.length() - 1, s.length());

                //Si toast vient d'etre crée ou si il est déja visible, pas de nouvel affichage du toast
                if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                    toast = Toast.makeText(getApplicationContext(),
                            "T'as besoin que de lettres pour ton nom !!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast.cancel();
                }
            }
        }

        //Check longueur du nom
        if (sequence.length() > maxCar) {

            //Bloquage écriture par suppression dernier caractère
            s.delete(s.length() - 1, s.length());

            //Si toast vient d'etre crée ou si il est déja visible, pas de nouvel affichage du toast
            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                toast = Toast.makeText(getApplicationContext(),
                        "Pas un nom si grand..!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast.cancel();
            }
        }

    }

}