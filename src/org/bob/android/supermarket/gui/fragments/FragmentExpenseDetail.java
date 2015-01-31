package org.bob.android.supermarket.gui.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.adapters.AdapterExpenseArticles;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.tasks.ATRetrieveExpenseArticles;
import org.bob.android.supermarket.utilities.Constants;
import org.bob.android.supermarket.utilities.Utilities;

public class FragmentExpenseDetail extends Fragment
{

	/**
	 * ListView con la lista articoli della spesa.
	 */
	private ListView articlesLV;

	/**
	 * Spesa selezionata.
	 */
	private ExpenseBean expenseSelected;

    /**
     * Istanza del task di recupero dati.
     */
    private ATRetrieveExpenseArticles task;

	/* --------------------------------------------------------------------- *
	 *                            OVERRIDDEN METHODS                         *
	 * --------------------------------------------------------------------- */		
	
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
		Logger.lfc_log("Avvio thread di recupero articoli di spesa...");
        this.task = new ATRetrieveExpenseArticles();
		//this.task.execute();

		/*this.lvAdapter = new AdapterExpenseArticles(this.getActivity(), R.layout.layout_single_item, this.expenseSelected.getArticlesList());

        Logger.lfc_log("|--> Setto l'adapter per la listview...");
		this.articlesLV.setAdapter(this.lvAdapter);
		this.lvAdapter.notifyDataSetChanged();
		Logger.lfc_log("|--> Aggancio l'item click handler alla listview...");
		this.articlesLV.setOnItemClickListener(new OnArticleSelectedListener(this));
        this.articlesLV.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        this.articlesLV.setOnScrollListener(touchListener.makeScrollListener());*/
		return v;
	}
	

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
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
