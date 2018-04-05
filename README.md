This project mainly does the following things:
- Integrate the java and so files generated by Bangcle Crypto System
- Generate the [`CryptoUtils.java`](generated_files_examples/CryptoUtils.java) and [`Keys.java`](generated_files_examples/Keys.java) files automatically
  - The `CryptoUtils.java` is a simplified wrapper of Bangcle's crypto tools
  - The `Keys.java` contains the encrypted keys which are encrypted **at build time**

### Starting

After you clone this repostory, you need to excute the following command before you open this project with IDE:

```sh
touch exclude && ./gradlew :gradle-plugin:uploadArchives
```

And then copy the java and so files generated by Bangcle Crypto System into the corresponding directory in `lib-exported` module. Now you can configure the generator-plugin in the [`lib-exported/build.gradle`](lib-exported/build.gradle).


```gradle
apply plugin: 'bangcle'
bangcle {
    aesKeyHex = ... // Hex string of your AES key
    ivHex = ... // Hex string of your AES iv
    bangcleKey1 = ... // Bangcle encryption key
    bangcleKey2 = ... // Bangcle decryption key
    genPackage = ... // Target package of the generated java files

    keys {
        // key ConstantFiledName, SecretKeyValue
        key ..., ...
    }
}
```

Note that the generator-plugin will use `AES/ECB/PKCS5Padding` encryption algorithm to encrypt the keys if you don't provide the `ivHex`. Otherwise will use `AES/CBC/PKCS5Padding` encryption algorithm. You can generate random AES keys and ivs at [https://asecuritysite.com/encryption/keygen](https://asecuritysite.com/encryption/keygen).