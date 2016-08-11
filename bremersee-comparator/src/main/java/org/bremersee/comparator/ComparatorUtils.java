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
public abstract class ComparatorUtils {

    private ComparatorUtils() {
    }
    
    public static void doSetAscRecursively(ComparatorItem comparatorItem, boolean asc) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setAsc(asc);
            tmp = tmp.getNextComparatorItem();
        }
    }

    public static void doSetIgnoreCaseRecursively(ComparatorItem comparatorItem, boolean ignoreCase) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setIgnoreCase(ignoreCase);
            tmp = tmp.getNextComparatorItem();
        }
    }

    public static void doSetNullIsFirstRecursively(ComparatorItem comparatorItem, boolean nullIsFirst) {
        ComparatorItem tmp = comparatorItem;
        while (tmp != null) {
            tmp.setNullIsFirst(nullIsFirst);
            tmp = tmp.getNextComparatorItem();
        }
    }

}
