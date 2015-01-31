/*
 * The MIT License (MIT)
 *
 * Copyright (c) <year> <copyright holders>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.bob.android.supermarket.gui.activities;

import java.util.ArrayList;
import java.util.List;

import android.widget.*;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.persistence.cp.ArticlePMD.ArticleTableMetaData;
import org.bob.android.supermarket.persistence.cp.CategoryPMD.CategoryTableMetaData;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * Rappresenta una activity con una lista di massima della spesa da eseguire.
 * L'activity presentera' una lista di articoli in versione checkbox (con
 * possibilita' di aggiunta articoli) e un tasto "salva" che creera' la spesa
 * nella lista delle spese eseguite. 
 * @author bob
 *
 */
public class SMDraftExpenseActivity extends ListActivity
								implements 	  LoaderManager.LoaderCallbacks<Cursor>
											, SearchView.OnQueryTextListener 
											, SearchView.OnCloseListener
{
	
	/**
	 * Stringa di utilita' per il logging.
	 */
	private static final String TAG = DSMDraftExpenseActivityclass.getName();
	
	/**
	 * URI loader per il cursorloader.
	 */
	private static final int URI_LOADER = 10;

	/**
	 * Adapter per la ExpandableListView.
	 */
    private ExpandableListAdapter listAdapter;
    
    /**
     * ExpandableListView dell'activity.
     */
    private ExpandableListView expListView;
	
    /**
     * EditText con il filtro per l'expandable list view.
     */
    private SearchView filterTV;
    
	/* ********************************************************************* */
	/*                          SPECIFIC CLASS METHODS                       */        
	/* ********************************************************************* */
	
	/**
	 * Il metodo crea una spesa a partire dalla bozza.
	 */
	private boolean saveDraft()
	{
		// ArrayList<String> articles = this.lvAdapter.getSelectedArticles();
		// Creare un asyncTask che esegua la creazione della spesa
		// salvare la spesa
		return false;
	}
	
	/**
	 * Il metodo espande tutti i gruppi.
	 */
	private void expandAll() 
	{
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) this.expListView.expandGroup(i);
	 }
	 
	/* ********************************************************************* */
	/*                    OVERRIDING ListActivity Methods                    */        
	/* ********************************************************************* */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_draft_expense);
		//this.listview = this.getListView();
		this.getLoaderManager().initLoader(URI_LOADER, null, this);
		this.expListView = (ExpandableListView) this.findViewById(R.id.explv);
        this.expListView.setOnChildClickListener(new OnChildClickListener() 
        	{
        	 
            	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) 
            	{
            		CheckBox cb = (CheckBox) v.findViewById(R.id.draft_checkbox);
            		cb.setChecked(! cb.isChecked() );
            		//listAdapter.get(groupPosition).
            		/*final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
            		Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG).show();*/
            		return true;
            	}
        	});
        
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		this.filterTV = (SearchView) this.findViewById(R.id.search_article);
		//this.filterTV.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		//this.filterTV.setIconifiedByDefault(false);
		this.filterTV.setOnQueryTextListener(this);
		this.filterTV.setOnCloseListener(this);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}
	
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super(l, v, pothis.lvAdapter.onClick(position);
	} */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
        getMenuInflater().inflate(R.menu.menu_draft_expense, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch ( item.getItemId() )
		{
		case R.id.menu_save_draft: return this.saveDraft();
		default: return super.onOptionsItemSelected(item);
		}
	}
	
	/* ********************************************************************* */
	/*          OVERRIDING LoaderManager.LoaderCallbacks<Cursor>             */        
	/* ********************************************************************* */
	
	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) 
	{
		Log.i(TAG, "Recupero gli articoli dal db...");
		CursorLoader cl = null;
		switch ( loaderId )
		{
		case DSMDraftExpenseActivityURI_LOADER:
            cl = new CursorLoader(
                    this,       // Parent activity context
                    ArticlePMD.DISTINCT_CATEGORY_ID_URI, // Table to query
                    new String[] { CategoryTableMetaData.DESCRIPTION, ArticleTableMetaData.DESCRIPTION},
                    ArticleTableMetaData.CATEGORY_ID + " = c." + CategoryTableMetaData._ID,
                    null,            // No selection arguments
                    CategoryTableMetaData.DESCRIPTION + " asc");
            break;
			default: break;
		}
		return cl;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) 
	{
		/*this.lvAdapter = new CustomAdapter(this, data);
		this.listview.setAdapter(this.lvAdapter);
		this.lvAdapter.notifyDataSetChanged();*/
		
		this.listAdapter = new ExpandableListAdapter(this, data);
		this.expListView.setAdapter(this.listAdapter);
		this.listAdapter.notifyDataSetChanged();
		this.expandAll();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) 
	{
		Log.i(TAG, "Resetto il cursor della listview degli articoli");
		this.expListView.setAdapter(new ExpandableListAdapter(this, null));
		//listview.setAdapter(null);
	}
	
//	/**
//	 * Classe innestata privata che implementa una personale versione del
//	 * CursorAdapter per la modifica del layout sugli item della listview.
//	 * @author bob
//	 *
//	 */
//	private class CustomAdapter extends ArrayAdapter<String>
//	{
//
//		private ArrayList<String>  objects = null;
//		private ArrayList<Boolean> checked = null;
//		
//		class ViewHolder
//		{
//			TextView object;
//			CheckBox check;
//		}
//
//		public CustomAdapter(Context context, Cursor cursor) 
//		{
//			super(context, R.layout.layout_single_article_draft_expense);
//			int size = cursor.getCount();
//			this.objects = new ArrayList<String>(size);
//			this.checked = new ArrayList<Boolean>(size);
//			// popolo le liste
//			while ( cursor.moveToNext() )
//			{
//				this.objects.add(cursor.getString(1));
//				this.checked.add(Boolean.valueOf(false));
//			}
//		}
//		
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) 
//		{
//			View view = convertView;
//			if ( view == null )
//			{
//				// nuova view, creo da zero
//				LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
//				view = inflater.inflate(R.layout.layout_single_article_draft_expense, null);
//				((CheckBox) view.findViewById(R.id.draft_checkbox)).setChecked(false);
//				((TextView) view.findViewById(R.id.single_article_text)).setText(this.objects.get(position));
//			}
//			else
//			{
//				((CheckBox) view.findViewById(R.id.draft_checkbox)).setChecked(this.checked.get(position));
//				((TextView) view.findViewById(R.id.single_article_text)).setText(this.objects.get(position));
//			}
//			return view;
//		}
//		
//		
//		public void onClick(int position)
//		{
//			boolean state = this.checked.get(position); 
//			// lo rimuovo...
//			this.checked.remove(position);
//			// ... e lo sostituisco con il valore opposto allo state!
//			this.checked.add(position, ! state);
//		}
//		
//		@Override
//		public int getCount() 
//		{
//			if ( this.objects == null ) return 0;
//			else return this.objects.size();
//		}
//		
//		/**
//		 * Il metodo ritorna una lista con gli oggetti flaggati a true.
//		 * @return
//		 */
//		public ArrayList<String> getSelectedArticles()
//		{
//			ArrayList<String> articles = new ArrayList<String>();
//			for ( int i = 0; i < this.objects.size(); i++ )
//			{
//				if ( this.checked.get(i) ) articles.add(this.objects.get(i));
//				else continue;
//			}
//			return articles;
//		}
//		
//	}

	/**
	 * Classe innestata privata per l'adapter della expandable listview.
	 * In fase di creazione, la expListView sostituira' la listview
	 * corrente.
	 * @author roberto.gatti
	 *
	 */
	private class ExpandableListAdapter extends BaseExpandableListAdapter
	{
		private int SIZE = 0; // variabile statica per capire quando la stringa
		                      // in input di ricerca viene accorciata (nel caso
		                      // ricalcolo il filtro dalla lista originale
	    private Context _context;
	    List<ExtendedCategory> _datas        = new ArrayList<ExtendedCategory>();
	    List<ExtendedCategory> originalDatas = new ArrayList<ExtendedCategory>();

        class ViewHolder
		{
			TextView object;
			CheckBox check;
		}

	    //List<String> _listDataHeader; // header titles
	    // child data in format of header title, child title
	    //HashMap<String, List<String>> _listDataChild;
	 
	    //List<String> _originalDataHeader ;
	    //HashMap<String, List<String>> _originalDataChild;
	    
	    public ExpandableListAdapter(Context c, Cursor datas)
	    {
	    	this._context = c;
	    	if ( datas == null ) return;
	    	// Leggo dal cursor...
	    	while ( datas.moveToNext() )
	    	{
	    		// ... e per ogni riga recupero articolo e categoria.
	    		String category = datas.getString(0);
	    		String article = datas.getString(1);
	    		ExtendedCategory ec = new ExtendedCategory(category, null);
	    		// Se la categoria e' contenuta nella lista...
	    		if ( _datas.contains(ec) ) // extendedcategory.equals --> controlla l'uguaglianza della categoria
	    		{
	    			// ... allora recupero l'indice della lista e ci aggiungo l'articolo, 
	    			_datas.get(_datas.indexOf(ec)).addArticle(article);
	    		}
	    		// altrimenti creo una nuova lista con l'articolo al suo interno
	    		else _datas.add(new ExtendedCategory(category, article));
	    	}
	    	// qui ho popolato la lista, popolo anche il doppione per il backup
	    	this.originalDatas.addAll(this._datas);
	    	
	    	/*
	    	_listDataHeader = new ArrayList<String>(30); // 30 categorie come valore di default...?
	    	_listDataChild = new HashMap<String, List<String>>(size);
	    	if ( size == 0 ) { datas.close(); return; } 
	    	String category = "", article = "";
			_listDataHeader = new ArrayList<String>();
			while( datas.moveToNext() )
			{
				category = datas.getString(0);
				if ( _listDataHeader.contains(category) ) continue;
				else _listDataHeader.add(category);
			}
			List<List<String>> articles = new ArrayList<List<String>>(_listDataHeader.size()); 
			datas.moveToFirst();
			int headerIndex = -1;
			// itero sugli articoli
	    	while ( datas.moveToNext() )
	    	{
	    		category = datas.getString(0);
	    		article = datas.getString(1);
	    		Log.i(TAG,  "Articolo rilevato: " + category + " -- " + article);
	    		headerIndex = _listDataHeader.indexOf(category);
	    		if ( headerIndex >= articles.size() ) articles.add(new ArrayList<String>());
	    		articles.get(headerIndex).add(article); // al max l'headerindex dovrebbe essere a +1 rispetto 
	    		 										// rispetto alla size
	    	}
	    	headerIndex = 0;
	    	for ( List<String> list : articles )
	    	{
	    		_listDataChild.put(_listDataHeader.get(headerIndex++), list);
	    	}
	    	// Filling the original list...
	    	this._originalDataHeader = new ArrayList<String>(this._listDataHeader.size());
	    	this._originalDataHeader.addAll(this._listDataHeader);
	    	this._originalDataChild = new HashMap<String, List<String>>(this._listDataChild.size());
	    	this._originalDataChild.putAll(this._listDataChild);*/
	    	// chiudo la lista con l'ultimo elemento
	    	//_listDataChild.put(_listDataHeader.get(indexHeader), partial);
	    }
	    
	    @Override
		public int getGroupCount() 
		{
	    	return this._datas.size(); 
			//return this._listDataHeader.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) 
		{
			return this._datas.get(groupPosition).getArticles().size();
			//return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition)
		{
			return this._datas.get(groupPosition);
			//return this._listDataHeader.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition)
		{
			return this._datas.get(groupPosition).getArticles().get(childPosition);
			//return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) 
		{
			return childPosition;
		}

		@Override
		public boolean hasStableIds() 
		{
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
		{
			String headerTitle = ((ExtendedCategory) getGroup(groupPosition)).getCategory();
	        if (convertView == null)
	        {
	            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.expandable_lv_category, null);
	        }
	        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	        return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
		{
	        final String childText = (String) this.getChild(groupPosition, childPosition);
	        if (convertView == null) 
	        {
	            convertView = LayoutInflater.from(DSMDraftExpenseActivitythis).inflate(R.layout.layout_single_article_draft_expense, null);

                ViewHolder childHolder = new ViewHolder();
	            childHolder.check  = (CheckBox) convertView.findViewById(R.id.draft_checkbox);
	            childHolder.object = (TextView)  convertView.findViewById(R.id.single_article_text);
	            convertView.setTag(childHolder);
	        }
	        TextView txtListChild = (TextView) convertView.findViewById(R.id.single_article_text);
	        CheckBox cb = (CheckBox) convertView.findViewById(R.id.draft_checkbox);
	        txtListChild.setText(childText);
	        
	        return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) 
		{
			return true;
		}

		
		/* ********************************************************************* */
		/*                   OVERRIDING Filterable methods                       */        
		/* ********************************************************************* */
		
		public void filterData(String query) 
		{
			ArrayList<ExtendedCategory> newlist = new ArrayList<ExtendedCategory>();
			if ( query == null || query.equals("") ) 
			{
				Log.i(TAG, "Resetto la vista...");
				this._datas = (ArrayList<ExtendedCategory>) originalDatas;
				this.notifyDataSetChanged();
				return;
			}
			String valueToCheck = query.toString().toUpperCase();
			
			// controllo la lunghezza della stringa: se minore riparto dall'inizio
			if ( this.SIZE < query.length() ) _datas = (ArrayList<ExtendedCategory>) originalDatas;
			
			for ( ExtendedCategory e : _datas )
			{
				for ( String article : e.getArticles() )
				{
					if ( article.toUpperCase().contains(valueToCheck) ) 
					{
						// il match e' passato: se non c'e' aggiungo la categoria e aggiungo 
						// alla categoria l'articolo
						if ( ! newlist.contains(e)) newlist.add(new ExtendedCategory(e.getCategory(), article));
						else newlist.get(newlist.indexOf(e)).addArticle(article);
					}
				}
			}
			
/*			ArrayList<String> header = new ArrayList<String>();
			ArrayList<String> children = new ArrayList<String>();
               for ( Entry entry : _listDataChild.entrySet() )
               {
               	String key = entry.getKey().toString();
               	if ( ! header.contains(key) )
               	{
               		header.add(key);
               		if ( children.size() > 0 ) newdatas.put(key, children);
               		children = new ArrayList<String>();
                	}
                	Object value = entry.getValue();
                	if ( value == null ) continue;
                	valueStr = value.toString();
                	if ( valueStr.contains(constraint) ) children.add(valueStr);
                }*/
				/*Filter.FilterResults fr = new Filter.FilterResults();
				fr.count = newlist.size();
				fr.values = newlist;
				return fr; */
			Log.i(TAG, "|--> Match ok su " + newlist.size() + " articoli!");
			this._datas = newlist;
			this.notifyDataSetChanged();
		}
	}
	/* ********************************************************************* */
	/*                   OVERRIDING SearchView methods                       */        
	/* ********************************************************************* */
	
		
	@Override
	public boolean onQueryTextSubmit(String query) 
	{
		  listAdapter.filterData(query);
		  this.expandAll();
		  return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) 
	{
		  listAdapter.filterData(newText);
		  this.expandAll();
		  return false;
	}

	@Override
	public boolean onClose() 
	{
		this.listAdapter.filterData("");
		this.expandAll();
		return false;
	}

}

class ExtendedCategory
{
	String category;
	List<String> articles = new ArrayList<String>();
	List<Boolean> checked = new ArrayList<Boolean>();
	
	public ExtendedCategory(String c, String article)
	{
		this.category = c;
		if ( article != null ) 
			this.articles.add(article); 
	}
	
	public void addArticle(String article)
	{
		this.articles.add(article);
	}
	
	public boolean isArticleInCategory(String article)
	{
		return this.articles.contains(article);
	}
	
	public String getCategory() { return this.category; } 
	
	public List<String> getArticles() { return this.articles; } 
	
	/**
	 * Ritorna true se le categorie dei due oggetti extendedcategory sono uguali.
	 */
	@Override
	public boolean equals(Object _o) 
	{
		if ( _o == null || ! ( _o instanceof ExtendedCategory) ) return false;
		ExtendedCategory c = (ExtendedCategory) _o;
		return c.getCategory().equals(this.getCategory());
		/*if ( _o == null || ! ( _o instanceof String ) ) return false;
		return _o.toString().equals(this.category);*/
	}
}