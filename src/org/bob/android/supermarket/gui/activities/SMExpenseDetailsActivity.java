package org.bob.android.supermarket.gui.activities;



import org.bob.android.supermarket.R;
import org.bob.android.supermarket.command.ActionManager;
import org.bob.android.supermarket.gui.fragments.ExpenseDetailFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import org.bob.android.supermarket.gui.fragments.ExpenseDetailFragment.OnArticleSelectedListener;
import android.widget.Toast;

public class SMExpenseDetailsActivity extends Activity
												//implements OnArticleSelectedListener
{

	/**
	 * Stringa di utilita' per il logging dell'applicazione.
	 */
	private static final String TAG = SSMExpenseDetailsActivityclass.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "|--> Setto il portrait layout per la view...");
		Log.d(TAG, "|--> Id del portrait_main_activity: " + R.layout.portrait_main_activity);
		setContentView(R.layout.portrait_expense_detail_activity);

		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d(TAG, "|--> Setto il menu per portrait layout della view...");
		getMenuInflater().inflate(R.menu.super_market_expense_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	Log.d(TAG, "SuperMarketExpenseDetailActivity: onOptionsItemSelected called.");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch ( id ) 
        {
        	case R.id.menu_add_article : 
        	{
        		Log.i(TAG, "Richiesto inserimento articolo...");
        		return ((ExpenseDetailFragment) this.getFragmentManager().findFragmentById(R.id.expenseDetail_fragment)).addOrUpdateArticle(null, -1); //showArticle(null, -1);
        		//return ActionManager.getInstance().exec(new ActionAddArticle(this));
        	}
        	case R.id.menu_validate_expense:
        	{
        		Log.i(TAG, "Richiesta validazione spesa...");
        		return ((ExpenseDetailFragment) this.getFragmentManager().findFragmentById(R.id.expenseDetail_fragment)).checkExpense();
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
        	}
        	default: return super.onOptionsItemSelected(item);
        }
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			Log.d(TAG, "|--> onCreateView of SuperMarketExpenseDetailActivity...");
			View rootView = inflater.inflate(R.layout.fragment_expense_detail, container, false);
			Log.d(TAG, "|--> Ok, returning from SuperMarketExpenseDetailActivity.PlaceHolderFragment.onCreateView ...");
			return rootView;
		}
	}

	/**
	 * Gestore evento di selezione su view del singolo articolo di spesa.
	 */
	/*@Override
	public void onArticleSelected(ExpenseArticleBean eab) 
	{
		Log.d(TAG, "|--> Selezionato articolo '" + eab.toString() + "'");
	}*/

	
}
