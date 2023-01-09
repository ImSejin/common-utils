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

package io.github.imsejin.common.assertion.net;

import java.net.URL;
import java.util.AbstractMap.SimpleEntry;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.lang.IntegerAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.util.StringUtils;

public class UrlAssert<
        SELF extends UrlAssert<SELF>>
        extends ObjectAssert<SELF, URL> {

    public UrlAssert(URL actual) {
        super(actual);
    }

    protected UrlAssert(Descriptor<?> descriptor, URL actual) {
        super(descriptor, actual);
    }

    public SELF hasHost(String expected) {
        String host = actual.getHost();

        if (StringUtils.isNullOrEmpty(host) || !host.equals(expected)) {
            setDefaultDescription("It is expected to have that host, but it doesn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.host", host),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF doesNotHaveHost() {
        String host = actual.getHost();

        if (!StringUtils.isNullOrEmpty(host)) {
            setDefaultDescription("It is expected not to have host, but it does.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.host", host));

            throw getException();
        }

        return self;
    }

    public SELF hasPort(int expected) {
        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();

        if (port != expected) {
            setDefaultDescription("It is expected to have that port, but it doesn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.port", port),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF doesNotHavePort() {
        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();

        if (port != -1) {
            setDefaultDescription("It is expected not to have port, but it does.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.port", port));

            throw getException();
        }

        return self;
    }

    public SELF hasPath(String expected) {
        String path = actual.getPath();

        if (StringUtils.isNullOrEmpty(path) || !path.equals(expected)) {
            setDefaultDescription("It is expected to have that path, but it doesn't.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.path", path),
                    new SimpleEntry<>("expected", expected));

            throw getException();
        }

        return self;
    }

    public SELF doesNotHavePath() {
        String path = actual.getPath();

        if (!StringUtils.isNullOrEmpty(path)) {
            setDefaultDescription("It is expected not to have path, but it does.");
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.path", path));

            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public StringAssert<?> asHost() {
        class StringAssertImpl extends StringAssert<StringAssertImpl> {
            StringAssertImpl(Descriptor<?> descriptor, String actual) {
                super(descriptor, actual);
            }
        }

        String host = actual.getHost();
        return new StringAssertImpl(this, host);
    }

    public IntegerAssert<?> asPort() {
        class IntegerAssertImpl extends IntegerAssert<IntegerAssertImpl> {
            IntegerAssertImpl(Descriptor<?> descriptor, Integer actual) {
                super(descriptor, actual);
            }
        }

        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();
        return new IntegerAssertImpl(this, port);
    }

    public StringAssert<?> asPath() {
        class StringAssertImpl extends StringAssert<StringAssertImpl> {
            StringAssertImpl(Descriptor<?> descriptor, String actual) {
                super(descriptor, actual);
            }
        }

        String path = actual.getPath();
        return new StringAssertImpl(this, path);
    }

}
