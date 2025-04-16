/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.api.parser;

import java.math.BigDecimal;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class TimeDuration implements Token {
    private final long nanoseconds;
    private final String original;

    public TimeDuration(String input) {
        this.original = input;
        this.nanoseconds = this.parseNanoseconds(input);
    }

    private long parseNanoseconds(String input) {
    input = input.trim().toLowerCase();
    if (input.endsWith("ms")) {
        BigDecimal value = new BigDecimal(input.replace("ms", ""));
        return value.multiply(BigDecimal.valueOf(1_000_000L)).longValue();
    } else if (input.endsWith("s")) {
        BigDecimal value = new BigDecimal(input.replace("s", ""));
        return value.multiply(BigDecimal.valueOf(1_000_000_000L)).longValue();
    } else if (input.endsWith("m")) {
        BigDecimal value = new BigDecimal(input.replace("m", ""));
        return value.multiply(BigDecimal.valueOf(60_000_000_000L)).longValue();
    } else if (input.endsWith("h")) {
        BigDecimal value = new BigDecimal(input.replace("h", ""));
        return value.multiply(BigDecimal.valueOf(3_600_000_000_000L)).longValue();
    }
    throw new IllegalArgumentException("Unknown time duration: " + input);
}

    public long getNanoseconds() {
        return nanoseconds;
    }

    @Override
    public Object value() {
        return nanoseconds;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(original);
    }
}
