package org.bob.android.supermarket.tasks;

import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.adapters.AdapterExpenses;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BaseSMBean;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.DBConstants;

import java.util.ArrayList;

/**
 * Created by roberto.gatti on 21/01/2015.
 */
public class ATRetrieveExpenses extends AsyncTask<Void, ExpenseBean, ArrayList<BaseSMBean>> {

    /**
     * ListAdapter della listview con i contatti
     */
    private AdapterExpenses expenseAL = null;

    /**
     * Boolean che indica se il task e' fallito.
     */
    private boolean failedTask = false;

    /**
     * Stringa dell'errore relativo al fallimento del task.
     */
    private String failureMessage = Constants.EMPTY_STRING;

    /**
     * Costruttore
     */
    public ATRetrieveExpenses(AdapterExpenses ae)
    {
        this.expenseAL = ae;
    }

    @Override
    protected ArrayList<BaseSMBean> doInBackground(Void... voids)
    {
        Cursor cursor = ApplicationSM.getInstance().getContentResolver().query(DBConstants.URI_JOIN_EXPENSE_SHOP, DBConstants.PROJECTION_EXPENSE_LIST, null, null, DBConstants.FIELD_EXPENSE_DATE);
        if (cursor == null || cursor.getCount() < 1)
        {
            Logger.writeLog("Nessun contatto censito!");
            return null;
        }

        if (cursor.moveToFirst())
        {
            Logger.writeLog("MoveToFirst sul cursor ha restituito false: nessun dato presente");
        }

        try
        {
            return BeanFactory.createBulkSMBean(DBConstants.URI_JOIN_EXPENSE_SHOP, cursor);
        }
        catch (SuperMarketException ex)
        {
            Logger.app_log("ERRORE: si e' verificato un errore nel recupero dei dati dal db");
            Logger.app_log("      |--> messaggio eccezione: " + ex.getMessage());
            this.failedTask = true;
            this.failureMessage = ex.getMessage();
            this.publishProgress(null);
        }
        cursor.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(ExpenseBean... values)
    {
        if ( values == null )
            if ( this.failedTask )
                Toast.makeText(ApplicationSM.getInstance().getApplicationContext(), "Task recupero dati fallito: " + this.failureMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(ArrayList<BaseSMBean> list)
    {
        Logger.writeLog("Dimensione lista spese: " + (list == null ? "NULL" : String.valueOf(list.size()) ) );
        Toast.makeText(ApplicationSM.getInstance().getApplicationContext(), "Spese recuperate con successo!", Toast.LENGTH_SHORT).show();

        this.expenseAL.translateAndSetList(list);
        this.expenseAL.notifyDataSetChanged();
    }

}