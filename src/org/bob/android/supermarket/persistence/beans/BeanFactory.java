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

package org.bob.android.supermarket.persistence.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.cp.SuperMarketCP;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.DBConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto.gatti on 23/01/2015.
 */
public class BeanFactory
{


    /**
     * Insert method for a bean of the application.
     *
     * @param bean2insert
     * @return
     * @throws SuperMarketException
     */
    public static BaseSMBean insertBean(BaseSMBean bean2insert) throws SuperMarketException
    {
        Uri uri = null;
        if ( bean2insert instanceof ExpenseBean )
            uri = DBConstants.URI_EXPENSES_CONTENT;

        if ( uri == null )
            throw new SuperMarketException("ERROR: insertBean not implemented for class '" + bean2insert.getClass().getName() + "'!");

        Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(uri, bean2insert.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        bean2insert.setId(newId);
        return bean2insert;
    }


    /**
     * Update method for the bean of the application.
     *
     * @param bean2update
     * @return
     * @throws SuperMarketException
     */
    public static int updateBean(BaseSMBean bean2update) throws SuperMarketException
    {
        int rowsUpdated = -1;
        Uri uri = null;
        if ( bean2update instanceof ExpenseBean )
            uri = DBConstants.URI_EXPENSES_CONTENT;

        if ( uri == null )
            throw new SuperMarketException("ERROR: updateBean not implemented for class '" + bean2update.getClass().getName() + "'!");

        // Executing update...
        return ApplicationSM
                    .getInstance()
                    .getContentResolver()
                    .update(uri,
                            bean2update.getContentValues(),
                            DBConstants.FIELD_DEFAULT_ID + " = ?",
                            new String[]{String.valueOf(bean2update.getId())}
                    );
    }

    /* ********************************************************************* */
    /*                        APPLICATION LAYER METHODS                      */
    /* ********************************************************************* */

    /**
     * The method get a shop from the database. If the shop exists, his details
     * are retreived, otherwise it will be inserted in the database.
     *
     * @param shopName The shop name to search/create.
     * @return a shop object (or null if shopNotFound)
     * @throws SuperMarketException when something strange happens
     */
    public static ShopBean getShop(String shopName) throws SuperMarketException
    {
        ShopBean sb = null;
        try
        {
            sb = new ShopBean(-1, shopName);
            Cursor c = ApplicationSM.getInstance().getContentResolver().query(DBConstants.URI_SHOPS_CONTENT, DBConstants.PROJECTION_SHOP_BEAN, DBConstants.FIELD_SHOP_DESCRIPTION + " = ?", new String[ ] { shopName }, null);
            if ( ! c.moveToFirst() )
            {
                Logger.dtb_log("Inserting shop '" + shopName + "' in database . . .");
                Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_SHOPS_CONTENT, sb.getContentValues());
                sb.setId( Integer.parseInt(newIns.getLastPathSegment()) );
            }
            else
                sb = BeanFactory.createShopBean(c);
        }
        catch ( Exception ex )
        {

        }
        return sb;
    }


    /* ********************************************************************* */
    /*                        DATABASE LAYER METHODS                         */
    /* ********************************************************************* */

    /**
     * Il metodo ritorna, a partire da un contentValues, un oggetto di tipo SMBaseBean.
     *
     * @param cv il content values da cui recuperare l'istanza
     * @return
     */
    @Deprecated
    public final static BaseSMBean getSMBeanFromContentValues(ContentValues cv, Class clazz)
    {
        Logger.lfc_log( "Recupero l'oggetto dal contentvalues");
        try
        {
            Object[] constrParams = new Object[] { cv } ;
            return (BaseSMBean) clazz.getConstructor(ContentValues.class).newInstance(constrParams); // newInstance(cv);

        }
        catch ( NoSuchMethodException ex )
        {
            ex.printStackTrace();
            return null;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Il metodo crea un oggetto di tipo BaseSMBean a seconda dell'URI passato. I dati da cui
     * partire per la creazione devono essere contenuti in un cursor.
     * @param uri per identificare il tipo di bean da creare;
     * @param cursor il contenitore dei dati.
     * @return un oggetto SMBean
     */
    public final static BaseSMBean createSMBean(Uri uri, Cursor cursor) throws SuperMarketException
    {
        // junit.framework.Assert.assertNotNull(cursor);
        Logger.app_log("Creazione bean: controllo cursore...");
        if ( cursor == null ) throw new SuperMarketException("ERRORE: nessun dato recuperato, il cursor e' null!");

        Logger.dtb_log("Traduzione oggetto da riga di cursore");
        switch ( DBConstants.sURIMatcher.match(uri) )
        {
            case DBConstants.URI_INDICATOR_EXPENSES_JOIN_SHOP: return BeanFactory.createExpenseShopBean(cursor);
            default: return null;
        }
    }

    /**
     *
     * @param uri
     * @param cursor
     * @return
     * @throws SuperMarketException
     */
    public final static ArrayList<BaseSMBean> createBulkSMBean(Uri uri, Cursor cursor) throws SuperMarketException
    {
        // junit.framework.Assert.assertNotNull(cursor);
        Logger.app_log("Creazione bean massiva: controllo cursore...");
        if ( cursor == null ) throw new SuperMarketException("ERRORE: nessun dato recuperato, il cursor e' a null!");

        Logger.app_log("Procedo con l'identificazione del tipo di oggetto da creare");
        ArrayList<BaseSMBean> output = new ArrayList<BaseSMBean>(cursor.getCount());

        switch ( DBConstants.sURIMatcher.match(DBConstants.URI_JOIN_EXPENSE_SHOP) )
        {
            case DBConstants.URI_INDICATOR_EXPENSES_JOIN_SHOP:
            {
                do
                {
                    output.add(BeanFactory.createExpenseShopBean(cursor));
                } while ( cursor.moveToNext() );
                break;
            }
            default:
                throw new SuperMarketException("ERRORE: uri '" + uri.toString() + "' non gestito nel BeanFactory!");
        }
        do
        {

        } while ( cursor.moveToNext() );
        return null;
    }

    /**
     * Il metodo crea un oggetto ExpenseBean al cui interno e' contenuto un oggetto ShopBean.
     * Il cursor in input deve arrivare dalla vista legata tra spese e negozi (per il recupero dei
     * dati generali della spesa).
     * @param cursor
     * @return
     */
    private static ExpenseBean createExpenseShopBean(Cursor cursor) throws SuperMarketException
    {
        Logger.app_log("Richiesta creazione oggetto ExpenseBean [ ShopBean ]", Logger.Level.VERBOSE);
        try
        {
            int expenseId = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            long expenseDate = cursor.getLong(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_DATE));
            double expenseCost = cursor.getDouble(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_COST));
            int shopId = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_SHOP_ID));
            String shopDesc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_SHOP_DESCRIPTION));
            return new ExpenseBean(expenseId, expenseDate, new ShopBean(shopId, shopDesc), expenseCost);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione spesa da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile creare il bean ExpenseBean: " + ex.getMessage(), ex);
        }
    }

    /**
     * This methods create, starting from a cursor, a ShopBean object.
     *
     * @param cursor the cursor with datas.
     * @return a shopbean
     * @throws SuperMarketException if something strange happens
     */
    private static ShopBean createShopBean(Cursor cursor) throws SuperMarketException
    {
        Logger.app_log("Trying creating a ShopBean from cursor . . .", Logger.Level.VERBOSE);
        try
        {
            int shopId = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            String shopDesc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_SHOP_DESCRIPTION));
            return new ShopBean(shopId, shopDesc);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione spesa da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile recuperare il negozio: " + ex.getMessage(), ex);
        }
    }
}
