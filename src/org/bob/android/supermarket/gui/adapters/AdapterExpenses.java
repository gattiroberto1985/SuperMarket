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
	static class VHExpense
	{
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
            vh.cost = (EditText) convertView.findViewById(R.id.view_exp_header_cost);
            vh.shop = (EditText) convertView.findViewById(R.id.view_exp_header_shop);
            vh.shop.setTag(expense.getShop());
            vh.date = (EditText) convertView.findViewById(R.id.view_exp_header_date);
            convertView.setTag(vh);
        }
        vh = (VHExpense) convertView.getTag();
        vh.cost.setText(String.valueOf(expense.getId()));
        vh.shop.setText(String.valueOf(   ( (ShopBean)vh.shop.getTag()).getId()));
        vh.date.setText( Constants.DM_FORMATTER.format( expense.getFormattedDate() ) );
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
