/* Copyright 2013 Benjamin Linskey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.benlinskey.greekreference.lexicon;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.benlinskey.greekreference.MainActivity;
import com.benlinskey.greekreference.R;
import com.benlinskey.greekreference.data.appdata.AppDataContract;
import com.benlinskey.greekreference.data.lexicon.LexiconContract;
import com.benlinskey.greekreference.navigationdrawer.NavigationDrawerFragment;

/**
 * An activity representing a single Word detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link LexiconDetailFragment}.
 */
public class LexiconDetailActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle; // Used to store the last screen title.

    public static final String ARG_LEXICON_ID = "lexicon_id";
    public static final String ARG_WORD = "word";
    private int mLexiconId;
    private String mWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        mLexiconId = intent.getIntExtra(ARG_LEXICON_ID, -1);
        mWord = intent.getStringExtra(ARG_WORD);

        // Show the Up button in the action bar.
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.userLearnedDrawer();

        mTitle = getTitle(); // TODO: Display mode title on application launch.

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(LexiconDetailFragment.ARG_ENTRY,
                    getIntent().getStringExtra(LexiconDetailFragment.ARG_ENTRY));
            LexiconDetailFragment fragment = new LexiconDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen if the drawer is not
        // showing. Otherwise, let the drawer decide what to show in the action bar.
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            return super.onCreateOptionsMenu(menu);
        }

        getMenuInflater().inflate(R.menu.lexicon_detail_menu, menu);
        setLexiconFavoriteIcon(menu);
        restoreActionBar();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen if the drawer is not
        // showing. Otherwise, let the drawer decide what to show in the action bar.
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            return super.onCreateOptionsMenu(menu);
        }

        setLexiconFavoriteIcon(menu);
        restoreActionBar();
        return super.onPrepareOptionsMenu(menu);
    }

    private void setLexiconFavoriteIcon(Menu menu) {
        LexiconListFragment fragment = (LexiconListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.item_list_container);

        MenuItem addFavorite = menu.findItem(R.id.action_add_favorite);
        MenuItem removeFavorite = menu.findItem(R.id.action_remove_favorite);

        // Hide both icons when no word is selected or the app is in one-pane mode.
        if (isFavorite(mLexiconId)) {
            addFavorite.setVisible(false);
            removeFavorite.setVisible(true);
        } else {
            addFavorite.setVisible(true);
            removeFavorite.setVisible(false);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
            case R.id.action_add_favorite:
                addLexiconFavorite();
                return true;
            case R.id.action_remove_favorite:
                removeLexiconFavorite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // TODO: Send an intent to start MainActivity in the selected mode.
        // Switch to the selected mode.
        switch (MainActivity.Mode.getModeFromPosition(position)) {
            case LEXICON_BROWSE:
                // TODO
                break;
            case LEXICON_FAVORITES:
                // TODO
                break;
            case LEXICON_HISTORY:
                // TODO
                break;
            case SYNTAX_BROWSE:
                // TODO
                break;
            case SYNTAX_BOOKMARKS:
                // TODO
                break;
            default:
                throw new IllegalArgumentException("Invalid mode");
        }
    }

    private void addLexiconFavorite() {
        ContentValues values = new ContentValues();
        values.put(AppDataContract.LexiconFavorites.COLUMN_NAME_LEXICON_ID, mLexiconId);
        values.put(AppDataContract.LexiconFavorites.COLUMN_NAME_WORD, mWord);
        getContentResolver().insert(AppDataContract.LexiconFavorites.CONTENT_URI, values);
        invalidateOptionsMenu();
    }

    private void removeLexiconFavorite() {
        String selection = AppDataContract.LexiconFavorites.COLUMN_NAME_LEXICON_ID + " = ?";
        String[] selectionArgs = {Integer.toString(mLexiconId)};
        getContentResolver()
                .delete(AppDataContract.LexiconFavorites.CONTENT_URI, selection, selectionArgs);
        invalidateOptionsMenu();
    }

    private boolean isFavorite(int lexiconId) {
        String[] columns = new String[] {AppDataContract.LexiconFavorites._ID};
        String selection = AppDataContract.LexiconFavorites.COLUMN_NAME_LEXICON_ID + " = ?";
        String[] selectionArgs = new String[] {Integer.toString(lexiconId)};
        Cursor cursor = getContentResolver().query(AppDataContract.LexiconFavorites.CONTENT_URI,
                columns, selection, selectionArgs, null);
        boolean result = false;
        if (cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        return result;
    }
}

