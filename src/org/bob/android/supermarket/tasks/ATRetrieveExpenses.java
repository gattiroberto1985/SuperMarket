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