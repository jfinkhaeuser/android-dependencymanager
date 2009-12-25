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

import android.os.IBinder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import de.finkhaeuser.dm.common.Intents;
import de.finkhaeuser.dm.common.Schemas;

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
      // Parse intents from the package.
      List<Intent> il = scanPackageForDependencies(packageName);
      if (null == il) {
        // Done. No dependencies are specified, or parsing failed.
        return;
      }

      // Filter out resolvable intents
      il = removeResolvableIntents(il);
      if (null == il) {
        // Done. All dependencies are resolvable.
        return;
      }

      // For the remaining intents, query the DependencyResolutionProvider for
      // possible matches.
      displayChoicesForIntents(il);
    }



    public List<Intent> scanPackageForDependencies(String packageName)
    {
      return Intents.parseIntents(DependencyManagerService.this, packageName);
    }



    public List<Intent> removeResolvableIntents(List<Intent> intents)
    {
      if (null == intents || 0 >= intents.size()) {
        return null;
      }


      PackageManager pm = getPackageManager();
      LinkedList<Intent> results = new LinkedList<Intent>();

      for (Intent i : intents) {
        String componentType = i.getStringExtra(
            Schemas.Client.EXTRA_COMPONENT_TYPE);

        if (Schemas.Client.CT_RECEIVER.equals(componentType)) {
          // "receiver"
          List<ResolveInfo> info = pm.queryBroadcastReceivers(i, 0);
          if (null == info || 0 >= info.size()) {
            results.add(i);
          }
        }

        else if (Schemas.Client.CT_SERVICE.equals(componentType)) {
          // "service"
          List<ResolveInfo> info = pm.queryIntentServices(i, 0);
          if (null == info || 0 >= info.size()) {
            results.add(i);
          }
        }

        else {
          // "activity", default & lacuna
          List<ResolveInfo> info = pm.queryIntentActivities(i, 0);
          if (null == info || 0 >= info.size()) {
            results.add(i);
          }
        }
      }


      if (0 >= results.size()) {
        return null;
      }
      return results;
    }



    public void displayChoicesForIntents(List<Intent> intents)
    {
      if (null == intents || 0 >= intents.size()) {
        Log.e(LTAG, "Intent list is empty.");
        return;
      }

      // In order to serialize the intents list into a new Intent, we'll need
      // to create an ArrayList first.
      ArrayList<Intent> al = new ArrayList<Intent>(intents);

      // Now create the Intent to launch ResolverActivity with, passing the
      // array list as an extra.
      Intent i = new Intent(DependencyManagerService.this, ResolverActivity.class);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.putParcelableArrayListExtra(ResolverActivity.EXTRA_INTENTS, al);

      startActivity(i);
    }
  };
}
