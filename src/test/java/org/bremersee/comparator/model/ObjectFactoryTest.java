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

package org.bremersee.comparator.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The object factory test.
 *
 * @author Christian Bremer
 */
public class ObjectFactoryTest {

  private static final ObjectFactory factory = new ObjectFactory();

  /**
   * Create comparator field.
   */
  @Test
  public void createComparatorField() {
    assertEquals(new ComparatorField(), factory.createComparatorField());
  }

  /**
   * Create comparator fields.
   */
  @Test
  public void createComparatorFields() {
    assertEquals(new ComparatorFields(), factory.createComparatorFields());
  }
}