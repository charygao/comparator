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

package org.bremersee.comparator.test;

import junit.framework.TestCase;
import org.bremersee.comparator.ObjectComparator;
import org.bremersee.comparator.ObjectComparatorFactory;
import org.bremersee.comparator.model.ComparatorItem;
import org.junit.Test;

/**
 * The object comparator tests.
 *
 * @author Christian Bremer
 */
public class ObjectComparatorTests {

  private static ObjectComparator createObjectComparator(ComparatorItem comparatorItem) {
    return ObjectComparatorFactory.newInstance().newObjectComparator(comparatorItem);
  }

  /**
   * Test primitive type.
   */
  @Test
  public void testPrimitiveType() {
    System.out.println("Testing primitive type ...");
    int result = createObjectComparator(null).compare(1, 2);
    System.out.println(result);
    TestCase.assertTrue(result < 0);
    System.out.println("OK\n");
  }

  /**
   * Test simple object.
   */
  @Test
  public void testSimpleObject() {
    System.out.println("Testing simple object ...");
    int result = createObjectComparator(new ComparatorItem("number")).compare(new SimpleObject(1),
        new SimpleObject(2));
    System.out.println(result);
    TestCase.assertTrue(result < 0);
    System.out.println("OK\n");
  }

  /**
   * Test simple get object.
   */
  @Test
  public void testSimpleGetObject() {
    System.out.println("Testing simple 'get' object ...");
    int result = createObjectComparator(new ComparatorItem("number"))
        .compare(new SimpleGetObject(1),
            new SimpleGetObject(2));
    System.out.println(result);
    TestCase.assertTrue(result < 0);
    System.out.println("OK\n");
  }

  /**
   * Test simple is object.
   */
  @Test
  public void testSimpleIsObject() {
    System.out.println("Testing simple 'is' object ...");
    int result = createObjectComparator(new ComparatorItem("nice", false))
        .compare(new SimpleIsObject(true),
            new SimpleIsObject(false));
    System.out.println(result);
    TestCase.assertTrue(result < 0);
    System.out.println("OK\n");
  }

  /**
   * Test complex object.
   */
  @Test
  public void testComplexObject() {
    System.out.println("Testing complex object ...");
    int result = createObjectComparator(new ComparatorItem("simple.number", true))
        .compare(new ComplexObject(new SimpleObject(1)), new ComplexObject(new SimpleObject(2)));
    System.out.println(result);
    TestCase.assertTrue(result < 0);
    System.out.println("OK\n");
  }

  private class SimpleObject {

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private int number;

    private SimpleObject(int number) {
      this.number = number;
    }
  }

  private class SimpleGetObject {

    private int no;

    private SimpleGetObject(int number) {
      this.no = number;
    }

    @SuppressWarnings("unused")
    private int getNumber() {
      return no;
    }
  }

  private class SimpleIsObject {

    private boolean _isNice;

    private SimpleIsObject(boolean isNice) {
      this._isNice = isNice;
    }

    @SuppressWarnings("unused")
    private boolean isNice() {
      return _isNice;
    }
  }

  private class ComplexObject {

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private SimpleObject simple;

    private ComplexObject(SimpleObject simple) {
      this.simple = simple;
    }
  }

}
