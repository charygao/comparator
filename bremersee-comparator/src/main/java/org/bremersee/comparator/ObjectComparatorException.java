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

/**
 * <p>
 * Exception that might be thrown by a {@link ObjectComparator} implementation.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ObjectComparatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ObjectComparatorException() {
        super();
    }

    /**
     * Construct the exception with the specified message.
     *
     * @param message the exception message
     */
    public ObjectComparatorException(String message) {
        super(message);
    }

    /**
     * Construct the exception with the specified cause.
     *
     * @param cause the cause
     */
    public ObjectComparatorException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception with the specified message and cause.
     *
     * @param message the exception message
     * @param cause   the cause
     */
    public ObjectComparatorException(String message, Throwable cause) {
        super(message, cause);
    }

}
