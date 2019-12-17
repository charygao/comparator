package org.bremersee.comparator;

import java.util.Arrays;
import java.util.List;
import org.bremersee.comparator.model.ComparatorField;
import org.bremersee.comparator.model.ComparatorFields;
import org.junit.Assert;
import org.junit.Test;

/**
 * The type Well known text converter test.
 *
 * @author Christian Bremer
 */
public class WellKnownTextConverterTest {

  /**
   * Convert.
   */
  @Test
  public void convert() {
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
    Assert.assertNotNull(fields);
    Assert.assertEquals(expected.size(), fields.getFields().size());
    for (int i = 0; i < expected.size(); i++) {
      Assert.assertEquals(expected.get(i), fields.getFields().get(i));
    }
  }

  /**
   * Convert and expect empty.
   */
  @Test
  public void convertAndExpectEmpty() {
    WellKnownTextConverter converter = new WellKnownTextConverter(ValueComparator::new);
    ComparatorFields fields = converter.convert(null);
    Assert.assertNotNull(fields);
    Assert.assertTrue(fields.getFields().isEmpty());
  }

}