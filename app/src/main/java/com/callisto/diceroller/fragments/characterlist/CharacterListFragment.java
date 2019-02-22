package com.callisto.diceroller.fragments.characterlist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public class CharacterListFragment
    extends BaseFragment
    implements CharacterListView
{
    CharacterListPresenter presenter;

    GridView gv;

    FloatingActionButton fabNewCharacter;
    FloatingActionButton fab1;              // Reserved for future use
    FloatingActionButton fab2;              // Reserved for future use

    private List<Character> characters;

    private boolean isFABOpen;

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterListPresenter(this);

        bindGridView();

        setClickListenerOnGridView();
    }

    private void bindGridView()
    {
        characters = presenter.getCharacters();

        gv.setAdapter(new CharacterListAdapter(characters));
    }

    private void setClickListenerOnGridView()
    {
        gv.setOnItemClickListener((parent, view, position, id) ->
        {
            Character character = characters.get(position);

            presenter.requestCharacterEditor(character.getName());

//            Toast.makeText(
//                this.getContext(),
//                character.getName(),
//                Toast.LENGTH_SHORT
//            ).show();
        });
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_character_list;
    }

    @Override
    protected void findViews()
    {
        gv = rootView.findViewById(R.id.gv);

        setUpFAB();
    }

    private void setUpFAB()
    {
        FloatingActionButton fabUnfold = rootView.findViewById(R.id.fabUnfold);

        fabNewCharacter = rootView.findViewById(R.id.fabNewCharacter);
        fab1 = rootView.findViewById(R.id.fab1);
        fab2 = rootView.findViewById(R.id.fab2);

        fabUnfold.setOnClickListener(v -> {
            if (!isFABOpen)
            {
                showFABMenu();
            }
            else
            {
                closeFABMenu();
            }
        });

        fabNewCharacter.setOnClickListener(v -> spawnCharacterCreationDialog());
    }

    private void spawnCharacterCreationDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        // Set dialog title
        dialogBuilder.setTitle("Character creation");

        // Set dialog view
        final View view = getLayoutInflater().inflate(R.layout.dialog_character_create, null);
        dialogBuilder.setView(view);

        // Find dialog components
        final EditText inputName = view.findViewById(R.id.inputName);
        Button btnCreate = view.findViewById(R.id.btnCreate);

        // Store dialog reference to later be able to dismiss it
        final AlertDialog dialog = dialogBuilder.show();

        btnCreate.setOnClickListener(view1 ->
        {
            String characterName = inputName.getText().toString();

            presenter.createNewCharacter(
                characterName,
                // TODO Create support for setting player
                "Kindred"                 // TODO Create support for setting template
            );

            presenter.requestCharacterEditor(characterName);

            dialog.cancel();
        });

    }

    private void showFABMenu()
    {
        isFABOpen = true;

        fabNewCharacter.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_1));
        fab1.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_2));
        fab2.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_3));
    }

    private void closeFABMenu()
    {
        isFABOpen = false;

        fabNewCharacter.animate().translationY(0);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
    }
}
