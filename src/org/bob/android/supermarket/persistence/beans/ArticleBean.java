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
@DatabaseTable(tableName = DBConstants.TABLE_ARTICLES_NAME)
public class ArticleBean extends BaseSMBean
{
    @DatabaseField(generatedId = true, index = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(index = true, columnName = DBConstants.FIELD_ARTICLE_BRAND_ID)
    private int brandId = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(index = true, columnName = DBConstants.FIELD_ARTICLE_CATEGORY_ID)
    private int categoryId = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_ARTICLE_DESCRIPTION)
    private String description = Constants.EMPTY_STRING;

    private BrandBean brand;

    private CategoryBean category;

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public ArticleBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param description
     */
    public ArticleBean(int id, BrandBean b, CategoryBean c, String description)
    {
        this.setId(id);
        this.setDescription(description);
        this.setBrand(b);
        this.setCategory(c);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public ArticleBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setDescription(cv.getAsString(DBConstants.FIELD_ARTICLE_DESCRIPTION));
        this.setBrand(new BrandBean(cv.getAsInteger(DBConstants.FIELD_ARTICLE_BRAND_ID), Constants.DEFAULT_DESCRIPTION));
        this.setCategory(new CategoryBean(cv.getAsInteger(DBConstants.FIELD_ARTICLE_CATEGORY_ID), Constants.DEFAULT_DESCRIPTION, Constants.DEFAULT_CHAR));
    }

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

    @Override
    public final void setId(int id) { this.id = id; }
    public final void setDescription(String description) { this.description = description; }
    public final void setBrand(BrandBean b) { this.brand = b; this.brandId = this.getBrand().getId(); }
    public final void setCategory(CategoryBean c) { this.category = c; this.categoryId = this.getCategory().getId(); }

    public final int getId() { return this.id; }
    public final String getDescription() { return this.description; }
    public final BrandBean getBrand() { return this.brand; }
    public final CategoryBean getCategory() { return this.category; }
    public final int getBrandId() { return this.brandId; }
    public final int getCategoryId() { return this.categoryId; }



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
        Logger.dtb_log("Creating content values for ArticleBean...");
        ContentValues cv = new ContentValues(4);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_ARTICLE_BRAND_ID, this.getBrandId());
        cv.put(DBConstants.FIELD_ARTICLE_CATEGORY_ID, this.getCategoryId());
        cv.put(DBConstants.FIELD_ARTICLE_DESCRIPTION, this.getDescription());
        return cv;
    }

    @Override
    public String getObjectDescription() {
        return this.getDescription();
    }

    @Override
    public boolean equals(Object o) {

        if ( o == null )
            return false;

        if ( !( o instanceof ArticleBean ) )
            return false;

        ArticleBean ab = ( ArticleBean ) o;

        // Exiting with true if the ids are equals and different from -1
        if ( ab.getId() == this.getId() && this.getId() != -1 )
            return true;

        if (
            this.getBrand()   .equals( ab.getBrand()    ) &&
            this.getCategory().equals( ab.getCategory() ) &&
            this.getDescription().equals(ab.getDescription())
            )
                return true;

        return false;

    }
}