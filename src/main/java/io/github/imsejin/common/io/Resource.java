/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.io;

import java.io.InputStream;
import java.time.Instant;

public interface Resource {

    /**
     * Gets path of resource.
     *
     * @return path
     */
    String getPath();

    /**
     * Gets name of resource.
     *
     * @return name
     */
    String getName();

    /**
     * Gets last modified time of resource.
     *
     * @return last modified time
     */
    Instant getLastModifiedTime();

    /**
     * Gets input stream from resource.
     *
     * @return input stream
     */
    InputStream getInputStream();

    /**
     * Gets size of resource.
     *
     * @return size
     */
    long getSize();

    /**
     * Returns whether it is directory or not.
     *
     * @return whether it is directory or not
     */
    boolean isDirectory();

}
