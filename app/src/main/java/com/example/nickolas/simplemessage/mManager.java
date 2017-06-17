package com.example.nickolas.simplemessage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Nickolas on 17.06.2017.
 */

public class mManager extends LinearLayoutManager{

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public mManager(Context context) {
        super(context);
    }

    public mManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public mManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
