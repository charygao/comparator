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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The comparator field.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comparatorField")
@XmlType(name = "comparatorFieldType", propOrder = {
    "field",
    "asc",
    "ignoreCase",
    "nullIsFirst"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {
    "field",
    "asc",
    "ignoreCase",
    "nullIsFirst"
})
@ApiModel(
    value = "ComparatorField",
    description = "A comparator field defines how a field of an object is sorted.")
@SuppressWarnings({"UnusedAssignment", "unused"})
public class ComparatorField {

  @XmlElement(name = "field")
  private String field;

  @XmlElement(name = "asc", defaultValue = "true")
  private boolean asc = true;

  @XmlElement(name = "ignoreCase", defaultValue = "true")
  private boolean ignoreCase = true;

  @XmlElement(name = "nullIsFirst", defaultValue = "false")
  private boolean nullIsFirst = false;

  /**
   * Instantiates a new comparator field.
   */
  public ComparatorField() {
    this(null, true, true, false);
  }

  /**
   * Instantiates a new comparator field.
   *
   * @param field       the field
   * @param asc         the asc
   * @param ignoreCase  the ignore case
   * @param nullIsFirst the null is first
   */
  @JsonCreator
  public ComparatorField(
      @JsonProperty("field") String field,
      @JsonProperty(value = "asc", defaultValue = "true") boolean asc,
      @JsonProperty(value = "ignoreCase", defaultValue = "true") boolean ignoreCase,
      @JsonProperty(value = "nullIsFirst", defaultValue = "false") boolean nullIsFirst) {
    this.field = field;
    this.asc = asc;
    this.ignoreCase = ignoreCase;
    this.nullIsFirst = nullIsFirst;
  }

  /**
   * Gets field.
   *
   * @return the field
   */
  public String getField() {
    return field;
  }

  /**
   * Is asc or desc sorting.
   *
   * @return the boolean
   */
  public boolean isAsc() {
    return asc;
  }

  /**
   * Is ignore case.
   *
   * @return the boolean
   */
  public boolean isIgnoreCase() {
    return ignoreCase;
  }

  /**
   * Is null is first.
   *
   * @return the boolean
   */
  public boolean isNullIsFirst() {
    return nullIsFirst;
  }

  /**
   * To well known text.
   *
   * @return the well known text
   */
  public String toWkt() {
    return (field != null ? field : "") + ","
        + (asc ? "asc," : "desc,")
        + ignoreCase + ","
        + nullIsFirst;
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
    if (!(o instanceof ComparatorField)) {
      return false;
    }
    ComparatorField that = (ComparatorField) o;
    return asc == that.asc
        && ignoreCase == that.ignoreCase
        && nullIsFirst == that.nullIsFirst
        && Objects.equals(field, that.field);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, asc, ignoreCase, nullIsFirst);
  }

}
