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