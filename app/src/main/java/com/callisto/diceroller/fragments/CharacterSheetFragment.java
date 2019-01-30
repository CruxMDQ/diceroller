package com.callisto.diceroller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.callisto.diceroller.R;
import com.callisto.diceroller.beans.Stat;
import com.callisto.diceroller.interfaces.StatObserver;
import com.callisto.diceroller.presenters.CharacterSheetPresenter;
import com.callisto.diceroller.viewmanagers.CharacterSheet;
import com.callisto.diceroller.views.StatBox;
import com.callisto.diceroller.views.StatLayout;

import java.util.ArrayList;
import java.util.Iterator;

public class CharacterSheetFragment
    extends BaseFragment
    implements
        CharacterSheet.View,
        StatObserver {

    CharacterSheetPresenter presenter;

    private StatLayout containerAttrsMental;
    private StatLayout containerAttrsPhysical;
    private StatLayout containerAttrsSocial;

    private LinearLayout panelAttrsMental;
    private LinearLayout panelAttrsPhysical;
    private LinearLayout panelAttrsSocial;

    private TextView txtSelectedMentalAttribute;
    private TextView txtSelectedPhysicalAttribute;
    private TextView txtSelectedSocialAttribute;
    
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

    @Override
    protected int getLayout() {
        return R.layout.fragment_char_sheet;
    }

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterSheetPresenter(this, getContext());

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

        FloatingActionButton fabRoll = rootView.findViewById(R.id.fabRoll);
        fabRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.hasDiceToRoll()) {
                    spawnCustomDiceRollDialog(presenter.getStats());
                } else {
                    spawnNoDiceAlert();
                }
            }
        });

        containerAttrsMental = rootView.findViewById(R.id.containerAttrsMental);
        containerAttrsMental.setPanelStats(
            (LinearLayout) rootView.findViewById(R.id.panelAttrsMental));
        containerAttrsMental.setTxtSelectedStats(
            (TextView) rootView.findViewById(R.id.txtSelectedMentalAttribute));
        containerAttrsMental.setLblSelectedStats(
            (TextView) rootView.findViewById(R.id.labelAttrsMental));
        containerAttrsMental.addOrRemoveContainedStat(statIntelligence.getStat());
        containerAttrsMental.addOrRemoveContainedStat(statWits.getStat());
        containerAttrsMental.addOrRemoveContainedStat(statResolve.getStat());

        containerAttrsPhysical = rootView.findViewById(R.id.containerAttrsPhysical);
        containerAttrsPhysical.setPanelStats(
            (LinearLayout) rootView.findViewById(R.id.panelAttrsPhysical));
        containerAttrsPhysical.setTxtSelectedStats(
            (TextView) rootView.findViewById(R.id.txtSelectedPhysicalAttribute));
        containerAttrsPhysical.setLblSelectedStats(
            (TextView) rootView.findViewById(R.id.labelAttrsPhysical));
        containerAttrsPhysical.addOrRemoveContainedStat(statStrength.getStat());
        containerAttrsPhysical.addOrRemoveContainedStat(statDexterity.getStat());
        containerAttrsPhysical.addOrRemoveContainedStat(statStamina.getStat());

        containerAttrsSocial = rootView.findViewById(R.id.containerAttrsSocial);
        containerAttrsSocial.setPanelStats(
            (LinearLayout) rootView.findViewById(R.id.panelAttrsSocial));
        containerAttrsSocial.setTxtSelectedStats(
            (TextView) rootView.findViewById(R.id.txtSelectedSocialAttribute));
        containerAttrsSocial.setLblSelectedStats(
            (TextView) rootView.findViewById(R.id.labelAttrsSocial));
        containerAttrsSocial.addOrRemoveContainedStat(statPresence.getStat());
        containerAttrsSocial.addOrRemoveContainedStat(statManipulation.getStat());
        containerAttrsSocial.addOrRemoveContainedStat(statComposure.getStat());
    }

    private void spawnNoDiceAlert() {
        Toast.makeText(getContext(), getString(R.string.alert_no_dice), Toast.LENGTH_SHORT).show();
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
    public void spawnCustomDiceRollDialog(ArrayList<Pair<String, Integer>> stats) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        // Set dialog title
        dialogBuilder.setTitle(getString(R.string.label_dice_roll));

        // Set dialog view
        final View view = getLayoutInflater().inflate(R.layout.dialog_dice_roll, null);
        dialogBuilder.setView(view);

        // Find dialog components
        TextView txtDicePool = view.findViewById(R.id.txtDicePool);
        final EditText inputRerollThreshold = view.findViewById(R.id.inputRerollThreshold);
        final CheckBox chkExtendedRoll = view.findViewById(R.id.chkExtendedRoll);
        Button btnRoll = view.findViewById(R.id.btnRoll);

        // Assemble dice pool string
        String statList = "";

        Iterator iterator = stats.iterator();

        while (iterator.hasNext()) {
            Pair<String, Integer> stat = (Pair<String, Integer>) iterator.next();

            statList = statList.concat(stat.first);

            if (iterator.hasNext()) {
                statList = statList.concat(" + ");
            }
        }

        // Set dice pool string
        txtDicePool.setText(statList);

        // Store dialog reference to later be able to dismiss it
        final AlertDialog dialog = dialogBuilder.show();

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int threshold = Integer.parseInt(inputRerollThreshold.getText().toString());

                if (!chkExtendedRoll.isChecked()) {
                    presenter.rollDice(threshold);
                } else {
                    presenter.rollExtended(threshold);
                }

                dialog.cancel();
            }
        });

    }

    @Override
    public void spawnStatEditionDialog(final int id, String statName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        dialogBuilder.setTitle(getString(R.string.label_stat_edition));
        dialogBuilder.setMessage(getString(R.string.prompt_stat_edition, statName));

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
    public void changeDicePool(String statName, int value, int colorSelected) {
        presenter.addOrRemoveStat(new Pair<>(statName, value));
        presenter.getStatDetails(statName);
    }

    @Override
    public void showResults(ArrayList<Integer> rolls, int successes, boolean isExtended) {
        AlertDialog ad = new AlertDialog.Builder(getContext())
                .create();
        ad.setCancelable(true);

        if (successes == 0 && isExtended) {
            ad.setMessage(getString(R.string.alert_roll_failed));
        } else {
            Iterator iterator = rolls.iterator();

            String rollString = "";

            while (iterator.hasNext()) {
                int roll = Integer.parseInt(iterator.next().toString());

                rollString = rollString.concat(String.valueOf(roll));

                if (iterator.hasNext()) {
                    rollString = rollString.concat(", ");
                }
            }

            ad.setTitle(String.valueOf(getString(R.string.label_successes_report, successes)));
            ad.setMessage(getString(R.string.label_roll_report, rollString));
        }
        ad.show();
    }

    @Override
    public void addSelectedStatToPanel(Stat stat)
    {
        if (containerAttrsMental.shouldContain(stat))
        {
            containerAttrsMental.addOrRemovePickedStat(stat);
        }
        else if (containerAttrsSocial.shouldContain(stat))
        {
            containerAttrsSocial.addOrRemovePickedStat(stat);
        }
        else if (containerAttrsPhysical.shouldContain(stat))
        {
            containerAttrsPhysical.addOrRemovePickedStat(stat);
        }
    }

    @Override
    public void setStatPanelColor(Stat stat)
    {
        String type = stat.getType();
        String category = stat.getCategory();

        StatLayout target = null;

        if (category.equals(getString(R.string.stat_category_attr)))
        {
            switch (type)
            {
                case "Mental":
                {
                    target = containerAttrsMental;

                    break;
                }
                case "Physical":
                {
                    target = containerAttrsPhysical;

                    break;
                }
                case "Social":
                {
                    target = containerAttrsSocial;

                    break;
                }
            }
            target.setPanelColor();
        }
    }
}
