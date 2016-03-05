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

package org.bob.android.supermarket;

import android.app.Application;
import android.app.Service;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.view.LayoutInflater;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.cp.SuperMarketCP;
import org.bob.android.supermarket.utilities.DBConstants;

import java.util.ResourceBundle;

/**
 *
 * Application class per l'applicazione. Contiene metodi di utilita' generica per
 * il recupero di oggetti quali il context dell'applicazione, le risorse e il
 * layout inflater.
 *
 * Created by roberto.gatti on 22/01/2015.
 */
public class ApplicationSM extends Application
{

    /**
     * Istanza del singleton.
     */
    private static ApplicationSM singleton;

    /**
     * Getter per l'istanza singleton di classe.
     * @return
     */
    public static ApplicationSM getInstance()
    {
        return singleton;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.lfc_log("Creating application class for SuperMarket...");
        ApplicationSM.singleton = this;
    }

    /* ********************************************************************* */
    /*                               CLASS METHODS                           */
    /* ********************************************************************* */

    /**
     * Ritorna un LayoutInflater da utilizzare per le view.
     * @return
     */
    public static LayoutInflater getLayoutInflater()
    {
        Logger.lfc_log("Getting layout inflater from application context...");
        return (LayoutInflater) ApplicationSM.getInstance().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    }

}
