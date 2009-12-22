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

import android.content.Context;
import android.content.ContentResolver;

import android.database.Cursor;

import android.net.Uri;

import de.finkhaeuser.dm.common.DependencyManagerContract;

import android.util.Log;

/**
 * Runnable for fetching data from a dependency source. Each source can take a
 * while to respond. Fetching from multiple sources would imply that in the
 * worst case, results never make it to the user if the first source blocks
 * indefinitely.
 * By assigning a thread for fetching from each source, and merging the rsults
 * into an AggregateCursor, we'll be able to present updates as quickly as they
 * arrive.
 *
 * XXX Access to the AggregateCursor from these threads are synchronized on the
 *     cursor object itself. Access to the same cursor from other threads must
 *     also be synchronized.
 *
 * TODO: If the sources employ a similar approach to IntentManager, and return
 *       an empty cursor that they'll update as data arrives, then installing
 *       an observer in this thread would be useful.
 **/
class DependencySourceRunnable extends Runnable
{
  /***************************************************************************
   * Private constants
   **/
  public static final String LTAG = "DependencySourceRunnable";



  /***************************************************************************
   * Private data
   **/
  // Context
  private Context           mContext;

  // DependencySource to query
  private DependencySource  mSource;
  // AggregateCursor to insert results into
  private AggregateCursor   mResultCursor;

  // Query-related arguments
  private Uri               mOriginalUri;
  private Uri               mUri;
  private String[]          mProjection;
  private String            mSelection;
  private String[]          mSelectionArgs;



  /***************************************************************************
   * Implementation
   **/
  public DependencySourceRunnable(Context context, AggregateCursor resultCursor,
      DependencySource source)
  {
    super();
    mContext = context;
    mResultCursor = resultCursor;
    mSource = source;
  }



  public void setQuery(Uri uri, String[] projection, String selection,
      String[] selectionArgs)
  {
    mOriginalUri = uri;
    mProjection = projection;
    mSelection = selection;
    mSelectionArgs = selectionArgs;

    // The original URI is for DependencyResolutionManager to handle. Before we
    // can fetch information from this source, we need to swap the original
    // authority for this source's.
    mUri = Uri.parse(mOriginalUri.toString().replace(mOriginalUri.getAuthority(),
          mSource.getContentAuthority()));
  }



  public void run()
  {
    ContentResolver cr = mContext.getContentResolver();

    // Fetch data from the source.
    Cursor c = cr.query(mUri, mProjection, mSelection, mSelectionArgs,
        null);
    if (null == c) {
      return;
    }

    // Merge results into the result cursor
    if (0 < c.getCount()) {
      synchronized (mResultCursor) {
        mResultCursor.merge(c);
      }
      // Notify observers of the result cursor of the changes.
      cr.notifyChange(mOriginalUri, null);
    }
  }
}
