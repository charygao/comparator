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

package org.bremersee.comparator.testmodel;

import java.util.Objects;

/**
 * The simple test object with getter.
 *
 * @author Christian Bremer
 */
public class SimpleGetObject {

  private int no;

  /**
   * Instantiates a new simple object with getter.
   *
   * @param number the number
   */
  public SimpleGetObject(int number) {
    this.no = number;
  }

  private int getNumber() {
    return no;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SimpleGetObject)) {
      return false;
    }
    SimpleGetObject that = (SimpleGetObject) o;
    return getNumber() == that.getNumber();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNumber());
  }
}
