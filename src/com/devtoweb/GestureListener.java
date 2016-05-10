/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devtoweb;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
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

        if (start - end > minSwipeDistance) {
            System.out.println("right to left");

        } else if (end - start > minSwipeDistance) {
            System.out.println("left to right");
        }

        return true;
    }
}
