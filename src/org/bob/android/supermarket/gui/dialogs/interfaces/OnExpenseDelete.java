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
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.gui.fragments.FragmentExpenseList;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;

/**
 * Created by roberto.gatti on 07/03/2016.
 */
public class OnExpenseDelete implements DialogInterface.OnClickListener {


    private ExpenseBean currentExpense;

    private FragmentExpenseList frg;

    public OnExpenseDelete(FragmentExpenseList frg, ExpenseBean curExpense) {
        this.frg = frg;
        this.currentExpense = curExpense;
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
     * Update/Create method for an expense. It checks if the new expense is
     * different from the old. Then, if different, it makes some basic control
     * on the new object and, finally, the method tries the create/update
     * on DB.
     * If the openAfterEdit flag is checked, then the expense is open in the
     * Detail fragment activity.
     *
     * @param di
     */
    public void handleEdit(AlertDialog di) {
        Logger.app_log("Deleting expense with id " + this.currentExpense.getId() + " . . .");
        try {
            BeanFactory.deleteBean(this.currentExpense);
            this.frg.getListViewAdapter().remove(this.currentExpense);
        }
        catch ( SuperMarketException ex )
        {
            Logger.app_log("Error on deleting expense!", Logger.Level.ERROR);
            Logger.app_log("Inner message is: " + ex.getMessage(), Logger.Level.ERROR);
            Toast.makeText(this.frg.getActivity(), "Error on deleting expense: " + ex.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }
}
