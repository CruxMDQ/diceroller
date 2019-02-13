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
import com.callisto.diceroller.interfaces.RefreshingView;
import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.presenters.CharacterSheetPresenter;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;
import com.callisto.diceroller.viewmanagers.CharacterSheet;
import com.callisto.diceroller.views.DynamicStatLayout;
import com.callisto.diceroller.views.ResourceLayout;
import com.callisto.diceroller.views.UnfoldingLayout;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;

public class DynamicCharacterSheetFragment
    extends BaseFragment
    implements CharacterSheet.View
{
    CharacterSheetPresenter presenter;

    private DynamicStatLayout
        panelAttributesMental,
        panelAttributesPhysical,
        panelAttributesSocial,
        panelSkillsMental,
        panelSkillsPhysical,
        panelSkillsSocial,
        panelStatsDerived;

    private ResourceLayout
        resourcePanelHealth,
        resourcePanelWillpower;

    private TextView
        labelCharacterBio,
        labelAttributes,
        labelSkills,
        labelDerived,
        txtCharacterName;

    private String font;
    private String characterName;

    private ArrayList<DynamicStatLayout> statContainers;

    private ArrayList<RefreshingView> refreshingViews;

    public static DynamicCharacterSheetFragment newInstance(String font)
    {
        DynamicCharacterSheetFragment myFragment = new DynamicCharacterSheetFragment();

        Bundle args = new Bundle();

        args.putString(Constants.Parameters.FONT.getText(), font);

        myFragment.setArguments(args);

        return myFragment;
    }

    public static DynamicCharacterSheetFragment newInstance(String font, String characterName)
    {
        DynamicCharacterSheetFragment myFragment = new DynamicCharacterSheetFragment();

        Bundle args = new Bundle();

        args.putString(Constants.Parameters.FONT.getText(), font);
        args.putString(Constants.Parameters.CHARACTER_NAME.getText(), characterName);

        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        font = getArguments().getString(Constants.Parameters.FONT.getText());

        characterName = getArguments().getString(Constants.Parameters.CHARACTER_NAME.getText());
    }

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (characterName == null)
        {
            characterName = "Test character";
        }

        presenter = new CharacterSheetPresenter(this, getContext(), characterName);

        statContainers = new ArrayList<>();
        refreshingViews = new ArrayList<>();

        setUpContainers();

        subscribeToEvents();

        setTypefacesOnTitles();

        txtCharacterName.setText(characterName);
    }

    private void setUpContainers()
    {
        statContainers.add(panelAttributesMental);
        statContainers.add(panelAttributesPhysical);
        statContainers.add(panelAttributesSocial);
        statContainers.add(panelSkillsMental);
        statContainers.add(panelSkillsPhysical);
        statContainers.add(panelSkillsSocial);

        refreshingViews.addAll(statContainers);
        refreshingViews.add(resourcePanelHealth);
        refreshingViews.add(resourcePanelWillpower);
        refreshingViews.add(panelStatsDerived);

        panelAttributesMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_int)));
        panelAttributesMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_wits)));
        panelAttributesMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_res)));

        panelAttributesPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_str)));
        panelAttributesPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_dex)));
        panelAttributesPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_sta)));

        panelAttributesSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_pre)));
        panelAttributesSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_man)));
        panelAttributesSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_attr_com)));

        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_academics)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_computer)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_crafts)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_investigation)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_medicine)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_occult)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_politics)));
        panelSkillsMental.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_science)));

        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_athletics)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_brawl)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_drive)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_guns)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_larceny)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_stealth)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_survival)));
        panelSkillsPhysical.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_weaponry)));

        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_animal_ken)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_empathy)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_expression)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_intimidation)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_persuasion)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_socialize)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_streetwise)));
        panelSkillsSocial.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.skill_subterfuge)));

        panelStatsDerived.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_size)));
        panelStatsDerived.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_defense)));
        panelStatsDerived.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_initiative)));
        panelStatsDerived.addSelectableStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_speed)));

        resourcePanelHealth.setStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_health)));
        resourcePanelWillpower.setStat(presenter.getStatByName(App.getRes().getString(R.string.label_derived_willpower)));
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_char_sheet_dynamic;
    }

    @Override
    protected void findViews()
    {
        panelAttributesMental = rootView.findViewById(R.id.panelAttributesMental);
        panelAttributesPhysical = rootView.findViewById(R.id.panelAttributesPhysical);
        panelAttributesSocial = rootView.findViewById(R.id.panelAttributesSocial);

        panelSkillsMental = rootView.findViewById(R.id.panelSkillsMental);
        panelSkillsPhysical = rootView.findViewById(R.id.panelSkillsPhysical);
        panelSkillsSocial = rootView.findViewById(R.id.panelSkillsSocial);

        panelStatsDerived = rootView.findViewById(R.id.panelStatsDerived);

        labelCharacterBio = rootView.findViewById(R.id.labelCharacterBio);
        labelAttributes = rootView.findViewById(R.id.labelAttributes);
        labelSkills = rootView.findViewById(R.id.labelSkills);
        labelDerived = rootView.findViewById(R.id.labelDerived);

        txtCharacterName = rootView.findViewById(R.id.txtCharacterName);

        resourcePanelHealth = rootView.findViewById(R.id.panelHealth);
        resourcePanelWillpower = rootView.findViewById(R.id.panelWillpower);

        setUpFAB();
    }

    private void setUpFAB()
    {
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
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    private void setTypefacesOnTitles()
    {
        TypefaceSpanBuilder.setTypefacedTitle(
            labelCharacterBio,
            App.getRes().getString(R.string.label_character_bio),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        TypefaceSpanBuilder.setTypefacedTitle(
            labelAttributes,
            App.getRes().getString(R.string.label_attributes),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        TypefaceSpanBuilder.setTypefacedTitle(
            labelSkills,
            App.getRes().getString(R.string.label_skills),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        TypefaceSpanBuilder.setTypefacedTitle(
            labelDerived,
            App.getRes().getString(R.string.label_derived_stats),
            font,
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );
    }

    @Override
    public void showResults(ArrayList<Integer> rolls, int successes, boolean isExtended)
    {
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

    public void spawnStatEditionDialog(final int id, String statName)
    {
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

            refreshViews();

            presenter.persistChanges();
        });

        dialogBuilder.show();
    }

    public void spawnCustomDiceRollDialog(ArrayList<Pair<String, Integer>> stats)
    {

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

    private void spawnNoDiceAlert() {
        Toast.makeText(getContext(), getString(R.string.alert_no_dice), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStatPanelColor(Stat stat) 
    { 
        try
        {
            for (DynamicStatLayout statContainer : statContainers)
            {
                boolean shouldContain = statContainer.shouldContain(stat);

                if(shouldContain)
                {
                    statContainer.setPanelColor();
                    break;
                }
            }
        }
        catch (NullPointerException e)
        {
            Log.e(this.getClass().getName(), "Stat object has null fields");
        }
    }    

    @Override
    public void addSelectedStatToPanel(Stat stat)
    {
        for (DynamicStatLayout statContainer : statContainers)
        {
            if (statContainer.shouldContain(stat))
            {
                statContainer.addOrRemovePickedStat(stat);
            }
        }
    }

    @Subscribe
    public void changeDicePool(DicePoolChangedEvent event)
    {
        presenter.addOrRemoveStat(new Pair<>(event.getStatName(), event.getStatValue()));
        Stat stat = presenter.getStatDetails(event.getStatName());

        addSelectedStatToPanel(stat);
        setStatPanelColor(stat);
    }

    @Subscribe public void createStatEditionDialog(StatEditionRequestedEvent event)
    {
        spawnStatEditionDialog(event.getId(), event.getName());
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

    private void refreshViews()
    {
        for (RefreshingView refreshingView : refreshingViews)
        {
            refreshingView.performViewRefresh();
        }
    }
}
