package org.bob.android.supermarket.gui.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.gui.adapters.AdapterExpenses;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.tasks.ATRetrieveExpenses;
import org.bob.android.supermarket.utilities.Constants;

/**

 */
public class FragmentExpenseList extends ListFragment
{

    /**
     * Flag che indica la necessita' di recuperare le spese dal db.
     */
    private boolean flagLoadedExpenses = false;

    /**
     * Progress dialog per il caricamento delle spese.
     */
    private ProgressDialog pd = null;

    /**
     * Task di recupero spese dal db.
     */
    private ATRetrieveExpenses task = null;

    /**
     * Istanza della listview.
     */
    private ListView expenseLV = null;

    /* ********************************************************************* */
    /*                               CLASS METHODS                           */
    /* ********************************************************************* */

    public void setExpensesLoaded()
    {
        if ( this.pd != null )
        {
            this.pd.dismiss();
            this.pd = null;
        }
        this.flagLoadedExpenses = true;
    }

    /* ********************************************************************* */
    /*                     FRAGMENT LIFECYCLE OVERRIDE                       */
    /* ********************************************************************* */

    @Override
    public void onAttach(Activity activity)
    {
        Logger.lfc_log( "ListFragment: onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Logger.lfc_log( "ListFragment: onCreateView");
        View v = inflater.inflate(R.layout.fragment_expense_list, container);
        this.expenseLV = (ListView) this.getActivity().findViewById(R.id.frg_exp_list_listview);
        this.expenseLV.setAdapter(new AdapterExpenses(this.getActivity()));
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Logger.lfc_log( "ListFragment: onStart");
        super.onStart();
        // Marco il fragment come "retained fragment"
        this.setRetainInstance(true);
        if ( ! this.flagLoadedExpenses )
        {
            Logger.writeLog("Starting asynctask to retrieve datas from db....");
            this.pd = ProgressDialog.show(
                    this.getActivity(),
                    this.getString(R.string.PROGRESS_DIALOG_EXPENSE_LIST_LOADING_TITLE),
                    this.getString(R.string.PROGRESS_DIALOG_EXPENSE_LIST_LOADING_TEXT),
                    true);
            this.task = new ATRetrieveExpenses(this.getListViewAdapter());
            task.execute();
        }
    }

    @Override
    public void onResume()
    {
        Logger.lfc_log( "ListFragment: onResume");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Logger.lfc_log( "ListFragment: onPause");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        Logger.lfc_log( "ListFragment: onSaveInstanceState");
        if ( this.pd != null )
        {
            this.pd.dismiss();
            this.pd = null;
        }
        if ( this.task != null ) this.task.cancel(Constants.CANCEL_TASK_IF_RUNNING);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop()
    {
        Logger.lfc_log( "ListFragment: onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        Logger.lfc_log( "ListFragment: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Logger.lfc_log( "ListFragment: ondestroy");
        this.flagLoadedExpenses = false;
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    /*
     * Metodi per gestione menu
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        //super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Logger.app_log("OnOptionsItemSelected listfragment");
        // Handle item selection
        /*switch (item.getItemId()) {
            case R.id.:
                AlertDialog dialog = DialogFactory.showNewContactDialog(this);
                dialog.show();
                return true;
            default:*/
                return super.onOptionsItemSelected(item);
    }

    @Override
    public ListView getListView()
    {
        return this.expenseLV;
    }

    public AdapterExpenses getListViewAdapter()
    {
        return (AdapterExpenses) this.getListView().getAdapter();
    }
}

/**
 * Interfaccia di gestione dell'evento di long click su un item della listview.
 * Esegue, se confermata, la rimozione della spesa dal database.
 * @author roberto.gatti
 *
 */
class OnExpenseLongClickListener implements OnItemLongClickListener
{

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        return false;
    }
}