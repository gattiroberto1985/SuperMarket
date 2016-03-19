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

package org.bob.android.supermarket.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.activities.ActivityExpenseList;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BaseSMBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.persistence.beans.ShopBean;
import org.bob.android.supermarket.utilities.Constants;

import java.util.ArrayList;

public class AdapterExpenses extends ArrayAdapter<ExpenseBean>
{

	/**
	 * Lista delle spese eseguite.
	 */
	private ArrayList<ExpenseBean> expensesList = new ArrayList<ExpenseBean>();
	
	/**
	 * Inner class statica per "struttura" view di nuovo item
	 * @author elbobehi
	 *
	 */
	private class VHExpense
	{
        public TextView expId;
	    public TextView date;
	    public TextView shop;
	    public TextView cost;
	}
	
	/**
	 * Costruttore a tre parametri.
	 * @param context
	 */
	public AdapterExpenses(Context context)
	{
        //super(context, R.layout.view_expense_header, objects);
        super(context, R.layout.view_expense_header);
    }

    /* ********************************************************************* */
    /*                           OVERRIDDEN METHODS                          */
    /* ********************************************************************* */

    @Override
    public int getCount()
    {
        return this.expensesList.size();
    }

    @Override
    public ExpenseBean getItem(int position)
    {
        return this.expensesList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @Override
    public void add(ExpenseBean object) {
        this.expensesList.add(object);
        this.notifyDataSetChanged();
    }

    @Override
    public void remove(ExpenseBean object) {
        int i = -1;
        for ( ExpenseBean eb : this.expensesList )
        {
            i++;
            if ( eb.getId() == object.getId() )
            {
                this.expensesList.remove(i);
                break;
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getPosition(ExpenseBean item)
    {
        if ( this.expensesList == null )
            return -1;
        if ( item == null )
            return -2;
        for ( int i = 0; i < this.expensesList.size(); i++ )
        {
            if ( this.expensesList.get(i).equals(item) ) return i;
        }
        return -3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Logger.lfc_log("getView su AdapterexpensesList");
        ExpenseBean expense = this.getItem(position);
        VHExpense vh;
        if ( convertView == null )
        {
            vh = new VHExpense();
            convertView = ApplicationSM.getLayoutInflater().inflate(R.layout.view_expense_header, parent, false);
            vh.expId = (TextView) convertView.findViewById(R.id.view_exp_header_id);
            vh.expId.setTag(R.id.KEY_VIEW_TAG_EXPENSE, expense);
            vh.cost = (TextView) convertView.findViewById(R.id.view_exp_header_cost);
            vh.shop = (TextView) convertView.findViewById(R.id.view_exp_header_shop);
            vh.shop.setTag(R.id.KEY_VIEW_TAG_SHOP, expense.getShop());
            vh.date = (TextView) convertView.findViewById(R.id.view_exp_header_date);
            convertView.setTag(R.id.KEY_VIEW_HOLDER, vh);
        }
        vh = (VHExpense) convertView.getTag(R.id.KEY_VIEW_HOLDER);
        vh.expId.setText(String.valueOf(expense.getId()));
        vh.cost.setText(String.valueOf(expense.getCost()));
        vh.shop.setText(String.valueOf(   ( (ShopBean)vh.shop.getTag(R.id.KEY_VIEW_TAG_SHOP)).getDescription()));
        vh.date.setText( Constants.GLOBAL_DATE_FORMAT.format( expense.getFormattedDate() ) );
        return convertView;
    }

    @Override
    public void clear()
    {
        this.expensesList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged()
    {
        ((ActivityExpenseList) this.getContext()).setExpensesLoaded();
        super.notifyDataSetChanged();
    }

    /* ********************************************************************* */
    /*                              CLASS METHODS                            */
    /* ********************************************************************* */

    /**
     * Il metodo esegue la traduzione di una lista di oggetti generica in una
     * lista di ugual dimensione necessaria all'adapter per il popolamento della
     * listview.
     * @param list
     */
    public void translateAndSetList(ArrayList<BaseSMBean> list)
    {
        Logger.writeLog("Traduzione lista negli oggetti opportuni");
        if ( list == null || list.size() == 0 )
        {
            Logger.app_log("WARNING: Nessun oggetto nella lista!", Logger.Level.WARNING);
            return;
        }
        this.expensesList = new ArrayList<ExpenseBean>(list.size());
        for ( BaseSMBean bean : list )
            this.expensesList.add((ExpenseBean) bean);
        Logger.app_log("Lista popolata.");
    }



}
