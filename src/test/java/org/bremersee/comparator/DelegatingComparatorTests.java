package org.bremersee.comparator;

import static org.junit.Assert.assertEquals;
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
  @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
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

}