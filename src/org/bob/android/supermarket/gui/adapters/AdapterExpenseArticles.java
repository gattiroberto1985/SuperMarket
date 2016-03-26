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
import android.widget.TextView;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.activities.ActivityExpenseDetails;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BaseSMBean;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.utilities.Constants;

import java.util.ArrayList;

/**
 * Adapter per la listview con la lista di articoli di una spesa
 * @author elbobehi
 *
 */
public class AdapterExpenseArticles extends ArrayAdapter<ExpenseArticleBean>
{

	/**
	 * Lista degli oggetti nella lista della spesa
	 */
	private ArrayList<ExpenseArticleBean> expenseArticleList = new ArrayList<ExpenseArticleBean>();
	
	/**
	 * Inner class statica per "struttura" view di nuovo item
	 * @author elbobehi
	 *
	 */
	static class ViewHolder 
	{
	    public TextView category;
	    public TextView brand;
	    public TextView description;
	    public TextView quantity;
	    public TextView unitCost;
	    public TextView totalCost;
	}

	/**
	 * Costruttore.
	 * @param context
	 */
	public AdapterExpenseArticles(Context context)
	{
		super(context, R.layout.view_expense_list_item);
	}

    /* ********************************************************************* */
    /*                           OVERRIDDEN METHODS                          */
    /* ********************************************************************* */

	
	/**
	 * Recupera una view all'interno della listview
	 * @param position la posizione dell'oggetto nella lista
	 * @param convertView La 'vecchia' view da riutilizzare
	 * @param parent il parent della view 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
        ExpenseArticleBean ea;
        if ( position >= 0 )
        {
            ea = this.expenseArticleList.get(position);
            Logger.app_log("Articolo di spesa recuperato nella lista: " + ea.toString());
        }
        else
        {
            throw new RuntimeException("ERRORE: articolo di spesa non recuperato nella lista!");
        }
	    if (convertView == null)
	    {
			convertView = ApplicationSM.getLayoutInflater().inflate(R.layout.view_expense_detail_item, parent, false);
			viewHolder = new ViewHolder();
	        viewHolder.category = (TextView) convertView.findViewById(R.id.view_exp_item_category);
	        viewHolder.brand	 = (TextView) convertView.findViewById(R.id.view_exp_item_brand);
	        viewHolder.description = (TextView) convertView.findViewById(R.id.view_exp_item_desc);
	        viewHolder.unitCost = (TextView) convertView.findViewById(R.id.view_exp_item_single_price);
	        viewHolder.quantity = (TextView) convertView.findViewById(R.id.view_exp_item_quantity);
	        viewHolder.totalCost = (TextView) convertView.findViewById(R.id.view_exp_item_total_price);
            // Setting tags...
			convertView.setTag(R.id.KEY_VIEW_HOLDER, viewHolder);
	    }
		viewHolder = (ViewHolder) convertView.getTag(R.id.KEY_VIEW_HOLDER);
		viewHolder.category.setText(ea.getArticle().getCategory().getDescription());
		viewHolder.brand.setText(ea.getArticle().getBrand().getDescription());
		viewHolder.description.setText(ea.getArticle().getDescription());
		viewHolder.unitCost.setText(Constants.DM_FORMATTER.format(ea.getArticleCost()));
		viewHolder.quantity.setText(String.valueOf(ea.getArticleQuantity()));
		viewHolder.totalCost.setText(this.getContext().getString(R.string.STR_DEFAULT_TOTAL_PRICE) + Constants.DM_FORMATTER.format(ea.getFullCost()));
        convertView.setTag(R.id.KEY_VIEW_TAG_EXPENSE_ARTICLE, ea);
	    return convertView;
	}
	
	/**
	 * Override del getCount
	 */
	@Override
	public int getCount()
	{
		return this.expenseArticleList.size();
	}

	@Override
	public ExpenseArticleBean getItem(int position)
	{
		return this.expenseArticleList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return this.expenseArticleList.get(position).getId();
	}

	@Override
	public void add(ExpenseArticleBean eab)
	{
		// [ ADD_EXPENSE_ARTICLE ]
		super.add(eab);
		this.expenseArticleList.add(eab);
		Logger.writeLog("Aggiungo articolo di spesa: " + eab.toString());
	}
	
	/**
	 * Override del metodo remove sulla lista di articoli della spesa.
	 * @param eab L'oggetto della lista da rimuovere
	 */
	@Override
	public void remove(ExpenseArticleBean eab)
	{
		super.remove(eab);
		for( int i = 0; i < this.expenseArticleList.size(); i++ )
		{
			ExpenseArticleBean _ea = this.expenseArticleList.get(i);
			//Logger.writeLog(" |--> hashcode oggetto: " + eab.hashCode() + " -- hashcode ogg. lista: " + _ea.hashCode() );
			if ( eab.hashCode() == _ea.hashCode() )
			{
                Logger.writeLog("   |-- Rimozione ok! Trovato l'articolo da rimuovere!");
				this.expenseArticleList.remove(i);
				//this.notifyDataSetChanged();
                Logger.writeLog("   |-- Rimozione dal dataset eseguita!");
				return;
			}
		}
        Logger.app_log("|-- ATTENZIONE: non trovato un corrispondente oggetto da rimuovere!", Logger.Level.WARNING);
	}
	
	@Override
	public void insert(ExpenseArticleBean eab, int position)
	{
        Logger.writeLog("|--> Inserisco '" + eab.toString() + "' alla posizione " + position + "...");
		super.insert(eab, position);
		this.expenseArticleList.add(position, eab);
	}

	@Override
	public void clear()
	{
        super.clear();
		this.expenseArticleList.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged()
	{
		((ActivityExpenseDetails) this.getContext()).setExpenseArticlesLoaded();
		super.notifyDataSetChanged();
	}

    @Override
    public int getPosition(ExpenseArticleBean item)
    {
        // return -1 if the item is not existing
        if ( item == null )
            return -1;

        // Checking the list for the item . . .
        for ( int i = 0; i < this.expenseArticleList.size() ; i++)
        {
            if ( this.expenseArticleList.get(i).getArticle().equals(item.getArticle()) )
                return i;
        }

        // . . . if item is not in list, then return -1
        return -1;
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
        this.expenseArticleList = new ArrayList<ExpenseArticleBean>(list.size());
        for ( BaseSMBean bean : list )
        {
            this.add((ExpenseArticleBean) bean);
        }
        Logger.app_log("Lista popolata.");
    }

}
