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

package org.bob.android.supermarket.persistence.cp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.*;
import org.bob.android.supermarket.utilities.DBConstants;

import java.sql.SQLException;

//import org.bob.android.supermarket.test.TestPersistence;

/**
 * Classe dbHelper per il database dell'applicazione.
 * @author bob
 *
 */
public class SMDBHelper extends OrmLiteSqliteOpenHelper
{

	/**
	 * Stringa con il nome del database.
	 */
	private static final String DATABASE_NAME = DBConstants.DATABASE_NAME;
	
	/**
	 * Intero con la versione del database
	 */
	private static final int DATABASE_VERSION = DBConstants.DATABASE_VERSION;
	
	/**
	 * Costruttore di classe a singolo parametro.
	 * @param context
	 */
	public SMDBHelper(Context context) 
	{
		super(context, SMDBHelper.DATABASE_NAME, null, SMDBHelper.DATABASE_VERSION);
	}

	/**
	 * Il metodo esegue l'istanziazione delle tabelle del database
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs)
	{
        try
        {
            Logger.dtb_log("Trying to create the tables");
            TableUtils.createTable(cs, CategoryBean.class);
            TableUtils.createTable(cs, BrandBean.class);
            TableUtils.createTable(cs, ShopBean.class);
            TableUtils.createTable(cs, ArticleBean.class);
            TableUtils.createTable(cs, ExpenseBean.class);
            TableUtils.createTable(cs, ExpenseArticleBean.class);
            Logger.dtb_log("Calling test data insert...");
            //TestPersistence.createTestDatas();
        }
        catch (SQLException e)
        {
            Logger.dtb_log("ERROR: caught sqlexception: " + e.getMessage());
            throw new RuntimeException(e);
        }
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int i, int i2)
    {
        try
        {
            Logger.dtb_log("Trying to upgrade the database");
            Logger.dtb_log("Dropping tables...");
            TableUtils.dropTable(cs, CategoryBean.class, true);
            TableUtils.dropTable(cs, BrandBean.class, true);
            TableUtils.dropTable(cs, ShopBean.class, true);
            TableUtils.dropTable(cs, ArticleBean.class, true);
            TableUtils.dropTable(cs, ExpenseBean.class, true);
            TableUtils.dropTable(cs, ExpenseArticleBean.class, true);
            Logger.dtb_log("Ok, tables dropped. Re-creating tables...");
        }
        catch ( SQLException e)
        {
            Logger.dtb_log("ERROR: caught sqlexception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
