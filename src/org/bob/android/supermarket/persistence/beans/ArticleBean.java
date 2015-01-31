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
}