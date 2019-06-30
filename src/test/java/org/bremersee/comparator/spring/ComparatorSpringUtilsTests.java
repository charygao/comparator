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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.bremersee.comparator.model.ComparatorField;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;

/**
 * The comparator spring utilities tests.
 *
 * @author Christian Bremer
 */
public class ComparatorSpringUtilsTests {

  /**
   * Test to sort.
   */
  @Test
  public void toSort() {

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
   * Test from sort.
   */
  @Test
  public void fromSort() {

    System.out.println("Testing ComparatorSpringUtils 'fromSort' ...");

    ComparatorField field0 = new ComparatorField("f0", true, true, true);
    ComparatorField field1 = new ComparatorField("f1", false, false, false);
    List<ComparatorField> fields = Arrays.asList(field0, field1);
    Sort sort = ComparatorSpringUtils.toSort(fields);
    assertNotNull(sort);

    List<ComparatorField> actualFields = ComparatorSpringUtils.fromSort(sort);
    Assert.assertEquals(fields, actualFields);

    System.out.println("OK\n");
  }

}
