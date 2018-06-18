/*
 * Copyright 2018 nekocode (nekocode.cn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.bangcle

import org.gradle.api.Action

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class BangcleDsl {
    private byte[] aesKey
    private byte[] iv

    String bangcleKey1
    String bangcleKey2
    String genPackage = 'cn.nekocode.bangcle'

    private final KeysDsl keysDsl = new KeysDsl()


    void setAesKeyHex(String value) {
        aesKey = hexStringToByteArray(value)
    }

    byte[] getAesKey() {
        return aesKey
    }

    void setIvHex(String value) {
        iv = hexStringToByteArray(value)
    }

    byte[] getIv() {
        return iv
    }

    KeysDsl keys(Action<KeysDsl> action) {
        action.execute(keysDsl)
        return keysDsl
    }

    Map<String, String> getKeys() {
        return keysDsl.getKeys()
    }

    /**
     * https://stackoverflow.com/a/140861/8842049
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length()
        byte[] data = new byte[len / 2]
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16))
        }
        return data
    }
}