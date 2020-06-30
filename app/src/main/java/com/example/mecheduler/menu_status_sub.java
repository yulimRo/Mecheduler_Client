package com.example.mecheduler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class menu_status_sub extends LinearLayout {

    public menu_status_sub(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public menu_status_sub(Context context)
    {
        super(context);

        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_status_sub, this, true);
    }
}
