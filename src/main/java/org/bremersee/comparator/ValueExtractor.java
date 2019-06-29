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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The value extractor.
 *
 * @author Christian Bremer
 */
public interface ValueExtractor {

  /**
   * Find value object.
   *
   * @param obj   the obj
   * @param field the field
   * @return the object
   */
  Object findValue(Object obj, String field);

  /**
   * Find field optional.
   *
   * @param clazz the clazz
   * @param name  the name
   * @return the optional
   */
  default Optional<Field> findField(final Class<?> clazz, final String name) {
    return findField(clazz, name, null);
  }

  /**
   * Find field optional.
   *
   * @param clazz the clazz
   * @param name  the name
   * @param type  the type
   * @return the optional
   */
  default Optional<Field> findField(final Class<?> clazz, final String name,
      @SuppressWarnings("SameParameterValue") final Class<?> type) {
    Class<?> searchType = clazz;
    while (!Object.class.equals(searchType) && searchType != null) {
      Field[] fields = searchType.getDeclaredFields();
      for (Field field : fields) {
        if ((name == null || name.equals(field.getName())) && (type == null || type
            .equals(field.getType()))) {
          return Optional.of(field);
        }
      }
      searchType = searchType.getSuperclass();
    }
    return Optional.empty();
  }

  /**
   * Get possible method names string [ ].
   *
   * @param name the name
   * @return the string [ ]
   */
  default String[] getPossibleMethodNames(final String name) {
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

  /**
   * Find method optional.
   *
   * @param clazz the clazz
   * @param name  the name
   * @return the optional
   */
  default Optional<Method> findMethod(final Class<?> clazz, final String name) {
    return Arrays.stream(getPossibleMethodNames(name))
        .map(methodName -> findMethod(clazz, methodName, new Class[0]))
        .flatMap(method -> method.map(Stream::of).orElseGet(Stream::empty))
        .findFirst();
  }

  /**
   * Find method optional.
   *
   * @param clazz      the clazz
   * @param name       the name
   * @param paramTypes the param types
   * @return the optional
   */
  default Optional<Method> findMethod(final Class<?> clazz, final String name,
      final Class<?>... paramTypes) {
    Class<?> searchType = clazz;
    while (searchType != null) {
      Method[] methods =
          searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
      for (Method method : methods) {
        if (name.equals(method.getName())
            && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
          return Optional.of(method);
        }
      }
      searchType = searchType.getSuperclass();
    }
    return Optional.empty();
  }

  /**
   * Invoke object.
   *
   * @param m   the m
   * @param arg the arg
   * @return the object
   */
  default Object invoke(final Method m, final Object arg) {
    try {
      if (!m.isAccessible()) {
        m.setAccessible(true);
      }
      return m.invoke(arg);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ComparatorException("Invoking method '" + m.getName() + "' failed.", e);
    }
  }

  /**
   * Invoke object.
   *
   * @param f   the f
   * @param arg the arg
   * @return the object
   */
  default Object invoke(final Field f, final Object arg) {
    if (!f.isAccessible()) {
      f.setAccessible(true);
    }
    try {
      return f.get(arg);
    } catch (IllegalAccessException e) {
      throw new ComparatorException("Getting value from field '" + f.getName() + "' failed", e);
    }
  }

}
