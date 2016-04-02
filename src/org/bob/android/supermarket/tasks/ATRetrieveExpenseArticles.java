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
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.Toast;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.adapters.AdapterExpenseArticles;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BaseSMBean;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.DBConstants;

import java.util.ArrayList;

/**
 * Created by roberto.gatti on 21/01/2015.
 */
public class ATRetrieveExpenseArticles extends AsyncTask<Void, ExpenseArticleBean, ArrayList<BaseSMBean>>
{

    private AdapterExpenseArticles alAdapter = null;

    private ExpenseBean eb;

    private boolean failedTask = false;

    private String failureMessage = "";

    private ExpenseBean expense = null;

    public ATRetrieveExpenseArticles(ListAdapter aa, ExpenseBean expense)
    {
        this.alAdapter = (AdapterExpenseArticles) aa;
        this.expense = expense;
    }

    @Override
    protected void onPostExecute(ArrayList<BaseSMBean> list)
    {
        Logger.writeLog("Dimensione lista: " + (list == null ? "NULL" : String.valueOf(list.size()) ) );
        Toast.makeText(ApplicationSM.getInstance().getApplicationContext(), "Articoli recuperati con successo!", Toast.LENGTH_SHORT).show();
        if ( list != null )
            for ( BaseSMBean bsmb : list)
                this.expense.addExpenseItem((ExpenseArticleBean) bsmb);
        this.expense.setCost();
        this.alAdapter.translateAndSetList(list);
        this.alAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(ExpenseArticleBean... values) {
        if ( values == null )
            if ( this.failedTask )
                Toast.makeText(ApplicationSM.getInstance().getApplicationContext(), "Task recupero dati fallito: " + this.failureMessage, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
    }

    @Override
    protected ArrayList<BaseSMBean> doInBackground(Void... voids)
    {
        ArrayList<BaseSMBean> output = new ArrayList<BaseSMBean>(0);
        Logger.app_log("Recupero i dati della spesa selezionata");
        Cursor cursor = ApplicationSM.getInstance().getContentResolver().query(
                DBConstants.URI_VIEW_EXPENSE_ARTICLES,
                DBConstants.PROJECTION_EXPENSE_ARTICLE_LIST,
                DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID,
                new String[] { String.valueOf(this.expense.getId()) },
                DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID);

        if ( cursor == null || cursor.getCount() < 1 )
        {
            Logger.writeLog("Nessun articolo di spesa!");
            return null;
        }

        if (!cursor.moveToFirst())
        {
            Logger.writeLog("MoveToFirst sul cursor ha restituito false: nessun dato presente");
            return null;
        }

        try
        {
             output = BeanFactory.createBulkSMBean(DBConstants.URI_VIEW_EXPENSE_ARTICLES, cursor);
            //return null;
        }
        catch (SuperMarketException ex)
        {
            Logger.app_log("ERRORE: si e' verificato un errore nel recupero degli articoli di spesa!");
            Logger.app_log("      |--> messaggio eccezione: " + ex.getMessage());
            this.failedTask = true;
            this.failureMessage = ex.getMessage();
            this.publishProgress(null);
        }
        cursor.close();
        return output;
    }
}
