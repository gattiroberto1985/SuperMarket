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
import org.bob.android.supermarket.gui.adapters.ACTVAdapter;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BaseSMBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.DBConstants;

import java.util.ArrayList;

/**
 * Created by roberto.gatti on 21/01/2015.
 */
public class ATRetrieveObjects extends AsyncTask<Void, Void, Void>
{

    private Uri toSearch = null;

    private String field2search = null;

    ArrayList<String> objs = new ArrayList<String>();

    public ATRetrieveObjects(Uri uri)
    {
        this.toSearch = uri;
        if ( uri.equals(DBConstants.URI_SHOPS_CONTENT) )
            this.field2search = DBConstants.FIELD_SHOP_DESCRIPTION;
        else if ( uri.equals(DBConstants.URI_BRANDS_CONTENT))
            this.field2search = DBConstants.FIELD_BRAND_DESCRIPTION;
        else if ( uri.equals(DBConstants.URI_CATEGORIES_CONTENT))
            this.field2search = DBConstants.FIELD_CATEGORY_DESCRIPTION;
        else if ( uri.equals(DBConstants.URI_ARTICLES_CONTENT))
            this.field2search = DBConstants.FIELD_ARTICLE_DESCRIPTION;
        else
            throw new RuntimeException("ERROR: uri seems invalid: " + uri.toString());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String fieldName;
        Cursor cursor = ApplicationSM.getInstance().getContentResolver().query(toSearch, new String [ ] { DBConstants.FIELD_DEFAULT_ID, this.field2search }, null, null, this.field2search);
        if ( cursor == null || cursor.getCount() < 1 )
        {
            Logger.app_log("Nessun dato presente nel cursore!");
            return null;
        }
        if ( ! cursor.moveToFirst() )
        {
            Logger.app_log("MoveToFirst sul cursor ha restituito false: nessun dato presente!");
        }

        do
        {
            String desc = cursor.getString(cursor.getColumnIndex(this.field2search));
            this.objs.add(desc);
        } while ( cursor.moveToNext() );
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        ACTVAdapter aa = new ACTVAdapter(ApplicationSM.getInstance().getApplicationContext(), this.objs);
        if ( this.toSearch.equals(DBConstants.URI_SHOPS_CONTENT) )
        {
            ApplicationSM.shops.clear();
            ApplicationSM.shops.addAll(this.objs);
        }
        else if ( this.toSearch.equals(DBConstants.URI_BRANDS_CONTENT))
        {
            ApplicationSM.brands.clear();
            ApplicationSM.brands.addAll(this.objs);
        }
        else if ( this.toSearch.equals(DBConstants.URI_CATEGORIES_CONTENT))
        {
            ApplicationSM.categories.clear();
            ApplicationSM.categories.addAll(this.objs);
        }
        else if ( this.toSearch.equals(DBConstants.URI_ARTICLES_CONTENT))
        {
            ApplicationSM.articles.clear();
            ApplicationSM.articles.addAll(this.objs);
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
