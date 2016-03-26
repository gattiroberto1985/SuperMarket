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
import android.widget.Toast;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.utilities.Constants;

/**
 * Project : SuperMarket
 * File    : ${FILE_NAME}
 * <p/>
 * Created by roberto on 26/03/16.
 */
public class TwoDecimalTextWatcher implements TextWatcher
{

    private EditText et2check;

    private String currentText;

    public TwoDecimalTextWatcher(EditText et2check) {
        this.et2check = et2check;
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * This method is called to notify you that, within s, the count characters
     * beginning at start have just replaced old text that had length before.
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if ( charSequence == null )
            return;

        String newText = charSequence.toString();
        // Checking decimal number
        if ( newText.split("\\.").length > 1 && ( newText.split("\\.")[1] ).length() > 2 )
        {
            double d = 0;
            try
            {
                d = Double.parseDouble(charSequence.toString());
            }
            catch ( NumberFormatException ex )
            {
                Logger.app_log("NumberFormatException! Non dovrebbe esser possibile...");
                throw new RuntimeException("NumberFormatException! Non dovrebbe esser possibile...");
            }
            Toast.makeText(et2check.getContext(), "Only two decimals admitted!", Toast.LENGTH_SHORT).show();
            this.et2check.setText(Constants.DM_FORMATTER.format(d));
        }
    }
}
