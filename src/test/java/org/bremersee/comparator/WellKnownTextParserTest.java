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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bremersee.comparator.model.ComparatorField;
import org.junit.Test;

/**
 * The well known text parser test.
 *
 * @author Christian Bremer
 */
public class WellKnownTextParserTest {

  private static final WellKnownTextParser parser = ValueComparator::new;

  /**
   * Build comparator field.
   */
  @Test
  public void buildComparatorField() {
    assertEquals(
        new ComparatorField(null, true, true, false),
        parser.buildComparatorField(null));
    assertEquals(
        new ComparatorField(null, true, true, false),
        parser.buildComparatorField(""));
  }

  /**
   * Build comparator field and expect illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buildComparatorFieldAndExpectIllegalArgumentException() {
    parser.buildComparatorField("hallo|world");
  }

  /**
   * Find string part.
   */
  @Test
  public void findStringPart() {
    assertNull(WellKnownTextParser.findStringPart(null, "|", 0));
    assertEquals("abc", WellKnownTextParser.findStringPart("abc", null, 0));
    assertEquals("abc", WellKnownTextParser.findStringPart("abc", "", 0));
    assertNull(WellKnownTextParser.findStringPart("a|b|c", "|", 10));
  }

  /**
   * Find boolean part.
   */
  @Test
  public void findBooleanPart() {
    assertFalse(WellKnownTextParser.findBooleanPart("a|b|c", "|", 10, false));
    assertTrue(WellKnownTextParser.findBooleanPart(
        "a|b|c",
        "|", 2,
        false, "c", "d", "e"));
  }

}