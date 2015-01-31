package org.bob.android.supermarket.utilities;

import org.bob.android.supermarket.persistence.beans.BrandBean;
import org.bob.android.supermarket.persistence.beans.CategoryBean;

import java.io.StringBufferInputStream;
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
