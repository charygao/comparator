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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Comparator;
import org.junit.Test;

/**
 * The delegating comparator tests.
 *
 * @author Christian Bremer
 */
public class DelegatingComparatorTests {

  /**
   * Test delegating comparator.
   */
  @Test
  @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored", "rawtypes"})
  public void testDelegatingComparator() {
    ValueExtractor valueExtractor = mock(ValueExtractor.class);
    when(valueExtractor.findValue(any(), anyString())).thenReturn(1);

    Comparator comparator = mock(Comparator.class);
    when(comparator.compare(any(), any())).thenReturn(-1);

    DelegatingComparator delegatingComparator = new DelegatingComparator(
        "someField",
        valueExtractor,
        comparator);

    int result = delegatingComparator.compare(1, 2);

    assertEquals(-1, result);
    verify(comparator, times(1)).compare(any(), any());
    verify(valueExtractor, times(2)).findValue(any(), anyString());
  }

  /**
   * Test delegating comparator and expect illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDelegatingComparatorAndExpectIllegalArgumentException() {
    DelegatingComparator delegatingComparator = new DelegatingComparator(
        "someField",
        null);
    int result = delegatingComparator.compare(1, 2);
    assertEquals(-1, result);
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertTrue(new DelegatingComparator("number", null, Comparator.naturalOrder())
        .toString().contains("number"));
  }

  /**
   * Test equals and hash code.
   */
  @SuppressWarnings({"EqualsWithItself", "SimplifiableJUnitAssertion",
      "EqualsBetweenInconvertibleTypes", "ConstantConditions"})
  @Test
  public void testEqualsAndHashCode() {
    ValueExtractor extractor = new DefaultValueExtractor();
    Comparator<?> comparator = Comparator.naturalOrder();
    DelegatingComparator delegate = new DelegatingComparator("number", extractor, comparator);
    assertTrue(delegate.equals(delegate));
    assertTrue(delegate.equals(new DelegatingComparator("number", extractor, comparator)));
    assertFalse(delegate.equals(new DelegatingComparator("ch", extractor, comparator)));
    assertFalse(delegate.equals("test"));
    assertFalse(delegate.equals(null));

    assertEquals(
        new DelegatingComparator("field", comparator),
        new DelegatingComparator("field", comparator));

    assertEquals(
        delegate.hashCode(),
        new DelegatingComparator("number", extractor, comparator).hashCode());
  }

}