package cn.nekocode.bangcle.exported;

import com.bangcle.CryptoTool;
import com.bangcle.PreDataTool;
import java.lang.String;

public final class CryptoUtils {
    private static final String key1 = "e37c71ef9b85202aaa7276590488b2b5a7e0e9542e51c57fc23f471b55049cb125f9d4e79a9274a2fb3254000017a8418ece41620fb8e8aa5d8b41c56b4cd0ebaa7e35303cd9dbcf4383aa078813dc7cedf3dffc294ecf48cb1198d56e0cbd04966ec3c17842fece4e18ebbdcd8d58dcf9c28f6ceae5d6d1625e115e0d7256755bdd53148a445238ab66580bc88f94d085805b794a988d1cd4f2572b237454f63d50020dac14d4c3d1c5346703424dbf8af1dd43";

    private static final String key2 = "1ad7d7176cb7037909bd92431c0ac4d7bdf90809790cd5e2196032b7c953e0ffc0d257b8db51145f690c8b732980beb1269bfcb86baa7484237df6aad9a7c13a2026b2fd1b0c1b81d26e73e7636543db60451aeca41e3f95e30602af2b2648c7203fb0158b0ce7c6661c94c5e20db59f26330f268e02779cf0104a2f101518a6e03d46f5fb3a3b2862764699a32a39a5d947321fa6db2cd68591649a777860b7334421f6e77e1a8fa0a526f3e7b3d78f6d68263e";

    private static final byte[] iv = null;

    public static byte[] encrypt(byte[] plainData) {
        return PreDataTool.preDataOut160(CryptoTool.laesEncryptByteArr(PreDataTool.preDataIn160(plainData, key1, iv), key1, iv), key1, iv);
    }

    public static byte[] decrypt(byte[] cipherData) {
        return PreDataTool.preDataOut161(CryptoTool.laesDecryptByteArr(PreDataTool.preDataIn161(cipherData, key2, iv), key2, iv), key2, iv);
    }
}
