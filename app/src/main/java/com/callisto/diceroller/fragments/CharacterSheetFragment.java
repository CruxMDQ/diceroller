package com.callisto.diceroller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.callisto.diceroller.R;
import com.callisto.diceroller.interfaces.StatObserver;
import com.callisto.diceroller.presenters.CharacterSheetPresenter;
import com.callisto.diceroller.viewmanagers.DiceRollerNavigation;
import com.callisto.diceroller.views.StatBox;

import java.util.ArrayList;
import java.util.Iterator;

public class CharacterSheetFragment
    extends BaseFragment
    implements
        DiceRollerNavigation.View,
        StatObserver {

    CharacterSheetPresenter presenter;

    // Attributes
    private StatBox statIntelligence;
    private StatBox statWits;
    private StatBox statResolve;
    private StatBox statStrength;
    private StatBox statDexterity;
    private StatBox statStamina;
    private StatBox statPresence;
    private StatBox statManipulation;
    private StatBox statComposure;

    // Skills
    // Mental
    private StatBox skillAcademics;
    private StatBox skillCrafts;
    private StatBox skillComputer;
    private StatBox skillInvestigation;
    private StatBox skillMedicine;
    private StatBox skillOccult;
    private StatBox skillPolitics;
    private StatBox skillScience;

    // Physical
    private StatBox skillAthletics;
    private StatBox skillBrawl;
    private StatBox skillDrive;
    private StatBox skillFirearms;
    private StatBox skillLarceny;
    private StatBox skillStealth;
    private StatBox skillSurvival;
    private StatBox skillWeaponry;

    // Social
    private StatBox skillAnimalKen;
    private StatBox skillEmpathy;
    private StatBox skillExpression;
    private StatBox skillIntimidation;
    private StatBox skillPersuasion;
    private StatBox skillSocialize;
    private StatBox skillStreetwise;
    private StatBox skillSubterfuge;

    private FloatingActionButton fabRoll;

    @Override
    protected int getLayout() {
        return R.layout.fragment_char_sheet;
    }

    @Override
    public void onViewCreated
        (android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterSheetPresenter(this);

        observeBoxes();
    }

    protected void findViews() {
        statIntelligence = rootView.findViewById(R.id.statIntelligence);
        statWits = rootView.findViewById(R.id.statWits);
        statResolve = rootView.findViewById(R.id.statResolve);
        statStrength = rootView.findViewById(R.id.statStrength);
        statDexterity = rootView.findViewById(R.id.statDexterity);
        statStamina = rootView.findViewById(R.id.statStamina);
        statPresence = rootView.findViewById(R.id.statPresence);
        statManipulation = rootView.findViewById(R.id.statManipulation);
        statComposure = rootView.findViewById(R.id.statComposure);

        skillAcademics = rootView.findViewById(R.id.skillAcademics);
        skillAnimalKen = rootView.findViewById(R.id.skillAnimalKen);
        skillAthletics = rootView.findViewById(R.id.skillAthletics);
        skillBrawl = rootView.findViewById(R.id.skillBrawl);
        skillComputer = rootView.findViewById(R.id.skillComputer);
        skillCrafts = rootView.findViewById(R.id.skillCrafts);
        skillDrive = rootView.findViewById(R.id.skillDrive);
        skillEmpathy = rootView.findViewById(R.id.skillEmpathy);
        skillExpression = rootView.findViewById(R.id.skillExpression);
        skillFirearms = rootView.findViewById(R.id.skillFirearms);
        skillIntimidation = rootView.findViewById(R.id.skillIntimidation);
        skillInvestigation = rootView.findViewById(R.id.skillInvestigation);
        skillLarceny = rootView.findViewById(R.id.skillLarceny);
        skillMedicine = rootView.findViewById(R.id.skillMedicine);
        skillOccult = rootView.findViewById(R.id.skillOccult);
        skillPersuasion = rootView.findViewById(R.id.skillPersuasion);
        skillPolitics = rootView.findViewById(R.id.skillPolitics);
        skillScience = rootView.findViewById(R.id.skillScience);
        skillSocialize = rootView.findViewById(R.id.skillSocialize);
        skillStealth = rootView.findViewById(R.id.skillStealth);
        skillStreetwise = rootView.findViewById(R.id.skillStreetwise);
        skillSubterfuge = rootView.findViewById(R.id.skillSubterfuge);
        skillSurvival = rootView.findViewById(R.id.skillSurvival);
        skillWeaponry = rootView.findViewById(R.id.skillWeaponry);

        fabRoll = rootView.findViewById(R.id.fabRoll);
        fabRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spawnDiceRollDialog(presenter.getStats());
            }
        });
    }

    public void observeBoxes() {
        statIntelligence.setObserver(this);
        statWits.setObserver(this);
        statResolve.setObserver(this);
        statStrength.setObserver(this);
        statDexterity.setObserver(this);
        statStamina.setObserver(this);
        statPresence.setObserver(this);
        statManipulation.setObserver(this);
        statComposure.setObserver(this);

        skillAcademics.setObserver(this);
        skillAnimalKen.setObserver(this);
        skillAthletics.setObserver(this);
        skillBrawl.setObserver(this);
        skillComputer.setObserver(this);
        skillCrafts.setObserver(this);
        skillDrive.setObserver(this);
        skillEmpathy.setObserver(this);
        skillExpression.setObserver(this);
        skillFirearms.setObserver(this);
        skillIntimidation.setObserver(this);
        skillInvestigation.setObserver(this);
        skillLarceny.setObserver(this);
        skillMedicine.setObserver(this);
        skillOccult.setObserver(this);
        skillPersuasion.setObserver(this);
        skillPolitics.setObserver(this);
        skillScience.setObserver(this);
        skillSocialize.setObserver(this);
        skillStealth.setObserver(this);
        skillStreetwise.setObserver(this);
        skillSubterfuge.setObserver(this);
        skillSurvival.setObserver(this);
        skillWeaponry.setObserver(this);
    }

    // TODO Think up a logic to account for equipment-based penalties
    public void spawnDiceRollDialog(ArrayList<Pair<String, Integer>> stats) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        dialogBuilder.setTitle("Dice roll");

        String statList = "";

        Iterator iterator = stats.iterator();

        while (iterator.hasNext()) {
            Pair<String, Integer> stat = (Pair<String, Integer>) iterator.next();

            statList = statList.concat(stat.first);

            if (iterator.hasNext()) {
                statList = statList.concat(" + ");
            }
        }

        dialogBuilder.setMessage("Dice pool: " + statList);

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        dialogBuilder.setView(input);


        dialogBuilder.setPositiveButton(getString(R.string.label_btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int threshold = Integer.parseInt(input.getText().toString());

                presenter.rollDice(threshold);

                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    @Override
    public void spawnStatEditionDialog(final int id, String statName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        dialogBuilder.setTitle(getString(R.string.label_stat_edition));
        dialogBuilder.setMessage(getString(R.string.prompt_stat_edition) + statName);

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        dialogBuilder.setView(input);

        dialogBuilder.setPositiveButton(getString(R.string.label_btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                StatBox statBox = rootView.findViewById(id);

                statBox.setValue(newValue);
            }
        });

        dialogBuilder.show();
    }

    @Override
    public void changeDicePool(String statName, int value) {
        presenter.addOrRemoveStat(new Pair<>(statName, value));
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
