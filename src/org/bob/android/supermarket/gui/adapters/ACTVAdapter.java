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

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : SuperMarket
 * File    : ${FILE_NAME}
 * <p/>
 * Created by roberto on 26/03/16.
 */
public class ACTVAdapter extends ArrayAdapter<String> implements Filterable
{

    private String selectedObj;

    private List<String> allObjects;

    private List<String> filtered;

    /**
     * Local reference al layoutinflater.
     */
    private LayoutInflater inflater;

    private ArrayFilter filter;

    public static class ViewHolderList
    {
        public TextView tv_ref_id;
        public TextView tv_ref_desc;
    }

    public ACTVAdapter(Context context, List<String> objects)
    {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.inflater = (LayoutInflater) ApplicationSM.getInstance().getApplicationContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        this.allObjects = objects;
    }


    @Override
    public void add(String object)
    {
        super.add(object);
    }

    @Override
    public int getCount()
    {
        return this.allObjects.size();
    }

    @Override
    public String getItem(int position)
    {
        return this.allObjects.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(String item)
    {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String object = (String) this.getItem(position);
        ViewHolderList vh;
        if ( convertView == null )
        {
            vh = new ViewHolderList();
            convertView = this.inflater.inflate(R.layout.view_actv_suggest_row, parent, false);
            vh.tv_ref_id   = (TextView) convertView.findViewById(R.id.dlg_actv_ref_id);
            vh.tv_ref_desc = (TextView) convertView.findViewById(R.id.dlg_actv_ref_value);
            convertView.setTag(vh);
        }
        vh = (ViewHolderList) convertView.getTag();
        vh.tv_ref_desc.setText(object);
        /*viewHolder.contact.setText(contact.getSurname() + ", " + contact.getName());
        viewHolder.contactId.setText(String.valueOf(contact.getId()));
        viewHolder.contactImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.contact_default_image));*/
        return convertView;
    }

    @Override
    public void clear()
    {
        super.clear();
        //this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (this.filter == null){
            this.filter = new ArrayFilter();
        }
        return this.filter;
    }

    public void setSelected(String ob) { this.selectedObj = ob; }

    public String getSelected() { return this.selectedObj; }

    class ArrayFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();

            if ( ACTVAdapter.this.filtered == null)
            {
                ACTVAdapter.this.filtered = new ArrayList<String>(ACTVAdapter.this.allObjects);
            }

            if (constraint != null && constraint.length() != 0)
            {
                ArrayList<String> resultsSuggestions = new ArrayList<String>();
                for (int i = 0; i < ACTVAdapter.this.filtered.size(); i++)
                {
                    if(ACTVAdapter.this.filtered.get(i).toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        resultsSuggestions.add(ACTVAdapter.this.filtered.get(i));
                    }
                }

                results.values = resultsSuggestions;
                results.count = resultsSuggestions.size();

            }
            else {
                ArrayList <String> list = new ArrayList <String>(ACTVAdapter.this.filtered);
                results.values = list;
                results.count = list.size();
            }
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            clear();
            ArrayList<String> newValues = (ArrayList<String>) results.values;
            if(newValues !=null)
            {
                for (int i = 0; i < newValues.size(); i++)
                {
                    add(newValues.get(i));
                }
                if(results.count>0)
                {
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }

        }

    }


}

