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
import android.os.Bundle;
import android.view.*;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.dialogs.DialogFactory;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.logger.Logger;

public class ActivityExpenseDetails extends Activity
{

    public void setExpenseArticlesLoaded()
    {
        FragmentExpenseDetail ed = (FragmentExpenseDetail) this.getFragmentManager().findFragmentById(R.id.frg_expense_detail);
        ed.setExpenseArticlesLoaded();
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
        return super.onCreateOptionsMenu(menu);
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

        setContentView(R.layout.activity_expense_detail);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
    	Logger.lfc_log(this.getClass().getName() +  "SuperMarketExpenseDetailActivity: onOptionsItemSelected called.");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch ( id ) 
        {
        	/*case R.id.menu_add_article :
        	{
        		Log.i(TAG, "Richiesto inserimento articolo...");
        		return ((FragmentExpenseDetail) this.getFragmentManager().findFragmentById(R.id.expenseDetail_fragment)).addOrUpdateArticle(null, -1); //showArticle(null, -1);
        		//return ActionManager.getInstance().exec(new ActionAddArticle(this));
        	}
        	case R.id.menu_validate_expense:
        	{
        		Log.i(TAG, "Richiesta validazione spesa...");
        		return ((FragmentExpenseDetail) this.getFragmentManager().findFragmentById(R.id.expenseDetail_fragment)).checkExpense();
        		//return ActionManager.getInstance().exec(new ActionUpdateExpense(this));
        	}
        	case R.id.menu_undo_action:
        	{
        		Log.i(TAG, "Undo dell'ultima azione eseguita!");
        		int res = ActionManager.getInstance().undo();
        		String message = "";
        		if ( res == -1 )     message = "Nessuna azione da annullare!";
        		else if ( res == 0 ) message = "Azione annullata con successo!";
        		else if ( res == 1 ) message = "Problemi nell'undo dell'azione!";
        		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        		return true;
        	}
        	case R.id.menu_redo_action:
        	{
        		Log.i(TAG, "Redo dell'ultima azione eseguita!");
        		int res = ActionManager.getInstance().redo(); 
        		String message = "";
        		if ( res == -1 )     message = "Nessuna azione da rieseguire!";
        		else if ( res == 0 ) message = "Azione rieseguita con successo!";
        		else if ( res == 1 ) message = "Problemi nella redo dell'azione!";
        		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        		return true;
        	}*/
        	default: return super.onOptionsItemSelected(item);
        }
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            ( ( FragmentExpenseDetail) this.getFragmentManager().findFragmentById(R.id.frg_expense_detail) ).saveExpense();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
