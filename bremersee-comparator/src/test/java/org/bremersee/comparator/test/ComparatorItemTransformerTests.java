/*
 * Copyright 2015 the original author or authors.
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

import org.bremersee.comparator.ComparatorItemDeserializer;
import org.bremersee.comparator.ComparatorItemSerializer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.comparator.model.ComparatorItem;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class ComparatorItemTransformerTests {

    private ComparatorItemDeserializer deserializer;

    private ComparatorItemSerializer serializer;

    @Before
    public void createComparatorItemDeserializerAndSerializer() {
        ComparatorItemTransformerImpl transformer = new ComparatorItemTransformerImpl();
        this.deserializer = transformer;
        this.serializer = transformer;
    }

    @Test
    public void testEmptyComparatorItem() {

        System.out.println("Testing empty comparator item ...");

        ComparatorItem item0 = new ComparatorItem();
        String str0 = serializer.toString(item0, false, null);
        System.out.println(str0);
        ComparatorItem readItem0 = deserializer.fromString(str0, false, null);
        System.out.println(readItem0);
        TestCase.assertEquals(item0, readItem0);

        System.out.println("OK\n");
    }

    @Test
    public void testSimpleComparatorItem() {

        System.out.println("Testing simple comparator item ...");

        ComparatorItem item0 = new ComparatorItem("i0", true);
        String str0 = serializer.toString(item0, false, null);
        System.out.println(str0);
        ComparatorItem readItem0 = deserializer.fromString(str0, false, null);
        System.out.println(readItem0);
        TestCase.assertEquals(item0, readItem0);

        System.out.println("OK\n");
    }

    @Test
    public void testConcatenatedComparatorItems() {

        System.out.println("Testing concatenate comparator items ...");

        ComparatorItem item0 = new ComparatorItem("i0", true);
        item0.next("i1", false).next("i2", true);
        String str0 = serializer.toString(item0, false, null);
        System.out.println(str0);
        ComparatorItem readItem0 = deserializer.fromString(str0, false, null);
        System.out.println(readItem0.toList(false));
        TestCase.assertEquals(item0.toList(false), readItem0.toList(false));

        System.out.println("OK\n");
    }

    @Test
    public void testSimpleComparatorItemWithoutOrder() {

        System.out.println("Testing simple comparator item without order ...");

        ComparatorItem item0 = new ComparatorItem("i0", true);
        String str0 = "i0";
        System.out.println(str0);
        ComparatorItem readItem0 = deserializer.fromString(str0, false, null);
        System.out.println(readItem0);
        TestCase.assertEquals(item0, readItem0);

        System.out.println("OK\n");
    }

    @Test
    public void testConcatenatedComparatorItemsWithoutOrder() {

        System.out.println("Testing concatenate comparator items without order ...");

        ComparatorItem item0 = new ComparatorItem("i0", true);
        item0.next("i1", false).next("i2", true);
        String str0 = "i0|i1,desc|i2";
        System.out.println(str0);
        ComparatorItem readItem0 = deserializer.fromString(str0, false, null);
        System.out.println(readItem0.toList(false));
        TestCase.assertEquals(item0.toList(false), readItem0.toList(false));

        System.out.println("OK\n");
    }

}
