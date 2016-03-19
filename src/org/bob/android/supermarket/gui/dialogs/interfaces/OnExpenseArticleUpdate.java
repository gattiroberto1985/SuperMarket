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
import android.widget.Toast;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.GuiUtils;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;

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
            this.updateExpenseArticle(newExpBean); // the newExpBean shares the id with the old
            // ((ActivityExpenseList) this.frg.getActivity()).onExpenseSelected(newExpBean);
        }
        catch (SuperMarketException ex )
        {

        }
    }


    /**
     * This method exec the update or insert of a new expense in the database.
     *
     * @param eb the expense to update/create.
     *
     */
    private void updateExpense(ExpenseArticleBean eab)
    {
        //Logger.app_log("Trying to update expense with id '" + eb.getId() + "' . . .");
        try
        {
            // Not going on BeanFactory cause the expense will be updated manually/by exiting from the
            // expense!
            //BeanFactory.insertOrUpdateBean(eab);
            // Notifying change to the listview . . .
            this.frg.getListView().getAdapter().add(eab);
        }
        catch ( SuperMarketException ex )
        {
            Toast.makeText(this.frg.getActivity(), "ERRORE: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

    }    
}
