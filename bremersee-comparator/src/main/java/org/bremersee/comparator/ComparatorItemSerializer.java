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
 * Serializer of a {@link ComparatorItem}.
 * </p>
 *
 * @see ComparatorItemDeserializer
 * @author Christian Bremer
 */
public interface ComparatorItemSerializer {

	/**
	 * Creates a string representation of the comparator item that should be
	 * readable by {@link ComparatorItemDeserializer}.
	 * 
	 * @param comparatorItem
	 *            the comparator item
	 * @param urlEncode
	 *            should the resulting string be URL encoded
	 * @param charset
	 *            the charset to use for URL encoding (default is UTF-8)
	 * @return the string representation of the comparator item
	 */
	String toString(ComparatorItem comparatorItem, boolean urlEncode, String charset);

}
