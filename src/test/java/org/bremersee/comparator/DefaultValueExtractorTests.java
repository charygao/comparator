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

import org.bremersee.comparator.testmodel.ComplexObject;
import org.bremersee.comparator.testmodel.ComplexObjectExtension;
import org.bremersee.comparator.testmodel.SimpleGetObject;
import org.bremersee.comparator.testmodel.SimpleIsObject;
import org.bremersee.comparator.testmodel.SimpleObject;
import org.junit.Test;

/**
 * The default value extractor tests.
 *
 * @author Christian Bremer
 */
public class DefaultValueExtractorTests {

  private final DefaultValueExtractor extractor = new DefaultValueExtractor(false);

  private final DefaultValueExtractor throwingExtractor = new DefaultValueExtractor();

  /**
   * Test returning of given object.
   */
  @Test
  public void testReturningOfGivenObject() {
    assertNull(extractor.findValue(null, "foo"));
    assertNull(extractor.findValue("Object", "foo"));
    assertEquals("Object", extractor.findValue("Object", null));
    assertEquals("Object", extractor.findValue("Object", ""));
  }

  /**
   * Test illegal field and expect exception.
   */
  @Test(expected = ComparatorException.class)
  public void testIllegalFieldAndExpectException() {
    throwingExtractor.findValue("Object", "foo");
  }

  /**
   * Test objects.
   */
  @Test
  public void testObjects() {
    assertEquals(1, extractor.findValue(new SimpleObject(1), "number"));
    assertEquals(2, extractor.findValue(new SimpleGetObject(2), "number"));
    assertEquals(true, extractor.findValue(new SimpleIsObject(true), "nice"));
    assertEquals(
        new SimpleObject(3),
        extractor.findValue(new ComplexObject(new SimpleObject(3)), "simple"));
    assertEquals(
        4,
        extractor.findValue(new ComplexObject(new SimpleObject(4)), "simple.number"));
    assertEquals(
        4,
        extractor.findValue(new ComplexObject(new SimpleObject(4)), ". simple..number."));
    assertEquals(
        5,
        extractor.findValue(
            new ComplexObjectExtension(new SimpleObject(5), ""),
            "simple.number"));
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    DefaultValueExtractor extractor = new DefaultValueExtractor(true);
    assertTrue(extractor.toString().contains("true"));
    extractor = new DefaultValueExtractor(false);
    assertTrue(extractor.toString().contains("false"));
  }

  /**
   * Test equals.
   */
  @SuppressWarnings({"EqualsWithItself", "SimplifiableJUnitAssertion",
      "EqualsBetweenInconvertibleTypes", "ConstantConditions"})
  @Test
  public void testEquals() {
    DefaultValueExtractor extractor = new DefaultValueExtractor(true);
    assertTrue(extractor.equals(extractor));
    assertTrue(extractor.equals(new DefaultValueExtractor(true)));
    assertFalse(extractor.equals(new DefaultValueExtractor(false)));
    assertFalse(extractor.equals("Test"));
    assertFalse(extractor.equals(null));
  }

}