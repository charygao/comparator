/*
 * Copyright 2016 the original author or authors.
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
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public abstract class ComparatorUtils {

    private ComparatorUtils() {
    }

    /**
     * Sets the sort order of all items.
     *
     * @param comparatorItem the (root) item
     * @param asc            the new sort order
     */
    public static void doSetAscRecursively(ComparatorItem comparatorItem, boolean asc) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setAsc(asc);
            tmp = tmp.getNextComparatorItem();
        }
    }

    /**
     * Sets whether the sort is case sensitive or not to all items.
     *
     * @param comparatorItem the (root) item
     * @param ignoreCase     {@code true} if the sort is case sensitive, otherwise
     *                       {@code false}
     */
    public static void doSetIgnoreCaseRecursively(ComparatorItem comparatorItem, boolean ignoreCase) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setIgnoreCase(ignoreCase);
            tmp = tmp.getNextComparatorItem();
        }
    }

    /**
     * Sets the sort order of a null value to all items.
     *
     * @param comparatorItem the (root) item
     * @param nullIsFirst    {@code true} if the sort order of a null value is higher than
     *                       a non-null value, otherwise {@code false}
     */
    public static void doSetNullIsFirstRecursively(ComparatorItem comparatorItem, boolean nullIsFirst) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setNullIsFirst(nullIsFirst);
            tmp = tmp.getNextComparatorItem();
        }
    }

}
