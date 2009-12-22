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

package de.finkhaeuser.dm;

import android.app.Service;

import android.content.Intent;

import android.os.IBinder;

import android.util.Log;


/**
 * Service implementing IDependencyManager
 **/
public class DependencyManagerService extends Service
{
  /***************************************************************************
   * Private constants
   **/
  private static final String LTAG = "DependencyManagerService";



  /***************************************************************************
   * Service implementation
   **/
  @Override
  public IBinder onBind(Intent intent)
  {
    if (IDependencyManager.class.getName().equals(intent.getAction())) {
      return mBinder;
    }
    return null;
  }



  /***************************************************************************
   * IDependencyManager implementation
   **/
  private final IDependencyManager.Stub mBinder = new IDependencyManager.Stub()
  {
    public void resolveDependencies(String packageName)
    {
      Log.d(LTAG, "asked to resolve dependencies for package " + packageName);
      // TODO
    }
  };
}
