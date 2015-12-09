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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.model.ComparatorItem;

/**
 * <p>
 * This is the default implementation of a {@link ObjectComparator}. It will be
 * returned by
 * {@link ObjectComparatorFactory#newObjectComparator(ComparatorItem)}.
 * </p>
 * 
 * @author Christian Bremer
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ObjectComparatorImpl implements ObjectComparator, Serializable {

    private static final long serialVersionUID = 1L;

    private static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    private static Field findField(Class<?> clazz, String name, Class<?> type) {
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private static String[] getPossibleMethodNames(String name) {
        if (name == null || name.length() < 1) {
            return new String[0];
        }
        final String baseName;
        if (name.length() == 1) {
            baseName = name.toUpperCase();
        } else {
            baseName = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        String[] names = new String[2];
        names[0] = "get" + baseName;
        names[1] = "is" + baseName;
        return names;
    }

    private static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class[0]);
    }

    private static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (name.equals(method.getName())
                        && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private final ComparatorItem comparatorItem;

    /**
     * Default constructor.
     */
    public ObjectComparatorImpl() {
        this.comparatorItem = null;
    }

    /**
     * Construct an object comparator with the specified comparator item.
     * 
     * @param comparatorItem
     *            the comparator item to use
     */
    public ObjectComparatorImpl(ComparatorItem comparatorItem) {
        this.comparatorItem = comparatorItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.comparator.ObjectComparator#getComparatorItem()
     */
    @Override
    public ComparatorItem getComparatorItem() {
        return comparatorItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {

        final boolean asc = getComparatorItem() != null ? getComparatorItem().isAsc() : true;

        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 != null && o2 == null) {
            return asc ? -1 : 1;
        }
        if (o1 == null && o2 != null) {
            return asc ? 1 : -1;
        }

        if (getComparatorItem() == null || StringUtils.isBlank(getComparatorItem().getField())) {
            if (asc && o1 instanceof Comparable) {
                return ((Comparable) o1).compareTo(o2);
            } else if (!asc && o2 instanceof Comparable) {
                return ((Comparable) o2).compareTo(o1);
            } else {
                return 0;
            }
        }

        Object v1 = o1;
        Object v2 = o2;

        // supports fields like: fieldName1.fieldName2.fieldName3
        String[] fieldNames = getComparatorItem().getField().split(Pattern.quote("."));
        for (String fieldName : fieldNames) {

            if (StringUtils.isNotBlank(fieldName) && v1 != null && v2 != null) {

                Field f1 = findField(v1.getClass(), fieldName.trim());
                Field f2 = findField(v2.getClass(), fieldName.trim());

                if (f1 != null && f2 != null) {

                    if (!f1.isAccessible()) {
                        f1.setAccessible(true);
                    }
                    if (!f2.isAccessible()) {
                        f2.setAccessible(true);
                    }
                    try {
                        v1 = f1.get(v1);
                    } catch (IllegalArgumentException e) {
                        throw e;
                    } catch (IllegalAccessException e) {
                        throw new ObjectComparatorException(e);
                    }
                    try {
                        v2 = f2.get(v2);
                    } catch (IllegalArgumentException e) {
                        throw e;
                    } catch (IllegalAccessException e) {
                        throw new ObjectComparatorException(e);
                    }

                } else {

                    Method m1 = null;
                    Method m2 = null;
                    String[] methodNames = getPossibleMethodNames(fieldName.trim());
                    for (String methodName : methodNames) {
                        m1 = findMethod(v1.getClass(), methodName);
                        m2 = findMethod(v2.getClass(), methodName);
                        if (m1 != null && m2 != null) {
                            break;
                        }
                    }
                    if (m1 == null) {
                        return new ObjectComparatorImpl(getComparatorItem().getNextComparatorItem()).compare(v1, v2);
                    }

                    if (m2 == null) {
                        return new ObjectComparatorImpl(getComparatorItem().getNextComparatorItem()).compare(v1, v2);
                    }

                    if (!m1.isAccessible()) {
                        m1.setAccessible(true);
                    }
                    if (!m2.isAccessible()) {
                        m2.setAccessible(true);
                    }

                    try {
                        v1 = m1.invoke(v1);
                    } catch (IllegalAccessException e) {
                        throw new ObjectComparatorException(e);
                    } catch (IllegalArgumentException e) {
                        throw e;
                    } catch (InvocationTargetException e) {
                        throw new ObjectComparatorException(e);
                    }
                    try {
                        v2 = m2.invoke(v2);
                    } catch (IllegalAccessException e) {
                        throw new ObjectComparatorException(e);
                    } catch (IllegalArgumentException e) {
                        throw e;
                    } catch (InvocationTargetException e) {
                        throw new ObjectComparatorException(e);
                    }
                }
            }
        }

        int result = new ObjectComparatorImpl(new ComparatorItem(null, asc)).compare(v1, v2);

        if (result != 0) {
            return result;
        }

        return new ObjectComparatorImpl(getComparatorItem().getNextComparatorItem()).compare(o1, o2);
    }

}
