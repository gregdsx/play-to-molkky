/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devtoweb;

import android.app.Activity;
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

/**
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

        //Image de la coupe
        ImageView trophy = new ImageView(this);
        trophy.setBackgroundResource(R.drawable.trophy);
        trophy.setLayoutParams(lpTrophy);
        trophy.setId(62351);
        generalWrapper.addView(trophy);

        LinearLayout wrapperTxt = new LinearLayout(this);
        wrapperTxt.setLayoutParams(lpMatchParent);
        wrapperTxt.setOrientation(LinearLayout.VERTICAL);
        wrapperTxt.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.addView(wrapperTxt);

        TextView txtWin = ViewsMaker.newTextView(this, "A gagné !", ViewsMaker.getFontSizeWithScreenWidth(this), Color.BLACK, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 5, 0, 5, 0, Gravity.CENTER, true, true, RelativeLayout.ABOVE, trophy.getId());
        wrapperTxt.addView(txtWin);

        //Nom du gagnant
        TextView nameWin = ViewsMaker.newTextView(this, "Nom vainqueur", ViewsMaker.getFontSizeWithScreenWidth(this), Color.BLACK, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 5, 0, 5, 0, Gravity.CENTER, true, true, RelativeLayout.ABOVE, txtWin.getId());
        wrapperTxt.addView(nameWin);

        LinearLayout wrapperBtn = new LinearLayout(this);
        wrapperBtn.setLayoutParams(lpMatchParent);
        wrapperBtn.setOrientation(LinearLayout.VERTICAL);
        wrapperBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.addView(wrapperBtn);

        //Boutons de visualisation des scores, WinActivity
        Button next = ViewsMaker.newButton(this, "Scores", ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, false, true, RelativeLayout.BELOW, trophy.getId());
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

}
