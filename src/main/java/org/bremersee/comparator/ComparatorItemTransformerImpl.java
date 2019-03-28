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

package org.bremersee.comparator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.model.ComparatorItem;

/**
 * Class that implements {@link ComparatorItemDeserializer} and {@link ComparatorItemSerializer}.
 *
 * <p>The serialized form of a single {@link ComparatorItem} looks like:
 *
 * <pre>
 * fieldName,asc  or  fieldName,desc
 * </pre>
 *
 * <p>The serialized form of a concatenated {@link ComparatorItem} looks like:
 *
 * <pre>
 * fieldName1,asc|fieldName2,desc|fieldName3,asc
 * </pre>
 *
 * <p>The deserializer can read these forms and creates the corresponding
 * {@link ComparatorItem}.
 *
 * @author Christian Bremer
 */
public class ComparatorItemTransformerImpl implements ComparatorItemTransformer {

  @Override
  public String toString(final ComparatorItem comparatorItem, final boolean urlEncode,
      final String charset) {

    ComparatorItem tmp = comparatorItem;
    final StringBuilder sb = new StringBuilder();
    String str;
    while ((str = toString(tmp)) != null) {
      if (sb.length() > 0) {
        sb.append('|');
      }
      sb.append(str);
      tmp = tmp.getNextComparatorItem();
    }
    if (!urlEncode) {
      return sb.toString();
    }
    try {
      return URLEncoder.encode(sb.toString(),
          StringUtils.isBlank(charset) ? StandardCharsets.UTF_8.name() : charset);

    } catch (UnsupportedEncodingException e) {
      throw new ComparatorItemTransformerException(e);
    }
  }

  private String toString(final ComparatorItem comparatorItem) {
    if (comparatorItem == null) {
      return null;
    }
    final String order;
    if (comparatorItem.isAsc()) {
      order = "asc";
    } else {
      order = "desc";
    }
    return comparatorItem.getField() + "," + order + "," + comparatorItem.isIgnoreCase() + ","
        + comparatorItem.isNullIsFirst();
  }

  @Override
  public ComparatorItem fromString(final String serializedComparatorItem,
      final boolean isUrlEncoded, final String charset) {

    if (StringUtils.isBlank(serializedComparatorItem)) {
      return null;
    }

    final String item;
    final String enc = StringUtils.isBlank(charset) ? StandardCharsets.UTF_8.name() : charset;

    if (isUrlEncoded) {
      try {
        item = URLDecoder.decode(serializedComparatorItem, enc);

      } catch (UnsupportedEncodingException e) {
        throw new ComparatorItemTransformerException(e);
      }
    } else {
      item = serializedComparatorItem;
    }

    final int index = item.indexOf('|');
    if (index < 0) {
      return fromString(item);

    } else {
      final String param1 = item.substring(0, index);
      final String param2 = item.substring(index + 1);

      final ComparatorItem item1 = fromString(param1);
      final ComparatorItem item2 = fromString(param2, false, enc);
      if (item1 != null && item2 != null) {
        item1.setNextComparatorItem(item2);
      }

      return item1;
    }
  }

  private ComparatorItem fromString(final String serializedComparatorItem) {

    if (StringUtils.isBlank(serializedComparatorItem)) {
      return null;
    }

    String[] parts = serializedComparatorItem.split(Pattern.quote(","));
    switch (parts.length) {
      case 1:
        return new ComparatorItem(getField(serializedComparatorItem));

      case 2:
        return new ComparatorItem(getField(parts[0]), isAsc(parts[1]));

      case 3:
        return new ComparatorItem(getField(parts[0]), isAsc(parts[1]), isIgnoreCase(parts[2]));

      default:
        return new ComparatorItem(getField(parts[0]), isAsc(parts[1]), isIgnoreCase(parts[2]),
            isNullIsFirst(parts[3]));
    }
  }

  private String getField(final String part) {
    if (StringUtils.isBlank(part) || "null".equalsIgnoreCase(part)) {
      return null;
    }
    return part;
  }

  private boolean isAsc(final String part) {
    return !"desc".equalsIgnoreCase(part);
  }

  private boolean isIgnoreCase(final String part) {
    return !"false".equalsIgnoreCase(part);
  }

  private boolean isNullIsFirst(final String part) {
    return "true".equalsIgnoreCase(part);
  }

}
