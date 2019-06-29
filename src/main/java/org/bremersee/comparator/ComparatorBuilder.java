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

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.bremersee.comparator.model.ComparatorField;

/**
 * The comparator builder.
 *
 * @author Christian Bremer
 */
public interface ComparatorBuilder {

  /**
   * Comparator builder.
   *
   * @return the comparator builder
   */
  static ComparatorBuilder builder() {
    return new DefaultComparatorBuilder();
  }

  /**
   * Comparator comparator builder.
   *
   * @param comparator the comparator
   * @return the comparator builder
   */
  default ComparatorBuilder comparator(Comparator<?> comparator) {
    return comparator(null, null, comparator);
  }

  /**
   * Comparator comparator builder.
   *
   * @param field      the field
   * @param comparator the comparator
   * @return the comparator builder
   */
  default ComparatorBuilder comparator(String field, Comparator<?> comparator) {
    return comparator(field, null, comparator);
  }

  /**
   * Comparator comparator builder.
   *
   * @param field          the field
   * @param valueExtractor the value extractor
   * @param comparator     the comparator
   * @return the comparator builder
   */
  ComparatorBuilder comparator(String field, ValueExtractor valueExtractor,
      Comparator<?> comparator);

  /**
   * Fields comparator builder.
   *
   * @param fields the fields
   * @return the comparator builder
   */
  default ComparatorBuilder fields(Collection<? extends ComparatorField> fields) {
    return fields(fields, null);
  }

  /**
   * Fields comparator builder.
   *
   * @param fields         the fields
   * @param valueExtractor the value extractor
   * @return the comparator builder
   */
  default ComparatorBuilder fields(
      Collection<? extends ComparatorField> fields,
      ValueExtractor valueExtractor) {
    if (fields != null) {
      for (ComparatorField field : fields) {
        field(field, valueExtractor);
      }
    }
    return this;
  }

  /**
   * Field comparator builder.
   *
   * @param field the field
   * @return the comparator builder
   */
  default ComparatorBuilder field(ComparatorField field) {
    return field(field, null);
  }

  /**
   * Field comparator builder.
   *
   * @param field          the field
   * @param valueExtractor the value extractor
   * @return the comparator builder
   */
  default ComparatorBuilder field(ComparatorField field, ValueExtractor valueExtractor) {
    if (field == null) {
      throw new IllegalArgumentException("Field must not be null.");
    }
    return field(field.getField(), field.isAsc(), field.isIgnoreCase(), field.isNullIsFirst(),
        valueExtractor);
  }

  /**
   * Field comparator builder.
   *
   * @param field       the field
   * @param asc         the asc
   * @param ignoreCase  the ignore case
   * @param nullIsFirst the null is first
   * @return the comparator builder
   */
  default ComparatorBuilder field(
      String field,
      boolean asc,
      boolean ignoreCase,
      boolean nullIsFirst) {
    return field(field, asc, ignoreCase, nullIsFirst, null);
  }

  /**
   * Field comparator builder.
   *
   * @param field          the field
   * @param asc            the asc
   * @param ignoreCase     the ignore case
   * @param nullIsFirst    the null is first
   * @param valueExtractor the value extractor
   * @return the comparator builder
   */
  ComparatorBuilder field(
      String field,
      boolean asc,
      boolean ignoreCase,
      boolean nullIsFirst,
      ValueExtractor valueExtractor);

  /**
   * From well known text comparator builder.
   *
   * @param wkt the wkt
   * @return the comparator builder
   */
  default ComparatorBuilder fromWellKnownText(String wkt) {
    return fromWellKnownText(wkt, null);
  }

  /**
   * From well known text comparator builder.
   *
   * @param wkt       the wkt
   * @param wktParser the wkt parser
   * @return the comparator builder
   */
  ComparatorBuilder fromWellKnownText(String wkt, WellKnownTextParser wktParser);

  /**
   * Build comparator.
   *
   * @return the comparator
   */
  Comparator<Object> build();

  /**
   * The default comparator builder.
   */
  class DefaultComparatorBuilder implements ComparatorBuilder {

    private final List<Comparator> comparatorChain = new LinkedList<>();

    @Override
    public ComparatorBuilder comparator(Comparator<?> comparator) {
      comparatorChain.add(comparator);
      return this;
    }

    @Override
    public ComparatorBuilder comparator(
        String field,
        ValueExtractor valueExtractor,
        Comparator<?> comparator) {
      comparatorChain.add(new DelegatingComparator(field, valueExtractor, comparator));
      return this;
    }

    @Override
    public ComparatorBuilder field(
        String field,
        boolean asc,
        boolean ignoreCase,
        boolean nullIsFirst,
        ValueExtractor valueExtractor) {
      comparatorChain.add(new ValueComparator(field, asc, ignoreCase, nullIsFirst, valueExtractor));
      return this;
    }

    @Override
    public ComparatorBuilder fromWellKnownText(
        String wkt,
        WellKnownTextParser wktParser) {
      if (wktParser != null) {
        comparatorChain.add(wktParser.parse(wkt));
      } else {
        WellKnownTextParser parser = ValueComparator::new;
        comparatorChain.add(parser.parse(wkt));
      }
      return this;
    }

    @Override
    public Comparator<Object> build() {
      return new ComparatorChain(comparatorChain);
    }
  }

}
