package com.callisto.diceroller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;

public abstract class XmlFragment
    extends BaseFragment
{
    @Override
    public void onInflate(Context context, AttributeSet attrs,
                          Bundle savedInstanceState) {

        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            fm.beginTransaction().remove(this).commit();
        }

        super.onInflate(context, attrs, savedInstanceState);
    }


    @Override
    public void onDestroyView()
    {
        FragmentManager fm = getFragmentManager();

        assert fm != null;

        Fragment xmlFragment = fm.findFragmentById(this.getId());

        if (xmlFragment != null) {
            fm.beginTransaction().remove(xmlFragment).commit();
        }

        super.onDestroyView();
    }
}
