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

import com.squareup.javapoet.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.lang.model.element.Modifier

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class GenFilesTask extends DefaultTask {

    @TaskAction
    void gen() {
        final File genDir = new File(project.getBuildDir(), BangclePlugin.GEN_PATH)
        if (!genDir.exists()) {
            genDir.mkdirs()
        }

        final BangcleDsl bangcleDsl = project.extensions.getByName('bangcle')
        if (bangcleDsl == null || bangcleDsl.getAesKey() == null ||
                bangcleDsl.bangcleKey1 == null || bangcleDsl.bangcleKey2 == null) {

            return
        }

        genCryptoUtils(genDir, bangcleDsl)
        genKeys(genDir, bangcleDsl)
    }

    static void genCryptoUtils(File genDir, BangcleDsl bangcleDsl) {
        final TypeSpec.Builder BangcleUtils = TypeSpec.classBuilder(
                ClassName.get(bangcleDsl.genPackage, 'CryptoUtils')
        ).addModifiers(Modifier.PUBLIC, Modifier.FINAL)

        BangcleUtils.addField(
                FieldSpec.builder(String.class, 'key1')
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                        .initializer('"$L"', bangcleDsl.bangcleKey1)
                        .build()
        )

        BangcleUtils.addField(
                FieldSpec.builder(String.class, 'key2')
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                        .initializer('"$L"', bangcleDsl.bangcleKey2)
                        .build()
        )

        BangcleUtils.addField(
                FieldSpec.builder(ArrayTypeName.of(TypeName.BYTE), 'iv')
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                        .initializer(bangcleDsl.iv != null ? "\"${bangcleDsl.iv}\".getBytes()" : 'null')
                        .build()
        )

        final String bangclePackageName = 'com.bangcle'

        BangcleUtils.addMethod(
                MethodSpec.methodBuilder('encrypt')
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(ArrayTypeName.of(TypeName.BYTE))
                        .addParameter(ArrayTypeName.of(TypeName.BYTE), 'plainData')

                        .addStatement(
                        'return $T.preDataOut160($T.laesEncryptByteArr(' +
                                'PreDataTool.preDataIn160(plainData, key1, iv), key1, iv), key1, iv)',
                        ClassName.get(bangclePackageName, 'PreDataTool'),
                        ClassName.get(bangclePackageName, 'CryptoTool'))

                        .build()
        )

        BangcleUtils.addMethod(
                MethodSpec.methodBuilder('decrypt')
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(ArrayTypeName.of(TypeName.BYTE))
                        .addParameter(ArrayTypeName.of(TypeName.BYTE), 'cipherData')
                        .addStatement('return PreDataTool.preDataOut161(CryptoTool.laesDecryptByteArr(' +
                        'PreDataTool.preDataIn161(cipherData, key2, iv), key2, iv), key2, iv)')
                        .build()
        )

        JavaFile.builder(bangcleDsl.genPackage, BangcleUtils.build())
                .indent('    ')
                .build()
                .writeTo(genDir)
    }

    static void genKeys(File genDir, BangcleDsl bangcleDsl) {
        final TypeSpec.Builder Keys = TypeSpec.classBuilder(
                ClassName.get(bangcleDsl.genPackage, 'Keys')
        ).addModifiers(Modifier.PUBLIC, Modifier.FINAL)

        final AES aes = new AES(bangcleDsl.getAesKey(), bangcleDsl.getIv())
        for (Map.Entry<String, String> key : bangcleDsl.getKeys().entrySet()) {
            final byte[] cipherData = aes.encrypt(key.value)
            final StringBuilder cipherDataStr = new StringBuilder()
            for (byte d : cipherData) {
                cipherDataStr.append(String.valueOf((int) d)).append(',')
            }

            Keys.addField(
                    FieldSpec.builder(String.class, key.key)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer('new String(CryptoUtils.decrypt(new byte[] {$L}))', cipherDataStr)
                            .build()
            )
        }

        JavaFile.builder(bangcleDsl.genPackage, Keys.build())
                .indent('    ')
                .build()
                .writeTo(genDir)
    }


    static class AES {
        final Cipher cipher


        AES(byte[] key, byte[] iv) {
            if (iv == null) {
                cipher = Cipher.getInstance('AES/ECB/PKCS5Padding', 'SunJCE')
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, 'AES'))

            } else {
                cipher = Cipher.getInstance('AES/CBC/PKCS5Padding', 'SunJCE')
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, 'AES'), new IvParameterSpec(iv))
            }
        }

        byte[] encrypt(String plainText) {
            return cipher.doFinal(plainText.getBytes())
        }
    }
}