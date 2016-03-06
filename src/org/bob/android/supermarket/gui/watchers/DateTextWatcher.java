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

package org.bob.android.supermarket.gui.watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

/**
 *
 * TextWatcher for a date field. It makes basic controls
 * on an edittext view.
 *
 * Project : SuperMarket
 * File    : DateTextWatcher.java
 *
 * Created by roberto on 06/03/16.
 */
public class DateTextWatcher implements TextWatcher {

    private EditText dateField = null;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();

    public DateTextWatcher(EditText dateField)
    {
        this.setDateField(dateField);
    }

    private void setDateField(EditText et) { this.dateField = et; }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void beforeTextChanged(CharSequence currentText, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence newText, int start, int before, int count) {
        if (!newText.toString().equals(current)) {
            String clean = newText.toString().replaceAll("[^\\d.]", "");
            String cleanCurrent = current.replaceAll("[^\\d.]", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanCurrent)) sel--;

            if (clean.length() < 8){
                clean = clean + ddmmyyyy.substring(clean.length());
            }else{
                //This part makes sure that when we finish entering numbers
                //the date is correct, fixing it otherwise
                int day  = Integer.parseInt(clean.substring(0,2));
                int mon  = Integer.parseInt(clean.substring(2,4));
                int year = Integer.parseInt(clean.substring(4,8));

                if(mon > 12) mon = 12;
                cal.set(Calendar.MONTH, mon-1);
                year = (year<1900)?1900:(year>2100)?2100:year;
                cal.set(Calendar.YEAR, year);
                // ^ first set year for the line below to work correctly
                //with leap years - otherwise, date e.g. 29/02/2012
                //would be automatically corrected to 28/02/2012

                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                clean = String.format("%02d%02d%02d",day, mon, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            this.dateField.setText(current);
            this.dateField.setSelection(sel < current.length() ? sel : current.length());
        }
    }
}
