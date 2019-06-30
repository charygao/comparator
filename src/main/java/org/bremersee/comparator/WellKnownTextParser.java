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

package org.bremersee.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import org.bremersee.comparator.model.ComparatorField;

/**
 * The well known text parser.
 *
 * @author Christian Bremer
 */
public interface WellKnownTextParser {

  /**
   * Parse wkt.
   *
   * @param wkt the wkt
   * @return the comparator
   */
  default Comparator<Object> parse(String wkt) {
    ComparatorBuilder builder = ComparatorBuilder.builder();
    for (ComparatorField comparatorField : buildComparatorFields(wkt)) {
      Comparator comparator = apply(comparatorField);
      if (comparator != null) {
        builder.add(comparator);
      }
    }
    return builder.build();
  }

  /**
   * Build comparator fields.
   *
   * @param wkt the wkt
   * @return the list
   */
  default List<ComparatorField> buildComparatorFields(String wkt) {
    List<ComparatorField> fields = new ArrayList<>();
    for (String fieldDescription : wkt.split(Pattern.quote("|"))) {
      fields.add(buildComparatorField(fieldDescription));
    }
    return fields;
  }

  /**
   * Build comparator field comparator field.
   *
   * @param fieldDescription the field description
   * @return the comparator field
   */
  default ComparatorField buildComparatorField(String fieldDescription) {
    if (fieldDescription == null || fieldDescription.trim().length() == 0) {
      return new ComparatorField(null, true, true, false);
    }
    if (fieldDescription.contains("|")) {
      throw new IllegalArgumentException("The field description [" + fieldDescription
          + "] contains more than one field description. Use 'buildComparatorFields' instead.");
    }
    return new ComparatorField(
        findStringPart(fieldDescription, ",", 0),
        findBooleanPart(fieldDescription, ",", 1, true, "asc", "true", "1"),
        findBooleanPart(fieldDescription, ",", 2, true, "ignoreCase", "true", "1"),
        findBooleanPart(fieldDescription, ",", 3, false, "nullIsFirst", "true", "1"));
  }

  /**
   * Apply comparator.
   *
   * @param comparatorField the comparator field
   * @return the comparator
   */
  Comparator apply(ComparatorField comparatorField);

  /**
   * Find string part string.
   *
   * @param fieldDescription the field description
   * @param delimiter        the delimiter
   * @param index            the index
   * @return the string
   */
  static String findStringPart(String fieldDescription, String delimiter, int index) {
    if (delimiter == null || delimiter.length() == 0 || fieldDescription == null) {
      return fieldDescription;
    }
    String[] parts = fieldDescription.split(Pattern.quote(delimiter));
    if (parts.length > index) {
      return parts[index].trim();
    }
    return null;
  }

  /**
   * Find boolean part boolean.
   *
   * @param fieldDescription the field description
   * @param delimiter        the delimiter
   * @param index            the index
   * @param defaultValue     the default value
   * @param expectedValues   the expected values
   * @return the boolean
   */
  static boolean findBooleanPart(
      String fieldDescription,
      String delimiter,
      int index,
      boolean defaultValue,
      String... expectedValues) {
    String part = findStringPart(fieldDescription, delimiter, index);
    if (part != null && part.length() > 0 && expectedValues != null) {
      return Arrays.stream(expectedValues)
          .anyMatch(part::equalsIgnoreCase);
    }
    return defaultValue;
  }

}
