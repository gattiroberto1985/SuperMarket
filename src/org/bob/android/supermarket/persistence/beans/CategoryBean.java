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
@DatabaseTable(tableName = DBConstants.TABLE_CATEGORIES_NAME)
public class CategoryBean extends BaseSMBean
{
    @DatabaseField(generatedId = true, index = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_CATEGORY_DESCRIPTION)
    private String description = Constants.EMPTY_STRING;

    @DatabaseField(index = true, columnName = DBConstants.FIELD_CATEGORY_APPLIES_TO)
    private char appliesTo = 'X';

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public CategoryBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param description
     */
    public CategoryBean(int id, String description, char appliesTo)
    {
        this.setId(id);
        this.setDescription(description);
        this.setAppliesTo(appliesTo);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public CategoryBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setDescription(cv.getAsString(DBConstants.FIELD_CATEGORY_DESCRIPTION));
        this.setAppliesTo(cv.getAsString(DBConstants.FIELD_CATEGORY_APPLIES_TO).charAt(0));
    }

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

    @Override
    public final void setId(int id) { this.id = id; }
    public final void setDescription(String description) { this.description = description; }
    public final void setAppliesTo(char appliesTo) { this.appliesTo = Character.toUpperCase(appliesTo); }

    public final int getId() { return this.id; }
    public final String getDescription() { return this.description; }
    public final char getAppliesTo() { return this.appliesTo; }




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
        Logger.dtb_log("Creating content values for CategoryBean...");
        ContentValues cv = new ContentValues(3);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_CATEGORY_APPLIES_TO, String.valueOf(this.getAppliesTo()));
        cv.put(DBConstants.FIELD_CATEGORY_DESCRIPTION, this.getDescription());
        return cv;
    }

}