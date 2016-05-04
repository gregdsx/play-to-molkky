/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devtoweb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
        int marginTopBottomBtns = getDpFromPixel(context, 5);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "segoescb.ttf");
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

            sizeButton.setMargins(0, marginTopBottomBtns, 0, marginTopBottomBtns);

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
            sizeButton.setMargins(0, marginTopBottomBtns, 0, marginTopBottomBtns);

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
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, getFontSizeWithScreenWidth(context));
        button.setGravity(Gravity.CENTER);
        button.setTypeface(typeface);

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
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "segoesc.ttf");

        /**
         * Génération d'un id "unique" (Integer)
         */
        Random r = new Random();
        r.nextInt();
        int etId = r.nextInt(100000);

        int marginTopBottomEdTxt = getDpFromPixel(context, 7);

        //Taille de l'edittext
        LinearLayout.LayoutParams sizeEditText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeEditText.setMargins(0, marginTopBottomEdTxt, 0, marginTopBottomEdTxt);

        /**
         * Hauteur des EditText | Récupération de la taille en DP transformée en PX | height = 60dp
         */
        int heightEdTxt = getDpFromPixel(context, 50);

        //Création de l'edittext
        editText = new EditText(context);
        editText.setLayoutParams(sizeEditText);
        editText.setPadding(0, 10, 0, 10);
        editText.setId(etId);
        editText.setTextColor(Color.BLACK);
        editText.setBackgroundColor(Color.WHITE);
        editText.setHint(hint);
        editText.setHintTextColor(Color.BLACK);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editText.setHeight(heightEdTxt);
        editText.setTypeface(typeface);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getFontSizeWithScreenWidth(context));

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

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "snaket.ttf");

        Button keel = new Button(context);
        keel.setId(id);
        keel.setText(text);
        keel.setGravity(Gravity.CENTER);
        keel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getFontSizeWithScreenWidth(context));
        keel.setTextColor(Color.argb(255, 51, 204, 51));
        keel.setTypeface(typeface);
        keel.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
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

    /**
     * Création d'un nouveau TextView
     *
     * @param context Context du TextView
     * @param text Texte à afficher
     * @param textSize Taille du texte
     * @param textColor Couleur du texte
     * @param width Largeur du TextView
     * @param height Hauteur du TextView
     * @param weight Poids du TextView
     * @param marginLeft Marge Gauche
     * @param marginTop Marge Top
     * @param marginRight Marge Droite
     * @param marginBottom Marge Basse
     * @param gravity Gravité du TextView
     * @param idOrNot Si idOrNot true, création d'un id du TextView
     * @param relOrNot Si le TextView est en position relative ou non
     * @param rule1 Si relOrNot true, première règle de placement
     * @param rule2 Seconde règle de placement
     * @return Nouveau TextView
     */
    public static TextView newTextView(Context context, String text, int textSize, int textColor, int width, int height, float weight, int marginLeft, int marginTop, int marginRight, int marginBottom, int gravity, boolean idOrNot, boolean relOrNot, int rule1, int rule2) {

        TextView textView;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "segoesc.ttf");

        Random r = new Random();
        r.nextInt();
        int id = r.nextInt(100000);

        textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize((float) textSize);
        textView.setTextColor(textColor);
        textView.setTypeface(typeface);
        textView.setGravity(gravity);

        if (idOrNot) {
            textView.setId(id);
        }

        /**
         * Si textview en position relative
         */
        if (relOrNot) {

            /**
             * Largeur, Hauteur et marges du bouton
             */
            RelativeLayout.LayoutParams sizeTxtView = new RelativeLayout.LayoutParams(width, height);
            sizeTxtView.setMargins(getDpFromPixel(context, marginLeft), getDpFromPixel(context, marginTop), getDpFromPixel(context, marginRight), getDpFromPixel(context, marginBottom));

            //2 regles de positions prédefinies
            if (rule1 != 9999 && rule2 != 9999) {
                sizeTxtView.addRule(rule1, rule2);

                //1 regle de position prédéfinie
            } else if (rule1 != 9999 && rule2 == 9999) {
                sizeTxtView.addRule(rule1);
            }

            textView.setLayoutParams(sizeTxtView);

        } else {

            /**
             * Largeur, Hauteur, Poids et marges du bouton
             */
            LinearLayout.LayoutParams sizeTxtView = new LinearLayout.LayoutParams(width, height, weight);
            sizeTxtView.setMargins(getDpFromPixel(context, marginLeft), getDpFromPixel(context, marginTop), getDpFromPixel(context, marginRight), getDpFromPixel(context, marginBottom));

            textView.setLayoutParams(sizeTxtView);
        }

        return textView;
    }

    public static int getDpFromPixel(Context context, int dptoUse) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        float dp = Float.parseFloat(dptoUse + "f");
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        return pixels;
    }

    public static int getFontSizeWithScreenWidth(Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        float density = metrics.density;

        int sizeFontInPixel;

        //Ldpi et mdpi 
        if (width < 321) {

            sizeFontInPixel = 14;

            //large ldpi et mdpi, hdpi
        } else if (width < 481 && width > 321) {

            sizeFontInPixel = 18;

            //Si ldpi agrandissement
            if (density == 0.75) {

                sizeFontInPixel = 21;
            }

            //Extra large ldpi et hdpi
        } else if (width < 641 && width > 481) {

            sizeFontInPixel = 20;

            //Si ldpi agrandissement
            if (density == 0.75) {

                sizeFontInPixel = 23;
            }

        } else {

            sizeFontInPixel = 22;
        }

        return sizeFontInPixel;
    }
}
