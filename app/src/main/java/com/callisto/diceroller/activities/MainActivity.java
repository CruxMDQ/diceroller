package com.callisto.diceroller.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.callisto.diceroller.R;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.fragments.characterlist.CharacterListFragment;
import com.callisto.diceroller.fragments.charactersheet.CharacterSheetFragment;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.viewmanagers.MainActivityNavigation;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.callisto.diceroller.tools.Constants.FragmentTags.TAG_FRAGMENT_CHAR_LIST;
import static com.callisto.diceroller.tools.Constants.FragmentTags.TAG_FRAGMENT_CHAR_SHEET;

public class MainActivity
    extends AppCompatActivity
    implements MainActivityNavigation.View
{
    CharacterListFragment characterList;

    CharacterSheetFragment characterSheet;

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

        loadCharacterList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragment(BaseFragment fragment, String tag)
    {
        fragment.attachPresenter(presenter);

        removeFragment(tag);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        final FragmentTransaction ft = supportFragmentManager
            .beginTransaction();

        ft.detach(fragment);
        ft.replace(R.id.flContent, fragment);
        ft.attach(fragment);

        ft.addToBackStack(tag);
        ft.commit();
    }

    public void removeFragment(String tag)
    {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        final FragmentTransaction ft = supportFragmentManager
            .beginTransaction();

        Fragment t = supportFragmentManager.findFragmentByTag(tag);

        if (t != null)
        {
            ft.remove(t);
            ft.commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void loadCharacterList()
    {
        if (characterList == null)
        {
            characterList = new CharacterListFragment();
        }

        setFragment(characterList, TAG_FRAGMENT_CHAR_LIST.getText());
    }

    public void loadCharacterSheet(String fontName, String characterName)
    {
        String tag = TAG_FRAGMENT_CHAR_SHEET.getText();

        if (characterSheet == null)
        {
            characterSheet = new CharacterSheetFragment();
        }

        Bundle args = new Bundle();

        args.putString(Constants.Parameters.FONT.getText(), fontName);
        args.putString(Constants.Parameters.CHARACTER_NAME.getText(), characterName);

        Objects.requireNonNull(characterSheet).setArguments(args);

        setFragment(characterSheet, tag);
    }
}
