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

package org.bob.android.supermarket.utilities;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;

import java.util.Arrays;

/**
 * Created by roberto.gatti on 22/01/2015.
 */
public class DBConstants
{

    public final static String AUTHORITY     = "org.bob.android.app.supermarket";
    public final static String DATABASE_NAME = "supermarket_v2.db";
    public final static int DATABASE_VERSION = 3;


    /* ********************************************************************* */
    /*                          TABLE DEFINITIONS                            */
    /* ********************************************************************* */

    public final static String TABLE_CATEGORIES_NAME       = "categories";
    public final static String TABLE_SHOPS_NAME            = "shops";
    public final static String TABLE_BRANDS_NAME           = "brands";
    public final static String TABLE_ARTICLES_NAME         = "articles";
    public final static String TABLE_EXPENSES_NAME         = "expenses";
    public final static String TABLE_EXPENSE_ARTICLES_NAME = "expense_articles";

    /* DEFAULT DATABASE VALUES */
    public final static String FIELD_DEFAULT_ID       = "_id";
    public final static String FIELD_DEFAULT_TMST_INS = "tmst_ins";
    public final static String FIELD_DEFAULT_TMST_UPD = "tmst_upd";

    /* CATEGORIES TABLE */
    public final static String FIELD_CATEGORY_DESCRIPTION = "c_description";
    public final static String FIELD_CATEGORY_APPLIES_TO  = "applies_to";

    /* SHOPS TABLE */
    public final static String FIELD_SHOP_DESCRIPTION = "s_description";

    /* BRANDS TABLE */
    public final static String FIELD_BRAND_DESCRIPTION = "b_description";

    /* ARTICLES TABLE */
    public final static String FIELD_ARTICLE_BRAND_ID    = "brand_id";
    public final static String FIELD_ARTICLE_CATEGORY_ID = "category_id";
    public final static String FIELD_ARTICLE_DESCRIPTION = "a_description";

    /* EXPENSES TABLE */
    public final static String FIELD_EXPENSE_SHOP_ID = "shop_id";
    public final static String FIELD_EXPENSE_DATE    = "date";
    public final static String FIELD_EXPENSE_COST    = "cost";

    /* EXPENSE_ARTICLES TABLE */
    public final static String FIELD_EXPENSE_ARTICLE_EXPENSE_ID       = "expense_id";
    public final static String FIELD_EXPENSE_ARTICLE_ARTICLE_ID       = "article_id";
    public final static String FIELD_EXPENSE_ARTICLE_ARTICLE_COST     = "single_cost";
    public final static String FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY = "quantity";


    /* TABLE FULL PROJECTION */

    public final static String[] PROJECTION_EXPENSE_BEAN =
            new String[] { FIELD_DEFAULT_ID, FIELD_EXPENSE_DATE, FIELD_EXPENSE_SHOP_ID, FIELD_EXPENSE_COST };

    public final static String[] PROJECTION_SHOP_BEAN    =
            new String[] { FIELD_DEFAULT_ID, FIELD_SHOP_DESCRIPTION };

    public final static String[] PROJECTION_BRAND_BEAN    =
            new String[] { FIELD_DEFAULT_ID, FIELD_BRAND_DESCRIPTION };

    public final static String[] PROJECTION_CATEGORY_BEAN    =
            new String[] { FIELD_DEFAULT_ID, FIELD_CATEGORY_DESCRIPTION, FIELD_CATEGORY_APPLIES_TO};

    public final static String[] PROJECTION_ARTICLE_BEAN    =
            new String[] { FIELD_DEFAULT_ID, FIELD_ARTICLE_BRAND_ID, FIELD_ARTICLE_CATEGORY_ID, FIELD_ARTICLE_DESCRIPTION };

    public final static String[] PROJECTION_EXPENSE_ARTICLE_BEAN    =
            new String[] { FIELD_DEFAULT_ID, FIELD_EXPENSE_ARTICLE_ARTICLE_ID,
                    FIELD_EXPENSE_ARTICLE_EXPENSE_ID,
                    FIELD_EXPENSE_ARTICLE_ARTICLE_COST,
                    FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY };

    /* VIEW PROJECTION */
    // TODO: definire vista relativa a questa projection
    public final static String[] PROJECTION_EXPENSE_LIST = new String[] {
                FIELD_DEFAULT_ID,
                FIELD_EXPENSE_DATE,
                FIELD_EXPENSE_COST,
                FIELD_EXPENSE_SHOP_ID,
                FIELD_SHOP_DESCRIPTION };


    public final static String[] PROJECTION_EXPENSE_ARTICLE_LIST = new String[] {
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
    };

    /* ********************************************************************* */
    /*                            VIEW DEFINITIONS                           */
    /* ********************************************************************* */

    /**
     * View with the complete expense + shop datas for each expense.
     */
    public static final String VIEW_EXPENSE_SHOP_NAME = "expense_shop";
    public static final String VIEW_EXPENSE_SHOP = "SELECT e." + DBConstants.FIELD_DEFAULT_ID + ", e." + DBConstants.FIELD_EXPENSE_DATE  +
                                                   ", e." + DBConstants.FIELD_EXPENSE_COST + ", e." + DBConstants.FIELD_EXPENSE_SHOP_ID  +
                                                   ", s." + DBConstants.FIELD_SHOP_DESCRIPTION                                           +
                                                   " FROM " + DBConstants.TABLE_EXPENSES_NAME + " e JOIN " + DBConstants.TABLE_SHOPS_NAME + " s"  +
                                                   " ON e." + DBConstants.FIELD_EXPENSE_SHOP_ID + " = s." + DBConstants.FIELD_DEFAULT_ID;


    /**
     * View with the full data of the articles (description, brand and category) of the expenses.
     */
    public static final String VIEW_EXPENSE_ARTICLE_FULL_DETAILS_NAME = "expense_article_details";
    public static final String VIEW_EXPENSE_ARTICLE_FULL_DETAILS =
        "SELECT " +
            "ea." + DBConstants.FIELD_DEFAULT_ID                       + ", " +
            "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID       + ", " +
            "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID       + ", " +
            "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST     + ", " +
            "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY + ", " +
            "a."  + DBConstants.FIELD_ARTICLE_DESCRIPTION              + ", " +
            "a."  + DBConstants.FIELD_ARTICLE_BRAND_ID                 + ", " +
            "b."  + DBConstants.FIELD_BRAND_DESCRIPTION                + ", " +
            "a."  + DBConstants.FIELD_ARTICLE_CATEGORY_ID              + ", " +
            "c."  + DBConstants.FIELD_CATEGORY_DESCRIPTION             + " "  +
        "FROM " + DBConstants.TABLE_EXPENSE_ARTICLES_NAME + " ea " +
        "JOIN " + DBConstants.TABLE_ARTICLES_NAME   + " a " +
            "ON ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID + " = a." + DBConstants.FIELD_DEFAULT_ID + " " +
        "JOIN " + DBConstants.TABLE_BRANDS_NAME     + " b " +
            "ON a."  + DBConstants.FIELD_ARTICLE_BRAND_ID           + " = b." + DBConstants.FIELD_DEFAULT_ID + " " +
        "JOIN " + DBConstants.TABLE_CATEGORIES_NAME + " c " +
            "ON a."  + DBConstants.FIELD_ARTICLE_CATEGORY_ID        + " = c." + DBConstants.FIELD_DEFAULT_ID;

    public static final String VIEW_ARTICLE_PRICE_BY_EXPENSE_NAME = "article_prices_trend";
    public static final String VIEW_ARTICLE_PRICE_BY_EXPENSE      =
        "SELECT " +
            "ea." + DBConstants.FIELD_DEFAULT_ID                       + ", " +
         // "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_ID       + ", " +
            "ea." + DBConstants.FIELD_EXPENSE_ARTICLE_ARTICLE_COST     + ", " +
            "s."  + DBConstants.FIELD_SHOP_DESCRIPTION                 + ", " +
            "e."  + DBConstants.FIELD_EXPENSE_DATE                     + " " +
        "FROM " + DBConstants.TABLE_EXPENSE_ARTICLES_NAME + " ea " +
        "JOIN " + DBConstants.TABLE_EXPENSES_NAME + " e " +
            "ON e." + DBConstants.FIELD_DEFAULT_ID + " = ea." + DBConstants.FIELD_EXPENSE_ARTICLE_EXPENSE_ID + " " +
        "JOIN " + DBConstants.TABLE_SHOPS_NAME + " s " +
            "ON s." + DBConstants.FIELD_DEFAULT_ID + " = e." + DBConstants.FIELD_EXPENSE_SHOP_ID;

    /* ********************************************************************* */
    /*                     CONTENT PROVIDER CONSTANTS                        */
    /* ********************************************************************* */

    /* Uri string, Uri and uri indicator */
    // ARTICLES
    public final static String STR_URI_ARTICLES_CONTENT              = DBConstants.TABLE_ARTICLES_NAME;
    //public final static String STR_URI_ARTICLES_DISTINCT_CATEGORY_ID = DBConstants.TABLE_ARTICLES_NAME + ".CD";
    //public final static String STR_URI_ARTICLES_DISTINCT_BRAND_ID    = DBConstants.TABLE_ARTICLES_NAME + ".BD";
    // BRANDS
    public final static String STR_URI_BRANDS_CONTENT                = DBConstants.TABLE_BRANDS_NAME;
    // CATEGORIES
    public final static String STR_URI_CATEGORIES_CONTENT            = DBConstants.TABLE_CATEGORIES_NAME;
    // SHOPS
    public final static String STR_URI_SHOPS_CONTENT                 = DBConstants.TABLE_SHOPS_NAME;
    // EXPENSES
    public final static String STR_URI_EXPENSES_CONTENT              = DBConstants.TABLE_EXPENSES_NAME;
    // EXPENSE_ARTICLES
    public final static String STR_URI_EXPENSE_ARTICLES_CONTENT      = DBConstants.TABLE_EXPENSE_ARTICLES_NAME;
    // VIEWS
    public final static String STR_URI_VIEW_EXPENSE_SHOP             = DBConstants.VIEW_EXPENSE_SHOP_NAME;
    // Get all articles from an expense
    public final static String STR_URI_VIEW_EXPENSE_ARTICLES         = DBConstants.VIEW_EXPENSE_ARTICLE_FULL_DETAILS_NAME;
    // Get all expenses data for a single article
    public final static String STR_URI_VIEW_ARTICLE_PRICE_TREND      = DBConstants.VIEW_ARTICLE_PRICE_BY_EXPENSE_NAME;

    /* Uri */
    public final static Uri URI_ARTICLES_CONTENT                = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_ARTICLES_CONTENT                );
    public final static Uri URI_BRANDS_CONTENT                  = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_BRANDS_CONTENT                  );
    public final static Uri URI_CATEGORIES_CONTENT              = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_CATEGORIES_CONTENT              );
    public final static Uri URI_SHOPS_CONTENT                   = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_SHOPS_CONTENT                   );
    public final static Uri URI_EXPENSES_CONTENT                = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_EXPENSES_CONTENT                );
    public final static Uri URI_EXPENSE_ARTICLES_CONTENT        = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_EXPENSE_ARTICLES_CONTENT        );
  //public final static Uri URI_ARTICLES_DISTINCT_CATEGORY_ID   = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_ARTICLES_DISTINCT_CATEGORY_ID   );
  //public final static Uri URI_ARTICLES_DISTINCT_BRAND_ID      = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_ARTICLES_DISTINCT_BRAND_ID      );
    public final static Uri URI_VIEW_EXPENSE_SHOP               = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_EXPENSE_SHOP               );
    public final static Uri URI_VIEW_EXPENSE_ARTICLES           = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_EXPENSE_ARTICLES           );
    public final static Uri URI_VIEW_ARTICLE_PRICE_TREND        = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_ARTICLE_PRICE_TREND        );
    // Adding uri for specific id of expense in the view expense-shop
    public final static Uri URI_VIEW_EXPENSE_SHOP_BY_EXP        = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_EXPENSE_SHOP        + "/#" );
    // Adding uri for specific id of expense in the full expense article list
    public final static Uri URI_VIEW_EXPENSE_ARTICLES_BY_EXP    = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_EXPENSE_ARTICLES    + "/#" );
    // Adding uri for specific id of article in the price trend view
    public final static Uri URI_VIEW_ARTICLE_PRICE_TREND_BY_ART = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + DBConstants.STR_URI_VIEW_ARTICLE_PRICE_TREND + "/#" );

    /* Uri indicators */
    // Tables
    public final static int URI_INDICATOR_ARTICLES_COLLECTION           = 10;
    public final static int URI_INDICATOR_ARTICLES                      = 11;
  //public final static int URI_INDICATOR_ARTICLES_DISTINCT_CATEGORY_ID = 12;
  //public final static int URI_INDICATOR_ARTICLES_DISTINCT_BRAND_ID    = 13;
    public final static int URI_INDICATOR_BRANDS_COLLECTION             = 20;
    public final static int URI_INDICATOR_BRANDS                        = 21;
    public final static int URI_INDICATOR_CATEGORIES_COLLECTION         = 30;
    public final static int URI_INDICATOR_CATEGORIES                    = 31;
  //public final static int URI_INDICATOR_CATEGORIES_DISTINCT_APPLY_TO  = 32;
    public final static int URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION   = 40;
    public final static int URI_INDICATOR_EXPENSE_ARTICLES              = 41;
    public final static int URI_INDICATOR_EXPENSES_COLLECTION           = 50;
    public final static int URI_INDICATOR_EXPENSES                      = 51;
    public final static int URI_INDICATOR_SHOPS_COLLECTION              = 60;
    public final static int URI_INDICATOR_SHOPS                         = 61;
    // Views
    public final static int URI_INDICATOR_VIEW_EXPENSE_SHOP               = 99;
    public final static int URI_INDICATOR_VIEW_EXPENSE_ARTICLES           = 98;
    public final static int URI_INDICATOR_VIEW_ARTICLE_PRICE_TREND        = 97;
    public final static int URI_INDICATOR_VIEW_EXPENSE_SHOP_BY_EXP        = 96;
    public final static int URI_INDICATOR_VIEW_EXPENSE_ARTICLES_BY_EXP    = 95;
    public final static int URI_INDICATOR_VIEW_ARTICLE_PRICE_TREND_BY_ART = 94;
    /* Content types */
    public final static String CONTENT_TYPE_ARTICLE              = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_ARTICLES_NAME;
    public final static String CONTENT_ITEM_TYPE_ARTICLE         = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_ARTICLES_NAME + ".element";
    public final static String CONTENT_TYPE_BRAND                = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_BRANDS_NAME;
    public final static String CONTENT_ITEM_TYPE_BRAND           = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_BRANDS_NAME + ".element";
    public final static String CONTENT_TYPE_CATEGORY             = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_CATEGORIES_NAME;
    public final static String CONTENT_ITEM_TYPE_CATEGORY        = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_CATEGORIES_NAME + ".element";
    public final static String CONTENT_TYPE_EXPENSE_ARTICLE      = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_EXPENSE_ARTICLES_NAME;
    public final static String CONTENT_ITEM_TYPE_EXPENSE_ARTICLE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSE_ARTICLES_NAME + ".element";
    public final static String CONTENT_TYPE_EXPENSE              = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_EXPENSES_NAME;
    public final static String CONTENT_ITEM_TYPE_EXPENSE         = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSES_NAME + ".element";
    public final static String CONTENT_TYPE_SHOP                 = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + TABLE_SHOPS_NAME;
    public final static String CONTENT_ITEM_TYPE_SHOP            = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_SHOPS_NAME + ".element";
    public final static String CONTENT_JOIN_SHOP_ITEM_TYPE       = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSES_NAME +"_" + TABLE_SHOPS_NAME + ".element";


    /**
     * Uri matcher per il controllo dell'uri e quindi degli oggetti da recuperare.
     */
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * Blocco statico di inizializzazione risorse generali della classe.
     */
    static
    {
        // Uri di tabelle
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_ARTICLES_CONTENT               , DBConstants.URI_INDICATOR_ARTICLES_COLLECTION            );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_BRANDS_CONTENT                 , DBConstants.URI_INDICATOR_BRANDS_COLLECTION              );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_CATEGORIES_CONTENT             , DBConstants.URI_INDICATOR_CATEGORIES_COLLECTION          );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_EXPENSE_ARTICLES_CONTENT       , DBConstants.URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION    );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_EXPENSES_CONTENT               , DBConstants.URI_INDICATOR_EXPENSES_COLLECTION            );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_SHOPS_CONTENT                  , DBConstants.URI_INDICATOR_SHOPS_COLLECTION               );
        // Uri viste
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_EXPENSE_ARTICLES          , DBConstants.URI_INDICATOR_VIEW_EXPENSE_ARTICLES          );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_EXPENSE_SHOP              , DBConstants.URI_INDICATOR_VIEW_EXPENSE_SHOP              );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_ARTICLE_PRICE_TREND       , DBConstants.URI_INDICATOR_VIEW_ARTICLE_PRICE_TREND       );
        // Uri di singola riga su tabelle
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_ARTICLES_CONTENT + "/#"        , DBConstants.URI_INDICATOR_ARTICLES                       );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_BRANDS_CONTENT + "/#"          , DBConstants.URI_INDICATOR_BRANDS                         );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_CATEGORIES_CONTENT + "/#"      , DBConstants.URI_INDICATOR_CATEGORIES                     );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_EXPENSE_ARTICLES_CONTENT + "/#", DBConstants.URI_INDICATOR_EXPENSE_ARTICLES               );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_EXPENSES_CONTENT + "/#"        , DBConstants.URI_INDICATOR_EXPENSES                       );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_SHOPS_CONTENT + "/#"           , DBConstants.URI_INDICATOR_SHOPS                          );
        // Uri di parzializzazione viste
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_EXPENSE_SHOP        + "/#", DBConstants.URI_INDICATOR_VIEW_EXPENSE_SHOP_BY_EXP       );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_EXPENSE_ARTICLES    + "/#", DBConstants.URI_INDICATOR_VIEW_EXPENSE_ARTICLES_BY_EXP   );
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.STR_URI_VIEW_ARTICLE_PRICE_TREND + "/#", DBConstants.URI_INDICATOR_VIEW_ARTICLE_PRICE_TREND_BY_ART);

    }

}
