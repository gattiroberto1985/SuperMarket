package org.bob.android.supermarket.test;

import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.*;
import org.bob.android.supermarket.utilities.DBConstants;
import org.bob.android.supermarket.utilities.Utilities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by roberto on 25/01/15.
 */
public class TestPersistence
{

    public static ArrayList<CategoryBean> categories = new ArrayList<CategoryBean>(10);
    public static ArrayList<ShopBean> shops = new ArrayList<ShopBean>(10);
    public static ArrayList<BrandBean> brands = new ArrayList<BrandBean>(10);
    public static ArrayList<ArticleBean> articles = new ArrayList<ArticleBean>(10);
    public static ArrayList<ExpenseBean> expense = new ArrayList<ExpenseBean>(20);

    public static void createTestDatas()
    {
        createCategories();
        createBrands();
        createShops();
        createArticles();
        createExpenses();
    }

    public static void createCategories()
    {
        Logger.tst_log("Creating categories");
        for ( int i = 0; i < 10; i++ )
        {
            CategoryBean object = new CategoryBean(i + 1, "Categoria " + ( i + 1 ), 'A' );
            Logger.tst_log("|--> Creating category '" + object.toString() + "' on database...");
            ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_CATEGORIES_CONTENT, object.getContentValues());
            categories.add(object);
        }
        Logger.tst_log("Categories created!");
    }

    public static void createBrands()
    {
        Logger.tst_log("Creating brands");
        for ( int i = 0; i < 10; i++ )
        {
            BrandBean object = new BrandBean(i + 1, "Brand " + ( i + 1 ) );
            Logger.tst_log("|--> Creating brand '" + object.toString() + "' on database...");
            ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_BRANDS_CONTENT, object.getContentValues());
            brands.add(object);
        }
        Logger.tst_log("Brands created!");
    }

    public static void createShops()
    {
        Logger.tst_log("Creating shops");
        for ( int i = 0; i < 10; i++ )
        {
            ShopBean object = new ShopBean(i + 1, "Negozio " + ( i + 1 ));
            Logger.tst_log("|--> Creating shop '" + object.toString() + "' on database...");
            ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_CATEGORIES_CONTENT, object.getContentValues());
            shops.add(object);
        }
        Logger.tst_log("shops created!");
    }


    public static void createArticles()
    {
        Logger.tst_log("Creating articles");
        for ( int i = 0; i < 10; i++ )
        {
            ArticleBean object = new ArticleBean(i + 1,
                    brands.get(Utilities.randInt(0,9)),
                    categories.get(Utilities.randInt(0,9)),
                    "Articolo " + (i+1) );
            Logger.tst_log("|--> Creating shop '" + object.toString() + "' on database...");
            ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_ARTICLES_CONTENT, object.getContentValues());
            if ( articles.indexOf(object) < 0 ) articles.add(object);
            else i--;
        }
        Logger.tst_log("articles created!");
    }

    public static void createExpenses()
    {
        Logger.tst_log("Creating expenses");
        int totalIndex = 1;
        for ( int i = 0; i < 20; i++ )
        {
            int objectInList = Utilities.randInt(0,9);
            ArrayList<ExpenseArticleBean> exlist = new ArrayList<ExpenseArticleBean>(objectInList);
            ExpenseBean eb = new ExpenseBean(i, (new Date()).getTime(), shops.get(objectInList), null);
            ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_EXPENSES_CONTENT, eb.getContentValues());
            for ( int j = 0; j < objectInList; j++ )
            {
                ExpenseArticleBean eab = new ExpenseArticleBean(
                        totalIndex++,
                        eb,
                        articles.get(Utilities.randInt(0, articles.size()) ),
                        (double) Utilities.randInt(1,100) / (double) Utilities.randInt(1,20),
                        Utilities.randInt(1,6)
                );
                eb.addExpenseItem(eab);
                ApplicationSM.getInstance().getContentResolver().insert(DBConstants.URI_EXPENSE_ARTICLES_CONTENT, eab.getContentValues());
            }
            Logger.tst_log("Expense created");
        }
        Logger.tst_log("articles created!");
    }
}
