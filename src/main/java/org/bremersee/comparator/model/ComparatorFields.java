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

package org.bremersee.comparator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The comparator fields.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comparatorFields")
@XmlType(name = "comparatorFieldsType")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(
    value = "ComparatorFields",
    description = "A list of comparator fields.")
@SuppressWarnings("WeakerAccess")
public class ComparatorFields {

  private List<ComparatorField> fields = new ArrayList<>();

  /**
   * Instantiates new comparator fields.
   */
  public ComparatorFields() {
  }

  /**
   * Instantiates new comparator fields.
   *
   * @param fields the fields
   */
  @JsonCreator
  public ComparatorFields(@JsonProperty("fields") Collection<? extends ComparatorField> fields) {
    if (fields != null) {
      this.fields.addAll(fields);
    }
  }

  /**
   * Gets fields.
   *
   * @return the fields
   */
  public List<ComparatorField> getFields() {
    return fields;
  }

  /**
   * To well known text.
   *
   * @return the well known text
   */
  public String toWkt() {
    return fields.stream()
        .map(ComparatorField::toWkt)
        .collect(Collectors.joining("|"));
  }

  @Override
  public String toString() {
    return toWkt();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ComparatorFields)) {
      return false;
    }
    ComparatorFields fields1 = (ComparatorFields) o;
    return Objects.equals(fields, fields1.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields);
  }

  /**
   * From comparator item.
   *
   * @param comparatorItem the comparator item
   * @return the comparator fields
   */
  public static ComparatorFields fromComparatorItem(ComparatorItem comparatorItem) {
    ComparatorFields fields = new ComparatorFields();
    if (comparatorItem != null) {
      ComparatorItem item = comparatorItem;
      while (item != null) {
        ComparatorField field = new ComparatorField(
            item.getField(),
            item.isAsc(),
            item.isIgnoreCase(),
            item.isNullIsFirst());
        fields.getFields().add(field);
        item = item.getNextComparatorItem();
      }
    }
    return fields;
  }
}
