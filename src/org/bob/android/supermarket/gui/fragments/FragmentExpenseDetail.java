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

package org.bob.android.supermarket.gui.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.adapters.AdapterExpenseArticles;
import org.bob.android.supermarket.gui.dialogs.DialogFactory;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.tasks.ATRetrieveExpenseArticles;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.Utilities;

public class FragmentExpenseDetail extends ListFragment
{

	private ProgressDialog pd = null;

	/**
	 * ListView con la lista articoli della spesa.
	 */
	private ListView articlesLV;

	/**
	 * Spesa selezionata.
	 */
	private ExpenseBean expenseSelected;

	private boolean flagLoadedExpenseArticles = false;

    /**
     * Istanza del task di recupero dati.
     */
    private ATRetrieveExpenseArticles task;


	/* ********************************************************************* */
	/*                             CLASS METHODS                             */
	/* ********************************************************************* */

	public void setExpenseArticlesLoaded()
	{
		if ( this.pd != null )
		{
			this.pd.dismiss();
			this.pd = null;
		}
		this.flagLoadedExpenseArticles = true;
	}

	public void setSelectedExpense(ExpenseBean eb)
	{
		this.expenseSelected = eb;
		// Setting delle informazioni di testata della spesa
		// Recupero degli articoli di spesa
		/*( (TextView) this.getActivity().findViewById(R.id.dtl_hdr_id)     ).setText(String.valueOf(this.selectedContact.getId()));
		( (TextView) this.getActivity().findViewById(R.id.dtl_hdr_name)   ).setText(this.selectedContact.getName());
		( (TextView) this.getActivity().findViewById(R.id.dtl_hdr_surname)).setText(this.selectedContact.getSurname());
		( (TextView) this.getActivity().findViewById(R.id.dtl_hdr_birthday)).setText(Utilities.DATE_FORMATTER.format(new java.util.Date(this.selectedContact.getBirthday())));*/
		this.pd = ProgressDialog.show(this.getActivity(), getString(R.string.PROGRESS_DIALOG_LOAD_EXPENSE_ARTICLE_TITLE), getString(R.string.PROGRESS_DIALOG_LOAD_EXPENSE_ARTICLE_TEXT));
		this.task = new ATRetrieveExpenseArticles(this.articlesLV.getAdapter(), this.expenseSelected.getId());
		task.execute();
	}


	/* --------------------------------------------------------------------- *
	 *                            OVERRIDDEN METHODS                         *
	 * --------------------------------------------------------------------- */

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Logger.app_log("Article selected!");
		ExpenseArticleBean eab = (ExpenseArticleBean) l.getAdapter().getItem(position);
		DialogFactory.updateExpenseArticleDialog(eab, this);
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null) 
		{
			Logger.lfc_log("|--> Recupero la spesa selezionata dal bundle di salvataggio...");
			this.expenseSelected = (ExpenseBean) this.getArguments().getSerializable(Constants.KEY_SELECTED_EXPENSE);
		}
		//this.getDataFromDB();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        Logger.lfc_log("Creazione view del fragment di dettaglio spesa");
		View v =  inflater.inflate(R.layout.fragment_expense_detail, container, false);

        this.articlesLV = (ListView) v.findViewById(android.R.id.list);
		this.articlesLV.setOnItemClickListener(new OnArticleSelectedListener());
		if ( savedInstanceState != null )
		{
            Logger.lfc_log("Recupero la spesa dal savedBundle...");
            this.expenseSelected = (ExpenseBean) savedInstanceState.getSerializable(Constants.KEY_SELECTED_EXPENSE);
		}
		else
		{
			Logger.lfc_log("Recupero la spesa dall'intent dell'activity...");
			this.expenseSelected = (ExpenseBean) this.getActivity().getIntent().getSerializableExtra(Constants.KEY_SELECTED_EXPENSE);
		}
		// Setting data of expense header
		( (TextView) v.findViewById(R.id.view_exp_header_cost)).setText(String.valueOf(this.expenseSelected.getCost())                     );
		( (TextView) v.findViewById(R.id.view_exp_header_shop)).setText(String.valueOf(this.expenseSelected.getShop().getDescription())    );
		( (TextView) v.findViewById(R.id.view_exp_header_date)).setText(Constants.GLOBAL_DATE_FORMAT.format(this.expenseSelected.getDate()));

		Logger.lfc_log("Avvio thread di recupero articoli di spesa...");
        this.articlesLV.setAdapter(new AdapterExpenseArticles(this.getActivity()));
        this.articlesLV.setClickable(true);
        /*this.task = new ATRetrieveExpenseArticles(this.articlesLV.getAdapter());
		this.task.execute();*/
		return v;
	}

	@Override
	public void onStart() {
		Logger.lfc_log( "ListFragment: onStart");
		super.onStart();
		// Marco il fragment come "retained fragment"
		this.setRetainInstance(true);
		if ( ! this.flagLoadedExpenseArticles )
		{
			/*Logger.writeLog("Starting asynctask to retrieve datas from db....");
			this.pd = ProgressDialog.show(
					this.getActivity(),
					this.getString(R.string.PROGRESS_DIALOG_LOAD_EXPENSE_ARTICLE_TITLE),
					this.getString(R.string.PROGRESS_DIALOG_LOAD_EXPENSE_ARTICLE_TEXT),
					true);*/
			this.task = new ATRetrieveExpenseArticles(this.articlesLV.getAdapter(), this.expenseSelected.getId());
			task.execute();
		}
	}


	@Override
	public void onDetach() 
	{
		super.onDetach();
	}
	

	@Override
	public void onPause()
	{
		super.onPause();
	}
	
	/* --------------------------------------------------------------------- *
	 *                               UTILITIES                               *
	 * --------------------------------------------------------------------- */		

}

class OnArticleSelectedListener implements AdapterView.OnItemClickListener
{

	/**
	 * Metodo onItemClick. Visualizza una view di dialog 
	 * {@see org.bob.android.supermarket.gui.dialogs.AddOrUpdateArticle}.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{

	}
	
}
