/**
 * This file is part of the Android DependencyManager project hosted at
 * http://code.google.com/p/android-dependencymanager/
 *
 * Copyright (C) 2009 Jens Finkhaeuser <jens@finkhaeuser.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package de.finkhaeuser.dm.client;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.ComponentName;

import android.os.IBinder;
import android.os.RemoteException;

import de.finkhaeuser.dm.IDependencyManager;

import android.util.Log;

/**
 * Client object to the IDependencyManager interface; encapsulates binding to
 * the service.
 **/
public class DependencyManager
{
  /***************************************************************************
   * Private constants
   **/
  private static final String LTAG = "AggregateCursor";


  /***************************************************************************
   * Private data
   **/
  private Context             mContext;

  // Service stub & lock for synchronous access
  private Object              mStubLock = new Object();
  private IDependencyManager  mStub = null;

  // Service connection
  private ServiceConnection   mConnection = new ServiceConnection() {
    public void onServiceConnected(ComponentName className, IBinder service)
    {
      synchronized (mStubLock) {
        mStub = IDependencyManager.Stub.asInterface(service);
      }
    }


    public void onServiceDisconnected(ComponentName className)
    {
      synchronized (mStubLock) {
        mStub = null;
      }
    }
  };



  /***************************************************************************
   * Extra functions
   **/
  public DependencyManager(Context context)
  {
    mContext = context;
  }



  /***************************************************************************
   * Implementation of IDependencyManager API
   **/
  public void resolveDependencies(String packageName)
  {
    // FIXME sort out synchronization

    bindService();
    try {
      mStub.resolveDependencies(packageName);
    } catch (RemoteException ex) {
      Log.e(LTAG, "Exception " + ex.getMessage());
    }
  }



  /***************************************************************************
   * Private functions
   **/
  private void bindService()
  {
    // FIXME sort out synchronization

    mContext.bindService(new Intent(IDependencyManager.class.getName()),
        mConnection, Context.BIND_AUTO_CREATE);
  }
}
