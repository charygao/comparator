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

package org.bremersee.comparator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * @author Christian Bremer
 */
//@formatter:off
@XmlRootElement(name = "comparatorItem")
@XmlType(name = "comparatorItemType", propOrder = { 
        "field", 
        "asc",
        "ignoreCaseAsBoolean",
        "nullIsFirstAsBoolean",
        "nextComparatorItem" })
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonInclude(Include.NON_EMPTY)
//@formatter:on
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
    }

    /**
     * Construct an comparator item for the specified field with an ascending
     * sort order.
     * 
     * @param field
     *            the field name
     */
    public ComparatorItem(String field) {
        this.field = field;
    }

    /**
     * Construct an comparator item for the specified field with the specified
     * sort order.
     * 
     * @param field
     *            the field name
     * @param asc
     *            the sort order
     */
    public ComparatorItem(String field, boolean asc) {
        this.field = field;
        this.asc = asc;
    }

    public ComparatorItem(String field, boolean asc, boolean ignoreCase) {
        this.field = field;
        this.asc = asc;
        this.ignoreCase = ignoreCase;
    }

    public ComparatorItem(String field, boolean asc, boolean ignoreCase, boolean nullIsFirst) {
        this.field = field;
        this.asc = asc;
        this.ignoreCase = ignoreCase;
        this.nullIsFirst = nullIsFirst;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [field = %s, asc = %s, ignoreCase = %s, nullIsFirst = %s]", getClass().getSimpleName(), field, asc, ignoreCase, nullIsFirst);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (asc ? 1231 : 1237);
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((ignoreCase == null) ? 0 : ignoreCase.hashCode());
        result = prime * result + ((nullIsFirst == null) ? 0 : nullIsFirst.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComparatorItem other = (ComparatorItem) obj;
        if (asc != other.asc)
            return false;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (ignoreCase == null) {
            if (other.ignoreCase != null)
                return false;
        } else if (!ignoreCase.equals(other.ignoreCase))
            return false;
        if (nullIsFirst == null) {
            if (other.nullIsFirst != null)
                return false;
        } else if (!nullIsFirst.equals(other.nullIsFirst))
            return false;
        return true;
    }

    /**
     * Returns the field name.
     * 
     * @return the field name
     */
    @XmlElement(name = "field", required = false)
    @JsonProperty(value = "field", required = false)
    public String getField() {
        return field;
    }

    /**
     * Sets the field name.
     * 
     * @param field
     *            the field name
     */
    @JsonProperty(value = "field", required = false)
    public void setField(String field) {
        if (field != null && field.trim().length() == 0) {
            field = null;
        }
        this.field = field;
    }

    /**
     * Returns the sort order.
     * 
     * @return the sort order
     */
    @XmlElement(name = "asc", defaultValue = "true")
    @JsonProperty(value = "asc", required = true)
    public boolean isAsc() {
        return asc;
    }

    /**
     * Sets the sort order.
     * 
     * @param asc
     *            the sort order
     */
    @JsonProperty(value = "asc", required = false)
    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    /**
     * Returns whether the sort is case sensitive or not.
     * 
     * @return {@code true} if the sort is case sensitive, otherwise
     *         {@code false}
     */
    @XmlElement(name = "ignoreCase", required = false)
    @JsonProperty(value = "ignoreCase", required = false)
    protected final Boolean getIgnoreCaseAsBoolean() {
        return ignoreCase;
    }

    /**
     * Sets whether the sort is case sensitive or not.
     * 
     * @param ignoreCase
     *            {@code true} if the sort is case sensitive, otherwise
     *            {@code false}; {@code null will be ignored}
     */
    @JsonProperty(value = "ignoreCase", required = false)
    protected final void setIgnoreCaseAsBoolean(Boolean ignoreCase) {
        if (ignoreCase != null) {
            this.ignoreCase = ignoreCase;
        }
    }

    /**
     * Returns whether the sort is case sensitive or not.
     * 
     * @return {@code true} if the sort is case sensitive, otherwise
     *         {@code false}
     */
    @XmlTransient
    @JsonIgnore
    public final boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * Sets whether the sort is case sensitive or not.
     * 
     * @param ignoreCase
     *            {@code true} if the sort is case sensitive, otherwise
     *            {@code false}
     */
    @JsonIgnore
    public final void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Returns the sort order of a {@code null} value.
     * 
     * @return {@code true} if the sort order of a {@code null} value is higher
     *         than a {@code non-null} value, otherwise {@code false}.
     */
    @XmlElement(name = "nullIsFirst", required = false)
    @JsonProperty(value = "nullIsFirst", required = false)
    protected final Boolean getNullIsFirstAsBoolean() {
        return nullIsFirst;
    }

    /**
     * Sets the sort order of a {@code null} value.
     * 
     * @param nullIsFirst
     *            {@code true} if the sort order of a {@code null} value is
     *            higher than a {@code non-null} value, otherwise {@code false}; {@code null will be ignored}
     */
    @JsonProperty(value = "nullIsFirst", required = false)
    protected final void setNullIsFirstAsBoolean(Boolean nullIsFirst) {
        if (nullIsFirst != null) {
            this.nullIsFirst = nullIsFirst;
        }
    }

    /**
     * Returns the sort order of a {@code null} value.
     * 
     * @return {@code true} if the sort order of a {@code null} value is higher
     *         than a {@code non-null} value, otherwise {@code false}.
     */
    @XmlTransient
    @JsonIgnore
    public final boolean isNullIsFirst() {
        return nullIsFirst;
    }

    /**
     * Sets the sort order of a {@code null} value.
     * 
     * @param nullIsFirst
     *            {@code true} if the sort order of a {@code null} value is
     *            higher than a {@code non-null} value, otherwise {@code false}
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
    @JsonIgnore
    public final ComparatorItem getParentComparatorItem() {
        return parentComparatorItem;
    }

    /**
     * Returns the next comparator item.
     * 
     * @return the next comparator item
     */
    @XmlElement(name = "nextComparatorItem", required = false)
    @JsonProperty(value = "nextComparatorItem", required = false)
    public final ComparatorItem getNextComparatorItem() {
        return nextComparatorItem;
    }

    /**
     * Sets the next comparator item.
     * 
     * @param nextComparatorItem
     *            the next comparator item
     * @throws IllegalArgumentException
     *             if the comparator items build an illegal circle
     */
    @JsonProperty(value = "nextComparatorItem", required = false)
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
     * @param field
     *            the field name of the next comparator item
     * @param asc
     *            the sort order of the next comparator item
     * @return the next comparator item
     * @throws IllegalArgumentException
     *             if the comparator items build an illegal circle
     */
    public final ComparatorItem next(String field, boolean asc) {
        setNextComparatorItem(new ComparatorItem(field, asc));
        return this.nextComparatorItem;
    }

    public final ComparatorItem next(String field, boolean asc, boolean ignoreCase) {
        setNextComparatorItem(new ComparatorItem(field, asc, ignoreCase));
        return this.nextComparatorItem;
    }

    public final ComparatorItem next(String field, boolean asc, boolean ignoreCase, boolean nullIsFirst) {
        setNextComparatorItem(new ComparatorItem(field, asc, ignoreCase, nullIsFirst));
        return this.nextComparatorItem;
    }

    /**
     * Generates a list of the items.
     * 
     * @param fromRoot
     *            if {@code true} the list will start at the root item,
     *            otherwise at the current item
     * @return a list of the items
     */
    public final List<ComparatorItem> toList(boolean fromRoot) {
        ArrayList<ComparatorItem> list = new ArrayList<ComparatorItem>();
        ComparatorItem tmp = fromRoot ? getRootComparatorItem() : this;
        while (tmp != null) {
            list.add(tmp);
            tmp = tmp.nextComparatorItem;
        }
        return list;
    }

}
