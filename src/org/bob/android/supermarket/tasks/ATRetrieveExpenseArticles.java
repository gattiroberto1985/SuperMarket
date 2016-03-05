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
