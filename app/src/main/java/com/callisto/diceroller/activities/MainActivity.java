package com.callisto.diceroller.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.callisto.diceroller.R;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.fragments.DiceRollerFragment_;
import com.callisto.diceroller.presenters.MainActivityPresenter;
import com.callisto.diceroller.viewmanagers.MainActivityNavigation;

public class MainActivity extends AppCompatActivity
    implements MainActivityNavigation.View {

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

        setFragment(new DiceRollerFragment_());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragment(BaseFragment fragment) {
        fragment.attachPresenter(presenter);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.flContent, fragment)
            .commit();
    }
}
