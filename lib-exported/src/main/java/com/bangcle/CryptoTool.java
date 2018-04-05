package com.bangcle;

public class CryptoTool {

    /**
     * 算法：laes
     * 加密函数，待加密的数据是String，加密结果使用base64编码
     * str  待加密的String
     * key  白盒密钥，由16进制字符串组成
     * iv   初始化向量，由16字节组成，使用CBC模式加密；如果传入null，则表示使用ECB模式加密
     * 返回值：正确--加密后密文（Base64 编码），错误--NULL
     */
    public native static String laesEncryptStringWithBase64(String str, String key, byte[] iv);

    /**
     * 算法：laes
     * 解密函数，待解密数据使用base64编码，解密结果是String
     * str  待解密的String
     * key  白盒密钥，由16进制字符串组成
     * iv   初始化向量，由16字节组成，使用CBC模式加密；如果传入null，则表示使用ECB模式加密
     * 返回值：正确--解密后明文，错误--NULL
     */
    public native static String laesDecryptStringWithBase64(String str, String key, byte[] iv);

    /**
     * 算法：laes
     * 加密函数，待加密数据是byte数组，加密结果是byte数组
     * data 待加密的byte数组
     * key  白盒密钥，由16进制字符串组成
     * iv   初始化向量，由16字节组成，使用CBC模式加密；如果传入null，则表示使用ECB模式加密
     * 返回值：正确--加密后密文数组，错误--NULL
     */
    public native static byte[] laesEncryptByteArr(byte[] data, String key, byte[] iv);

    /**
     * 算法：laes
     * 解密函数，待解密数据是byte数组，解密结果是byte数组
     * data 待加密的byte数组
     * key  白盒密钥，由16进制字符串组成
     * iv   初始化向量，由16字节组成，使用CBC模式加密；如果传入null，则表示使用ECB模式加密
     * 返回值：正确--解密后明文数组，错误--NULL
     */
    public native static byte[] laesDecryptByteArr(byte[] data, String key, byte[] iv);


    static {
        System.loadLibrary("bangcle_crypto_tool");
    }
}
