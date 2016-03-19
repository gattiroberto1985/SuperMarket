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

package org.bob.android.supermarket.gui.dialogs;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.dialogs.interfaces.OnExpenseArticleUpdate;
import org.bob.android.supermarket.gui.dialogs.interfaces.OnExpenseDelete;
import org.bob.android.supermarket.gui.dialogs.interfaces.OnExpenseUpdate;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseList;
import org.bob.android.supermarket.gui.watchers.DateTextWatcher;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;

/**
 *
 * Dialog factory class. This class expose all the methods to create
 * and handle response from all the dialog of the application.
 *
 * Created by roberto on 25/01/15.
 */
public class DialogFactory {


    public static final AlertDialog updateExpenseArticleDialog(Fragment frg, ExpenseArticleBean eab)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(frg.getActivity());
        // Getting layout inflater and creating view with correct layout . . .
        LayoutInflater inflater = frg.getActivity().getLayoutInflater();
        // Creating view for the dialog . . .
        View dialogView = inflater.inflate(R.layout.dialog_update_expense_item, null);
        // Setting views . . .
        AutoCompleteTextView actvCategory = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_article_category);
        AutoCompleteTextView actvBrand    = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_article_brand);
        AutoCompleteTextView actvArticle  = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_article_article);
        EditText             etCost       = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_article_cost);
        EditText             etQnty       = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_article_quantity);

        // Checking if expense article is to modify . . .
        if ( eab != null )
        {
            Logger.app_log("Editing article! Setting values of fields based on selected item. . .");
            actvArticle .setText(eab.getArticle()              .getDescription());
            actvCategory.setText(eab.getArticle().getCategory().getDescription());
            actvBrand   .setText(eab.getArticle().getBrand()   .getDescription());
            etCost      .setText(String.valueOf(eab.getArticleCost()    )       );
            etQnty      .setText(String.valueOf(eab.getArticleQuantity())       );

            actvArticle.setTag(R.id.KEY_VIEW_TAG_ARTICLE, eab.getArticle());
            actvCategory.setTag(R.id.KEY_VIEW_TAG_CATEGORY, eab.getArticle().getCategory());
            actvBrand.setTag(R.id.KEY_VIEW_TAG_BRAND, eab.getArticle().getBrand());
            dialogView.setTag(R.id.KEY_VIEW_TAG_EXPENSE_ARTICLE, eab);

        }
        // Setting alert dialog by cascade calling of 'set' methods . . .
        OnExpenseArticleUpdate oeau = new OnExpenseArticleUpdate( (FragmentExpenseDetail) frg, eab);
        builder
                // Setting view . . .
                .setView(dialogView)
                        // Setting message . . .
                .setMessage(R.string.DIALOG_UPDATE_EXPENSE_ARTICLE_TEXT)
                        // Setting title . . .
                .setTitle(R.string.DIALOG_UPDATE_EXPENSE_ARTICLE_TITLE)
                        // Setting OK button . . .
                .setPositiveButton(android.R.string.ok, oeau)
                        // Setting KO button . . .
                .setNegativeButton(android.R.string.cancel, oeau);

        return builder.create();
    }

    /**
     * Shows a dialog for create/update an expense selected.
     *
     * @param frg the parent fragment
     * @return a dialog for create/update an expense
     */
    public static final AlertDialog updateExpenseDialog(Fragment frg, ExpenseBean expense)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(frg.getActivity());
        // Getting layout inflater and creating view with correct layout . . .
        LayoutInflater inflater = frg.getActivity().getLayoutInflater();
        // Creating view for the dialog . . .
        View dialogView = inflater.inflate(R.layout.dialog_update_expense, null);
        AutoCompleteTextView actvShop = (AutoCompleteTextView) dialogView.findViewById(R.id.dialog_update_expense_actv_shop);
        EditText date = (EditText) dialogView.findViewById(R.id.dialog_update_expense_et_date);
        date.addTextChangedListener(new DateTextWatcher(date));
        // Checking if expense is to modify . . .
        if ( expense != null )
        {
            Logger.app_log("Editing expense! Setting values of fields based on selected expense . . .");
            actvShop.setText(expense.getShop().getDescription());
            date.setText(expense.getFormattedDate().toString());
        }
        // Setting alert dialog by cascade calling of 'set' methods . . .
        OnExpenseUpdate oeu = new OnExpenseUpdate( (FragmentExpenseList) frg, expense);
        builder
                // Setting view . . .
                .setView(dialogView)
                // Setting message . . .
                .setMessage(R.string.DIALOG_ADD_EXPENSE_MESSAGE)
                // Setting title . . .
                .setTitle(R.string.DIALOG_ADD_EXPENSE_TITLE)
                        // Setting OK button . . .
                .setPositiveButton(android.R.string.ok, oeu)
                        // Setting KO button . . .
                .setNegativeButton(android.R.string.cancel, oeu);

        return builder.create();
    }


    public static final AlertDialog deleteExpenseDialog(Fragment frg, ExpenseBean expense)
    {

        // Checking if expense is to delete . . .
        if ( expense == null )
        {
            Logger.app_log("No expense to delete!");
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(frg.getActivity());
        // Setting alert dialog by cascade calling of 'set' methods . . .
        OnExpenseDelete oed = new OnExpenseDelete( (FragmentExpenseList) frg, expense);
        builder
                        // Setting message . . .
                .setMessage(R.string.DIALOG_DELETE_EXPENSE_MESSAGE)
                        // Setting title . . .
                .setTitle(R.string.DIALOG_DELETE_EXPENSE_TITLE)
                        // Setting OK button . . .
                .setPositiveButton(android.R.string.ok, oed)
                        // Setting KO button . . .
                .setNegativeButton(android.R.string.cancel, oed);

        return builder.create();
    }

}
