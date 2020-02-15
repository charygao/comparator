/*
 * Copyright 2015-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.bremersee.comparator.model.ComparatorField;
import org.bremersee.comparator.model.ComparatorFields;
import org.junit.jupiter.api.Test;

/**
 * The well known text converter test.
 *
 * @author Christian Bremer
 */
class WellKnownTextConverterTest {

  /**
   * Convert.
   */
  @Test
  void convert() {
    WellKnownTextConverter converter = new WellKnownTextConverter();

    ComparatorField field0 = new ComparatorField("field0", true, true, true);
    ComparatorField field1 = new ComparatorField("field1", true, false, true);
    ComparatorField field2 = new ComparatorField("field2", true, true, false);
    ComparatorField field3 = new ComparatorField("field3", true, false, false);
    ComparatorField field4 = new ComparatorField("field4", false, false, true);
    ComparatorField field5 = new ComparatorField("field5", false, false, false);
    List<ComparatorField> expected = Arrays.asList(field0, field1, field2, field3, field4, field5);

    ComparatorFields fields = converter.convert(
        "field0,true,true,true"
            + "|field1,true,false,true"
            + "|field2,true,true,false"
            + "|field3,true,false,false"
            + "|field4,false,false,true"
            + "|field5,false,false,false");
    assertNotNull(fields);
    assertEquals(expected.size(), fields.getFields().size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), fields.getFields().get(i));
    }
  }

  /**
   * Convert and expect empty.
   */
  @Test
  void convertAndExpectEmpty() {
    WellKnownTextConverter converter = new WellKnownTextConverter(ValueComparator::new);
    //noinspection ConstantConditions
    ComparatorFields fields = converter.convert(null);
    assertNotNull(fields);
    assertTrue(fields.getFields().isEmpty());
  }

}