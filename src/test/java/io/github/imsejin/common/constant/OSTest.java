/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

import static org.assertj.core.api.Assertions.*;

@DisplayName("OS")
class OSTest {

    @Nested
    @DisplayName("method 'getCurrentOS'")
    class GetCurrentOS {
        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.LINUX)
        void linux() {
            assertThat(OS.getCurrentOS()).isEqualTo(OS.LINUX);
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.MAC)
        void mac() {
            assertThat(OS.getCurrentOS()).isEqualTo(OS.MAC);
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.AIX)
        void aix() {
            assertThat(OS.getCurrentOS()).isEqualTo(OS.AIX);
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.SOLARIS)
        void solaris() {
            assertThat(OS.getCurrentOS()).isEqualTo(OS.SOLARIS);
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
        void windows() {
            assertThat(OS.getCurrentOS()).isEqualTo(OS.WINDOWS);
        }
    }

    @Nested
    @DisplayName("method 'isCurrentOS'")
    class IsCurrentOS {
        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.LINUX)
        void linux() {
            for (OS os : OS.values()) {
                if (os == OS.LINUX) {
                    assertThat(OS.LINUX.isCurrentOS()).isTrue();
                } else {
                    assertThat(os.isCurrentOS()).isFalse();
                }
            }
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.MAC)
        void mac() {
            for (OS os : OS.values()) {
                if (os == OS.MAC) {
                    assertThat(OS.MAC.isCurrentOS()).isTrue();
                } else {
                    assertThat(os.isCurrentOS()).isFalse();
                }
            }
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.AIX)
        void aix() {
            for (OS os : OS.values()) {
                if (os == OS.AIX) {
                    assertThat(OS.AIX.isCurrentOS()).isTrue();
                } else {
                    assertThat(os.isCurrentOS()).isFalse();
                }
            }
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.SOLARIS)
        void solaris() {
            for (OS os : OS.values()) {
                if (os == OS.SOLARIS) {
                    assertThat(OS.SOLARIS.isCurrentOS()).isTrue();
                } else {
                    assertThat(os.isCurrentOS()).isFalse();
                }
            }
        }

        @Test
        @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
        void windows() {
            for (OS os : OS.values()) {
                if (os == OS.WINDOWS) {
                    assertThat(OS.WINDOWS.isCurrentOS()).isTrue();
                } else {
                    assertThat(os.isCurrentOS()).isFalse();
                }
            }
        }
    }

}
