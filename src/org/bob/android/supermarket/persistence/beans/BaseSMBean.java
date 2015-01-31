package org.bob.android.supermarket.persistence.beans;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by roberto.gatti on 22/01/2015.
 */
public abstract class BaseSMBean implements Serializable
{
    public abstract ContentValues getContentValues();
}
