package com.devtoweb;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Ecouteur de GameActivity (generalWrapper) pour la gestion des swipe : affichge des scores
 *
 * @author Greg27
 */
public class GestureListener extends SimpleOnGestureListener{

    GestureDetector gDetector;
    Context context;

    public GestureListener() {
        super();
    }

    public GestureListener(Context context) {
        this(context, null);
    }

    public GestureListener(Context context, GestureDetector gDetector) {

        if (gDetector == null) {
            gDetector = new GestureDetector(context, this);
        }
        this.context = context;
        this.gDetector = gDetector;
    }

    /**
     * Swipe détécté
     *
     * @param e1 Début action
     * @param e2 Fin action
     * @param velocityX Rapidité horizontale
     * @param velocityY Rapidité verticale
     * @return true si swipe horizontal
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        int minSwipeDistance = viewConfiguration.getScaledPagingTouchSlop();
        int minSwipeVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        int maxSwipeOffPath = viewConfiguration.getScaledTouchSlop();

        float start = e1.getX();
        float end = e2.getX();

        if (Math.abs(velocityX) < minSwipeVelocity) {

            return false;
        }

        else if (start - end > minSwipeDistance) {

            return true;

        } else if (end - start > minSwipeDistance) {

            return true;

        } else {

            return false;
        }
    }
}
