package com.jpndev.niravu.utility;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpndev.utilitylibrary.WrapLinearLayoutManager;

public class WrapContentLinearLayoutManager extends WrapLinearLayoutManager {

    //LinearLayoutManager
  /*  public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }*/


    public WrapContentLinearLayoutManager(Context context, @RecyclerView.Orientation int orientation,
                               boolean reverseLayout) {
        super(context,orientation,reverseLayout);
    }

    //... constructor
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {

            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens");
        }
    }
}
