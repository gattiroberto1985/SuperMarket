package org.bob.android.supermarket.tasks;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.DBConstants;

/**
 * Created by roberto.gatti on 21/01/2015.
 */
public class ATRetrieveExpenseArticles extends AsyncTask<Void, Void, Void>
{

    private ExpenseBean eb;

    private boolean task_canceled = false;

    public ATRetrieveExpenseArticles(ExpenseBean eb)
    {
        this.eb = eb;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        Logger.app_log("Recupero i dati della spesa selezionata");
        Cursor cursor = ApplicationSM.getInstance().getContentResolver().query(
                Uri.parse(DBConstants.URI_JOIN_EXPENSE_ARTICLE + "/e=" + this.eb.getId()),
                DBConstants.PROJECTION_EXPENSE_ARTICLE_LIST,
                DBConstants.FIELD_DEFAULT_ID + " = ? ",
                new String[] { String.valueOf(this.eb.getId()) },
                DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID);
        return null;
    }
}
