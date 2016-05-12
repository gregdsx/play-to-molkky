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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.devtoweb.factory.GameFactory;

/**
 * Activité passerelle après la victoire d'un joueur. Affichage du nom du vainqueur. Possibilité de recommencer ou voir les scores généraux.
 *
 * @author Greg27
 */
public class TrophyActivity extends Activity {

    private RelativeLayout generalWrapper;
    private final RelativeLayout.LayoutParams lpMatchParent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new RelativeLayout(this);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);
        generalWrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        setContentView(generalWrapper);

        RelativeLayout.LayoutParams lpTrophy = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpTrophy.addRule(RelativeLayout.CENTER_IN_PARENT);
        lpTrophy.topMargin = ViewsMaker.getDpFromPixel(this, 10);
        lpTrophy.bottomMargin = ViewsMaker.getDpFromPixel(this, 10);

        //Image de la coupe
        ImageView trophy = new ImageView(this);
        trophy.setBackgroundResource(R.drawable.trophy);
        trophy.setLayoutParams(lpTrophy);
        trophy.setId(62351);
        generalWrapper.addView(trophy);

        RelativeLayout.LayoutParams lpTxt = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpTxt.addRule(RelativeLayout.ABOVE, trophy.getId());

        LinearLayout wrapperTxt = new LinearLayout(this);
        wrapperTxt.setLayoutParams(lpTxt);
        wrapperTxt.setOrientation(LinearLayout.VERTICAL);
        wrapperTxt.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.addView(wrapperTxt);

        //Nom du gagnant
        TextView nameWin = ViewsMaker.newTextView(this, GameFactory.getGameServiceImpl().getWinner().getName(), ViewsMaker.getFontSizeWithScreenWidth(this) + 7, Color.BLACK, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 5, 0, 5, 0, Gravity.CENTER, false, false, 9999, 9999);
        wrapperTxt.addView(nameWin);

        TextView txtWin = ViewsMaker.newTextView(this, "A gagné !", ViewsMaker.getFontSizeWithScreenWidth(this) + 5, Color.BLACK, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 5, 0, 5, 0, Gravity.CENTER, true, false, 9999, 9999);
        wrapperTxt.addView(txtWin);

        RelativeLayout.LayoutParams lpBtn = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpBtn.addRule(RelativeLayout.BELOW, trophy.getId());

        LinearLayout wrapperBtn = new LinearLayout(this);
        wrapperBtn.setLayoutParams(lpBtn);
        wrapperBtn.setOrientation(LinearLayout.HORIZONTAL);
        wrapperBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.addView(wrapperBtn);

        //Boutons de visualisation des scores, WinActivity
        Button replay = ViewsMaker.newButton(this, "Replay", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, false, false, 9999, 9999);
        replay.setOnTouchListener(new View.OnTouchListener() {

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
        replay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                restartNewGame();
            }
        });
        wrapperBtn.addView(replay);

        //Boutons de visualisation des scores, WinActivity
        Button next = ViewsMaker.newButton(this, "Scores", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1, false, false, 9999, 9999);
        next.setOnTouchListener(new View.OnTouchListener() {

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
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(TrophyActivity.this, WinActivity.class);
                startActivity(intent);
                finish();
            }
        });
        wrapperBtn.addView(next);
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
                "Voulez vous vraiment retourner au menu sans voir les scores ?",
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
        btnsWrapper.setLayoutParams(lpMatchParent);
        btnsWrapper.setOrientation(LinearLayout.HORIZONTAL);
        dialWrapper.addView(btnsWrapper);

        //Retour menu et fin partie
        Button yes = ViewsMaker.newButton(this, "Oui", 0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.5, false, false, 9999, 9999);
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(TrophyActivity.this, StartActivity.class);
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
     * Click sur btn Recommencer | Début nouvelle partie
     *
     */
    private void restartNewGame() {

        //Nouvelle partie créée
        GameFactory.getGameServiceImpl().restartNewGame();

        Intent intent = new Intent(TrophyActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

}
