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

    public final static String AUTHORITY = "org.bob.android.app.supermarket";
    public final static String DATABASE_NAME = "supermarket_v2.db";
    public final static int DATABASE_VERSION = 1;


    /* ********************************************************************* */
    /*                          TABLE DEFINITIONS                            */
    /* ********************************************************************* */

    public final static String TABLE_CATEGORIES_NAME = "categories";
    public final static String TABLE_SHOPS_NAME = "shops";
    public final static String TABLE_BRANDS_NAME = "brands";
    public final static String TABLE_ARTICLES_NAME = "articles";
    public final static String TABLE_EXPENSES_NAME = "expenses";
    public final static String TABLE_EXPENSE_ARTICLES_NAME = "expense_articles";

    /* DEFAULT DATABASE VALUES */
    public final static String FIELD_DEFAULT_ID = "_id";
    public final static String FIELD_DEFAULT_TMST_INS = "tmst_ins";
    public final static String FIELD_DEFAULT_TMST_UPD = "tmst_upd";

    /* CATEGORIES TABLE */
    public final static String FIELD_CATEGORY_DESCRIPTION = "c_description";
    public final static String FIELD_CATEGORY_APPLIES_TO = "applies_to";

    /* SHOPS TABLE */
    public final static String FIELD_SHOP_DESCRIPTION = "s_description";

    /* BRANDS TABLE */
    public final static String FIELD_BRAND_DESCRIPTION = "b_description";

    /* ARTICLES TABLE */
    public final static String FIELD_ARTICLE_BRAND_ID = "brand_id";
    public final static String FIELD_ARTICLE_CATEGORY_ID = "brand_id";
    public final static String FIELD_ARTICLE_DESCRIPTION = "a_description";

    /* EXPENSES TABLE */
    public final static String FIELD_EXPENSE_SHOP_ID = "shop_id";
    public final static String FIELD_EXPENSE_DATE = "date";
    public final static String FIELD_EXPENSE_COST = "cost";

    /* EXPENSE_ARTICLES TABLE */
    public final static String FIELD_EXPENSE_ARTICLE_EXPENSE_ID = "expense_id";
    public final static String FIELD_EXPENSE_ARTICLE_ARTICLE_ID = "article_id";
    public final static String FIELD_EXPENSE_ARTICLE_ARTICLE_COST = "single_cost";
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
                FIELD_DEFAULT_ID,                           // id della spesa
                FIELD_ARTICLE_CATEGORY_ID,                  // id della categoria articolo
                FIELD_CATEGORY_DESCRIPTION,                 // descrizione categoria
                FIELD_ARTICLE_BRAND_ID,                     // id del brand dell'articolo
                FIELD_BRAND_DESCRIPTION,                    // descrizione del brand
                FIELD_EXPENSE_ARTICLE_ARTICLE_COST,         // costo dell'articolo
                FIELD_EXPENSE_ARTICLE_ARTICLE_QUANTITY      // quantita' dell'articolo
    };

    /* ********************************************************************* */
    /*                     CONTENT PROVIDER CONSTANTS                        */
    /* ********************************************************************* */

    /* ARTICLES */
    // uri indicator
    public final static int URI_INDICATOR_ARTICLES_COLLECTION = 10;
    public final static int URI_INDICATOR_ARTICLES = 11;
    public final static int URI_INDICATOR_ARTICLES_DISTINCT_CATEGORY_ID = 12;
    public final static int URI_INDICATOR_ARTICLES_DISTINCT_BRAND_ID = 13;
    // uri
    public final static Uri URI_ARTICLES_CONTENT = Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_ARTICLES_NAME);
    public final static Uri URI_ARTICLES_DISTINCT_CATEGORY_ID = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ARTICLES_NAME + ".CD");
    public final static Uri URI_ARTICLES_DISTINCT_BRAND_ID = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ARTICLES_NAME + ".BD");
    // content type
    public final static String CONTENT_TYPE_ARTICLE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_ARTICLES_NAME;
    public final static String CONTENT_ITEM_TYPE_ARTICLE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_ARTICLES_NAME + ".element";


    /* BRANDS */
    // uri indicator
    public final static int URI_INDICATOR_BRANDS_COLLECTION = 20;
    public final static int URI_INDICATOR_BRANDS = 21;
    // uri
    public final static Uri URI_BRANDS_CONTENT = Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_BRANDS_NAME);
    // content type
    public final static String CONTENT_TYPE_BRAND = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_BRANDS_NAME;
    public final static String CONTENT_ITEM_TYPE_BRAND = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_BRANDS_NAME + ".element";


    /* CATEGORIES */
    // uri indicator
    public final static int URI_INDICATOR_CATEGORIES_COLLECTION= 30;
    public final static int URI_INDICATOR_CATEGORIES = 31;
    public final static int URI_INDICATOR_CATEGORIES_DISTINCT_APPLY_TO = 32;
    // uri
    public final static Uri URI_CATEGORIES_CONTENT= Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_CATEGORIES_NAME);
    // content type
    public final static String CONTENT_TYPE_CATEGORY = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_CATEGORIES_NAME;
    public final static String CONTENT_ITEM_TYPE_CATEGORY = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_CATEGORIES_NAME + ".element";


    /* EXPENSE_ARTICLES */
    // uri indicator
    public final static int URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION = 40;
    public final static int URI_INDICATOR_EXPENSE_ARTICLES = 41;
    // uri
    public final static Uri URI_EXPENSE_ARTICLES_CONTENT= Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_EXPENSE_ARTICLES_NAME);
    // content type
    public final static String CONTENT_TYPE_EXPENSE_ARTICLE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_EXPENSE_ARTICLES_NAME;
    public final static String CONTENT_ITEM_TYPE_EXPENSE_ARTICLE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSE_ARTICLES_NAME + ".element";


    /* EXPENSES */
    // uri indicator
    public final static int URI_INDICATOR_EXPENSES_COLLECTION = 50;
    public final static int URI_INDICATOR_EXPENSES = 51;
    public final static int URI_INDICATOR_EXPENSES_DISTINCT_DATE = 52;
    public final static int URI_INDICATOR_EXPENSES_DISTINCT_SHOP = 53;
    public final static int URI_INDICATOR_EXPENSES_JOIN_SHOP = 54;
    public final static int URI_INDICATOR_EXPENSES_JOIN_EXPENSE_ARTICLE = 55;
    // uri
    public final static Uri URI_EXPENSES_CONTENT = Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_EXPENSES_NAME);
    public final static Uri URI_JOIN_EXPENSE_SHOP = Uri.parse("content://" + AUTHORITY + "/" + TABLE_EXPENSES_NAME + "_" + TABLE_SHOPS_NAME);
    public final static Uri URI_JOIN_EXPENSE_ARTICLE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_EXPENSES_NAME + "_" + TABLE_ARTICLES_NAME);
    // content type
    public final static String CONTENT_TYPE_EXPENSE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_EXPENSES_NAME;
    public final static String CONTENT_ITEM_TYPE_EXPENSE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSES_NAME + ".element";


    /* SHOPS */
    public final static int URI_INDICATOR_SHOPS_COLLECTION = 60;
    public final static int URI_INDICATOR_SHOPS = 61;
    public final static Uri SHOPS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DBConstants.TABLE_SHOPS_NAME);
    public final static String CONTENT_TYPE_SHOP = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + TABLE_SHOPS_NAME;
    public final static String CONTENT_ITEM_TYPE_SHOP = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_SHOPS_NAME + ".element";
    public final static String CONTENT_JOIN_SHOP_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + TABLE_EXPENSES_NAME +"_" + TABLE_SHOPS_NAME + ".element";


    /**
     * Uri matcher per il controllo dell'uri e quindi degli oggetti da recuperare.
     */
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * Blocco statico di inizializzazione risorse generali della classe.
     */
    static
    {
        // Aggiungo gli uri relativi alle tabelle
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_ARTICLES_NAME, DBConstants.URI_INDICATOR_ARTICLES_COLLECTION);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_BRANDS_NAME, DBConstants.URI_INDICATOR_BRANDS_COLLECTION);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_CATEGORIES_NAME, DBConstants.URI_INDICATOR_CATEGORIES_COLLECTION);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSE_ARTICLES_NAME, DBConstants.URI_INDICATOR_EXPENSE_ARTICLES_COLLECTION);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSES_NAME, DBConstants.URI_INDICATOR_EXPENSES_COLLECTION);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_SHOPS_NAME, DBConstants.URI_INDICATOR_SHOPS_COLLECTION);

        // join tra expenses e expenseArticle
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSES_NAME + "_" + DBConstants.TABLE_EXPENSE_ARTICLES_NAME, DBConstants.URI_INDICATOR_EXPENSES_JOIN_EXPENSE_ARTICLE);
        // join tra expenses e shop
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSES_NAME + "_" + DBConstants.TABLE_SHOPS_NAME, DBConstants.URI_INDICATOR_EXPENSES_JOIN_SHOP);
        // join tra articles ed expenseArticle
        //sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_ARTICLES_NAME + "_" + DBConstants.TABLE_EXPENSE_ARTICLES_NAME, DBConstants.URI_INDICATOR_ARTICLES_JOIN_EXPENSE_ARTICLE);

        // Aggiungo gli uri relativi alle singole righe delle tabelle
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_ARTICLES_NAME + "/#", DBConstants.URI_INDICATOR_ARTICLES);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_BRANDS_NAME + "/#", DBConstants.URI_INDICATOR_BRANDS);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_CATEGORIES_NAME + "/#", DBConstants.URI_INDICATOR_CATEGORIES);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSE_ARTICLES_NAME + "/#", DBConstants.URI_INDICATOR_EXPENSE_ARTICLES);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_EXPENSES_NAME + "/#", DBConstants.URI_INDICATOR_EXPENSES);
        sURIMatcher.addURI(DBConstants.AUTHORITY, DBConstants.TABLE_SHOPS_NAME + "/#", DBConstants.URI_INDICATOR_SHOPS);

		/*// Aggiungo gli uri particolari di ciascuna tabella
		// tabella article: distinct per brand e per category
		sURIMatcher.addURI(DBConstants.AUTHORITY, ArticlePMD.ArticleTableMetaData.TABLE_NAME + ".BD", ArticlePMD.DISTINCT_BRAND_ID_URI_INDICATOR);
		sURIMatcher.addURI(DBConstants.AUTHORITY, ArticlePMD.ArticleTableMetaData.TABLE_NAME + ".CD", ArticlePMD.DISTINCT_CATEGORY_ID_URI_INDICATOR);
		sURIMatcher.addURI(DBConstants.AUTHORITY, ExpenseArticlePMD.ExpenseArticleTableMetaData.TABLE_NAME + ".PA", ExpenseArticlePMD.EXPENSE_ARTICLE_PARTIAL_INFO_URI_INDICATOR);

		// tabella category: distinct per apply_to
		sURIMatcher.addURI(DBConstants.AUTHORITY, CategoryPMD.CategoryTableMetaData.TABLE_NAME + ".CAT", CategoryPMD.DISTINCT_APPLY_TO_URI_INDICATOR);

		// Tabella expense: distinct per data e per negozio
		sURIMatcher.addURI(DBConstants.AUTHORITY, ExpensePMD.ExpenseTableMetaData.TABLE_NAME + ".ED", ExpensePMD.DISTINCT_DATE_URI_INDICATOR);
		sURIMatcher.addURI(DBConstants.AUTHORITY, ExpensePMD.ExpenseTableMetaData.TABLE_NAME + ".ES", ExpensePMD.DISTINCT_SHOP_URI_INDICATOR);
		*/
    }

}
