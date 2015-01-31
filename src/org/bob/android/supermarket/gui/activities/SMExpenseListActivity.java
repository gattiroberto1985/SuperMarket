package org.bob.android.supermarket.gui.activities;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bob.android.supermarket.R;
import org.bob.android.supermarket.persistence.oldbeans.ExpenseBean;
import org.bob.android.supermarket.persistence.oldbeans.ShopBean;
import org.bob.android.supermarket.gui.fragments.ExpenseDetailFragment;
import org.bob.android.supermarket.gui.fragments.ExpenseListFragment;
import org.bob.android.supermarket.utilities.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity dell'applicazione.
 * @author roberto.gatti
 *
 */
public class SMExpenseListActivity extends Activity
                                     implements ExpenseListFragment.OnExpenseSelectedListener
{
	/**
	 * Stringa di utilita' per il log.
	 */
	private static final String TAG = SSMExpenseListActivityclass.getName();

	private static final int DB_EXPORT_REQUEST_CODE = 1234;
	
	private static final int DB_IMPORT_REQUEST_CODE = 1235;
	/**
	 * View con il dialog di creazione spesa.
	 */
	private View dialogCreateExpenseView;
	
	/**
	 * Override del metodo onCreate.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	Log.d(TAG, "SuperMarketMainActivity: onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait_main_activity);
        /*if (savedInstanceState == null) 
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }
    
    /**
	 * Gestione evento di pausa dell'activity principale
	 */
	@Override
	public void onPause()
	{
		Log.d(TAG, "SuperMarketMainActivity: onPause called.");
		super.onPause();
	}
	
	/**
	 * Gestione evento di ripristino activity principale
	 */
	@Override
	public void onResume()
	{
		Log.d(TAG, "SuperMarketMainActivity: onResume called.");
		super.onResume();
	}	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	Log.d(TAG, "SuperMarketMainActivity: onCreateOptionsMenu called");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.super_market_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	Log.d(TAG, "SuperMarketMainActivity: onOptionsItemSelected called.");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch ( id ) 
        {
        	case R.id.action_settings : return true;
        	case R.id.menu_create_expense_draft:
        	{
        		this.createExpenseDraft();
        		return true;
        	}
        	case R.id.add_expense:
        	{
        		//Toast.makeText(SuperMarketMainActivity.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
        		this.addExpense();
        		return true;
        	}
        	case R.id.menu_export_database:
        	{
        		this.exportDatabase();
        		return true;
        	}
        	case R.id.menu_import_database:
        	{
        		this.importDatabase();
        		return true;
        	}
        	case R.id.menu_show_objects:
        	{
        		this.showObjects();
        		return true;
        	}
        	default: return super.onOptionsItemSelected(item);
        }
        
    }

    /**
     * Il metodo passa il controllo all'activity di gestione dati.
     */
    private void showObjects() 
    {
    	Log.i(TAG, "Passo all'activity di gestione dati");
		Intent intent = new Intent(this.getApplicationContext(), HandleObjectsActivity.class);
        startActivity(intent);
	}

    /**
     *  Passa il controllo all'activity di creazione bozza spesa
     */
    private void createExpenseDraft() 
    {
    	Log.i(TAG, "Passo all'activity di creazione bozza");
		Intent intent = new Intent(this.getApplicationContext(), SMDraftExpenseActivity.class);
        startActivity(intent);    	
    }
    
	private void importDatabase()
    {
    	final Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT); 
    	chooserIntent.setType("*/.db");
    	startActivityForResult(chooserIntent, SSMExpenseListActivityDB_IMPORT_REQUEST_CODE);
    }
    
    /**
     * Metodo di esportazione del database dell'applicazione su un file.
     */
    private void exportDatabase() 
    {
        /*Intent intent = new Intent(Intent.ACTION_PICK); 
        intent.setType("text/directory"); 
        intent.addCategory(Intent.cate);
        try 
        {
            startActivityForResult(Intent.createChooser(intent, "Selezione cartella"), SuperMarketMainActivity.DB_EXPORT_REQUEST_CODE);
        } 
        catch (android.content.ActivityNotFoundException ex) 
        {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }*/
    	final Intent chooserIntent = new Intent(this, DirectoryChooserActivity.class);

    	// Optional: Allow users to create a new directory with a fixed name.
    	chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_NEW_DIR_NAME, "DirChooserSample");

    	// REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
    	startActivityForResult(chooserIntent, SSMExpenseListActivityDB_EXPORT_REQUEST_CODE);
		
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ( requestCode == SSMExpenseListActivityDB_EXPORT_REQUEST_CODE )
		{
			if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) 
		    {
		       	String dir = data.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR);
				String source = this.getApplicationContext().getFilesDir() + "/../databases/supermarket_db";
				String target = dir + "/" + "supermarket." + Utilities.TIMESTAMP_FORMAT.format(new java.util.Date()) + ".db";
				Log.i(TAG, "Ok, directory selezionata: '" + dir + "'");
				Log.i(TAG, "Procedo con il salvataggio del file da '" + source + "' a '" + target + "'...");
				Utilities.copyFile(source, target);
				Toast.makeText(this, "File '" + target + "' creato, backup eseguito!", Toast.LENGTH_LONG).show();
				return;
		    }
		}
		
		if ( requestCode == SSMExpenseListActivityDB_IMPORT_REQUEST_CODE )
		{
			if ( resultCode == RESULT_OK )
			{
		       	String source = data.getData().toString().replace("content://com.estrongs.files", ""); // getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR);
				String target = this.getApplicationContext().getFilesDir() + "/../databases/supermarket_db";
				//String source = dir + "/" + "supermarket." + Utilities.TIMESTAMP_FORMAT.format(new java.util.Date()) + ".db";
				Log.i(TAG, "Ok, file sorgente selezionato, a tuo rischio e pericolo: '" + source + "'");
				Log.i(TAG, "Procedo con il salvataggio del file da '" + source + "' a '" + target + "'...");
				Utilities.copyFile(source, target);
				Toast.makeText(this, "File '" + target + "' sovrascritto!", Toast.LENGTH_LONG).show();
				return;				
			}
		}
	}
	/**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment 
    {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
            View rootView = inflater.inflate(R.layout.fragment_expense_list/*fragment_expense_detail*/, container, false);
            return rootView;
        }
    }

	/**
	 * Implementa il metodo dell'interfaccia OnExpenseSelectedListener.
	 * Il metodo si occupa di visualizzare il dettaglio della spesa selezionata
	 * dall'utente. Nel caso il fragment di dettaglio sia gia' caricato, viene
	 * impostato il relativo adapter della listview contenuta nel fragment.
	 * In caso contrario, passa il controllo all'activity di dettaglio,
	 * passando via Intent l'oggetto Expense selezionato dall'utente.
	 */    
	@Override
	public void OnExpenseSelected(ExpenseBean eb) 
	{
		Log.i(TAG, "Controllo se il fragment di dettaglio e' gia' caricato");
		ExpenseDetailFragment edf = (ExpenseDetailFragment) getFragmentManager().findFragmentById(R.id.expenseDetail_fragment);
	    if ( edf != null && edf.isInLayout() )
	    {
	    	Log.i(TAG, " |-- Fragment '" + edf.getTag() + "' gia' caricato e in layout, setto l'adapter");
	    	//edf.setExpenseArticleAdapter(uri);
	    }
	    else
	    {
	    	Log.i(TAG, " |-- Fragment di dettaglio spese non caricato! Passo la spesa selezionata all'activity nuova");
	    	Intent intent = new Intent(this.getApplicationContext(), SMExpenseDetailsActivity.class);
	    	intent.putExtra(Utilities.INTENT_SELECTED_EXPENSE_NAME, eb);
	    	Log.i(TAG, " |-- Intent creati e oggetto expense registrato. Eseguo lo start dell'activity di dettaglio...");
	        startActivity(intent);
	    }
	}
	
	private int addExpense()
	{
		int newExpenseId = -1;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Creazione spesa");
		Log.i(TAG, "|--> Richiesto censimento spesa!");
		Log.d(TAG, "|--> Preparo il layout da utilizzare...");
		LayoutInflater li = this.getLayoutInflater();
		
		dialogCreateExpenseView = li.inflate( R.layout.menu_add_expense, 
											  (ViewGroup) findViewById(R.id.layout_menu_add_expense));
		Log.d(TAG, "|--> Imposto la view...");
		builder.setView(dialogCreateExpenseView);
		Log.d(TAG, "|--> Aggancio l'event handler per il click su OK...");
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) 
	           {
	        	   try 
	        	   {
					createExpense();
	        	   }
	        	   catch (ParseException e) 
	        	   {
	        		   Log.e(TAG, "ERRORE: parsing errato della data: " + e.getMessage() );
	        		   Toast.makeText(SSMExpenseListActivitythis, "Impossibile eseguire il parsing della data!", Toast.LENGTH_SHORT).show();
	        	   }
	           }
		});
		Log.d(TAG, "|--> Aggancio l'event handler per il click su CANCEL...");
	    builder.setNegativeButton(R.string.STR_DIALOG_BACK, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) 
	           {
	               Log.i(TAG, "|--> Operazione di creazione spesa annullata!");
	               return;
	           }
	    });
	    Log.d(TAG, "|--> Creo l'alert dialog...");
	    AlertDialog dialog = builder.create();
	
		Log.d(TAG, "|--> Layout caricato, recupero le actv per settare i negozi e impostare la data di default...");
		AutoCompleteTextView shopACTV = (AutoCompleteTextView) dialogCreateExpenseView.findViewById(R.id.DIALOG_ADD_EXPENSE_SHOP_ACTV);
		TextView dateTV = ((TextView) dialogCreateExpenseView.findViewById(R.id.DIALOG_ADD_EXPENSE_DATE_TV));
	    // setting the default data
		dateTV.setText(DateFormat.getDateInstance().format(new Date()));
		Log.d(TAG, "|--> ACTV recuperate e date settate. Imposto l'adapter per l'actv relativa ai recupero i negozi...");
		Cursor shops = getContentResolver().query(
			    ShopPMD.CONTENT_URI,                                    // The content URI of the words table
			    new String[] { ShopPMD.ShopTableMetaData.DESCRIPTION }, // The columns to return for each row
			    null,                                                   // Selection criteria
			    null,                                                   // Selection criteria
			    ShopPMD.ShopTableMetaData.DESCRIPTION + " ASC ");       // The sort order for the returned rows
		Log.d(TAG, "|--> Dati recuperati!");
		List<String> shopsSTR = new ArrayList<String>();
		while ( shops.moveToNext() )
		{
			Log.d(TAG, "|----> Recuperato negozio: '" + shops.getString(0) );
			shopsSTR.add(shops.getString(0));
		}
		Log.d(TAG, "|--> Ok, lista creata! La setto come adapter per la ACTV...");
		shopACTV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, shopsSTR));
		Log.d(TAG, "|--> Ok, set eseguito con successo!");
		dialog.show();		
		return newExpenseId;
	}
    
	/**
	 * Metodo di gestione per la creazione della spesa.
	 * @throws ParseException 
	 */
	private void createExpense() throws ParseException 
	{
		int id;
        Log.i(TAG, "|--> Confermata creazione spesa!");
        Log.d(TAG, "|--> Controllo esistenza negozio selezionato");
        String shopDesc = ((AutoCompleteTextView) this.dialogCreateExpenseView.findViewById(R.id.DIALOG_ADD_EXPENSE_SHOP_ACTV)).getText().toString();
        String dateStr = ((TextView) dialogCreateExpenseView.findViewById(R.id.DIALOG_ADD_EXPENSE_DATE_TV)).getText().toString();
        // parsing date from string...
        Date date;
        date = DateFormat.getDateInstance().parse(dateStr);
        // instancing a new bean for the shop
        ShopBean shop = new ShopBean(-1, shopDesc);
        id = shop.exists( this.getContentResolver() ) ;
        if ( id > 0 )
     	   Log.d(TAG, "|--> Negozio '" + shop.toString() + "' gia' esistente!");
        else
        {
     	   Log.i(TAG, "|--> Procedo all'inserimento del negozio...");
     	   id = shop.dbinsert(this.getContentResolver());
        }
        ExpenseBean eb = new ExpenseBean(-1, shop, date, null);
        eb.dbinsert(this.getContentResolver());
        this.getLoaderManager().getLoader(ExpenseListFragment.URL_LOADER).forceLoad();
        this.OnExpenseSelected(eb);
	}

}
