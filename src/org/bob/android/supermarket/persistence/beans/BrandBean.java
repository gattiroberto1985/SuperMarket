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

@DatabaseTable(tableName = DBConstants.TABLE_BRANDS_NAME)
public class BrandBean extends BaseSMBean
{

    @DatabaseField(generatedId = true, index = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_BRAND_DESCRIPTION)
    private String description = Constants.EMPTY_STRING;

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public BrandBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param description
     */
    public BrandBean(int id, String description)
    {
        this.setId(id);
        this.setDescription(description);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public BrandBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setDescription(cv.getAsString(DBConstants.FIELD_BRAND_DESCRIPTION));
    }    

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

    @Override
    public final void setId(int id) { this.id = id; }
    public final void setDescription(String description) { this.description = description; }

    public final int getId() { return this.id; }
    public final String getDescription() { return this.description; }


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
        Logger.dtb_log("Creating content values for BrandBean...");
        ContentValues cv = new ContentValues(2);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_BRAND_DESCRIPTION, this.getDescription());
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

        if ( !( o instanceof BrandBean ) )
            return false;

        BrandBean bb = (BrandBean) o;

        if ( bb.getId() == this.getId() && this.getId() != -1 )
            return true;

        if ( this.getDescription().equals(bb.getDescription()))
            return true;

        return false;
    }
}