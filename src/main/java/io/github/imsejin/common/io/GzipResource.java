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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class GzipResource extends AbstractResource {

    private final long compressedSize;

    private final Instant lastModifiedTime;

    public GzipResource(
            String name, InputStream inputStream,
            long size, long compressedSize, long lastModifiedMilliTime
    ) {
        super(name, name, inputStream, size, false);
        this.compressedSize = compressedSize;
        this.lastModifiedTime = Instant.ofEpochMilli(lastModifiedMilliTime);
    }

}
