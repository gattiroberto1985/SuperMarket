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

package org.bob.android.supermarket.exceptions;

public class SuperMarketException extends Exception 
{
	/**
	 * Stringa di utilita' per il logging
	 */
	private static final String TAG = SuperMarketException.class.getName();

	/**
	 * Il messaggio di eccezione
	 */
	private String message;
	
	/**
	 * L'inner exception eventualmente generante l'eccezione
	 */
	private Throwable t;

	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Costruttore ad un parametro
	 * @param message La stringa di dettaglio dell'errore
	 */
	public SuperMarketException(String message) 
	{
		super(message);
		this.message = message;
		this.t = null;
	}

	/**
	 * Costruttore ad un parametro che specifica una eccezione interna
	 * @param T un oggetto Throwable generante l'eccezione
	 */
	public SuperMarketException(Throwable T)
	{
		super(T);
		this.t = T;
		this.message = "Eccezione interna: " + this.t.getMessage();
	}
	
	/**
	 * Costruttore a due parametri
	 * @param message Il messaggio dell'eccezione
	 * @param t un oggetto Throwable 
	 */
	public SuperMarketException(String message, Throwable t)
	{
		super(message, t);
		this.message = message;
		this.t = t;
	}
}
