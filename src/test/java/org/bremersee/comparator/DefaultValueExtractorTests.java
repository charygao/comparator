package org.bremersee.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

}