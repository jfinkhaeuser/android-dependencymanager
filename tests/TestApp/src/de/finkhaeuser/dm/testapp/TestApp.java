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

import android.database.Cursor;
import android.database.ContentObserver;
import android.database.DataSetObserver;

import android.net.Uri;

import java.util.LinkedList;

import de.finkhaeuser.dm.common.DependencyManagerContract;
import de.finkhaeuser.dm.common.DependencyManagerContract.DependencyColumns;
import de.finkhaeuser.dm.common.Intents;

import de.finkhaeuser.dm.client.DependencyManager;

import android.util.Log;


public class TestApp extends Activity implements DependencyManager.BindListener
{
  public static final String LTAG = "TestApp";

  private DependencyManager mDependencyManager;

  class Foo extends ContentObserver
  {
    Cursor mCursor;
    public Foo(Cursor c)
    {
      super(new Handler());
      mCursor = c;
    }

    public void onChange(boolean selfChange)
    {
      Log.d(LTAG, "on change");
      mCursor.requery();
      Log.d(LTAG, "size after requery: " + mCursor.getCount());
      mCursor.moveToFirst();
      do {
        String pkg = mCursor.getString(mCursor.getColumnIndex(
              DependencyColumns.APP_PACKAGE));
        Log.d(LTAG, "package: " + pkg);
        mCursor.moveToNext();
      } while (!mCursor.isAfterLast());

    }
  }


  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Create an Intent that we'll query for. XXX This is manual now, but should
    // be read from the mandatory intents in the longer term.
    LinkedList<Intent> il = new LinkedList<Intent>();

    Intent i = new Intent();
    i.setAction("dummyAction");
    i.addCategory("dummyCategory");
    il.add(i);
    i = new Intent();
    i.setAction("action2");
    i.addCategory("cat2");
    il.add(i);

    Uri uri = Uri.parse(String.format("content://%s/%s?%s",
            DependencyManagerContract.CONTENT_AUTHORITY,
            DependencyManagerContract.PATH_LIST_CANDIDATES,
            Intents.serializeIntents(il)));

    // XXX This'll need to change. It's just to launch the DpendencyManager
    // right now.
    Cursor managedCursor = managedQuery(uri,
        DependencyColumns.CANDIDATE_PROJECTION,
        null,          // WHERE clause.
        null,          // WHERE clause value substitution
        null);   // Sort order.

    Log.d(LTAG, "count: " + managedCursor.getCount());
    managedCursor.registerContentObserver(new Foo(managedCursor));

    if (0 < managedCursor.getCount()) {
      managedCursor.moveToFirst();
      do {
        String pkg = managedCursor.getString(managedCursor.getColumnIndex(
              DependencyColumns.APP_PACKAGE));
        Log.d(LTAG, "package: " + pkg);
        managedCursor.moveToNext();
      } while (!managedCursor.isAfterLast());
    }


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
