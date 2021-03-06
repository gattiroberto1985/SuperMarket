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

    public static int deleteBean(BaseSMBean bean2delete) throws SuperMarketException
    {
        if ( bean2delete instanceof ExpenseBean )
            return BeanFactory.deleteExpenseBean((ExpenseBean) bean2delete);

        if ( bean2delete instanceof ExpenseArticleBean )
            return BeanFactory.deleteExpenseArticleBean( ( ExpenseArticleBean ) bean2delete );

        throw new SuperMarketException("ERROR: method 'deleteBean' not yet implemented for class '" + bean2delete.getClass().getName() + "'!");
    }


    private static int deleteExpenseBean(ExpenseBean eb) throws SuperMarketException
    {
        if ( eb == null )
            throw new SuperMarketException("ERROR: eb is null!");

        if ( eb.getId() < 0 )
            throw new SuperMarketException("ERROR: id is not valid: '" + eb.getId() + "'!");

        // Proceeding with the removal of the expense . . .
        return ApplicationSM.getInstance().getContentResolver().delete(
                Uri.parse(DBConstants.URI_EXPENSES_CONTENT + "/" + String.valueOf(eb.getId())), null, null);
    }

    private static int deleteExpenseArticleBean(ExpenseArticleBean eab) throws SuperMarketException
    {
        if ( eab == null )
            throw new SuperMarketException("ERROR: eab is null!");

        if ( eab.getId() < 0 )
            throw new SuperMarketException("ERROR: id is not valid: '" + eab.getId() + "'!");

        // Proceeding with the removal of the expense . . .
        return ApplicationSM.getInstance().getContentResolver().delete(
                Uri.parse(DBConstants.URI_EXPENSE_ARTICLES_CONTENT + "/" + String.valueOf(eab.getId())), null, null);
    }

    public static Object insertOrUpdateBean(BaseSMBean bean) throws SuperMarketException
    {
        if ( bean.getId() == -1 )
            return BeanFactory.insertBean(bean);
        else
            return BeanFactory.updateBean(bean);
    }

    /**
     * Method to query a bean from the db.
     *
     * @param bean2query
     * @return
     * @throws SuperMarketException
     *
     */
    public static BaseSMBean queryBean(BaseSMBean bean2query) throws SuperMarketException
    {
        if ( bean2query instanceof ShopBean )
            return BeanFactory.getShop((ShopBean) bean2query);

        if ( bean2query instanceof BrandBean )
            return BeanFactory.getBrand( (BrandBean) bean2query);

        if ( bean2query instanceof ArticleBean )
            return BeanFactory.getArticle( (ArticleBean) bean2query);

        /*if ( bean2query instanceof ExpenseArticleBean )
            return BeanFactory.getExpenseArticle( (ExpenseArticleBean) bean2query);

        if ( bean2query instanceof ExpenseBean )
            return BeanFactory.getExpense( (ExpenseBean) bean2query);*/

        if ( bean2query instanceof CategoryBean )
            return BeanFactory.getCategory( (CategoryBean) bean2query);

        throw new SuperMarketException("ERROR: method 'queryBean' not yet implemented for class '" + bean2query.getClass().getName() + "'!");
    }

    /*public static BaseSMBean queryOrInsertBean(BaseSMBean bean2query) throws SuperMarketException
    {
        BaseSMBean bean2return = BeanFactory.queryBean(bean2query);
        if ( bean2query == null )
            bean2return = ( BaseSMBean) BeanFactory.insertOrUpdateBean(bean2query);
        return bean2return;
    }*/

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
            return BeanFactory.insertExpenseBean((ExpenseBean) bean2insert);

        if ( bean2insert instanceof ShopBean )
            return BeanFactory.insertShopBean((ShopBean) bean2insert);

        if ( bean2insert instanceof ExpenseArticleBean )
            return BeanFactory.insertExpenseArticleBean((ExpenseArticleBean) bean2insert);

        if ( bean2insert instanceof ArticleBean )
            return BeanFactory.insertArticleBean( (ArticleBean) bean2insert);

        if ( bean2insert instanceof BrandBean )
            return BeanFactory.insertBrandBean( (BrandBean) bean2insert);

        if ( bean2insert instanceof CategoryBean )
            return BeanFactory.insertCategoryBean( (CategoryBean) bean2insert);

        /*if ( uri == null ) */
        throw new SuperMarketException("ERROR: insertBean not implemented for class '" + bean2insert.getClass().getName() + "'!");

        /*Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(uri, bean2insert.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        bean2insert.setId(newId);
        return bean2insert;*/
    }


    private static ShopBean insertShopBean(ShopBean sb) throws SuperMarketException
    {
        Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_SHOPS_CONTENT, sb.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        sb.setId(newId);
        return sb;
    }

    private static ExpenseBean insertExpenseBean(ExpenseBean eb) throws SuperMarketException
    {
        // Setting the cost of the expense . . .
        eb.setCost();
        // Checking shop and inserting it if not existent . . .
        ShopBean sb = (ShopBean) BeanFactory.queryBean(eb.getShop());
        eb.setShop(sb);
        Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_EXPENSES_CONTENT, eb.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        eb.setId(newId);
        return eb;
    }

    private static ExpenseArticleBean insertExpenseArticleBean(ExpenseArticleBean eab) throws SuperMarketException
    {
        // Checking if article, brand and category . . .
        if ( eab.getArticle().getId() == -1 )
            eab.setArticle( ( ArticleBean) BeanFactory.queryBean(eab.getArticle()));
        Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_EXPENSE_ARTICLES_CONTENT, eab.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        eab.setId(newId);
        return eab;
    }

    private static ArticleBean insertArticleBean(ArticleBean ab) throws SuperMarketException
    {
        if ( ab.getCategory().getId() == -1 )
            ab.setCategory((CategoryBean) BeanFactory.queryBean(ab.getCategory()));
        if ( ab.getBrand().getId() == -1 )
            ab.setBrand( (BrandBean) BeanFactory.queryBean(ab.getBrand()));

        Uri newIns = ApplicationSM.getInstance()
                        .getContentResolver()
                        .insert(DBConstants.URI_ARTICLES_CONTENT, ab.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        ab.setId(newId);
        return ab;
    }

    private static CategoryBean insertCategoryBean(CategoryBean cb) throws SuperMarketException
    {
        Uri newIns = ApplicationSM.getInstance()
                .getContentResolver()
                .insert(DBConstants.URI_CATEGORIES_CONTENT, cb.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        cb.setId(newId);
        return cb;
    }

    private static BrandBean insertBrandBean(BrandBean bb) throws SuperMarketException
    {
        Uri newIns = ApplicationSM.getInstance()
                .getContentResolver()
                .insert(DBConstants.URI_BRANDS_CONTENT, bb.getContentValues());
        int newId = Integer.parseInt(newIns.getLastPathSegment().toString());
        bb.setId(newId);
        return bb;
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
        Uri uri = null;
        if ( bean2update instanceof ExpenseBean )
        {
            uri = Uri.parse(DBConstants.URI_EXPENSES_CONTENT + "/" + String.valueOf(bean2update.getId()));
            if ( ( (ExpenseBean) bean2update).getArticles() != null )
            {
                BeanFactory.updateExpenseArticleList((ExpenseBean)bean2update);
            }
        }

        if ( bean2update instanceof ExpenseArticleBean )
        {
            uri = Uri.parse(DBConstants.URI_EXPENSE_ARTICLES_CONTENT + "/" + String.valueOf(bean2update.getId()));
        }

        if ( uri == null )
            throw new SuperMarketException("ERROR: updateBean not implemented for class '" + bean2update.getClass().getName() + "'!");

        // Executing update...
        return ApplicationSM
                    .getInstance()
                    .getContentResolver()
                    .update(uri,
                            bean2update.getContentValues(),
                            DBConstants.FIELD_DEFAULT_ID + " = ?",            // Maybe this and next row
                            new String[]{String.valueOf(bean2update.getId())} // are useless by update implementation...
                    );
    }

    /**
     * The method updates or inserts the expense articles in the list.
     * @param eb
     * @throws SuperMarketException
     */
    private static void updateExpenseArticleList(ExpenseBean eb) throws SuperMarketException
    {
        // TODO: how to correctly update the expense?
        // Case 1: coming from an expense header update dialog
        // Case 2: coming from an expense article list update screen
        //  --> when expense article list is null -> updating header
        //  --> when expense article list not null -> updating full expense
        // updating full expense, so saving (by inserting or updating)
        // the expenseArticleList
        for ( ExpenseArticleBean eab : eb.getArticles() )
        {
            Logger.dtb_log("Updating expense article bean with id '" + eab.getId() + "' relative to expense with id '" + eb.getId() + "' . . .");
            BeanFactory.insertOrUpdateBean(eab);
        }
    }

    /* ********************************************************************* */
    /*                        APPLICATION LAYER METHODS                      */
    /* ********************************************************************* */

    /**
     * The method get a shop from the database. If the shop exists, his details
     * are retreived, otherwise it will be inserted in the database.
     *
     * @param shop The shop bean to search.
     * @return a shop object (or null if shopNotFound)
     * @throws SuperMarketException when something strange happens
     */
    public static ShopBean getShop(ShopBean shop) throws SuperMarketException
    {
        ShopBean sb = null;
        String shopName = shop.getDescription();
        try
        {
            sb = new ShopBean(-1, shopName);
            Cursor c = ApplicationSM.getInstance().getContentResolver().query(DBConstants.URI_SHOPS_CONTENT, DBConstants.PROJECTION_SHOP_BEAN, DBConstants.FIELD_SHOP_DESCRIPTION, new String[ ] { shopName }, null);
            if ( ! c.moveToFirst() )
            {
                /*Logger.dtb_log("Inserting shop '" + shopName + "' in database . . .");
                Uri newIns = ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_SHOPS_CONTENT, sb.getContentValues());
                sb.setId( Integer.parseInt(newIns.getLastPathSegment()) );*/
                sb = (ShopBean) BeanFactory.insertBean(sb);
            }
            else
                sb = BeanFactory.createShopBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return sb;
    }

    public static BrandBean getBrand(BrandBean brand) throws SuperMarketException
    {
        try
        {
            Cursor c = ApplicationSM.getInstance()
                        .getContentResolver()
                        .query(DBConstants.URI_BRANDS_CONTENT,
                                DBConstants.PROJECTION_BRAND_BEAN,
                                DBConstants.FIELD_BRAND_DESCRIPTION,
                                new String[]{brand.getDescription()}, null);
            if ( c == null || ! c.moveToFirst() )
            {
                brand = (BrandBean) BeanFactory.insertBean(brand);
            }
            else
                brand = BeanFactory.createBrandBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return brand;
    }

    public static CategoryBean getCategory(CategoryBean cb) throws SuperMarketException
    {
        try
        {
            Cursor c = ApplicationSM.getInstance()
                    .getContentResolver()
                    .query(DBConstants.URI_CATEGORIES_CONTENT,
                            DBConstants.PROJECTION_CATEGORY_BEAN,
                            DBConstants.FIELD_CATEGORY_DESCRIPTION,
                            new String[]{cb.getDescription()}, null);
            if ( c == null || ! c.moveToFirst() )
            {
                cb = (CategoryBean) BeanFactory.insertBean(cb);
            }
            else
                cb = BeanFactory.createCategoryBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return cb;
    }

    public static ArticleBean getArticle(ArticleBean ab) throws SuperMarketException
    {
        try
        {
            if ( ab.getCategory().getId() == -1 )
                ab.setCategory( BeanFactory.getCategory( ab.getCategory() ));

            if ( ab.getBrand().getId() == -1 )
                ab.setBrand( BeanFactory.getBrand( ab.getBrand() ));

            // Checking article
            Cursor c = ApplicationSM.getInstance()
                    .getContentResolver()
                    .query(Uri.parse(DBConstants.STR_URI_VIEW_ARTICLE_DETAILS),
                            DBConstants.PROJECTION_ARTICLE_DETAILS_BEAN,
                            DBConstants.FIELD_ARTICLE_DESCRIPTION + ","
                                    + DBConstants.FIELD_ARTICLE_BRAND_ID + ","
                                    + DBConstants.FIELD_ARTICLE_CATEGORY_ID,
                            new String[]{ab.getDescription(),
                                         String.valueOf( ab.getBrand().getId()   ),
                                         String.valueOf( ab.getCategory().getId())}
                            , null);
            if ( c == null || ! c.moveToFirst() )
            {
                ab = (ArticleBean) BeanFactory.insertBean(ab);
            }
            else
                ab = BeanFactory.createArticleBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return ab;
    }

    /*public ExpenseArticleBean getExpenseArticle(ExpenseArticleBean eab) throws SuperMarketException
    {
        try
        {
            Cursor c = ApplicationSM.getInstance()
                    .getContentResolver()
                    .query(DBConstants.URI_BRANDS_CONTENT,
                            DBConstants.PROJECTION_BRAND_BEAN,
                            DBConstants.FIELD_BRAND_DESCRIPTION,
                            new String[]{brand.getDescription()}, null);
            if ( ! c.moveToFirst() )
            {
                brand = (BrandBean) BeanFactory.insertBean(brand);
            }
            else
                brand = BeanFactory.createBrandBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return brand;
    }*/

    /*public ExpenseBean getExpense(ExpenseBean eb) throws SuperMarketException
    {
        try
        {
            Cursor c = ApplicationSM.getInstance()
                    .getContentResolver()
                    .query(DBConstants.URI_BRANDS_CONTENT,
                            DBConstants.PROJECTION_BRAND_BEAN,
                            DBConstants.FIELD_BRAND_DESCRIPTION,
                            new String[]{brand.getDescription()}, null);
            if ( ! c.moveToFirst() )
            {
                brand = (BrandBean) BeanFactory.insertBean(brand);
            }
            else
                brand = BeanFactory.createBrandBean(c);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw new SuperMarketException(ex);
        }
        return brand;
    }*/

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
            case DBConstants.URI_INDICATOR_VIEW_EXPENSE_SHOP: return BeanFactory.createExpenseShopBean(cursor);
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

        switch ( DBConstants.sURIMatcher.match(uri) )
        {
            case DBConstants.URI_INDICATOR_VIEW_EXPENSE_SHOP:
            {
                do
                {
                    output.add(BeanFactory.createExpenseShopBean(cursor));
                } while ( cursor.moveToNext() );
                break;
            }
            case DBConstants.URI_INDICATOR_VIEW_EXPENSE_ARTICLES:
            {
                do {
                    output.add(BeanFactory.createExpenseArticleBean(cursor));
                } while ( cursor.moveToNext() );
                break;
            }
            default:
                throw new SuperMarketException("ERRORE: uri '" + uri.toString() + "' non gestito nel BeanFactory!");
        }
        /*do
        {

        } while ( cursor.moveToNext() );*/
        return output;
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

    private static ArticleBean createArticleBean(Cursor cursor) throws SuperMarketException
    {
        Logger.app_log("Trying creating an ArticleBean from cursor . . .", Logger.Level.VERBOSE);
        try
        {
            int id      = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            String aDsc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_DESCRIPTION));
            int cId     = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_CATEGORY_ID));
            String cDsc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_CATEGORY_DESCRIPTION));
            int bId     = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_BRAND_ID));
            String bDsc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_BRAND_DESCRIPTION));
            return new ArticleBean(id, new BrandBean(bId, bDsc), new CategoryBean(cId, cDsc, 'X'), aDsc);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione spesa da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile recuperare il negozio: " + ex.getMessage(), ex);
        }
    }

    private static CategoryBean createCategoryBean(Cursor cursor) throws SuperMarketException
    {
        Logger.app_log("Trying creating an CategoryBean from cursor . . .", Logger.Level.VERBOSE);
        try
        {
            int id     = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            String dsc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_CATEGORY_DESCRIPTION));
            return new CategoryBean(id, dsc, 'X');
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione CategoryBean da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile recuperare il CategoryBean: " + ex.getMessage(), ex);
        }
    }

    private static BrandBean createBrandBean(Cursor cursor) throws SuperMarketException
    {
        Logger.app_log("Trying creating an BrandBean from cursor . . .", Logger.Level.VERBOSE);
        try
        {
            int id     = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            String dsc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_BRAND_DESCRIPTION));
            return new BrandBean(id, dsc);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione BrandBean da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile recuperare il BrandBean: " + ex.getMessage(), ex);
        }
    }


    private static ExpenseArticleBean createExpenseArticleBean(Cursor cursor) throws SuperMarketException
    {
        try
        {
            /*
                        DBConstants.FIELD_DEFAULT_ID                       ,
            DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID       ,
            DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID       ,
            DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST     ,
            DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY ,
            DBConstants.FIELD_ARTICLE_DESCRIPTION              ,
            DBConstants.FIELD_ARTICLE_BRAND_ID                 ,
            DBConstants.FIELD_BRAND_DESCRIPTION                ,
            DBConstants.FIELD_ARTICLE_CATEGORY_ID              ,
            DBConstants.FIELD_CATEGORY_DESCRIPTION
             */
            // Getting ids...
            int expenseArticleId = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_DEFAULT_ID));
            int articleId        = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID));
            int brandId          = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_BRAND_ID));
            int categoryId       = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_CATEGORY_ID));
            int expenseId        = cursor.getInt(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID));

            // Getting string...
            String articleDesc  = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_ARTICLE_DESCRIPTION));
            String brandDesc    = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_BRAND_DESCRIPTION));
            String categoryDesc = cursor.getString(cursor.getColumnIndex(DBConstants.FIELD_CATEGORY_DESCRIPTION));

            // Getting expense article details . . .
            double articleCost = cursor.getDouble(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST));
            double articleQnty = cursor.getDouble(cursor.getColumnIndex(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY));

            CategoryBean cat = new CategoryBean(categoryId, categoryDesc, Constants.DEFAULT_CHAR);
            BrandBean brn = new BrandBean(brandId, brandDesc);
            ArticleBean art = new ArticleBean(articleId, brn, cat, articleDesc);
            return new ExpenseArticleBean(expenseArticleId, expenseId, art, articleCost, articleQnty);
        }
        catch ( Exception ex )
        {
            Logger.app_log("ERRORE: eccezione in fase di creazione articolo di spesa da cursore!", Logger.Level.ERROR);
            Logger.app_log("Messaggio: " + ex.getMessage(), Logger.Level.ERROR);
            throw new SuperMarketException("Impossibile recuperare l'articolo di spesa! Messaggio interno: " + ex.getMessage(), ex);
        }
    }

}
