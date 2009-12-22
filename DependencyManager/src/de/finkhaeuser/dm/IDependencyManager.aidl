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


/**
 * Service interface for DependencyManager.
 **/
interface IDependencyManager
{
  /**
   * Resolve dependencies for the given package name. That encompasses:
   * - Scanning the package for dependency information
   * - Querying the system for unmet dependencies
   * - Querying data sources for packages that would meet those dependencies
   * - Displaying the results, and offering them to the user for installation.
   **/
  void resolveDependencies(String packageName);

  // TODO break this down into smaller functions and (also) expose them one
  // by one.
}
