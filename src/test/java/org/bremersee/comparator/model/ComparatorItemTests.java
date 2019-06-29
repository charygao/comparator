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

package org.bremersee.comparator.model;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.Before;
import org.junit.Test;

/**
 * The comparator item tests.
 *
 * @author Christian Bremer
 */
public class ComparatorItemTests {

  private JAXBContext jaxbContext;

  /**
   * Create jaxb context.
   *
   * @throws JAXBException the jaxb exception
   */
  @Before
  public void createJaxbContext() throws JAXBException {
    this.jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
  }

  /**
   * Test xml comparator item.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXmlComparatorItem() throws Exception {

    System.out.println("Testing XML write-read operations ...");

    ComparatorItem item = new ComparatorItem("i0", true);
    item.next("i1", false).next("i2", true);

    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    StringWriter sw = new StringWriter();

    marshaller.marshal(item, sw);

    String xmlStr = sw.toString();

    System.out.println(xmlStr);

    ComparatorItem readItem = (ComparatorItem) jaxbContext.createUnmarshaller()
        .unmarshal(new StringReader(xmlStr));

    System.out.println(item.toList(true));
    System.out.println(readItem.toList(true));

    assertEquals(item.toList(true), readItem.toList(true));

    System.out.println("OK\n");
  }

  /**
   * Test json comparator item.
   *
   * @throws Exception the exception
   */
  @Test
  public void testJsonComparatorItem() throws Exception {

    System.out.println("Testing JSON write-read operations ...");

    ComparatorItem item = new ComparatorItem("i0", true);
    item.next("i1", false).next("i2", true);

    ObjectMapper om = new ObjectMapper();

    String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(item);

    System.out.println(jsonStr);

    ComparatorItem readItem = om.readValue(jsonStr, ComparatorItem.class);

    System.out.println(item.toList(true));
    System.out.println(readItem.toList(true));

    assertEquals(item.toList(true), readItem.toList(true));

    System.out.println("OK\n");
  }

  /**
   * Test comparator item circle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testComparatorItemCirclePart0() {

    System.out.println("Testing circle part A0 ...");
    ComparatorItem i0 = new ComparatorItem("i0", true);
    i0.setNextComparatorItem(i0);
    System.out.println("OK\n");
  }

  /**
   * Test comparator item circle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testComparatorItemCirclePart1() {

    System.out.println("Testing circle part A1 ...");
    ComparatorItem i0 = new ComparatorItem("i0", true);
    ComparatorItem i1 = new ComparatorItem("i1", true);
    try {
      i1.setNextComparatorItem(i0);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("An IllegalArgumentException should not happen here.");
    }
    i0.setNextComparatorItem(i1);
    System.out.println("OK\n");
  }

  /**
   * Test comparator item circle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testComparatorItemCirclePart2() {

    System.out.println("Testing circle part A2 ...");
    ComparatorItem i0 = new ComparatorItem("i0", true);
    ComparatorItem i1 = new ComparatorItem("i1", false);
    ComparatorItem i2 = new ComparatorItem("i2", true);
    try {
      i0.setNextComparatorItem(i1);
      i2.setNextComparatorItem(i0);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("An IllegalArgumentException should not happen here.");
    }
    i1.setNextComparatorItem(i2);
    System.out.println("OK\n");
  }

}
