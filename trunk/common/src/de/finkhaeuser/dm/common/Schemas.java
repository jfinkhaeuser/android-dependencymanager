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

/**
 * Constants related to XML namespace schemas
 **/
public class Schemas
{
  /**
   * String constants related to the data source schema for DependencyManager
   * http://schemas.finkhaeuser.de/android/dependency-manager/1.0/source
   **/
  public static class Source
  {
    // Not strictly speaking part of the schema, but the meta-data label used
    // for pointing to a resource file that follows this schema.
    public static String META_DATA_LABEL        = "de.finkhaeuser.dm.dependency-source";

    // Schema name
    public static String SCHEMA                 = "http://schemas.finkhaeuser.de/android/dependency-manager/1.0/source";

    // The schema defines a single element.
    public static String ELEM_DEPENDENCY_SOURCE = "dependency-source";

    // ELEM_DEPENDENCY_SOURCE defines a single attribute: the ContentProvider
    // authority to use when fetching dependency information from this data
    // source.
    public static String ATTR_AUTHORITY         = "authority";
  }



  /**
   * String constants related to the client schema for DependencyManager
   * http://schemas.finkhaeuser.de/android/dependency-manager/1.0/client
   **/
  public static class Client
  {
    // Not strictly speaking part of the schema, but the meta-data label used
    // for pointing to a resource file that follows this schema.
    public static String META_DATA_LABEL        = "de.finkhaeuser.dm.dependency-client";

    // Schema name
    public static String SCHEMA                 = "http://schemas.finkhaeuser.de/android/dependency-manager/1.0/client";

    // TODO
  }
}
