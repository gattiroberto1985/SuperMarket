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

package org.bob.android.supermarket.gui.dialogs.interfaces;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.GuiUtils;
import org.bob.android.supermarket.gui.adapters.AdapterExpenseArticles;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.utilities.Constants;

/**
 * Created by roberto.gatti on 07/03/2016.
 */
public class OnExpenseArticleUpdate implements DialogInterface.OnClickListener {

    private ExpenseArticleBean oldEab;

    private FragmentExpenseDetail frg;

    public OnExpenseArticleUpdate(FragmentExpenseDetail frg, ExpenseArticleBean eab)
    {
        this.frg    = frg;
        this.oldEab = eab;
    }


    /* ********************************************************************* */
    /*                         IMPLEMENTS METHODS                            */
    /* ********************************************************************* */

    @Override
    public void onClick(DialogInterface dialogInterface, int buttonPressed) {
        switch (buttonPressed) {
            case DialogInterface.BUTTON_POSITIVE: {
                this.handleEdit((AlertDialog) dialogInterface);
                break;
            }
            case DialogInterface.BUTTON_NEGATIVE:
                break;
            default:
                //Log.e(TAG, "Scelta non valida!");
        }
        return;
    }




    /* ********************************************************************* */
    /*                             CLASS METHODS                             */
    /* ********************************************************************* */


    /**
     * @param di
     */
    public void handleEdit(AlertDialog di)
    {
        try {

            ExpenseArticleBean newExpBean = GuiUtils.getExpenseArticleBeanFromView(di);
            if ( newExpBean.getExpenseId() == -1 )
                newExpBean.setExpenseId(this.frg.getSelectedExpenseId());
            //ExpenseArticleBean oldExpBean = (ExpenseArticleBean) di.findViewById(R.id.dialog_update_expense_article_view).getTag(R.id.KEY_VIEW_TAG_EXPENSE_ARTICLE);
            this.updateExpenseArticle(newExpBean); // the newExpBean shares the id with the old
            // ((ActivityExpenseList) this.frg.getActivity()).onExpenseSelected(newExpBean);
        }
        catch (SuperMarketException ex )
        {

        }
    }


    /**
     * This method exec the update or insert of a new expense in the list.
     *
     * @param eab the expense articled modified/inserted
     */
    private void updateExpenseArticle(ExpenseArticleBean eab)
    {
        AdapterExpenseArticles aea = (AdapterExpenseArticles) this.frg.getListView().getAdapter();

        int position2update = aea.getPosition(this.oldEab);

        if ( aea.getPosition( eab ) != -1 )
        {
            // Updating old expesne article
            aea.remove( this.oldEab );
            aea.insert(eab, position2update );
            // Aggiungo la differenza tra il nuovo costo e il vecchio costo
            this.frg.updateExpenseCost(eab.getFullCost() - this.oldEab.getFullCost());
        }
        else
        {
            // Adding new expense article
            aea.add(eab);
            this.frg.updateExpenseCost(eab.getFullCost());
        }
            //Toast.makeText(this.frg.getActivity(), R.string.TOAST_DOUBLE_EXPENSE_ARTICLE, Toast.LENGTH_LONG).show();


    }    
}
