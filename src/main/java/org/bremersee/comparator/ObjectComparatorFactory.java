/*
 * Copyright 2015-2019 the original author or authors.
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
 */

package org.bremersee.comparator;

import org.bremersee.comparator.model.ComparatorItem;

/**
 * Factory to create a new {@link ObjectComparator}.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public abstract class ObjectComparatorFactory {

  /**
   * Create a new instance of a {@link ObjectComparatorFactory}.
   *
   * @return a new instance of a {@link ObjectComparatorFactory}
   */
  public static ObjectComparatorFactory newInstance() {
    return new DefaultObjectComparatorFactory();
  }

  /**
   * Create a new instance of a the specified factory class.
   *
   * @param objectComparatorFactoryClassName the factory class
   * @return a new instance of a the specified factory
   * @throws InstantiationException if creation of the factory fails
   * @throws IllegalAccessException if creation of the factory fails
   * @throws ClassNotFoundException if creation of the factory fails
   */
  public static ObjectComparatorFactory newInstance(
      final String objectComparatorFactoryClassName)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return (ObjectComparatorFactory) Class.forName(objectComparatorFactoryClassName).newInstance();
  }

  /**
   * Create a new instance of a the specified factory class by using the specified class loader.
   *
   * @param objectComparatorFactoryClassName the factory class
   * @param classLoader                      the class loader
   * @return a new instance of a the specified factory
   * @throws InstantiationException if creation of the factory fails
   * @throws IllegalAccessException if creation of the factory fails
   * @throws ClassNotFoundException if creation of the factory fails
   */
  public static ObjectComparatorFactory newInstance(final String objectComparatorFactoryClassName,
      final ClassLoader classLoader)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return (ObjectComparatorFactory) Class
        .forName(objectComparatorFactoryClassName, true, classLoader)
        .newInstance();
  }

  /**
   * Create a new {@link ObjectComparator} with the specified {@link ComparatorItem}.
   *
   * @param comparatorItem the comparator item which will be used by the comparator (may be {@code
   *                       null})
   * @return a new object comparator
   */
  public abstract ObjectComparator newObjectComparator(ComparatorItem comparatorItem);

  /**
   * The default {@link ObjectComparatorFactory} implementation.
   *
   * @author Christian Bremer
   */
  private static class DefaultObjectComparatorFactory extends ObjectComparatorFactory {

    @Override
    public String toString() {
      return ObjectComparatorFactory.class.getName();
    }

    @Override
    public ObjectComparator newObjectComparator(ComparatorItem comparatorItem) {

      return new ObjectComparatorImpl(comparatorItem);
    }
  }

}
