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
package com.addthis.hydra.util;

import java.io.File;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WriteableDiskCheck extends MeteredHealthCheck {

    private List<File> checkedFiles;

    public WriteableDiskCheck(int maxFails, List<File> checkedFiles) {
        super(maxFails, "touch_disk_failure", TimeUnit.MINUTES);
        this.checkedFiles = checkedFiles;
    }

    @Override
    public boolean check() {
        for (File file : this.checkedFiles) {
            String[] cmdarray = {"touch", file.getAbsolutePath()};
            try {
                Process process = Runtime.getRuntime().exec(cmdarray);
                if (process.waitFor() == 0) {
                    return true;
                }
            } catch (Exception e) {
                // keep trying files until one succeeds, or all fail
            }
        }
        return false;
    }
}
