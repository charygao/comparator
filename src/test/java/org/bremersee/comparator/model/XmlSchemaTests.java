/*
 * Copyright 2019 the original author or authors.
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;

/**
 * The xml schema tests.
 *
 * @author Christian Bremer
 */
public class XmlSchemaTests {

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
   * Test xml schema.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXmlSchema() throws Exception {

    System.out.println("Testing XML schema ...");

    BufferSchemaOutputResolver res = new BufferSchemaOutputResolver();
    jaxbContext.generateSchema(res);
    System.out.print(res);

    System.out.println("OK\n");
  }

}