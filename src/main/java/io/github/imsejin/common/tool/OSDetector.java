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

package io.github.imsejin.common.tool;

import io.github.imsejin.common.constant.OperatingSystem;

/**
 * Operating system detector
 *
 * @see OperatingSystem
 */
public final class OSDetector {

    private static final String CURRENT_OS_NAME = System.getProperty("os.name").toLowerCase();

    private OSDetector() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns current operating system.
     *
     * @return current operating system.
     */
    public static OperatingSystem getOS() {
        return OperatingSystem.of(CURRENT_OS_NAME).orElse(null);
    }

}
