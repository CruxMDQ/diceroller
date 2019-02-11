package com.callisto.diceroller.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.DicePoolChangedEvent;
import com.callisto.diceroller.bus.events.PanelTappedEvent;
import com.callisto.diceroller.bus.events.StatEditionRequestedEvent;
import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.interfaces.ViewWatcher;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.presenters.CharacterSheetPresenter;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;
import com.callisto.diceroller.viewmanagers.CharacterSheet;
import com.callisto.diceroller.views.ResourceLayout;
import com.callisto.diceroller.views.StatBox;
import com.callisto.diceroller.views.StatLayout;
import com.callisto.diceroller.views.UnfoldingLayout;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class CharacterSheetFragment
    extends BaseFragment
    implements
        CharacterSheet.View,
        ViewWatcher
{
    CharacterSheetPresenter presenter;

    private StatLayout containerAttrsMental;
    private StatLayout containerAttrsPhysical;
    private StatLayout containerAttrsSocial;

    private StatLayout containerSkillsMental;
    private StatLayout containerSkillsPhysical;
    private StatLayout containerSkillsSocial;

    private ResourceLayout resourcePanelHealth;
    private ResourceLayout resourcePanelWillpower;

    private ArrayList<UnfoldingLayout> statContainers;

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

    // Derived stats
    private StatBox statSize;
    private StatBox derivedSpeed;
    private StatBox derivedInitiative;
    private StatBox derivedDefense;

    private TextView lblAttributes;
    private TextView lblSkills;
    private TextView lblDerived;

    private String font;

//    private DynamicStatLayout panelTrialRun;

    @Override
    protected int getLayout() {
        return R.layout.fragment_char_sheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        font = getArguments().getString(Constants.Parameters.FONT.getText());
    }

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterSheetPresenter(this, getContext());

        setUpContainers();

        observeBoxes();

        subscribeToEvents();

        setTypefacesOnTitles();

//        setUpTrialPanel();
    }

    public static CharacterSheetFragment newInstance(String font)
    {
        CharacterSheetFragment myFragment = new CharacterSheetFragment();

        Bundle args = new Bundle();

        args.putString(Constants.Parameters.FONT.getText(), font);

        myFragment.setArguments(args);

        return myFragment;
    }

    private void setTypefacesOnTitles()
    {
        TypefaceSpanBuilder.setTypefacedTitle(
            lblAttributes,
            App.getRes().getString(R.string.label_attributes),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        TypefaceSpanBuilder.setTypefacedTitle(
            lblSkills,
            App.getRes().getString(R.string.label_skills),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        TypefaceSpanBuilder.setTypefacedTitle(
            lblDerived,
            App.getRes().getString(R.string.label_derived_stats),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );
    }

    private void setUpContainers()
    {
        containerAttrsMental.addOrRemoveContainedStat(presenter.getStatByTag(statIntelligence.getTag()));
        containerAttrsMental.addOrRemoveContainedStat(presenter.getStatByTag(statWits.getTag()));
        containerAttrsMental.addOrRemoveContainedStat(presenter.getStatByTag(statResolve.getTag()));

        containerAttrsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(statStrength.getTag()));
        containerAttrsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(statDexterity.getTag()));
        containerAttrsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(statStamina.getTag()));

        containerAttrsSocial.addOrRemoveContainedStat(presenter.getStatByTag(statPresence.getTag()));
        containerAttrsSocial.addOrRemoveContainedStat(presenter.getStatByTag(statManipulation.getTag()));
        containerAttrsSocial.addOrRemoveContainedStat(presenter.getStatByTag(statComposure.getTag()));

        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillAcademics.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillComputer.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillCrafts.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillInvestigation.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillMedicine.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillOccult.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillPolitics.getTag()));
        containerSkillsMental.addOrRemoveContainedStat(presenter.getStatByTag(skillScience.getTag()));

        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillAthletics.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillBrawl.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillDrive.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillFirearms.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillLarceny.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillStealth.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillSurvival.getTag()));
        containerSkillsPhysical.addOrRemoveContainedStat(presenter.getStatByTag(skillWeaponry.getTag()));

        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillAnimalKen.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillEmpathy.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillExpression.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillIntimidation.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillPersuasion.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillSocialize.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillStreetwise.getTag()));
        containerSkillsSocial.addOrRemoveContainedStat(presenter.getStatByTag(skillSubterfuge.getTag()));
    }

    protected void findViews()
    {
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
        fabRoll.setOnClickListener(v ->
        {
            if (presenter.hasDiceToRoll())
            {
                spawnCustomDiceRollDialog(presenter.getStats());
            }
            else
            {
                spawnNoDiceAlert();
            }
        });

        containerAttrsMental = rootView.findViewById(R.id.containerAttrsMental);
        containerAttrsMental.setPanelStats(
            rootView.findViewById(R.id.panelAttrsMental));
        containerAttrsMental.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedMentalAttribute));
        containerAttrsMental.setLblSelectedStats(
            rootView.findViewById(R.id.labelAttrsMental));

        containerAttrsPhysical = rootView.findViewById(R.id.containerAttrsPhysical);
        containerAttrsPhysical.setPanelStats(
            rootView.findViewById(R.id.panelAttrsPhysical));
        containerAttrsPhysical.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedPhysicalAttribute));
        containerAttrsPhysical.setLblSelectedStats(
            rootView.findViewById(R.id.labelAttrsPhysical));

        containerAttrsSocial = rootView.findViewById(R.id.containerAttrsSocial);
        containerAttrsSocial.setPanelStats(
            rootView.findViewById(R.id.panelAttrsSocial));
        containerAttrsSocial.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedSocialAttribute));
        containerAttrsSocial.setLblSelectedStats(
            rootView.findViewById(R.id.labelAttrsSocial));
        containerSkillsMental = rootView.findViewById(R.id.containerSkillsMental);
        containerSkillsMental.setPanelStats(
            rootView.findViewById(R.id.panelSkillsMental));
        containerSkillsMental.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedMentalSkill));
        containerSkillsMental.setLblSelectedStats(
            rootView.findViewById(R.id.lblSkillsMental));

        containerSkillsPhysical = rootView.findViewById(R.id.containerSkillsPhysical);
        containerSkillsPhysical.setPanelStats(
            rootView.findViewById(R.id.panelSkillsPhysical));
        containerSkillsPhysical.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedPhysicalSkill));
        containerSkillsPhysical.setLblSelectedStats(
            rootView.findViewById(R.id.lblSkillsPhysical));

        containerSkillsSocial = rootView.findViewById(R.id.containerSkillsSocial);
        containerSkillsSocial.setPanelStats(
            rootView.findViewById(R.id.panelSkillsSocial));
        containerSkillsSocial.setTxtSummary(
            rootView.findViewById(R.id.txtSelectedSocialSkill));
        containerSkillsSocial.setLblSelectedStats(
            rootView.findViewById(R.id.lblSkillsSocial));

        resourcePanelHealth = rootView.findViewById(R.id.panelHealth);
        resourcePanelWillpower = rootView.findViewById(R.id.panelWillpower);

        statContainers = new ArrayList<>();
        statContainers.add(containerAttrsMental);
        statContainers.add(containerAttrsPhysical);
        statContainers.add(containerAttrsSocial);
        statContainers.add(containerSkillsMental);
        statContainers.add(containerSkillsPhysical);
        statContainers.add(containerSkillsSocial);
        statContainers.add(resourcePanelHealth);
        statContainers.add(resourcePanelWillpower);

        statSize = rootView.findViewById(R.id.statSize);
        derivedDefense = rootView.findViewById(R.id.derivedDefense);
        derivedInitiative = rootView.findViewById(R.id.derivedInitiative);
        derivedSpeed = rootView.findViewById(R.id.derivedSpeed);

        lblAttributes = rootView.findViewById(R.id.lblAttributes);
        lblSkills = rootView.findViewById(R.id.lblSkills);
        lblDerived = rootView.findViewById(R.id.lblDerived);

//        panelTrialRun = rootView.findViewById(R.id.panelTrialRun);
    }

    private void spawnNoDiceAlert() {
        Toast.makeText(getContext(), getString(R.string.alert_no_dice), Toast.LENGTH_SHORT).show();
    }

    public void observeBoxes() {
        statIntelligence.setViewWatcher(this);
        statWits.setViewWatcher(this);
        statResolve.setViewWatcher(this);
        statStrength.setViewWatcher(this);
        statDexterity.setViewWatcher(this);
        statStamina.setViewWatcher(this);
        statPresence.setViewWatcher(this);
        statManipulation.setViewWatcher(this);
        statComposure.setViewWatcher(this);

        skillAcademics.setViewWatcher(this);
        skillAnimalKen.setViewWatcher(this);
        skillAthletics.setViewWatcher(this);
        skillBrawl.setViewWatcher(this);
        skillComputer.setViewWatcher(this);
        skillCrafts.setViewWatcher(this);
        skillDrive.setViewWatcher(this);
        skillEmpathy.setViewWatcher(this);
        skillExpression.setViewWatcher(this);
        skillFirearms.setViewWatcher(this);
        skillIntimidation.setViewWatcher(this);
        skillInvestigation.setViewWatcher(this);
        skillLarceny.setViewWatcher(this);
        skillMedicine.setViewWatcher(this);
        skillOccult.setViewWatcher(this);
        skillPersuasion.setViewWatcher(this);
        skillPolitics.setViewWatcher(this);
        skillScience.setViewWatcher(this);
        skillSocialize.setViewWatcher(this);
        skillStealth.setViewWatcher(this);
        skillStreetwise.setViewWatcher(this);
        skillSubterfuge.setViewWatcher(this);
        skillSurvival.setViewWatcher(this);
        skillWeaponry.setViewWatcher(this);

        statSize.setViewWatcher(this);
        derivedDefense.setViewWatcher(this);
        derivedInitiative.setViewWatcher(this);
        derivedSpeed.setViewWatcher(this);

        resourcePanelHealth.setViewWatcher(this);
        resourcePanelWillpower.setViewWatcher(this);

//        derivedWillpower.setViewWatcher(this);
//        derivedHealth.setViewWatcher(this);
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

        btnRoll.setOnClickListener(view1 ->
        {
            int threshold = Integer.parseInt(inputRerollThreshold.getText().toString());

            if (!chkExtendedRoll.isChecked()) {
                presenter.rollDice(threshold);
            } else {
                presenter.rollExtended(threshold);
            }

            dialog.cancel();
        });

    }

    @Subscribe public void createStatEditionDialog(StatEditionRequestedEvent event)
    {
        spawnStatEditionDialog(event.getId(), event.getName());
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

        dialogBuilder.setPositiveButton(getString(R.string.label_btn_ok), (dialog, which) ->
        {
            int newValue = Integer.parseInt(input.getText().toString());

            StatContainer container = rootView.findViewById(id);

            container.performValueChange(newValue);

            presenter.persistChanges();
        });

        dialogBuilder.show();
    }

    @Subscribe public void changeDicePool(DicePoolChangedEvent event)
    {
        presenter.addOrRemoveStat(new Pair<>(event.getStatName(), event.getStatValue()));
        Stat stat = presenter.getStatDetails(event.getStatName());

        addSelectedStatToPanel(stat);
        setStatPanelColor(stat);
    }

//    @Override
//    public void changeDicePool(String statName, int value, int colorSelected) {
//        presenter.addOrRemoveStat(new Pair<>(statName, value));
//        Stat stat = presenter.getStatDetails(statName);
//
//        addSelectedStatToPanel(stat);
//        setStatPanelColor(stat);
//    }

    @Override
    public void setStatOnView(Object tag)
    {
        Stat stat = presenter.getStatByTag(tag);

        StatContainer statContainer = rootView.findViewWithTag(tag);

        statContainer.setStat(stat);
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

//        if (panelTrialRun.shouldContain(stat))
//        {
//            panelTrialRun.addOrRemovePickedStat(stat);
//        }
    }

    @Override
    public void setStatPanelColor(Stat stat)
    {
        try
        {
            String type = stat.getType();
            String category = stat.getCategory();

//            StatLayout target = null;

            if (category.equals(getString(R.string.stat_category_attr)))
            {
                switch (type)
                {
                    case "Mental":
                    {
//                        target = containerAttrsMental;
                        Objects.requireNonNull(containerAttrsMental).setPanelColor();
//                        Objects.requireNonNull(panelTrialRun).setPanelColor();

                        break;
                    }
                    case "Physical":
                    {
                        Objects.requireNonNull(containerAttrsPhysical).setPanelColor();
//                        target = containerAttrsPhysical;

                        break;
                    }
                    case "Social":
                    {
                        Objects.requireNonNull(containerAttrsSocial).setPanelColor();
//                        target = containerAttrsSocial;

                        break;
                    }
                }
//                Objects.requireNonNull(target).setPanelColor();
            }
        }
        catch (NullPointerException e)
        {
            Log.e(this.getClass().getName(), "Stat object has null fields");
        }
    }

    @Subscribe
    public void togglePanels(PanelTappedEvent event)
    {
        for (UnfoldingLayout layout : statContainers)
        {
            if (layout.getId() != event.getViewId())
            {
                layout.toggleStatPanel(View.GONE, false);
            }
        }
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }
}
