/*
 * Copyright 2020 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.constant.interfaces;

/**
 * This is for that enum provides {@code key()} and {@code value()}.
 */
public interface KeyValue {

    /**
     * Gets name of the enum.
     *
     * <p> It replaces {@link Enum#name()} with itself.
     *
     * @return enum's name
     */
    String key();

    /**
     * Gets value of the enum.
     *
     * <p> This should be implemented to return the first parameter
     * of enum's constructor.
     *
     * @return the first parameter of enum's constructor
     */
    String value();

}
