package com.devtoweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Choix du mode de jeu : seul ou en équipe | Non utilisée pour la version 1.0
 *
 * @author Grégory Desormeaux
 */
public class ChoiceActivity extends Activity {

    private LinearLayout generalWrapper;
    private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new LinearLayout(this);
        generalWrapper.setOrientation(LinearLayout.VERTICAL);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);

        setContentView(generalWrapper);

        LinearLayout.LayoutParams lpLogo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginBottomLogo = ViewsMaker.getDpFromPixel(this, 30);
        lpLogo.setMargins(0, 0, 0, marginBottomLogo);

        ImageView logo = new ImageView(this);
        logo.setLayoutParams(lpLogo);
        logo.setBackgroundResource(R.drawable.logo);
        generalWrapper.addView(logo);

        Button solo = ViewsMaker.newButton(this, "Solo", ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, false, 9999, 9999);
        solo.setOnTouchListener(new View.OnTouchListener() {

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
        solo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ChoiceActivity.this, SetGameActivity.class);
                intent.putExtra("typeDePartie", "Solo");
                startActivity(intent);
                finish();
            }
        });
        generalWrapper.addView(solo);

        Button team = ViewsMaker.newButton(this, "En équipe", ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, true, false, 9999, 9999);
        team.setOnTouchListener(new View.OnTouchListener() {

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
        team.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ChoiceActivity.this, SetGameActivity.class);
                intent.putExtra("typeDePartie", "Equipe");
                startActivity(intent);
                finish();
            }
        });
        generalWrapper.addView(team);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ChoiceActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

}
