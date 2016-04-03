/*
 *  The MIT License (MIT)
 *
 * Copyright (c) $date.year $user.name
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package org.bob.android.supermarket.gui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseList;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.Constants;

/**
 * Main Activity dell'applicazione.
 * @author roberto.gatti
 *
 */
public class ActivityExpenseList extends Activity implements FragmentExpenseList.OnExpenseSelectedListener
{

    /* ********************************************************************* */
    /*                                CLASS METHODS                          */
    /* ********************************************************************* */

    public void setExpensesLoaded()
    {
        FragmentExpenseList el = (FragmentExpenseList) this.getFragmentManager().findFragmentById(R.id.frg_expense_list);
        el.setExpensesLoaded();
    }

    /* ********************************************************************* */
    /*                     ACTIVITY LIFECYCLE OVERRIDE                       */
    /* ********************************************************************* */

    /**
     * TODO: gestione event handler menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_expense_list_menu, menu);
        return true;
    }

    /**
     * TODO: gestione recupero bundle di salvataggio per recupero dati
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        Logger.lfc_log(this.getClass().getSimpleName() + " -- onStart");
        super.onStart();
    }

    @Override
    protected void onRestart()
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onResume");
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause()
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onPause");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onMenuItemSelected");
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onCreateContextMenu");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onContextItemSelected");
        return super.onContextItemSelected(item);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Logger.lfc_log( this.getClass().getSimpleName() + " -- onCreate");
        super.onCreate(savedInstanceState);
        // ActionBar
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        setContentView(R.layout.activity_expense_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try {
            if (requestCode == 1) {

                if (resultCode == RESULT_OK) {
                    FragmentExpenseList frg = (FragmentExpenseList) this.getFragmentManager().findFragmentById(R.id.frg_expense_list);
                    frg.updateExpense((ExpenseBean) data.getExtras().get(Constants.KEY_SELECTED_EXPENSE_UPDATED));
                }
            /*if (resultCode == RESULT_CANCELED)
            {
                //Do nothing?
            }*/
            }
        }
        catch ( SuperMarketException ex )
        {
            Logger.app_log("ERROR: SuperMarketException caught!");
            Logger.app_log("     : " + ex.getMessage());
            Toast.makeText(this, "ERRORE: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /* ********************************************************************* */
    /*                           IMPLEMENT METHODS                           */
    /* ********************************************************************* */

    @Override
    public void onExpenseSelected(ExpenseBean eb) {
        Logger.lfc_log("Opening expense '" + eb.toString() + ". . .");

        // Verifico la presenza del fragment di dettaglio
        FragmentExpenseDetail fed = (FragmentExpenseDetail) this.getFragmentManager().findFragmentById(R.id.frg_expense_detail);
        if ( fed == null || ! fed.isVisible() )
        {
            // Portrait
            Intent intent = new Intent(this.getApplicationContext(), ActivityExpenseDetails.class);
            intent.putExtra(Constants.KEY_SELECTED_EXPENSE, eb);
            this.startActivityForResult(intent, Constants.KEY_CHANGED_EXPENSE_REQUEST_CODE);
        }
        else
        {
            fed.setSelectedExpense(eb);
        }
    }

}
