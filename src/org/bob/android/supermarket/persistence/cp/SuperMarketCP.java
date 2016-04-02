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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.*;
import org.bob.android.supermarket.utilities.DBConstants;

import java.sql.SQLException;

/**
 * Content provider per l'applicazione SuperMarket
 * @author bob
 *
 */
public class SuperMarketCP extends ContentProvider 
{
    
	/**
	 * Stringa relativa alle authority dell'applicazione.
	 */
	static final String AUTHORITY = "org.bob.android.supermarket";

	/**
	 * Oggetto database helper dell'applicazione.
	 */
	private SMDBHelper dbh;
	
	/**
	 * Costruttore di default del content provider.
	 */
	public SuperMarketCP() 
	{
        Logger.dtb_log("SuperMarket Content provider: called constructor");
	}

	/**
	 * Override del metodo onCreate. Istanzia un nuovo dbhelper.
	 */
	@Override
	public boolean onCreate() 
	{
		Logger.dtb_log("Initializing Content Provider...");
		if ( this.dbh == null ) this.dbh = new SMDBHelper(this.getContext());
		return false;
	}
	
	/**
	 * Metodo di decodifica dell'uri.
	 */
	@Override
	public String getType(Uri uri) 
	{
		Logger.dtb_log("Decoding uri '" + uri.toString() + "'...");
		int match = DBConstants.sURIMatcher.match(uri);
		switch ( match )
		{
            case DBConstants.URI_INDICATOR_ARTICLES                   : return DBConstants.CONTENT_ITEM_TYPE_ARTICLE;
            case DBConstants.URI_INDICATOR_ARTICLES_COLLECTION        : return DBConstants.CONTENT_TYPE_ARTICLE;
            /*case DBConstants.URI_INDICATOR_ARTICLES_DISTINCT_BRAND_ID : return DBConstants.CONTENT_ITEM_TYPE;
			case DBConstants.URI_INDICATOR_ARTICLES_DISTINCT_CATEGORY_ID: return DBConstants.CONTENT_;*/
			case DBConstants.URI_INDICATOR_BRANDS                      : return DBConstants.CONTENT_ITEM_TYPE_BRAND;
			case DBConstants.URI_INDICATOR_BRANDS_COLLECTION           : return DBConstants.CONTENT_TYPE_BRAND;
			case DBConstants.URI_INDICATOR_CATEGORIES                  : return DBConstants.CONTENT_ITEM_TYPE_CATEGORY;
			case DBConstants.URI_INDICATOR_CATEGORIES_COLLECTION       : return DBConstants.CONTENT_TYPE_CATEGORY;
			//case DBConstants.URI_INDICATOR_CATEGORIES_DISTINCT_APPLY_TO: return DBConstants.CONTENT_JOIN_SHOP_ITEM_TYPE;
			case DBConstants.URI_INDICATOR_EXPENSE_ARTICLES            : return DBConstants.CONTENT_ITEM_TYPE_EXPENSE_ARTICLE;
			case DBConstants.URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION : return DBConstants.CONTENT_TYPE_EXPENSE_ARTICLE;
			case DBConstants.URI_INDICATOR_EXPENSES                    : return DBConstants.CONTENT_ITEM_TYPE_EXPENSE;
			case DBConstants.URI_INDICATOR_EXPENSES_COLLECTION         : return DBConstants.CONTENT_TYPE_EXPENSE;
			//case DBConstants.URI_INDICATOR_EXPENSES_DISTINCT_DATE: return DBConstants.CONTENT_TYPE;
			//case DBConstants.URI_INDICATOR_EXPENSES_DISTINCT_SHOP: return DBConstants.CONTENT_ITEM_TYPE;
			//case DBConstants.URI_INDICATOR_EXPENSES_JOIN_EXPENSE_ARTICLE: return DBConstants.CONTENT_ITEM_TYPE;
			//case DBConstants.URI_INDICATOR_EXPENSES_JOIN_SHOP: return DBConstants.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Uri invalido: '" + uri + "' -- match: '" + match + "'!");
		}
	}

    /**
     * Il metodo, a partire dall'uri, fornisce in output la classe corretta da passare
     * ad ORMLite.
     * @param uri
     * @return
     */
    private Class getClassFromURI(Uri uri)
    {
        Logger.dtb_log("Decoding URI to get the right Class: '" + uri.toString() + "'");
        int match = DBConstants.sURIMatcher.match(uri);
        switch ( match )
        {
            case DBConstants.URI_INDICATOR_BRANDS:
            case DBConstants.URI_INDICATOR_BRANDS_COLLECTION: return BrandBean.class;

            case DBConstants.URI_INDICATOR_CATEGORIES:
            case DBConstants.URI_INDICATOR_CATEGORIES_COLLECTION: return CategoryBean.class;

            case DBConstants.URI_INDICATOR_ARTICLES:
            case DBConstants.URI_INDICATOR_ARTICLES_COLLECTION: return ArticleBean.class;

            case DBConstants.URI_INDICATOR_EXPENSES:
            case DBConstants.URI_INDICATOR_EXPENSES_COLLECTION: return ExpenseBean.class;

            case DBConstants.URI_INDICATOR_EXPENSE_ARTICLES:
            case DBConstants.URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION: return ExpenseArticleBean.class;

			case DBConstants.URI_INDICATOR_SHOPS:
			case DBConstants.URI_INDICATOR_SHOPS_COLLECTION: return ShopBean.class;

            default: throw new IllegalArgumentException("Uri invalido: '" + uri.toString() + "' -- match: " + match + "!");
        }
    }

	/* --------------------------------------------------------------------- *
	 *                           DB ACCESS METHODS                           *
	 * --------------------------------------------------------------------- */

    /**
	 * Override del metodo delete.
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
        Logger.dtb_log(  "Content Provider: delete");
        Class clazz = this.getClassFromURI(uri);
        int output = -1;
        int id = Integer.parseInt(uri.getLastPathSegment().toString());
        try
        {
            Dao<Object, Integer> dao = this.dbh.getDao(clazz);
			output = dao.deleteById(id);
            Logger.dtb_log( "Dao rimosso!");
        }
        catch ( SQLException ex )
        {
            Logger.dtb_log(  "ERRORE: impossibile rimuovere l'oggetto a db!");
            Logger.dtb_log(  "        " + ex.getMessage());
            ex.printStackTrace();
        }
        catch ( NumberFormatException ex )
        {
            Logger.dtb_log(  "ERRORE: impossibile rimuovere l'oggetto a db!");
            Logger.dtb_log(  "        " + ex.getMessage());
            output = -1;
        }
        return output;
	}

	/**
	 * Override del metodo insert.
	 */
    @Deprecated
	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
        Logger.dtb_log("Content Provider: insert");
        Class clazz = this.getClassFromURI(uri);
        int id = -1;
        try
        {
            Dao<BaseSMBean, Integer> dao = this.dbh.getDao(clazz);
            BaseSMBean obj = BeanFactory.getSMBeanFromContentValues(values, clazz);
            dao.create(obj);
            Logger.dtb_log(  "Dao creato, procedo con il recupero dell'id...");
            id = dao.extractId(obj);
            obj = null;
        }
        catch ( SQLException ex )
        {
            Logger.dtb_log(  "ERRORE: impossibile inserire l'oggetto a db!");
            Logger.dtb_log(  "        " + ex.getMessage());
            ex.printStackTrace();
        }
        Logger.dtb_log(  "Ritorno l'uri: ' " + Uri.withAppendedPath(uri, String.valueOf(id)) + "'");
        this.getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri, String.valueOf(id)), null);
        return Uri.withAppendedPath(uri, String.valueOf(id));
	}

	/**
	 * Override del metodo query.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String whereClauses, String[] whereValues, String sortOrder)
	{
        Logger.dtb_log("Content Provider: query");
		Cursor outCursor = null;
		if ( this.isClassURI(uri) )
		{
			outCursor = this.queryClassURI(uri, projection, whereClauses, whereValues, sortOrder);
		}
		else
			outCursor = this.queryNonClassURI(uri, projection, whereClauses, whereValues, sortOrder);
		return outCursor;
	}

	/**
	 * Class URI are setted with integer indicator ending in 0 or 1. Thus, the
	 * rest of a /10 division is 0 or 1. Otherwise, the uri did not point
	 * to a class object.
	 *
	 * @return true if the uri points to an object URI, false otherwise.
	 *
	 */
	private boolean isClassURI(Uri uri)
	{
		int remainder =  ( DBConstants.sURIMatcher.match(uri) ) % 10;
		if ( remainder < 0 )
			throw new RuntimeException("ERROR: controllare gli uri! UriMatcher.match(" + uri.toString() + ") % 10 ha dato resto " + remainder + "!");

		if ( remainder > 1 )
			return false;
		return true;
	}

	/**
	 * The method queries for a non class uri, typically for a database view.
	 *
	 * @param uri
	 * @param projection
	 * @param whereClauses
	 * @param whereValues
	 * @param sortOrder
	 * @return
	 */
	public Cursor queryNonClassURI(Uri uri, String[] projection, String whereClauses, String[] whereValues, String sortOrder)
	{
		Cursor output = null;
		switch ( DBConstants.sURIMatcher.match(uri) )
		{
			case DBConstants.URI_INDICATOR_VIEW_EXPENSE_SHOP: {
				output = this.queryExpenseJoinShop(uri, projection, whereClauses, whereValues, sortOrder);
				break;
			}
			case DBConstants.URI_INDICATOR_VIEW_EXPENSE_ARTICLES: {
				output = this.queryExpenseArticlesDetails(uri, projection, whereClauses, whereValues, sortOrder);
				break;
			}
			default:
				throw new RuntimeException("ERROR!");
		}
		return output;
	}

	public Cursor queryExpenseJoinShop(Uri uri, String[] projection, String whereClauses, String[] whereValues, String sortOrder)
	{
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DBConstants.VIEW_EXPENSE_SHOP_NAME);
		SQLiteDatabase db = this.dbh.getReadableDatabase();
		sortOrder = DBConstants.FIELD_EXPENSE_DATE + " ASC";
		Cursor cursor = queryBuilder.query(db, projection, whereClauses, whereValues, null, null, sortOrder);
		return cursor;
	}

	private Cursor queryExpenseArticlesDetails(Uri uri, String[] projection, String whereClauses, String[] whereValues, String sortOrder)
	{
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DBConstants.VIEW_EXPENSE_ARTICLE_FULL_DETAILS_NAME);
		queryBuilder.appendWhere(whereClauses + " = " + whereValues[0]);
		SQLiteDatabase db = this.dbh.getReadableDatabase();
		sortOrder = DBConstants.FIELD_DEFAULT_ID + " ASC";
		Cursor cursor = queryBuilder.query(db, projection, null, null, null, null, sortOrder);
		return cursor;
	}

	/**
	 * The method query for a standard object.
	 *
	 * @param uri
	 * @param projection
	 * @param whereClauses
	 * @param whereValues
	 * @param sortOrder
	 * @return
	 */
	private Cursor queryClassURI(Uri uri, String[] projection, String whereClauses, String[] whereValues, String sortOrder)
	{
		Cursor cursor = null;
		Class clazz = this.getClassFromURI(uri);
		try
		{
			Dao<Object, Integer> red = this.dbh.getDao(clazz);
			QueryBuilder<Object, Integer> qb = red.queryBuilder();

			if ( whereClauses != null )
				qb.where().eq(whereClauses, whereValues[0]);

			if ( sortOrder != null )
				qb.orderBy(sortOrder, true);

			//RuntimeExceptionDao<Class, String> red = this.dbh.getRuntimeExceptionDao(clazz);
			//QueryBuilder<Class, String> qb = red.queryBuilder();
			CloseableIterator<Object> iterator = null;
			iterator = red.iterator(qb.prepare());
			AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
			cursor = results.getRawCursor();
			cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return cursor;
	}


	/**
	 * Override del metodo update.
	 */
	@Override
	public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
	{
        Logger.dtb_log("Content Provider: update");
        Class clazz = this.getClassFromURI(uri);
        int output = -1;
        try
        {
            contentValues.put(DBConstants.FIELD_DEFAULT_ID, Integer.parseInt(uri.getLastPathSegment()));
            Dao<Object, Integer> dao = this.dbh.getDao(clazz);
            Object obj = BeanFactory.getSMBeanFromContentValues(contentValues, clazz);
            output = dao.update(obj);
            obj = null;
            Logger.dtb_log(  "Dao aggiornato!");
        }
        catch ( SQLException ex )
        {
            Logger.dtb_log(  "ERRORE: impossibile aggiornare l'oggetto a db!");
            Logger.dtb_log(  "        " + ex.getMessage());
            ex.printStackTrace();
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return output;

	}


}
