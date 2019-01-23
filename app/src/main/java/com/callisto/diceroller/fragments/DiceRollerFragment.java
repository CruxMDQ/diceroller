package com.callisto.diceroller.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.callisto.diceroller.R;
import com.callisto.diceroller.presenters.DiceRollerPresenter;
import com.callisto.diceroller.viewmanagers.DiceRollerNavigation;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Iterator;

@EFragment(R.layout.fragment_dice_roller)
public class DiceRollerFragment
    extends BaseFragment
    implements DiceRollerNavigation.View,
               DiceRollerNavigation.Presenter
{

    DiceRollerPresenter presenter;

    @ViewById EditText txtDice;
    @ViewById EditText txtThreshold;

    @Override
    public void onViewCreated
        (android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new DiceRollerPresenter(this);
    }

    @Click
    void fabRollDice() {
        presenter.rollDice(getDiceNumber(), getReRollThreshold());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dice_roller;
    }

    @Override
    public int getReRollThreshold() {
        return Integer.parseInt(txtThreshold.getText().toString());
    }

    @Override
    public int getDiceNumber() {
        return Integer.parseInt(txtDice.getText().toString());
    }

    @Override
    public void showResults(ArrayList<Integer> rolls, int successes) {
        Iterator iterator = rolls.iterator();

        String rollString = "";

        while (iterator.hasNext()) {
            int roll = Integer.parseInt(iterator.next().toString());

            rollString = rollString.concat(String.valueOf(roll));

            if (iterator.hasNext()) {
                rollString = rollString.concat(", ");
            }
        }

        AlertDialog ad = new AlertDialog.Builder(getContext())
            .create();
        ad.setCancelable(true);
        ad.setTitle(String.valueOf(successes) + " successes");
        ad.setMessage("The rolls were " + rollString);
        ad.show();
    }
}
