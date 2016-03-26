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

package org.bob.android.supermarket.logger;

import android.util.Log;

/**
 * Classe di gestione dei messaggi di log dell'applicazione. Contiene la definizione di
 * due tipi enum che definiscono una il livello (VERBOSE, DEBUG, INFO, WARNING, ERROR)
 * e uno che identifica lo stage da loggare (lifecycle, test, database, generico).
 * Il metodo generico logga con livello INFO e con STAGE_APP.
 *
 * Created by roberto.gatti on 22/01/2015.
 */
public final class Logger
{
    /* ********************************************************************* */
    /*                           LOGGING CONSTANTS                           */
    /* ********************************************************************* */

    /**
     * Tag di default per i messaggi di log dell'applicazione.
     */
    public static final String DEFAULT_LOG_TAG = "org.bob.android.app.supermarket";

    /**
     * Livello di log dell'applicazione.
     */
    //public static final Level MIN_LOG_LEVEL = Level.VERBOSE;

    /**
     * Boolean per il log dei messaggi relativi al lifecycle.
     */
    public static final boolean LOG_LFC_MESSAGES = true;

    /**
     * Boolean per il log dei messaggi relativi al testing.
     */
    public static final boolean LOG_TST_MESSAGES = true;

    /**
     * Boolean per il log dei messaggi relativi al database.
     */
    public static final boolean LOG_DTB_MESSAGES = true;

    /**
     * Boolean per il log dei messaggi generici dell'applicazione.
     */
    //public static final boolean LOG_APP_MESSAGES = true;

    /**
     * Tipo enum per lo stage da censire.
     */
    public static enum Stages {
        STAGE_LFC ("[LFC] "),
        STAGE_TST ("[TST] "),
        STAGE_APP ("[APP] "),
        STAGE_DTB ("[DTB] ");

        private String stage;

        Stages(String stage) { this.stage = stage; }
    }

    /**
     * Level del logging.
     */
    public static enum Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    /**
     * Metodo principale di logging.
     *
     * @param message il messaggio da loggare
     * @param stage lo stage del messaggio
     * @param level il livello di log del messaggio
     * @param tag tag per il logging
     */
    public static void writeLog(String message, Stages stage, Level level, String tag)
    {
        switch ( level )
        {
            case VERBOSE: Log.v(tag, stage.stage + message); break;
            case DEBUG  : Log.d(tag, stage.stage + message); break;
            case INFO   : Log.i(tag, stage.stage + message); break;
            case WARNING: Log.w(tag, stage.stage + message); break;
            case ERROR  : Log.e(tag, stage.stage + message); break;
            default     : Log.i(tag, Stages.STAGE_APP.stage + message);
        }
    }

    /**
     * Metodo standard di log. Utilizza @see Stages.STAGE_APP e @Level.INFO come parametri
     * di log. Il tag e' generato a partire dal tag di default dell'applicazione.
     * @param message il messaggio da loggare
     */
    public static void writeLog(String message)
    {
        Logger.writeLog(message, Stages.STAGE_APP, Level.INFO, Logger.DEFAULT_LOG_TAG);
    }



    /* SUPPORT LOGGING METHODS */

    /**
     * Logging per i messaggi di lifecycle.
     * @param message il messaggio da loggare
     * @param params varargs per recuperare eventuali parametri aggiuntivi. I parametri
     *               possono essere
     *               <ul>
     *               <li>un oggetto di tipo Level</li>
     *               <li>un oggetto di tipo Class (per generare il tag)</li>
     *               </ul>
     */
    public static void lfc_log(String message, Object ... params)
    {
        if ( !Logger.LOG_LFC_MESSAGES ) return;
        Level level = Level.INFO;
        String tag = Logger.DEFAULT_LOG_TAG;
        if ( params != null )
        {
            if ( params.length > 0 && params[0] instanceof Level) level = (Level) params[0];
            if ( params.length > 1 && params[1] instanceof Class) tag   = ( (Class) params[0]).getName();
        }
        Logger.writeLog(message, Stages.STAGE_LFC, level, tag);
    }

    /**
     * Logging per i messaggi di test.
     * @param message il messaggio da loggare
     * @param params varargs per recuperare eventuali parametri aggiuntivi. I parametri
     *               possono essere
     *               <ul>
     *               <li>un oggetto di tipo Level</li>
     *               <li>un oggetto di tipo Class (per generare il tag)</li>
     *               </ul>
     */
    public static void tst_log(String message, Object ... params)
    {
        if ( !Logger.LOG_TST_MESSAGES ) return;
        Level level = Level.INFO;
        String tag = Logger.DEFAULT_LOG_TAG;
        if ( params != null )
        {
            if ( params.length > 0 && params[0] instanceof Level) level = (Level) params[0];
            if ( params.length > 1 && params[1] instanceof Class) tag   = ( (Class) params[0]).getName();
        }
        Logger.writeLog(message, Stages.STAGE_TST, level, tag);
    }

    /**
     *
     * Logging per i messaggi relativi al database.
     * @param message il messaggio da loggare
     * @param params varargs per recuperare eventuali parametri aggiuntivi. I parametri
     *               possono essere
     *               <ul>
     *               <li>un oggetto di tipo Level</li>
     *               <li>un oggetto di tipo Class (per generare il tag)</li>
     *               </ul>
     */
    public static void dtb_log(String message, Object ... params)
    {
        if ( !Logger.LOG_DTB_MESSAGES ) return;
        Level level = Level.INFO;
        String tag = Logger.DEFAULT_LOG_TAG;
        if ( params != null )
        {
            if ( params.length > 0 && params[0] instanceof Level) level = (Level) params[0];
            if ( params.length > 1 && params[1] instanceof Class) tag   = ( (Class) params[0]).getName();
        }
        Logger.writeLog(message, Stages.STAGE_DTB, level, tag);
    }

    /**
     * Logging relativo ai messaggi generici dell'app.
     * @param message il messaggio da loggare
     * @param params varargs per recuperare eventuali parametri aggiuntivi. I parametri
     *               possono essere
     *               <ul>
     *               <li>un oggetto di tipo Level</li>
     *               <li>un oggetto di tipo Class (per generare il tag)</li>
     *               </ul>
     */
    public static void app_log(String message, Object ... params)
    {
        // no controllo perche' questi messaggi li loggo sempre
        Level level = Level.INFO;
        String tag = Logger.DEFAULT_LOG_TAG;
        if ( params != null )
        {
            if ( params.length > 0 && params[0] instanceof Level) level = (Level) params[0];
            if ( params.length > 1 && params[1] instanceof Class) tag   = ( (Class) params[0]).getName();
        }
        Logger.writeLog(message, Stages.STAGE_APP, level, tag);
    }

}
