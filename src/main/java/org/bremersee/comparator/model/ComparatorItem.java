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

package org.bremersee.comparator.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.bremersee.comparator.ObjectComparator;

/**
 * A comparator item defines how objects are sorted by the {@link ObjectComparator}. They may build
 * a chain to compare more than one attribute. If the first attribute of two object is equal, the
 * next attribute in the comparator item chain will be compared and so on.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "comparatorItem")
@XmlType(name = "comparatorItemType", propOrder = {
    "field",
    "asc",
    "ignoreCaseAsBoolean",
    "nullIsFirstAsBoolean",
    "nextComparatorItem"
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    creatorVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
    "field",
    "asc",
    "ignoreCaseAsBoolean",
    "nullIsFirstAsBoolean",
    "nextComparatorItem"
})
@ApiModel(
    value = "ComparatorItem",
    description = "A comparator item defines how objects are sorted by the "
        + "'ObjectComparator'. They may build a chain to compare more "
        + "than one attribute. If the first attribute of two object is "
        + "equal, the next attribute in the comparator item chain will "
        + "be compared and so on.")
@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class ComparatorItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private String field;

  private boolean asc = true;

  private Boolean ignoreCase = Boolean.TRUE;

  private Boolean nullIsFirst = Boolean.FALSE;

  private ComparatorItem nextComparatorItem;

  private ComparatorItem parentComparatorItem;

  /**
   * Default constructor.
   */
  public ComparatorItem() {
    super();
  }

  /**
   * Construct an comparator item for the specified field with an ascending sort order.
   *
   * @param field the field name
   */
  public ComparatorItem(String field) {
    setField(field);
  }

  /**
   * Construct an comparator item for the specified field with the specified sort order.
   *
   * @param field the field name
   * @param asc   the sort order
   */
  public ComparatorItem(String field, boolean asc) {
    setField(field);
    this.asc = asc;
  }

  /**
   * Instantiates a new Comparator item.
   *
   * @param field      the field
   * @param asc        the asc
   * @param ignoreCase the ignore case
   */
  public ComparatorItem(String field, boolean asc, boolean ignoreCase) {
    setField(field);
    this.asc = asc;
    this.ignoreCase = ignoreCase;
  }

  /**
   * Instantiates a new Comparator item.
   *
   * @param field       the field
   * @param asc         the asc
   * @param ignoreCase  the ignore case
   * @param nullIsFirst the null is first
   */
  public ComparatorItem(String field, boolean asc, boolean ignoreCase, boolean nullIsFirst) {
    setField(field);
    this.asc = asc;
    this.ignoreCase = ignoreCase;
    this.nullIsFirst = nullIsFirst;
  }

  @Override
  public String toString() {
    return String.format("%s [field = %s, asc = %s, ignoreCase = %s, nullIsFirst = %s]",
        getClass().getSimpleName(),
        field, asc, ignoreCase, nullIsFirst);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ComparatorItem)) {
      return false;
    }
    ComparatorItem that = (ComparatorItem) o;
    return asc == that.asc
        && Objects.equals(field, that.field)
        && Objects.equals(ignoreCase, that.ignoreCase)
        && Objects.equals(nullIsFirst, that.nullIsFirst)
        && Objects.equals(nextComparatorItem, that.nextComparatorItem);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(field, asc, ignoreCase, nullIsFirst);
  }

  /**
   * Returns the field name. It may be {@code null}.
   *
   * @return the field name
   */
  @XmlElement(name = "field")
  @JsonProperty(value = "field")
  @ApiModelProperty(value = "The field name that should be compared.")
  public String getField() {
    return field;
  }

  /**
   * Sets the field name. If the field name is {@code null}, the object will have to be comparable,
   * because it will be compared by the {@link Comparable#compareTo(Object)} method.
   *
   * @param field the field name (optional)
   */
  @JsonProperty(value = "field")
  public void setField(String field) {
    if (field == null || field.trim().length() == 0) {
      this.field = null;
    } else {
      this.field = field;
    }
  }

  /**
   * Returns the sort order.
   *
   * @return the sort order
   */
  @XmlElement(name = "asc", defaultValue = "true")
  @JsonProperty(value = "asc", required = true)
  @ApiModelProperty(value = "The sort order.", position = 1, required = true)
  public boolean isAsc() {
    return asc;
  }

  /**
   * Sets the sort order.
   *
   * @param asc the sort order
   */
  @JsonProperty(value = "asc")
  public void setAsc(boolean asc) {
    this.asc = asc;
  }

  /**
   * Returns whether the sort is case sensitive or not.
   *
   * @return {@code true} if the sort is case sensitive, otherwise {@code false}
   */
  @XmlElement(name = "ignoreCase")
  @JsonProperty(value = "ignoreCase")
  @ApiModelProperty(value = "Is the sort case sensitive?", position = 2)
  protected final Boolean getIgnoreCaseAsBoolean() {
    return ignoreCase;
  }

  /**
   * Sets whether the sort is case sensitive or not.
   *
   * @param ignoreCase {@code true} if the sort is case sensitive, otherwise {@code false}; {@code
   *                   null will be ignored}
   */
  @JsonProperty(value = "ignoreCase")
  protected final void setIgnoreCaseAsBoolean(Boolean ignoreCase) {
    if (ignoreCase != null) {
      this.ignoreCase = ignoreCase;
    }
  }

  /**
   * Returns whether the sort is case sensitive or not.
   *
   * @return {@code true} if the sort is case sensitive, otherwise {@code false}
   */
  @XmlTransient
  @JsonIgnore
  public final boolean isIgnoreCase() {
    return ignoreCase;
  }

  /**
   * Sets whether the sort is case sensitive or not.
   *
   * @param ignoreCase {@code true} if the sort is case sensitive, otherwise {@code false}
   */
  @JsonIgnore
  public final void setIgnoreCase(boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  /**
   * Returns the sort order of a {@code null} value.
   *
   * @return {@code true} if the sort order of a {@code null} value is higher than a {@code
   * non-null} value, otherwise {@code false}.
   */
  @XmlElement(name = "nullIsFirst")
  @JsonProperty(value = "nullIsFirst")
  @ApiModelProperty(value = "Is a null value in front of other values?", position = 3)
  protected final Boolean getNullIsFirstAsBoolean() {
    return nullIsFirst;
  }

  /**
   * Sets the sort order of a {@code null} value.
   *
   * @param nullIsFirst {@code true} if the sort order of a {@code null} value is higher than a
   *                    {@code non-null} value, otherwise {@code false}; {@code null will be
   *                    ignored}
   */
  @JsonProperty(value = "nullIsFirst")
  protected final void setNullIsFirstAsBoolean(Boolean nullIsFirst) {
    if (nullIsFirst != null) {
      this.nullIsFirst = nullIsFirst;
    }
  }

  /**
   * Returns the sort order of a {@code null} value.
   *
   * @return {@code true} if the sort order of a {@code null} value is higher than a {@code
   * non-null} value, otherwise {@code false}.
   */
  @XmlTransient
  @JsonIgnore
  public final boolean isNullIsFirst() {
    return nullIsFirst;
  }

  /**
   * Sets the sort order of a {@code null} value.
   *
   * @param nullIsFirst {@code true} if the sort order of a {@code null} value is higher than a
   *                    {@code non-null} value, otherwise {@code false}
   */
  @JsonIgnore
  public final void setNullIsFirst(boolean nullIsFirst) {
    this.nullIsFirst = nullIsFirst;
  }

  /**
   * Returns the root item.
   *
   * @return the root item
   */
  @XmlTransient
  @JsonIgnore
  public final ComparatorItem getRootComparatorItem() {
    ComparatorItem parent = this;
    while (parent.parentComparatorItem != null) {
      parent = parent.parentComparatorItem;
    }
    return parent;
  }

  /**
   * Returns the parent item.
   *
   * @return the parent item
   */
  @XmlTransient
  @JsonIgnore
  public final ComparatorItem getParentComparatorItem() {
    return parentComparatorItem;
  }

  /**
   * Returns the next comparator item.
   *
   * @return the next comparator item
   */
  @XmlElement(name = "nextComparatorItem")
  @JsonProperty(value = "nextComparatorItem")
  @ApiModelProperty(value = "The next comparator item.", position = 4)
  public final ComparatorItem getNextComparatorItem() {
    return nextComparatorItem;
  }

  /**
   * Sets the next comparator item.
   *
   * @param nextComparatorItem the next comparator item
   * @throws IllegalArgumentException if the comparator items build an illegal circle
   */
  @JsonProperty(value = "nextComparatorItem")
  public final void setNextComparatorItem(ComparatorItem nextComparatorItem) {

    if (nextComparatorItem == null) {
      if (this.nextComparatorItem != null) {
        this.nextComparatorItem.parentComparatorItem = null;
      }
      this.nextComparatorItem = null;
      return;
    }

    ComparatorItem bakNext = this.nextComparatorItem;
    this.nextComparatorItem = null;

    ComparatorItem tmpParent = getRootComparatorItem();
    while (tmpParent != null) {

      ComparatorItem tmp = nextComparatorItem;
      while (tmp != null) {
        if (tmp == tmpParent) {
          this.nextComparatorItem = bakNext;
          throw new IllegalArgumentException("Comparator items build an illegal circle.");
        }
        tmp = tmp.nextComparatorItem;
      }

      tmpParent = tmpParent.nextComparatorItem;
    }

    if (nextComparatorItem.parentComparatorItem != null) {
      nextComparatorItem.parentComparatorItem.nextComparatorItem = null;
    }
    nextComparatorItem.parentComparatorItem = this;
    this.nextComparatorItem = nextComparatorItem;
  }

  /**
   * Returns the last item.
   *
   * @return the last item
   */
  @XmlTransient
  @JsonIgnore
  public final ComparatorItem getLastComparatorItem() {
    ComparatorItem last = this;
    while (last.nextComparatorItem != null) {
      last = last.nextComparatorItem;
    }
    return last;
  }

  /**
   * Sets the next comparator item and returns the next comparator item.
   *
   * @param field the field name of the next comparator item
   * @param asc   the sort order of the next comparator item
   * @return the next comparator item
   * @throws IllegalArgumentException if the comparator items build an illegal circle
   */
  public final ComparatorItem next(String field, boolean asc) {
    setNextComparatorItem(new ComparatorItem(field, asc));
    return this.nextComparatorItem;
  }

  /**
   * Sets the next comparator item and returns the next comparator item.
   *
   * @param field      the field name of the next comparator item
   * @param asc        the sort order of the next comparator item
   * @param ignoreCase {@code true} if the sort is case sensitive, otherwise {@code false}; {@code
   *                   null will be ignored}
   * @return the next comparator item
   * @throws IllegalArgumentException if the comparator items build an illegal circle
   */
  public final ComparatorItem next(String field, boolean asc, boolean ignoreCase) {
    setNextComparatorItem(new ComparatorItem(field, asc, ignoreCase));
    return this.nextComparatorItem;
  }

  /**
   * Sets the next comparator item and returns the next comparator item.
   *
   * @param field       the field name of the next comparator item
   * @param asc         the sort order of the next comparator item
   * @param ignoreCase  {@code true} if the sort is case sensitive, otherwise {@code false}; {@code
   *                    null will be ignored}
   * @param nullIsFirst {@code true} if the sort order of a {@code null} value is higher than a
   *                    {@code non-null} value, otherwise {@code false}
   * @return the next comparator item
   * @throws IllegalArgumentException if the comparator items build an illegal circle
   */
  public final ComparatorItem next(String field, boolean asc, boolean ignoreCase,
      boolean nullIsFirst) {
    setNextComparatorItem(new ComparatorItem(field, asc, ignoreCase, nullIsFirst));
    return this.nextComparatorItem;
  }

  /**
   * Generates a list of the items.
   *
   * @param fromRoot if {@code true} the list will start at the root item, otherwise at the current
   *                 item
   * @return a list of the items
   */
  public final List<ComparatorItem> toList(boolean fromRoot) {
    ArrayList<ComparatorItem> list = new ArrayList<>();
    ComparatorItem tmp = fromRoot ? getRootComparatorItem() : this;
    while (tmp != null) {
      list.add(
          new ComparatorItem(tmp.getField(), tmp.isAsc(), tmp.isIgnoreCase(), tmp.isNullIsFirst()));
      tmp = tmp.nextComparatorItem;
    }
    return list;
  }

}
