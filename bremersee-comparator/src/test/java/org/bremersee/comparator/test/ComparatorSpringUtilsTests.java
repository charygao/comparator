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

package org.bremersee.comparator.test;

import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.comparator.spring.ComparatorSpringUtils;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class ComparatorSpringUtilsTests {
    
    @Test
    public void testSpringUtils() throws Exception {
        
        System.out.println("Testing ComparatorSpringUtils ...");

        ComparatorItem comparatorItem = new ComparatorItem("lastName", true, true);
        comparatorItem.next("firstName", true, false);
        
        Sort sort = ComparatorSpringUtils.toSort(comparatorItem);
        
        ComparatorItem testComparatorItem = ComparatorSpringUtils.fromSort(sort);
        TestCase.assertEquals(comparatorItem, testComparatorItem);
        TestCase.assertEquals(comparatorItem.getNextComparatorItem(), testComparatorItem.getNextComparatorItem());

        System.out.println("OK\n");
    }

}
