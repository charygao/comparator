/*
 * Copyright 2015 the original author or authors.
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

import java.util.Comparator;

/**
 * <p>
 * An object comparator compares any kind of objects. How the objects should be
 * compared is defined by a {@link ComparatorItem}.
 * </p>
 * <p>
 * An instance of an object comparator can be obtained from the
 * {@link ObjectComparatorFactory}:
 * <p>
 * <pre>
 * ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
 * ObjectComparator comparator = factory.newObjectComparator(new ComparatorItem("attributeName", true));
 * </pre>
 * </p>
 * <p>
 * A ComparatorItem defines how the objects should be compared. It contains the
 * name of the attribute, the ordering (ascending or descending sort order) and
 * optionally the next comparator item, so that a chain can be build:
 * <p>
 * <pre>
 * ComparatorItem item = new ComparatorItem("attributeName1", true);
 * item.next("attributeName2", false).next("attributeName3", true);
 * </pre>
 * </p>
 * <p>
 * The attribute name can be a path to the attribute, too:
 * <p>
 * <pre>
 * ComparatorItem item = new ComparatorItem("address.streetName", true);
 * </pre>
 * </p>
 * <p>
 * The classes may look like this:
 * <p>
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
 * </p>
 * <p>
 * If the comparator item is empty:
 * <p>
 * <pre>
 * ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
 * ObjectComparator comparator = factory.newObjectComparator(new ComparatorItem());
 * </pre>
 * <p>
 * the objects must implements {@link Comparable}.
 * </p>
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
