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
package com.addthis.hydra.data.filter.closeablebundle;

import com.addthis.codec.Codec;
import com.addthis.hydra.common.plugins.PluginReader;
import com.addthis.hydra.data.filter.bundle.BundleFilter;

@Codec.Set(classMapFactory = CloseableBundleFilter.CMAP.class)
public abstract class CloseableBundleFilter extends BundleFilter implements Codec.Codable {

    private static Codec.ClassMap cmap = new Codec.ClassMap() {
        @Override
        public String getClassField() {
            return "op";
        }
    };

    public static class CMAP implements Codec.ClassMapFactory {

        public Codec.ClassMap getClassMap() {
            return cmap;
        }
    }

    /** register types */
    static {
        PluginReader.registerPlugin("-closeablefilters.classmap", cmap, CloseableBundleFilter.class);
    }

    /* Filters can implement this if they want to save their data somehow after a job finishes */
    public abstract void close();


}
