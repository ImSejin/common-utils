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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.assertion.lang.StringAssert;
import io.github.imsejin.common.util.StringUtils;

import java.net.URL;

public class UrlAssert<
        SELF extends UrlAssert<SELF>>
        extends ObjectAssert<SELF, URL> {

    public UrlAssert(URL actual) {
        super(actual);
    }

    public SELF hasHost(String expected) {
        if (StringUtils.isNullOrEmpty(actual.getHost()) || !actual.getHost().equals(expected)) {
            setDefaultDescription("It is expected to have that host, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.getHost());
            throw getException();
        }

        return self;
    }

    public SELF doesNotHaveHost() {
        if (!StringUtils.isNullOrEmpty(actual.getHost())) {
            setDefaultDescription("It is expected not to have host, but it does.");
            throw getException();
        }

        return self;
    }

    public SELF hasPort(int expected) {
        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();

        if (port != expected) {
            setDefaultDescription("It is expected to have that port, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, port);
            throw getException();
        }

        return self;
    }

    public SELF doesNotHavePort() {
        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();

        if (port != -1) {
            setDefaultDescription("It is expected not to have port, but it does.");
            throw getException();
        }

        return self;
    }

    public SELF hasPath(String expected) {
        if (StringUtils.isNullOrEmpty(actual.getPath()) || !actual.getPath().equals(expected)) {
            setDefaultDescription("It is expected to have that path, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.getPath());
            throw getException();
        }

        return self;
    }

    public SELF doesNotHavePath() {
        if (!StringUtils.isNullOrEmpty(actual.getPath())) {
            setDefaultDescription("It is expected not to have path, but it does.");
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public StringAssert<?> asHost() {
        StringAssert<?> assertion = Asserts.that(actual.getHost());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public NumberAssert<?, Integer> asPort() {
        int port = actual.getPort() == -1 ? actual.getDefaultPort() : actual.getPort();
        NumberAssert<?, Integer> assertion = Asserts.that(port);
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public StringAssert<?> asPath() {
        StringAssert<?> assertion = Asserts.that(actual.getPath());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}
