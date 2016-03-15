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

package org.bob.android.supermarket.gui;

import android.app.AlertDialog;
import android.view.View;
import android.widget.*;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.persistence.beans.ShopBean;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.Utilities;

import java.util.Date;

/**
 * Created by roberto.gatti on 08/03/2016.
 */
public class GuiUtils {

    /**
     * Gets an expense bean from a view.
     *
     * @param view
     * @return
     */
    public static ExpenseBean getExpenseBeanFromView(View view) throws SuperMarketException
    {
        ExpenseBean eb;
        try
        {
            TextView tvId               = ( TextView )             view.findViewById(R.id.dialog_update_expense_expense_id);
            EditText etDate             = ( EditText )             view.findViewById(R.id.dialog_update_expense_et_date   );
            AutoCompleteTextView etShop = ( AutoCompleteTextView ) view.findViewById(R.id.dialog_update_expense_actv_shop );
            CheckBox             cbOpen = ( CheckBox )             view.findViewById(R.id.dialog_update_expense_flag_open );
            ShopBean sb = new ShopBean(-1, etShop.getText().toString());
            Date expDate = Utilities.checkDate(etDate.getText().toString());

            if ( tvId.getTag(R.id.KEY_VIEW_TAG_EXPENSE ) == null )
            {
                eb = new ExpenseBean(-1, expDate.getTime(), sb, -1.0);
            }
            else
            {
                // Expense already exists! Changing datas . . .
                eb = (ExpenseBean) tvId.getTag(R.id.KEY_VIEW_TAG_EXPENSE);
            }
            eb.setShop(sb);
            eb.setDate(expDate.getTime());
            return eb;
        }
        catch ( NullPointerException ex )
        {
            Logger.app_log("ERROR: shop or date not filled in!", Logger.Level.ERROR);
            Logger.app_log("      " + ex.getMessage() );
            throw new SuperMarketException("ERROR: shop or date not filled in: '" + ex.getMessage() + "'");
        }
        catch ( NumberFormatException ex )
        {
            Logger.app_log("ERROR: shop or date not filled in!", Logger.Level.ERROR);
            Logger.app_log("      " + ex.getMessage() );
            throw new SuperMarketException("ERROR: shop or date not filled in: '" + ex.getMessage() + "'");
        }
    }

    /**
     * Gets an expense bean from a view.
     *
     * @param view
     * @return
     */
    public static ExpenseBean getExpenseBeanFromView(AlertDialog view) throws SuperMarketException
    {
        ExpenseBean eb;
        try
        {
            TextView tvId               = ( TextView )             view.findViewById(R.id.dialog_update_expense_expense_id);
            EditText etDate             = ( EditText )             view.findViewById(R.id.dialog_update_expense_et_date   );
            AutoCompleteTextView etShop = ( AutoCompleteTextView ) view.findViewById(R.id.dialog_update_expense_actv_shop );
            CheckBox             cbOpen = ( CheckBox )             view.findViewById(R.id.dialog_update_expense_flag_open );
            ShopBean sb = new ShopBean(-1, etShop.getText().toString());
            Date expDate = Utilities.checkDate(etDate.getText().toString());

            if ( tvId.getTag(R.id.KEY_VIEW_TAG_EXPENSE ) == null )
            {
                eb = new ExpenseBean(-1, expDate.getTime(), sb, -1.0);
            }
            else
            {
                // Expense already exists! Changing datas . . .
                eb = (ExpenseBean) tvId.getTag(R.id.KEY_VIEW_TAG_EXPENSE);
            }
            eb.setShop(sb);
            eb.setDate(expDate.getTime());
            return eb;
        }
        catch ( NullPointerException ex )
        {
            Logger.app_log("ERROR: shop or date not filled in!", Logger.Level.ERROR);
            Logger.app_log("      " + ex.getMessage() );
            throw new SuperMarketException("ERROR: shop or date not filled in: '" + ex.getMessage() + "'");
        }
        catch ( NumberFormatException ex )
        {
            Logger.app_log("ERROR: shop or date not filled in!", Logger.Level.ERROR);
            Logger.app_log("      " + ex.getMessage() );
            throw new SuperMarketException("ERROR: shop or date not filled in: '" + ex.getMessage() + "'");
        }
    }
}
