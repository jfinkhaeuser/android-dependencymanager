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

package de.finkhaeuser.dm.testapp;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;

import android.content.Intent;

import de.finkhaeuser.dm.client.DependencyManager;

import android.util.Log;


public class TestApp extends Activity implements DependencyManager.BindListener
{
  public static final String LTAG = "TestApp";

  private DependencyManager mDependencyManager;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);


    // Test service!
    DependencyManager.bindService(this, this);
  }



  public void onBound(DependencyManager dm)
  {
    mDependencyManager = dm;
    mDependencyManager.resolveDependencies(getPackageName());
  }



  @Override
  public void onStop()
  {
    super.onStop();
    mDependencyManager.unbindService();
  }

}
