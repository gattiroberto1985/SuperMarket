package org.bob.android.supermarket.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;

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
	    View rowView = convertView;
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
	    if (rowView == null) 
	    {
	        rowView = ApplicationSM.getLayoutInflater().inflate(R.layout.view_expense_detail_item, null);
	        ViewHolder viewHolder = new ViewHolder();
	        viewHolder.category = (TextView) rowView.findViewById(R.id.view_exp_item_category);
	        viewHolder.brand	 = (TextView) rowView.findViewById(R.id.view_exp_item_brand);
	        viewHolder.description = (TextView) rowView.findViewById(R.id.view_exp_item_desc);
	        viewHolder.unitCost = (TextView) rowView.findViewById(R.id.view_exp_item_single_price);
	        viewHolder.quantity = (TextView) rowView.findViewById(R.id.view_exp_item_quantity);
	        viewHolder.totalCost = (TextView) rowView.findViewById(R.id.view_exp_item_total_price);
            // Setting tags...
            viewHolder.category.setTag(ea.getArticle().getCategory());
            viewHolder.brand.setTag(ea.getArticle().getBrand());
	        rowView.setTag(viewHolder);
	    }
	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.category.setText(ea.getArticle().getCategory().getDescription());
	    holder.brand.setText(ea.getArticle().getBrand().getDescription());
	    holder.description.setText(ea.getArticle().getDescription());
	    holder.unitCost.setText(String.valueOf(ea.getArticleCost()));
	    holder.quantity.setText(String.valueOf(ea.getArticleQuantity()));
	    holder.totalCost.setText(this.getContext().getString(R.string.STR_DEFAULT_TOTAL_PRICE) + String.format("%.2f", ea.getFullCost()));
	    return rowView;
	}
	
	/**
	 * Override del getCount
	 */
	@Override
	public int getCount()
	{
		return this.expenseArticleList == null ? 0 : this.expenseArticleList.size();
	}

	@Override
	public void add(ExpenseArticleBean eab)
	{
		super.add(eab);
		Logger.writeLog("Aggiungo articolo di spesa: " + eab.toString());
		this.expenseArticleList.add(eab);
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

}
