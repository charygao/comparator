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

import org.junit.Test;

/**
 * The comparator exception test.
 *
 * @author Christian Bremer
 */
public class ComparatorExceptionTest {

  /**
   * Test with one argument.
   */
  @Test(expected = ComparatorException.class)
  public void testWithOneArgument() {
    throw new ComparatorException("Test exception");
  }

  /**
   * Test with two arguments.
   */
  @Test(expected = ComparatorException.class)
  public void testWithTwoArguments() {
    throw new ComparatorException("Test exception", new Exception("Cause"));
  }

}