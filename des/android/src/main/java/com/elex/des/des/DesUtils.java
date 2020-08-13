package com.elex.des.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * des加解密
 *
 * @version: 1.0.0
 */
public class DesUtils {
    Key key;

    public DesUtils(String str) {
        // 生成密匙
        setKey(str);
    }

	/*public DesUtils() {
		setKey("8Ta4OaHZdpA=");
	}*/

    /**
     * 根据参数生成KEY
     */
    public void setKey(String strKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }


    /**
     * 加密String明文输入,String密文输出
     */
    public String encrypt(String strMing) throws Exception {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
//        BASE64Encoder base64en = new BASE64Encoder();
        byteMing = strMing.getBytes("UTF8");
        byteMi = this.getEncCode(byteMing);
//        strMi = base64en.encode(byteMi);
        strMi = bytesToHex(byteMi);
//        base64en = null;
        byteMing = null;
        byteMi = null;
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     * @throws IOException
     */
    public String decrypt(String strMi) throws Exception {
//        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
//        byteMi = base64De.decodeBuffer(strMi);
        byteMi = hexToByteArray(strMi);
        byteMing = this.getDesCode(byteMi);
        strMing = new String(byteMing, "UTF8");
//        base64De = null;
        byteMing = null;
        byteMi = null;
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    private String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    private byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    private byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static void main(String args[]) throws Exception {
        DesUtils des = new DesUtils("P&jFu6rzZ4");
        //String str1 = "{\"uName\":\"赵六\",\"uGender\":\"1\",\"uAge\":\"30\",\"uAllergy\":\"头孢\",\"uPhone\":\"13813813837\",\"uHeight\":\"168\",\"uWeight\":\"198\"}";
        String str1 = "{\"openId\":\"oIzb84tBkdGpApoYz0IvLJC_Sgdw\",\"pageNo\":\"1\", \"pageSize\": \"10\"}";
       // DES加密
        String str2 = des.encrypt(str1);
        String deStr = des.decrypt(str2);
        System.out.println("密文:" + str2);
//        // DES解密
        System.out.println("明文:" + deStr);
//
        System.out.println("852963"+des.decrypt("05c730a4c208e214e094ed0e7e263bfbefea9acaa44c17875861bf20e5207ccaad8f96823e4bb6ad07f17ea6f8e1efdacf3bc7ddd1582e507d58502b9832ddfeb4732f6e6f4941a74d7f17321c0ec883da111ecb607e837744888f1265d81ea66c3a28f5ab93a522286b1508c484f5747f405a4d8cdbe909cf3bc7ddd1582e50849cd1721c36bcaef14115803c88aa5a09ffbb10022fc2e40f616e291bd9ba331388bc1d890a219696488a27902cb9c324f7fd81065eb92d4e9189ecb2f60ab85a357621f8f59c1819a7f02c02b1a58611078a9f95829ffababda106bd07a567bde4883ad5282c18c29ca1d721f730c1186343e5c8714a9ffa5ccf3bfcf252a96632061ee3f00c4b354660321c5cce25ff8f7aeb788e03979e32ecd87fb951c1c3e26c8bbc416a7bd91aae1077c852ff84a56c56b0ff2988ad8f96823e4bb6ad3eeb5d173cdd3c90726ad572bc5c71b93d6a12535a4700af1b097d05bc0939e1e094ed0e7e263bfb52cc79e0337d6aeec3d535eaab0e2ea6713aef3456568010dfe9c1ec3add48995af91da86a07b573a362bc335a0f1e54a6d6ad3dddcba339cfbde4162fd7c3dea5fcc85f020b9039e9310038972c897b449b1ebeb4317c3390f36cfb606d6aff456ce349949bd89458f6826cb261f049be7136dd665ace9e80d6b04134765e01fa020fba6edbfd2184a56c56b0ff2988ad8f96823e4bb6ad3eeb5d173cdd3c90726ad572bc5c71b93d6a12535a4700afc48e36f249411db75e9ff79946d7badc86a1c141edf77e2bbe1baca5d7e6c886e6bbf94baad76e164e9189ecb2f60ab85a357621f8f59c1819a7f02c02b1a58611078a9f95829ffa0cfe95e47d0091dfed2a59b417b14a23ed276fafd457ef9c186343e5c8714a9fe6bbf94baad76e169842e78579be6c0e745088feb88d274b801205a62eda32d67cce8ca570266e2d4b99b85db091a883273920f1a0d16e45ff99e0e3a5caee91adb8f474fd9a11f9be403093ac4f516ab2890eb778c31d9fd26e79699f208295695ce56dbb86270643568f262a55ac668eaa57b9591f17ec60a5db637169e70a57a3c5611d74a199a2612452038011e097c73661c67a92f76747082f45941e6423799d0652812f95c27a92df057925f7d8e88a561bda5cc2c8816c733c40557a467ba00e49f9382074104c2998216eeecf3bc7ddd1582e50d39f6cd95b9b01ead91741fff7f593203281036d9ed3c706245c0a9e4b3d8b3f5e9ff79946d7badc661114e41b3d9d7f24f7fd81065eb92d4e9189ecb2f60ab85a357621f8f59c1819a7f02c02b1a58611078a9f95829ffa0cfe95e47d0091dfed2a59b417b14a23ed276fafd457ef9c186343e5c8714a9ffa5ccf3bfcf252a9df10fdf4fdeb637aa93b633377384fbe5a09546c56b91e61c1edaae080abab7eaebfc136949a8e86debacffdfeaa0f8b7caed459ebd9850629312af47cfb56fa4d3c37b273af9ef486a1c141edf77e2b01582161605884b431a527b9b8c741fb0cda0255c2c51e74710e7f735e769aab71096aca2aa0f447337b918fd5a899b011d43d8ca54734a6e299d554f6857c4e77e493a66a3a19681f06a24f9fce40777c048a7ae5d84e3ee1bc2d3605a6569e29eac748d447a4272ec1d77b112db8ef0384f7fc30f0dfeba29c45053d5d927bbd7742ddd53ad3e80f06337c3258b114ae0d4d2a23641dba3333a394df8e010ab42bbac125b7fd0b3efcc4b5446af1eb2b87e4df3a3b89d511adeaa6c585d1011d88f4d0e54d0933ede64d84aea5b0b36c86b1737e683035d0cce90e5e07ca433ebbee7ea017173b"));
    }

}
