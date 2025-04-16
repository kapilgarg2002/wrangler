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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.google.gson.JsonPrimitive;

/**
 * Tests {@link ByteSize}
 */

public class ByteSizeTest {

    @Test
    public void testParseBytesKB() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(10240L, byteSize.getBytes());
    }

    @Test
    public void testParseBytesMB() {
        ByteSize byteSize = new ByteSize("10MB");
        assertEquals(10485760L, byteSize.getBytes());
    }

    @Test
    public void testParseBytesGB() {
        ByteSize byteSize = new ByteSize("10GB");
        assertEquals(10737418240L, byteSize.getBytes());
    }

    @Test
    public void testParseBytesB() {
        ByteSize byteSize = new ByteSize("10B");
        assertEquals(10L, byteSize.getBytes());
    }

    @Test
    public void testValue() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(10240L, byteSize.value());
    }

    @Test
    public void testType() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(TokenType.BYTE_SIZE, byteSize.type());
    }

    @Test
    public void testToJson() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(new JsonPrimitive("10KB"), byteSize.toJson());
    }
}