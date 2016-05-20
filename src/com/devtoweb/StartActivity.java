package com.devtoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Première page accessible à l'utilisateur et launcher. Choix de jeu ou voir les instructions
 */
public class StartActivity extends Activity {

    private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final LinearLayout.LayoutParams lpWrapContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout generalWrapper;
    private Button newGame;
    private Button instructions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new LinearLayout(this);
        generalWrapper.setOrientation(LinearLayout.VERTICAL);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);

        //Définition de la vue à travers generalWrapper
        setContentView(generalWrapper);

        LinearLayout.LayoutParams lpLogo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginBottomLogo = ViewsMaker.getDpFromPixel(this, 30);
        lpLogo.setMargins(0, 0, 0, marginBottomLogo);

        ImageView logo = new ImageView(this);
        logo.setLayoutParams(lpLogo);
        logo.setBackgroundResource(R.drawable.logo);
        generalWrapper.addView(logo);

        newGame = ViewsMaker.newButton(this, "Play", ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, false, false, 9999, 9999);
        newGame.setOnTouchListener(new View.OnTouchListener() {

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
        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, SetGameActivity.class);
                //Type de partie solo défini en dur, en attendant la V2
                intent.putExtra("typeDePartie", "Solo");
                startActivity(intent);
                finish();
            }
        });
        generalWrapper.addView(newGame);

        instructions = ViewsMaker.newButton(this, "Règles", ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, false, false, 9999, 9999);
        instructions.setOnTouchListener(new View.OnTouchListener() {

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
        instructions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, InstructionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        generalWrapper.addView(instructions);

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

}
