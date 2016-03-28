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

/**
 * Created by roberto.gatti on 22/01/2015.
 */
@DatabaseTable(tableName = DBConstants.TABLE_EXPENSE_ARTICLES_NAME)
public class ExpenseArticleBean extends BaseSMBean
{
    @DatabaseField(generatedId = true, index = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField( index = true, columnName = DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID)
    private int expenseId = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID)
    private int articleId = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST)
    private double articleCost = -1.0;

    @DatabaseField( index = true, columnName = DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY)
    private double articleQuantity = -1.0;

    private ArticleBean article;

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public ExpenseArticleBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param
     */
    public ExpenseArticleBean(int id, ExpenseBean e, ArticleBean a, double cost, double quantity)
    {
        this.setId(id);
        this.setExpenseId(e != null ? e.getId() : -1);
        this.setArticle(a);
        this.setArticleCost(cost);
        this.setArticleQuantity(quantity);
    }

    public ExpenseArticleBean(int id, int expenseId, ArticleBean a, double cost, double quantity)
    {
        this.setId(id);
        this.setExpenseId(expenseId);
        this.setArticle(a);
        this.setArticleCost(cost);
        this.setArticleQuantity(quantity);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public ExpenseArticleBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setArticleId(cv.getAsInteger(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID));
        this.setArticleCost(cv.getAsDouble(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST));
        this.setArticleQuantity(cv.getAsDouble(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY));
        this.setExpenseId(cv.getAsInteger(DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID));
    }

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

    @Override
    public final void setId(int id)                       { this.id = id; }
    public final void setExpenseId(int e_id)              { this.expenseId = e_id; }
    public final void setArticle(ArticleBean a)           { this.article = a;  this.articleId = this.getArticle().getId(); }
    public final void setArticleId(int a_id)              { this.articleId = a_id; }
    public final void setArticleCost(double cost)         { this.articleCost = cost; }
    public final void setArticleQuantity(double quantity) { this.articleQuantity = quantity; }


    public final int getId() { return this.id; }
    public final int getExpenseId() { return this.expenseId; }
    public final ArticleBean getArticle() { return this.article; }
    public final double getArticleCost() { return this.articleCost; }
    public final double getArticleQuantity() { return this.articleQuantity; }
    public final int getArticleId() { return this.articleId; }
    public final double getFullCost() { return this.getArticleCost() * this.getArticleQuantity(); }


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
        Logger.dtb_log("Creating content values for ExpenseArticleBean...");
        ContentValues cv = new ContentValues(5);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID, this.getExpenseId());
        cv.put(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID, this.getArticleId());
        cv.put(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST, this.getArticleCost());
        cv.put(DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY, this.getArticleQuantity());
        return cv;
    }

    @Override
    public String getObjectDescription() {
        return this.getArticle().getObjectDescription() + ": " + this.getArticleCost() + " for " + this.getArticleQuantity();
    }
}
