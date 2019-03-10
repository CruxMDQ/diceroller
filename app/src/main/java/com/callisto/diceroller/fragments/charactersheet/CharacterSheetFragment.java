package com.callisto.diceroller.fragments.charactersheet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.fragments.StatEditionDialogFragment;
import com.callisto.diceroller.interfaces.RefreshingView;
import com.callisto.diceroller.interfaces.StatEditionCallbackHandler;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;
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
    implements CharacterSheet.View,
        RefreshingView,
        StatEditionCallbackHandler
{
    CharacterSheetPresenter presenter;

    private StatLayout
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
//        resourcePanelIntegrity;

    private TextView
        labelCharacterBio,
        labelAttributes,
        labelSkills,
        labelDerived,
        txtCharacterName;

    private String font;
    private String characterName;

    private ArrayList<StatLayout> statContainers;

    private ArrayList<RefreshingView> refreshingViews;

    private LinearLayout panelTemplateTraits;

    @Override
    public void onResume()
    {
        for (RefreshingView refreshingView : refreshingViews)
        {
            Log.d("Dynamic charsheet", "Refreshing view subscribed");
            refreshingView.subscribeToEvents();
        }

        super.onResume();
    }

    @Override
    public void onDetach()
    {
        for (RefreshingView refreshingView : refreshingViews)
        {
            Log.d("Dynamic charsheet", "Refreshing view unsubscribed");
            refreshingView.unsubscribeFromEvents();
        }

        for (StatLayout statLayout : statContainers)
        {
            statLayout.flush();
        }

        super.onDetach();
    }

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        font = getArguments().getString(Constants.Parameters.FONT.getText());

        characterName = getArguments().getString(Constants.Parameters.CHARACTER_NAME.getText());

        if (characterName == null)
        {
            characterName = "Test character";
        }

        presenter = new CharacterSheetPresenter(this, characterName);

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

        panelAttributesMental.addSelectableStats(
            presenter.getMentalAttributes()
        );
        panelAttributesPhysical.addSelectableStats(
            presenter.getPhysicalAttributes()
        );
        panelAttributesSocial.addSelectableStats(
            presenter.getSocialAttributes()
        );

        panelSkillsMental.addSelectableStats(
            presenter.getMentalSkills()
        );
        panelSkillsPhysical.addSelectableStats(
            presenter.getPhysicalSkills()
        );
        panelSkillsSocial.addSelectableStats(
            presenter.getSocialSkills()
        );

        panelStatsDerived.addSelectableStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_core_size)));
        panelStatsDerived.addSelectableStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_derived_defense)));
        panelStatsDerived.addSelectableStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_derived_initiative)));
        panelStatsDerived.addSelectableStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_derived_speed)));

        resourcePanelHealth.setFont(font);
        resourcePanelWillpower.setFont(font);
//        resourcePanelIntegrity.setFont(font);

        resourcePanelHealth.setStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_derived_health)));
        resourcePanelWillpower.setStat(
            presenter.getStatByName(App.getRes().getString(R.string.label_derived_willpower)));
//        resourcePanelIntegrity.setStat(
//            presenter.getStatByName(App.getRes().getString(R.string.label_core_integrity)));

        for (Stat advantage : presenter.getAdvantages())
        {
            ResourceLayout resourceLayout = new ResourceLayout(getContext(), font, false);

            panelTemplateTraits.addView(resourceLayout);

            resourceLayout.setStat(advantage);
        }

        for (Stat resource : presenter.getResources())
        {
            ResourceLayout resourceLayout = new ResourceLayout(getContext(), font, false);

            panelTemplateTraits.addView(resourceLayout);

            resourceLayout.setStat(resource);
        }

        Stat integrity = presenter.getMorality();
        if (integrity != null)
        {
            ResourceLayout resourceLayout = new ResourceLayout(getContext(), font, false);

            panelTemplateTraits.addView(resourceLayout);

            resourceLayout.setStat(integrity);
        }
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
//        resourcePanelIntegrity = rootView.findViewById(R.id.panelIntegrity);

        panelTemplateTraits = rootView.findViewById(R.id.panelTemplateTraits);

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

    public void subscribeToEvents()
    {
        try
        {
            BusProvider.getInstance().register(this);
        }
        catch (IllegalArgumentException ignored) {}
    }

    @Override
    public void unsubscribeFromEvents()
    {
        try
        {
            BusProvider.getInstance().unregister(this);
        }
        catch (IllegalArgumentException ignored) {}
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

        if (successes == 0 && isExtended)
        {
            ad.setMessage(getString(R.string.alert_roll_failed));
        }
        else
        {
            Iterator iterator = rolls.iterator();

            String rollString = "";

            while (iterator.hasNext())
            {
                int roll = Integer.parseInt(iterator.next().toString());

                rollString = rollString.concat(String.valueOf(roll));

                if (iterator.hasNext())
                {
                    rollString = rollString.concat(", ");
                }
            }

            ad.setTitle(String.valueOf(getString(R.string.label_successes_report, successes)));
            ad.setMessage(getString(R.string.label_roll_report, rollString));
        }
        ad.show();
    }

    public void spawnCustomDiceRollDialog(ArrayList<Pair<String, Integer>> stats)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

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

        while (iterator.hasNext())
        {
            Pair<String, Integer> stat = (Pair<String, Integer>) iterator.next();

            statList = statList.concat(stat.first);

            if (iterator.hasNext())
            {
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

            if (!chkExtendedRoll.isChecked())
            {
                presenter.rollDice(threshold);
            }
            else
            {
                presenter.rollExtended(threshold);
            }

            dialog.cancel();
        });
    }

    private void spawnNoDiceAlert()
    {
        Toast.makeText(getContext(), getString(R.string.alert_no_dice), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStatPanelColor(Stat stat)
    {
        try
        {
            for (StatLayout statContainer : statContainers)
            {
                boolean shouldContain = statContainer.shouldContain(stat);

                if (shouldContain)
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
    public void addOrRemoveStatFromPanel(Stat stat)
    {
        for (StatLayout statContainer : statContainers)
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

        addOrRemoveStatFromPanel(stat);
        setStatPanelColor(stat);
    }

    @Subscribe
    public void createStatEditionDialog(StatEditionRequestedEvent event)
    {
        Log.d("Stat tracking", "View: " + event.getViewId() + ", statId: " + event.getStatId()+ ", stat: " + event.getName());

        StatBox target = rootView.findViewById(event.getViewId());

        if (target != null)
        {
            StatEditionDialogFragment statEditionDialogFragment =
                StatEditionDialogFragment.newInstance(
                    event.getViewId(),
                    event.getStatId(),
                    event.getName());

            statEditionDialogFragment.setCallbackHandler(this);

            statEditionDialogFragment.show(
                Objects.requireNonNull(getFragmentManager()),
                Constants.FragmentTags.TAG_FRAGMENT_DIALOG_STAT_EDIT.getText()
            );
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

    public void performViewRefresh()
    {
        for (RefreshingView refreshingView : refreshingViews)
        {
            refreshingView.performViewRefresh();
        }
    }

    @Override
    public void updateContainer(int viewId, long statId, int newValue)
    {
        StatBox target = rootView.findViewById(viewId);

        Log.d("Stat tracking", "View: " + viewId + ", statId: " + statId + ", stat: " + target.getStatBoxName() + ", value: " + newValue);

        target.setValue(newValue);

        target.postStatChange();

        target.performViewRefresh();

        for (RefreshingView refreshingView : refreshingViews)
        {
            refreshingView.performViewRefresh();
        }

        presenter.persistChanges();
    }
}
