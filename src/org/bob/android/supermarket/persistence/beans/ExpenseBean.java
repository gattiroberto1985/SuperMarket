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
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.DBConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by roberto.gatti on 22/01/2015.
 */
@DatabaseTable(tableName = DBConstants.TABLE_EXPENSES_NAME)
public class ExpenseBean extends BaseSMBean
{

    @DatabaseField(generatedId = true, index = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_EXPENSE_DATE)
    private long date = 0;

    @DatabaseField(index = true, columnName = DBConstants.FIELD_EXPENSE_SHOP_ID)
    private int shopId = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_EXPENSE_COST)
    private double cost = 0;

    /**
     * Shop della spesa.
     */
    private ShopBean shop = null;

    /**
     * Lista articoli della spesa.
     */
    private List<ExpenseArticleBean> articles = null;

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public ExpenseBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param date
     * @param shop
     * @param articles
     */
    public ExpenseBean(int id, long date, ShopBean shop, List<ExpenseArticleBean> articles)
    {
        this.setId(id);
        this.setDate(date);
        this.setShop(shop);
        this.setArticles(articles);
        this.setCost();
    }

    /**
     * Costruttore full.
     * @param id
     * @param date
     * @param shop
     * @param cost
     */
    public ExpenseBean(int id, long date, ShopBean shop, double cost)
    {
        this.setId(id);
        this.setDate(date);
        this.setShop(shop);
        this.setArticles(null);
        this.setCost(cost);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public ExpenseBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setDate(cv.getAsLong(DBConstants.FIELD_EXPENSE_DATE));
        this.setCost(cv.getAsDouble(DBConstants.FIELD_EXPENSE_COST));
        this.setShopId(cv.getAsInteger(DBConstants.FIELD_EXPENSE_SHOP_ID));
        this.setArticles(null);
    }

    /* ********************************************************************* */
    /*                             CLASS METHODS                             */
    /* ********************************************************************* */

    public final void addExpenseItem(ExpenseArticleBean eab)
    {
        this.articles.add(eab);
    }

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

    @Override
    public final void setId(int id) { this.id = id; }
    @Deprecated
    public final void setDate(Date date) { this.date = date.getTime(); }
    public final void setDate(long date) { this.date = date; }
    public final void setShopId(int sid) { this.shopId = sid; }
    public final void setShop(ShopBean shop)
    {
        this.shop = shop;
        this.shopId = this.getShop().getId();
    }

    public final void setArticles(List<ExpenseArticleBean> articles)
    {
        if ( articles == null ) this.articles = new ArrayList<ExpenseArticleBean>();
        else this.articles = articles;
    }

    public final void setCost(double cost) { this.cost = cost; }
    public final void setCost()
    {
        this.cost = 0;
        if ( this.articles != null )
        {
            for ( ExpenseArticleBean eab : this.articles ) this.cost += (eab.getArticleCost() * eab.getArticleQuantity());
        }
    }

    public final int getId() { return this.id; }
    public final long getDate() { return this.date; }
    public final Date getFormattedDate() { return new Date(this.getDate()); }
    public final ShopBean getShop() { return this.shop; }
    public final int getShopId() { return this.shopId; }
    public final List<ExpenseArticleBean> getArticles() { return this.articles; }
    public final double getCost() { return this.cost; }




    /* ********************************************************************* */
    /*                            OVERRIDDEN METHODS                         */
    /* ********************************************************************* */

    /**
     * Override del metodo @see org.bob.android.supermarket.persistence.beans.BaseSMBean.#getContentValues().
     * @return un contentValues con gli oggetti del bean
     */
    @Override
    public ContentValues getContentValues()
    {
        Logger.dtb_log("Creating content values for ExpenseBean...");
        ContentValues cv = new ContentValues(4);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_EXPENSE_DATE, this.getDate());
        cv.put(DBConstants.FIELD_EXPENSE_SHOP_ID, this.getShopId());
        cv.put(DBConstants.FIELD_EXPENSE_COST, this.getCost());
        return cv;
    }


    @Override
    public boolean equals(Object o) {

        if ( ! ( o instanceof ExpenseBean ) )
            return false;

        ExpenseBean eb = ( ExpenseBean ) o;

        // Se id e' -1 --> false
        if ( eb.getId() == -1 )
            return false;

        // se il negozio e' null --> false
        if ( eb.getShop() == null || this.getShop() == null )
            return false;

        ShopBean osb = eb.getShop();
        ShopBean  sb = this.getShop();
        if ( ! osb.equals(sb) )
            return false;


        return true;
    }
}

