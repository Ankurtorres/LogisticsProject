package com.logimetrix.nj.logistics_project.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;

/**
 * Created by hp 15 on 30-Aug-17.
 */

public class BuilderManager {
    private static int[] imageResources = new int[]{
            R.drawable.home1,
            R.drawable.endjourney,
            R.drawable.logout1
    };
    private static int[] textResources=new int[]{
            R.string.home,
            R.string.end_journey,
            R.string.logout
    };
    private static int imageResourceIndex = 0;
    private static int textResourceIndex = 0;

    static int getImageResource(){
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    static int getTextResource() {
        if (textResourceIndex >= textResources.length) textResourceIndex = 0;
        return textResources[textResourceIndex++];
    }

    static HamButton.Builder getHamButtonBuilderWithDifferentPieceColor(){
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(getTextResource())
                .normalTextColor(Color.BLACK)
                .textGravity(Gravity.CENTER)
               // .textSize(R.dimen.dp_10)
                .pieceColor(Color.WHITE);
    }


    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}

