package org.bremersee.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.junit.Test;

/**
 * The comparator chain tests.
 *
 * @author Christian Bremer
 */
public class ComparatorChainTests {

  /**
   * Test empty comparator chain.
   */
  @Test
  public void testEmptyComparatorChain() {
    int result = new ComparatorChain(Collections.emptyList())
        .compare(1, 2);
    assertTrue(result < 0);

    result = new ComparatorChain(Collections.emptyList())
        .compare(2, 1);
    assertTrue(result > 0);

    result = new ComparatorChain(Collections.emptyList())
        .compare(1, 1);
    assertEquals(0, result);
  }

  /**
   * Test not comparable objects and expect comparator exception.
   */
  @Test(expected = ComparatorException.class)
  public void testNotComparableObjectsAndExpectComparatorException() {
    //noinspection ResultOfMethodCallIgnored
    new ComparatorChain(Collections.emptyList())
        .compare(new Object(), new Object());
  }

  /**
   * Test null objects and expect comparator exception.
   */
  @Test(expected = ComparatorException.class)
  public void testNullObjectsAndExpectComparatorException() {
    //noinspection ResultOfMethodCallIgnored
    new ComparatorChain(Collections.emptyList())
        .compare(null, null);
  }

  /**
   * Test two comparators and use both.
   */
  @Test
  @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
  public void testTwoComparatorsAndUseBoth() {
    Comparator comparatorA = mock(Comparator.class);
    when(comparatorA.compare(any(), any())).thenReturn(0);

    Comparator comparatorB = mock(Comparator.class);
    when(comparatorB.compare(any(), any())).thenReturn(-1);

    int result = new ComparatorChain(Arrays.asList(comparatorA, comparatorB))
        .compare(1, 2);

    assertTrue(result < 0);

    verify(comparatorA, times(1)).compare(any(), any());
    verify(comparatorB, times(1)).compare(any(), any());
  }

  /**
   * Test two comparators and use only first.
   */
  @Test
  @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
  public void testTwoComparatorsAndUseOnlyFirst() {
    Comparator comparatorA = mock(Comparator.class);
    when(comparatorA.compare(any(), any())).thenReturn(-1);

    Comparator comparatorB = mock(Comparator.class);
    when(comparatorB.compare(any(), any())).thenReturn(1);

    int result = new ComparatorChain(Arrays.asList(comparatorA, comparatorB))
        .compare(1, 2);

    assertTrue(result < 0);

    verify(comparatorA, times(1)).compare(any(), any());
    verify(comparatorB, times(0)).compare(any(), any());
  }

}