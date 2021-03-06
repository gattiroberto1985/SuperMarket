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

import android.app.AlertDialog;
import android.os.Environment;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.bob.android.supermarket.ApplicationSM;
import org.bob.android.supermarket.R;
import org.bob.android.supermarket.exceptions.SuperMarketException;
import org.bob.android.supermarket.logger.Logger;
import org.bob.android.supermarket.persistence.beans.BeanFactory;
import org.bob.android.supermarket.persistence.beans.ExpenseArticleBean;
import org.bob.android.supermarket.persistence.beans.ExpenseBean;
import org.bob.android.supermarket.persistence.beans.ShopBean;
import org.bob.android.supermarket.persistence.cp.SMDBHelper;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * Classe con costanti di utilita' per l'applicazione.
 * @author bob
 *
 */
public final class Utilities
{

    private static Random rand = new Random();

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max)
    {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = Utilities.rand.nextInt((max - min)) + min;
        return randomNum;
    }

	/**
	 * Il metodo trasforma una lista in una arraylist
	 * @param list la lista da riempire
	 * @param array l'array da trasformare
	 */
	public static <T> void arrayToList(List<T> list, T[] array)
	{
		if ( list == null ) 
		{
            Logger.app_log("ERROR: input list is null!", Logger.Level.ERROR);
            return;
		}
		/*if ( list.size() < array.length ) 
		{
			Log.e(TAG, "ERRORE: la lista e l'array non hanno la stessa dimensione!");
			return;
		}*/
		for ( T object : array ) list.add(object);
	}	
	

	/**
	 * Il metodo esegue la copia di un file da un path ad un altro.
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean copyFile(String from, String to) 
	{
	    try 
	    {
	        int bytesum = 0;
	        int byteread = 0;
	        File oldfile = new File(from);
	        if (oldfile.exists()) 
	        {
	            InputStream inStream = new FileInputStream(from);
	            FileOutputStream fs = new FileOutputStream(to);
	            byte[] buffer = new byte[1444];
	            while ((byteread = inStream.read(buffer)) != -1) 
	            {
	                bytesum += byteread;
	                fs.write(buffer, 0, byteread);
	            }
	            inStream.close();
	            fs.close();
	        }
	        return true;
	    } 
	    catch (Exception e)
	    {
	        Logger.app_log("ERROR: exception caught on file copy: " + e.getMessage(), Logger.Level.ERROR);
	        return false;
	    }
	}

	/**
	 * This method check a string to be a well-formed date string.
	 *
	 * TODO: called a deprecated method: review the logic !
	 *
	 * @param dateStr the string date to check
	 *
	 * @return a Date object
     */
	public static Date checkDate(String dateStr)
	{
		Logger.app_log("Checking date '" + dateStr + "' . . . ", Logger.Level.INFO);
		return new Date( Date.parse(dateStr) );
	}

	/**
	 * The method copy the SQL database to the external sd card.
	 *
	 * @throws IOException
	 */
	public static String copyDatabaseToExtSdCard() throws IOException {
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();

		if (sd.canWrite()) {
			String currentDBPath = "/data/data/" + ApplicationSM.getInstance().getPackageName() + "/databases/" + DBConstants.DATABASE_NAME;
			File devFolder = new File(sd, "DEVELOPER/" + ApplicationSM.getInstance().getPackageName());
			if ( ! devFolder.exists() )
				devFolder.mkdirs();
			String backupDBPath = DBConstants.DATABASE_NAME; // + "." + String.valueOf( (new Date()).getTime() );
			File currentDB = new File(currentDBPath);
			File backupDB = new File(devFolder, backupDBPath);

			if (currentDB.exists()) {
				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(backupDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
				return backupDB.getAbsolutePath();
			}
		}
		return null;
	}
}
