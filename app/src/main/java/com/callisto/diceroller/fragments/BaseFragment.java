package com.callisto.diceroller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.callisto.diceroller.viewmanagers.FragmentNavigation;

public abstract class BaseFragment
    extends Fragment
    implements FragmentNavigation.View {

    protected View rootView;

    protected FragmentNavigation.Presenter navigationPresenter;

    @Nullable
    @Override
    public View onCreateView
        (@NonNull LayoutInflater inflater,
         @Nullable ViewGroup container,
         @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayout(), container, false);

        findViews();

        return rootView;
    }

    @Override
    public void attachPresenter(FragmentNavigation.Presenter presenter) {
        navigationPresenter = presenter;
    }

    protected abstract int getLayout();

    protected abstract void findViews();

}
