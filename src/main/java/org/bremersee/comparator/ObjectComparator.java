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

import java.util.Comparator;
import org.bremersee.comparator.model.ComparatorItem;

/**
 * An object comparator compares any kind of objects. How the objects should be compared is defined
 * by a {@link ComparatorItem}.
 *
 * <p>An instance of an object comparator can be obtained from the
 * {@link ObjectComparatorFactory}:
 *
 * <pre>
 * ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
 * ObjectComparator comparator = factory.newObjectComparator(
 *     new ComparatorItem("attributeName", true));
 * </pre>
 *
 * <p>A ComparatorItem defines how the objects should be compared. It contains the
 * name of the attribute, the ordering (ascending or descending sort order) and optionally the next
 * comparator item, so that a chain can be build:
 *
 * <pre>
 * ComparatorItem item = new ComparatorItem("attributeName1", true);
 * item.next("attributeName2", false).next("attributeName3", true);
 * </pre>
 *
 * <p>The attribute name can be a path to the attribute, too:
 *
 * <pre>
 * ComparatorItem item = new ComparatorItem("address.streetName", true);
 * </pre>
 *
 * <p>The classes may look like this:
 *
 * <pre>
 * public class Person {
 *     private Address address;
 *     // getter and setter
 * }
 *
 * public class Address {
 *     private String streetName;
 *     // getter and setter
 * }
 * </pre>
 *
 * <p>If the comparator item is empty, the objects must implements {@link Comparable}.
 *
 * <pre>
 * ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
 * ObjectComparator comparator = factory.newObjectComparator(new ComparatorItem());
 * </pre>
 *
 * @author Christian Bremer
 * @see Comparator
 */
public interface ObjectComparator extends Comparator<Object> {

  /**
   * Gets the comparator item which is used by this comparator.
   *
   * @return the comparator item which is used by this comparator
   */
  ComparatorItem getComparatorItem();

}
