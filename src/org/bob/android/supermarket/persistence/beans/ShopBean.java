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
@DatabaseTable(tableName = DBConstants.TABLE_SHOPS_NAME)
public class ShopBean extends BaseSMBean
{

    @DatabaseField(index = true, generatedId = true, columnName = DBConstants.FIELD_DEFAULT_ID)
    private int id = Constants.DEFAULT_ID_VALUE;

    @DatabaseField(columnName = DBConstants.FIELD_SHOP_DESCRIPTION)
    private String description = Constants.EMPTY_STRING;

    /* ********************************************************************* */
    /*                             CONSTRUCTORS                              */
    /* ********************************************************************* */

    /**
     * Costruttore vuoto.
     */
    public ShopBean() { }

    /**
     * Costruttore full.
     * @param id
     * @param description
     */
    public ShopBean(int id, String description)
    {
        this.setId(id);
        this.setDescription(description);
    }

    /**
     * Costruttore per il beanfactory.
     * @param cv
     */
    public ShopBean(ContentValues cv)
    {
        this.setId(cv.getAsInteger(DBConstants.FIELD_DEFAULT_ID));
        this.setDescription(cv.getAsString(DBConstants.FIELD_SHOP_DESCRIPTION));
    }

    /* ********************************************************************* */
    /*                       GETTER AND SETTER METHODS                       */
    /* ********************************************************************* */

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
        Logger.dtb_log("Creating content values for ShopBean...");
        ContentValues cv = new ContentValues(2);
        cv.put(DBConstants.FIELD_DEFAULT_ID, this.getId());
        cv.put(DBConstants.FIELD_SHOP_DESCRIPTION, this.getDescription());
        return cv;
    }

}
