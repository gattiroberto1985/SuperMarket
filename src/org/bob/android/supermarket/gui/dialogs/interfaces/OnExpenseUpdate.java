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
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.Toast;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.GuiUtils;
import org.bob.android.supermarket.gui.activities.ActivityExpenseDetails;
import org.bob.android.supermarket.gui.activities.ActivityExpenseList;
import org.bob.android.supermarket.gui.dialogs.DialogFactory;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseDetail;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseList;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.persistence.beans.ShopBean;
import org.bob.android.supermarket.utilities.DBConstants;
import org.bob.android.supermarket.utilities.Utilities;

import java.util.List;

/**
 * Created by roberto.gatti on 07/03/2016.
 */
public class OnExpenseUpdate implements DialogInterface.OnClickListener {


    private ExpenseBean currentExpense;

    private Fragment frg;

    private boolean fullSave;

    private List<ExpenseArticleBean> removed;

    public OnExpenseUpdate(Fragment frg, ExpenseBean curExpense)
    {
        this.frg = frg;
        this.currentExpense = curExpense;
        this.fullSave = false;
        this.removed = null;
    }

    public OnExpenseUpdate(Fragment frg, ExpenseBean curExpense, boolean saveExpenseArticles, List<ExpenseArticleBean> removed)
    {
        this.frg = frg;
        this.currentExpense = curExpense;
        this.fullSave = saveExpenseArticles;
        this.removed = removed;
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
            {
                break;
            }
            case DialogInterface.BUTTON_NEUTRAL:
            {
                DialogFactory.deleteExpenseDialog(this.frg, this.currentExpense).show();

                break;
            }
            default:
                //Log.e(TAG, "Scelta non valida!");
        }
        if ( this.frg instanceof FragmentExpenseDetail )
        {
            FragmentExpenseDetail fg = (FragmentExpenseDetail) this.frg;
            if ( fg.getActivity() instanceof ActivityExpenseDetails )
                frg.getActivity().onBackPressed();
        }
        return;
    }




    /* ********************************************************************* */
    /*                             CLASS METHODS                             */
    /* ********************************************************************* */


    /**
     * Update/Create method for an expense. It checks if the new expense is
     * different from the old. Then, if different, it makes some basic control
     * on the new object and, finally, the method tries the create/update
     * on DB.
     * If the openAfterEdit flag is checked, then the expense is open in the
     * Detail fragment activity.
     *
     * @param di
     */
    public void handleEdit(AlertDialog di)
    {
        try {
            if ( ! this.fullSave )
                this.saveExpenseHeader(di);
            else
                this.saveFullExpense();
        }
        catch (SuperMarketException ex )
        {
            Toast.makeText(this.frg.getActivity(), "ERRORE: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * This method handles the changing in the expense header, by a change in the
     * shop or in the date. No changes in the articles are admitted nor handled!
     * @param di
     * @throws SuperMarketException
     */
    private void saveExpenseHeader(AlertDialog di) throws SuperMarketException
    {
        ExpenseBean newExpBean = GuiUtils.getExpenseBeanFromView(di);
        boolean openAfterEdit = ((CheckBox) di.findViewById(R.id.dialog_update_expense_flag_open)).isChecked();
        newExpBean.setArticles(this.currentExpense.getArticles());
        //newExpBean.setCost();
        BeanFactory.insertOrUpdateBean(newExpBean);
        // Notifying change to the listview . . .
        ( ( FragmentExpenseList ) this.frg).getListViewAdapter().add(newExpBean);
        // if openAfterEdit flag is checked, then the expense will be opened
        if (openAfterEdit) {
            Logger.lfc_log("Opening expense details . . .");
            ((ActivityExpenseList) this.frg.getActivity()).onExpenseSelected(newExpBean);
        }
    }

    private void saveFullExpense() throws SuperMarketException
    {
        try
        {
            // deleting old expense article
            if ( this.removed != null )
            {
                for (ExpenseArticleBean bean2remove : this.removed)
                {
                    BeanFactory.deleteBean(bean2remove);
                }
            }
            // Adding/updating existent expenseArticles and expense header
            BeanFactory.insertOrUpdateBean(this.currentExpense);
        }
        catch ( SuperMarketException ex )
        {
            Logger.app_log("ERRORE: " + ex.getMessage());
            throw ex;
        }
    }

}

