package org.bob.android.supermarket.persistence.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
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
     * Il metodo ritorna, a partire da un contentValues, un oggetto di tipo SMBaseBean.
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
        Logger.app_log("Creazione bean massiva: controllo cursore...");
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
}
