/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.addthis.hydra.task.source;

import com.addthis.codec.Codec;
import com.addthis.hydra.task.stream.StreamFile;

import com.google.common.base.Objects;

/** */
public class SimpleMark implements Codec.Codable {

    @Codec.Set(codable = true)
    private String val;
    @Codec.Set(codable = true)
    private long index;
    @Codec.Set(codable = true)
    private boolean end;

    public SimpleMark set(String val, long index) {
        this.setValue(val);
        this.setIndex(index);
        return this;
    }

    public static String calcValue(StreamFile stream) {
        return stream.lastModified() + "/" + stream.length();
    }

    public void update(StreamFile stream) {
        this.setValue(calcValue(stream));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("val", getValue())
                .add("index", getIndex())
                .add("end", isEnd())
                .toString();
    }

    public String getValue() {
        return val;
    }

    public void setValue(String val) {
        this.val = val;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    // no-op functions
    public int getError() {
        return -1;
    }

    public void setError(int error) {
    }
}
