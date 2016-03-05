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

package org.bob.android.supermarket.utilities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by roberto.gatti on 22/01/2015.
 */
public class Constants
{
    /* ********************************************************************* */
    /*                         DEFAULT AND CONSTANTS VALUES                  */
    /* ********************************************************************* */

    /**
     * Costante per la stringa vuota.
     */
    public static final String EMPTY_STRING = "";

    /**
     * Valore di default
     */
    public static final int DEFAULT_ID_VALUE = -1;
    public static final String DEFAULT_DESCRIPTION = "<NO_DESCRIPTION_ASSIGNED>";
    public static final char DEFAULT_CHAR = 'X';
    public static final boolean CANCEL_TASK_IF_RUNNING = true;

    /* ********************************************************************* */
    /*                             INFRASTRUCTURE INFOS                      */
    /* ********************************************************************* */

    public static final String KEY_SELECTED_EXPENSE = "expense_selected";
    public static final String KEY_SELECTED_ARTICLE = "article_selected";

    /**
     * Enum type con gli oggetti validi per l'applicazione.
     * @author bob
     *
     */
    public enum ObjectTypes { ARTICLE, BRAND, SHOP };

    /**
     * Oggetto SimpleDateFormat per la formattazione in stringa della
     * sola data.
     */
    public static final SimpleDateFormat GLOBAL_DATE_FORMAT = new SimpleDateFormat("d MMMMM yyyy");

    /**
     * Oggetto SimpleDateFormat per il parsing di una data da/in dd-MM-yyyy.
     */
    public static final SimpleDateFormat LINE_SEPARATED_FIELDS = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Oggetto SimpleDateFormat per la formattazione in stringa dell'ora attuale.
     */
    public static final SimpleDateFormat GLOBAL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * Formatter in forma timestamp.
     */
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd.hhmmss");

    /**
     * Oggetto DecimalFormat per la formattazione di numeri decimali.
     */
    public static final DecimalFormat DM_FORMATTER = new DecimalFormat("#.##");
}
