package com.psiphiglobal.proto;

import com.psiphiglobal.proto.util.crypto.AsymmetricCryptoUtil;
import com.psiphiglobal.proto.util.crypto.SymmetricCryptoUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        File file = new File("/Users/aditya/bitcoin.pdf");
        byte[] contents = IOUtils.toByteArray(new FileInputStream(file));
        System.out.println("File Size : " + contents.length);

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLDR0TMvdO6etP34OvpoweA3NWSb/h067A9Gqby248oWaUNeyeYpHDtBpZ6V6bUFvcFfJDqpMil6z0o09Cl8H0sbG34GCxKLyYGQfd0E7fWgGIKzOeAS9pOu6o+9JZqtWwT0GppVHxq81VqZdcmYFmEXVGH8vh5kA+UhFJ9AE3BwIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsNHRMy907p60/fg6+mjB4Dc1ZJv+HTrsD0apvLbjyhZpQ17J5ikcO0GlnpXptQW9wV8kOqkyKXrPSjT0KXwfSxsbfgYLEovJgZB93QTt9aAYgrM54BL2k67qj70lmq1bBPQamlUfGrzVWpl1yZgWYRdUYfy+HmQD5SEUn0ATcHAgMBAAECgYB3RK88qYmQ6XmJ0Cjuv8cNc145lBZ+yAfrv3m12f7bCIGo/LvRerCWkWPM3Svlwj6Yf0aGnceIc1rJl05Dy1Fzla7ji/xYizRUN3q3Pb4IPLIeF7FPMC8EXuMQ4eyF6SL4eplPzq05cAFaHlllLbldJF+s7ws2sFO9ev8OqBdVcQJBAPlDGUtIl2TvxWXhWhDZUikf7SJxyEZMgXfvZiIGru43cD088U/9TWs9ikYaMfsuB1S3McXLhx/XehGpt7fZKm8CQQCOz1opfX2pReMzQNMyXf5EHun4relE+saCEAtIFvJzrPK4iSbQ5aX9pi+8NIRXyghQsCNSe1y7GEq3o0Nz6+jpAkAbwNXTJQz+RxgihNnjYF8ehxh/Kk4TTbdopDYp+baGfZO4rc1si5aQJzT7IzWHcxOL4i3fiQ7Ng89qogv7o2lvAkBqWhUFLQV2GCvZMX+W1NTUmkkX/zvnOPK4TYZ5S02Hw9aGgu07SKrTOP33pQyh7D1BCctkU6Z9Vtvd4mILZDZxAkEAmfHnHgLLmP94gCOxot4Il8bPzpoHdoQqiK4D69m6d9B/G6unpLVACBpEDRsQDBD2djDQLEBBKHdkQHUn8q19gw==";
        String key = "secret";
        byte[] encryptedKey = AsymmetricCryptoUtil.encryptWithPublicKey(Base64.decodeBase64(publicKey), key.getBytes());
        System.out.println("Encrypted Key\n" + Base64.encodeBase64String(encryptedKey));
        byte[] dec = AsymmetricCryptoUtil.decryptWithPrivateKey(Base64.decodeBase64(privateKey), encryptedKey);
        System.out.println(new String(dec));

        String name = "bitcoin.pdf";
        String creator = "aditya";
        String encryptedContents = Base64.encodeBase64String(SymmetricCryptoUtil.encrypt(key, contents));
        System.out.println("\nEncrypted Content\n" + encryptedContents);
        System.out.println("Encrypted COntent Size: " + encryptedContents.length());

        String raw = name + "|" + creator + "|" + encryptedContents;
        byte[] rawBytes = raw.getBytes();
        byte[] signature = AsymmetricCryptoUtil.sign(Base64.decodeBase64(privateKey), rawBytes);
        System.out.println("\nSignature\n" + Base64.encodeBase64String(signature));
    }
}
