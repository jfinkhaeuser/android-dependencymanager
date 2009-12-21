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

package de.finkhaeuser.dm.common;

import android.net.Uri;

import android.content.Intent;

import java.util.List;
import java.util.LinkedList;


/**
 * Intents
 **/
public class Intents
{
  /***************************************************************************
   * Actions
   **/
  // Action for installing packages
  public static final String ACTION_PACKAGE_INSTALL = "de.finkhaeuser.dm.intent.action.PACKAGE_INSTALL";



  /***************************************************************************
   * Convenience functions
   **/

  /**
   * Parses serialized Intents from the query string of the given URI, assuming
   * the parameter key of DepedencyManagerContract.QUERY_PARAM_INTENT. Returns
   * null if no intents are found in the Uri.
   **/
  public static List<Intent> parseIntents(Uri uri)
  {
    List<String> values = uri.getQueryParameters(
        DependencyManagerContract.QUERY_PARAM_INTENT);
    if (null == values) {
      return null;
    }

    LinkedList<Intent> results = new LinkedList<Intent>();
    for (String v : values) {
      try {
        results.add(Intent.parseUri(v, Intent.URI_INTENT_SCHEME));
      } catch (java.net.URISyntaxException ex) {
        // pass
      }
    }

    if (0 >= results.size()) {
      return null;
    }

    return results;
  }



  /**
   * Serializes the list of Intents into a query string format, using
   * DependencyManagerContract.QUERY_PARAM_INTENT as the parameter key.
   * Returns null if the list is null or empty.
   **/
  public static String serializeIntents(List<Intent> intents)
  {
    if (null == intents || 0 >= intents.size()) {
      return null;
    }

    String result = new String();
    for (Intent i : intents) {
      result = String.format("%s&%s=%s", result,
          DependencyManagerContract.QUERY_PARAM_INTENT,
          i.toUri(Intent.URI_INTENT_SCHEME));
    }

    if (0 >= result.length()) {
      return null;
    }

    // Trim leading ampersand
    return result.substring(1);
  }



  /**
   * Serialize a single Intent. @see serializeIntents
   **/
  public static String serializeIntent(Intent intent)
  {
    return String.format("%s=%s",
        DependencyManagerContract.QUERY_PARAM_INTENT,
        intent.toUri(Intent.URI_INTENT_SCHEME));
  }

}
