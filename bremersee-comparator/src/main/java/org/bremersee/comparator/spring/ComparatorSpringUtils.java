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

package org.bremersee.comparator.spring;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bremersee.comparator.model.ComparatorItem;
import org.springframework.data.domain.Sort;

/**
 * @author Christian Bremer
 */
public abstract class ComparatorSpringUtils {

    private ComparatorSpringUtils() {
    }

    public static Sort.Order toSortOrder(ComparatorItem comparatorItem) {
        if (comparatorItem == null || comparatorItem.getField() == null
                || comparatorItem.getField().trim().length() == 0) {
            return null;
        }
        Sort.Direction direction = comparatorItem.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.NullHandling nullHandlingHint = comparatorItem.isNullIsFirst() ? Sort.NullHandling.NULLS_FIRST
                : Sort.NullHandling.NULLS_LAST;
        Sort.Order order = new Sort.Order(direction, comparatorItem.getField(), nullHandlingHint);
        if (comparatorItem.isIgnoreCase()) {
            return order.ignoreCase();
        }
        return order;
    }

    public static ComparatorItem fromSortOrder(Sort.Order sortOrder) {
        if (sortOrder == null || sortOrder.getProperty() == null || sortOrder.getProperty().trim().length() == 0) {
            return null;
        }
        boolean nullIsFirst = Sort.NullHandling.NULLS_FIRST.equals(sortOrder.getNullHandling());
        return new ComparatorItem(sortOrder.getProperty(), sortOrder.isAscending(), sortOrder.isIgnoreCase(),
                nullIsFirst);
    }

    public static Sort toSort(ComparatorItem comparatorItem) {
        List<Sort.Order> orderList = new LinkedList<>();
        ComparatorItem item = comparatorItem;
        Sort.Order order = null;
        while ((order = toSortOrder(item)) != null) {
            orderList.add(order);
            item = item.getNextComparatorItem();
        }
        if (orderList.isEmpty()) {
            return null;
        }
        return new Sort(orderList);
    }

    public static ComparatorItem fromSort(Sort sort) {
        if (sort == null) {
            return null;
        }
        ComparatorItem comparatorItem = null;
        Iterator<Sort.Order> orderIter = sort.iterator();
        while (orderIter.hasNext()) {
            Sort.Order order = orderIter.next();
            ComparatorItem item = fromSortOrder(order);
            if (item != null) {
                if (comparatorItem == null) {
                    comparatorItem = item;
                } else {
                    comparatorItem.getLastComparatorItem().setNextComparatorItem(item);
                }
            }
        }
        return comparatorItem;
    }

}
