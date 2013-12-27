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
package com.addthis.hydra.store.db;

import com.addthis.codec.CodableStatistics;
import com.addthis.codec.Codec;
import com.addthis.codec.CodecBin2;
import com.addthis.hydra.store.kv.KeyCoder;
import com.addthis.hydra.store.util.Raw;

import com.google.common.base.Objects;

/**
 */
class DBKeyCoder<V extends Codec.Codable> implements KeyCoder<DBKey, V> {

    protected final Codec codec;
    protected final Class<? extends V> clazz;

    public DBKeyCoder(Class<? extends V> clazz) {
        this(new CodecBin2(), clazz);
    }

    public DBKeyCoder(Codec codec, Class<? extends V> clazz) {
        this.codec = codec;
        this.clazz = clazz;
    }

    @Override
    public DBKey negInfinity() {
        return new DBKey(0, (Raw) null);
    }

    @Override
    public byte[] keyEncode(DBKey key) {
        return key != null ? key.toBytes() : new byte[0];
    }

    @Override
    public byte[] valueEncode(V value) {
        try {
            return codec.encode(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DBKey keyDecode(byte[] key) {
        return key.length > 0 ? new DBKey(key) : null;
    }

    @Override
    public V valueDecode(byte[] value) {
        try {
            return codec.decode(clazz, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean nullRawValueInternal(byte[] value) {
        return codec.storesNull(value);
    }

    public CodableStatistics valueStatistics(V value) {
        try {
            CodableStatistics statistics = codec.statistics(value);
            return statistics;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("codec", codec)
                .add("clazz", clazz)
                .toString();
    }
}
