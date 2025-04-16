/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.wrangler.api.parser;
import org.junit.Test;

import com.google.gson.JsonPrimitive;

import static org.junit.Assert.assertEquals;

public class TimeDurationTest {

    // ✅ Positive Test Cases
    @Test
    public void testMilliseconds() {
        TimeDuration td = new TimeDuration("500ms");
        assertEquals(500_000_000L, td.getNanoseconds());
    }

    @Test
    public void testSeconds() {
        TimeDuration td = new TimeDuration("1s");
        assertEquals(1_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testMinutes() {
        TimeDuration td = new TimeDuration("2m");
        assertEquals(120_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testHours() {
        TimeDuration td = new TimeDuration("0.5h");
        assertEquals(1_800_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testFractionalMilliseconds() {
        TimeDuration td = new TimeDuration("0.1ms");
        assertEquals(100_000L, td.getNanoseconds());
    }

    @Test
    public void testFractionalSeconds() {
        TimeDuration td = new TimeDuration("1.5s");
        assertEquals(1_500_000_000L, td.getNanoseconds());
    }

    @Test
    public void testFractionalMinutes() {
        TimeDuration td = new TimeDuration("0.5m");
        assertEquals(30_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testFractionalHours() {
        TimeDuration td = new TimeDuration("1.25h");
        assertEquals(4_500_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testTrimAndCase() {
        TimeDuration td = new TimeDuration(" 3S ");
        assertEquals(3_000_000_000L, td.getNanoseconds());
    }

    // ❌ Negative Test Cases
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSuffix() {
        new TimeDuration("3days");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingUnit() {
        new TimeDuration("100");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownUnit() {
        new TimeDuration("5d");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        new TimeDuration("");
    }

    @Test(expected = NumberFormatException.class)
    public void testOnlyUnitNoNumber() {
        new TimeDuration("ms");
    }

    @Test(expected = NumberFormatException.class)
    public void testAlphabeticalValue() {
        new TimeDuration("abcm");
    }

    @Test(expected = NumberFormatException.class)
    public void testSpecialCharacters() {
        new TimeDuration("@!#s");
    }

    @Test(expected = NullPointerException.class)
    public void testNullInput() {
        new TimeDuration(null);
    }

    // 🔍 Edge and Boundary Cases
    @Test
    public void testVerySmallValue() {
        TimeDuration td = new TimeDuration("0.000001s");
        assertEquals(1_000L, td.getNanoseconds());
    }

    @Test
    public void testVeryLargeValue() {
        TimeDuration td = new TimeDuration("10000h");
        assertEquals(36_000_000_000_000_000L, td.getNanoseconds());
    }

    @Test
    public void testZeroDuration() {
        TimeDuration td = new TimeDuration("0s");
        assertEquals(0L, td.getNanoseconds());
    }

    // 🧪 JSON Representation
    @Test
    public void testJsonOutput() {
        TimeDuration td = new TimeDuration("5s");
        assertEquals(new JsonPrimitive("5s"), td.toJson());
    }

    // 🔎 TokenType Check
    @Test
    public void testTokenType() {
        TimeDuration td = new TimeDuration("1s");
        assertEquals(TokenType.TIME_DURATION, td.type());
    }

    // 🔄 Value Method
    @Test
    public void testValue() {
        TimeDuration td = new TimeDuration("2s");
        assertEquals(2_000_000_000L, td.value());
    }
}