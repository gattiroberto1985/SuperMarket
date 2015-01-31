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
