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

package org.bremersee.comparator;

import org.bremersee.comparator.model.ComparatorItem;

/**
 * <p>
 * Deserializer of a {@link ComparatorItem}.
 * </p>
 *
 * @see ComparatorItemSerializer
 * @author Christian Bremer
 */
public interface ComparatorItemDeserializer {

    /**
     * Creates a {@link ComparatorItem} from it's string representation made by
     * a {@link ComparatorItemSerializer}.
     * 
     * @param serializedComparatorItem
     *            the string representation
     * @param isUrlEncoded
     *            is the string URL encoded?
     * @param charset
     *            the charset of the URL encoding (default is UTF-8)
     * @return the comparator item
     */
    ComparatorItem fromString(String serializedComparatorItem, boolean isUrlEncoded, String charset);

}
