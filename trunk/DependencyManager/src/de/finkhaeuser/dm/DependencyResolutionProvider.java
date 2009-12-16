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

import android.net.Uri;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;

import android.database.Cursor;

import android.util.Log;

/**
 * The DependencyResolutionProvider aggregates results from data sources for
 * dependency, and returns them in a sorted list.
 **/
public class DependencyResolutionProvider extends ContentProvider
{
  /***************************************************************************
   * Public constants
   **/
  // Log Tag
  public static final String LTAG = "DependencyResolutionProvider";

  // Base URI for this ContentProvider
  public static final String CONTENT_AUTHORITY    = "de.finkhaeuser.dm";

  // Content URI paths
  public static final String PATH_LIST_CANDIDATES = "list-candidates";

  // Content Types
  public static final String CANDIDATE_LIST_TYPE  = "vnd.android.cursor.dir/vnd.dependency.candidate";
  public static final String CANDIDATE_TYPE       = "vnd.android.cursor.item/vnd.dependendcy.candidate";

  // CANDIDATE_TYPE-related fields
  // Results always include:
  public static final String STORE_PACKAGE        = "dm_store_package";
  public static final String STORE_DISPLAY_NAME   = "dm_store_display_name";
  public static final String ICON_URI             = "dm_icon_uri";
  // Results either include a non-NULL EXTERNAL_SEARCH_URI...
  public static final String EXTERNAL_SEARCH_URI  = "dm_external_search_uri";
  // ... or the following fields, but never both.
  public static final String APP_PACKAGE          = "dm_app_package";
  public static final String APP_DISPLAY_NAME     = "dm_app_display_name";
  public static final String APP_VENDOR_NAME      = "dm_app_vendor_name";
  public static final String APP_PRICE            = "dm_app_price";
  public static final String APP_CURRENCY         = "dm_app_currency";
  public static final String APP_MATCHES          = "dm_app_matches";



  /***************************************************************************
   * Private constants
   **/
  // IDs for URI matches.
  private static final int ID_LIST_CANDIDATES     = 1;



  /***************************************************************************
   * Static members
   **/
  private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  static {
    sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_LIST_CANDIDATES, ID_LIST_CANDIDATES);
  }



  /***************************************************************************
   * Implementation
   */

  @Override
  public boolean onCreate()
  {
    // TODO scan installed packages for data sources
    return true;
  }



  @Override
  public String getType(Uri uri)
  {
    int match = sUriMatcher.match(uri);

    switch (match) {
      case ID_LIST_CANDIDATES:
        return CANDIDATE_LIST_TYPE;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }



  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder)
  {
    // Ensure the query is valid
    int match = sUriMatcher.match(uri);
    if (UriMatcher.NO_MATCh == match) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // TODO

    return null;
  }



  @Override
  public Uri insert(Uri uri, ContentValues values)
  {
    throw new java.lang.SecurityException(LTAG + " does not support "
        + "write access.");
  }



  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs)
  {
    throw new java.lang.SecurityException(LTAG + " does not support "
        + "write access.");
  }



  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs)
  {
    throw new java.lang.SecurityException(LTAG + " does not support "
        + "write access.");
  }
}
