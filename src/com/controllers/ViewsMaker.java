/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.devtoweb.R;
import java.util.Random;

/**
 *
 * @author Greg27
 */
public class ViewsMaker extends Activity {

    /**
     * Création d'un bouton
     *
     * @param context Context du bouton à créer
     * @param text Texte du bouton à afficher
     * @param width Largeur du bouton
     * @param height Hauteur du bouton
     * @param weight Largeur relative à la largeur de l'ecran et du nombre d'élément à insérer (poids)
     * @param idOrNot Id requis ou non
     * @param relOrLinear Bouton en position linéaire ou relative : Si true, bouton en position relative
     * @param rule1 Règle 1 pour un bouton relatif
     * @param rule2 Règle 2 pour un bouton relatif
     * @return Retourne le bouton souhaité
     */
    public static Button newButton(Context context, String text, int width, int height, float weight, boolean idOrNot, boolean relOrLinear, int rule1, int rule2) {

        Button button;

        /**
         * Génération d'un id "unique" (Integer)
         */
        Random r = new Random();
        r.nextInt();
        int buttonsId = r.nextInt(100000);

        /**
         * Si bouton en position relative
         */
        if (relOrLinear) {

            /**
             * Largeur, Hauteur et marges du bouton
             */
            RelativeLayout.LayoutParams sizeButton = new RelativeLayout.LayoutParams(width, height);

            //Si règle de position définie avec un id
            if (rule1 != 9999 && rule2 != 9999) {
                sizeButton.addRule(rule1, rule2);

                //Si règle de position définie sans id
            } else if (rule1 != 9999 && rule2 == 9999) {
                sizeButton.addRule(rule1);
            }

            sizeButton.setMargins(0, 5, 0, 5);

            /**
             * Création du bouton
             */
            button = new Button(context);
            button.setText(text);
            button.setLayoutParams(sizeButton);
            if (idOrNot) {
                button.setId(buttonsId);
            }

        } else {

            /**
             * Largeur, Hauteur, Poids et marges du bouton
             */
            LinearLayout.LayoutParams sizeButton = new LinearLayout.LayoutParams(width, height, weight);
            sizeButton.setMargins(0, 5, 0, 5);

            /**
             * Création du bouton
             */
            button = new Button(context);
            button.setText(text);
            button.setLayoutParams(sizeButton);
            if (idOrNot) {
                button.setId(buttonsId);
            }
        }

        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.bouton);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        return button;
    }

    /**
     * Création d'un nouvel EditText
     *
     * @param context Context de l'EditText
     * @param hint Texte à afficher avant saisie de l'utilisateur
     * @param drawableOrNot EditText contient un CompoundDrawable, oui ou non
     * @return Retourne l'EditText crée
     */
    public static EditText newEditText(Context context, String hint, boolean drawableOrNot) {

        EditText editText;

        /**
         * Génération d'un id "unique" (Integer)
         */
        Random r = new Random();
        r.nextInt();
        int etId = r.nextInt(100000);

        //Taille de l'edittext
        LinearLayout.LayoutParams sizeEditText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeEditText.setMargins(0, 5, 0, 5);

        /**
         * Hauteur des EditText | Récupération de la taille en DP transformée en PX | height = 60dp
         */
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 60f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        //Création de l'edittext
        editText = new EditText(context);
        editText.setLayoutParams(sizeEditText);
        editText.setPadding(0, 10, 0, 10);
        editText.setHeight(pixels);
        editText.setId(etId);
        editText.setTextColor(Color.BLACK);
        editText.setBackgroundColor(Color.WHITE);
        editText.setHint(hint);
        editText.setHintTextColor(Color.BLACK);

        //Gestion d'un drawable
        if (drawableOrNot) {

            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.poubelle, 0);

        }

        return editText;
    }

    /**
     * Création d'une quille
     *
     * @param context Context de la quille a créer
     * @param text Numéro de la quille
     * @param id Id de la quille (son numéro)
     * @param rule1 Règle de placement 1
     * @param rule2 Règle de placement 2
     * @param listener Ecouteur de la quille créee
     * @return
     */
    public static Button newKeel(Context context, String text, int id, View.OnClickListener listener, int rule1, int rule2) {

        Button keel = new Button(context);
        keel.setId(id);
        keel.setText(text);
        keel.setTextSize(20);
        keel.setTextColor(Color.BLACK);
        keel.setBackgroundResource(R.drawable.quille);

        RelativeLayout.LayoutParams sizeKeel = new RelativeLayout.LayoutParams(keel.getBackground().getMinimumWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);

        //Ajout des régles de placements
        //9999 = no rule
        if (rule1 != 9999 && rule2 != 9999) {
            sizeKeel.addRule(rule1, rule2);

            //Si règle de position définie sans id
        } else if (rule1 != 9999 && rule2 == 9999) {
            sizeKeel.addRule(rule1);
        }

        keel.setLayoutParams(sizeKeel);
        keel.setOnClickListener(listener);

        return keel;
    }
}
