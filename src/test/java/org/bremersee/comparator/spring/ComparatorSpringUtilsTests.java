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

package org.bremersee.comparator.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bremersee.comparator.model.ComparatorField;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;

/**
 * The comparator spring utilities tests.
 *
 * @author Christian Bremer
 */
class ComparatorSpringUtilsTests {

  /**
   * Test to sort.
   */
  @Test
  void toSort() {

    System.out.println("Testing ComparatorSpringUtils 'toSort' ...");

    ComparatorField field0 = new ComparatorField("f0", true, true, true);
    ComparatorField field1 = new ComparatorField("f1", false, false, false);
    List<ComparatorField> fields = Arrays.asList(field0, field1);

    Sort sort = ComparatorSpringUtils.toSort(fields);

    assertNotNull(sort);

    Sort.Order sortOrder = sort.getOrderFor("f0");
    assertNotNull(sortOrder);
    assertTrue(sortOrder.isAscending());
    assertTrue(sortOrder.isIgnoreCase());
    assertEquals(NullHandling.NULLS_FIRST, sortOrder.getNullHandling());

    sortOrder = sort.getOrderFor("f1");
    assertNotNull(sortOrder);
    assertFalse(sortOrder.isAscending());
    assertFalse(sortOrder.isIgnoreCase());
    assertEquals(NullHandling.NULLS_LAST, sortOrder.getNullHandling());

    System.out.println("OK\n");
  }

  /**
   * To sort with empty list.
   */
  @Test
  void toSortWithEmptyList() {
    Sort sort = ComparatorSpringUtils.toSort(Collections.emptyList());
    assertTrue(sort.isUnsorted());
  }

  /**
   * Test from sort.
   */
  @Test
  void fromSort() {

    System.out.println("Testing ComparatorSpringUtils 'fromSort' ...");

    ComparatorField field0 = new ComparatorField("f0", true, true, true);
    ComparatorField field1 = new ComparatorField("f1", false, false, false);
    List<ComparatorField> fields = Arrays.asList(field0, field1);
    Sort sort = ComparatorSpringUtils.toSort(fields);
    assertNotNull(sort);

    List<ComparatorField> actualFields = ComparatorSpringUtils.fromSort(sort);
    assertEquals(fields, actualFields);

    System.out.println("OK\n");
  }

  /**
   * From sort with null.
   */
  @Test
  void fromSortWithNull() {
    List<ComparatorField> fields = ComparatorSpringUtils.fromSort(null);
    assertTrue(fields.isEmpty());
  }

  /**
   * To sort order.
   */
  @Test
  void toSortOrder() {
    assertNull(ComparatorSpringUtils.toSortOrder(null));
    assertNull(ComparatorSpringUtils.toSortOrder(new ComparatorField()));
    assertNull(ComparatorSpringUtils.toSortOrder(new ComparatorField("", true, true, true)));
  }

  /**
   * From sort order.
   */
  @Test
  void fromSortOrder() {
    assertNull(ComparatorSpringUtils.fromSortOrder(null));
  }

}
