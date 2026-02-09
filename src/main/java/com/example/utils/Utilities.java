package com.example.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.reflect.Type;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;
import com.google.gson.reflect.TypeToken;
import com.example.configurations.PropertyInputs;
import com.google.gson.Gson;


/**
 *
 * @author 21701
 */
public class Utilities {

    String JCE_PROVIDER = "BC";
    static PropertyInputs propertyInputs = PropertyInputs.getPropertyInputs();
       
    public static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(90000) + 10000;
        return n;
    }

    public static byte[] GenerateSessionKey() throws NoSuchAlgorithmException, NoSuchProviderException {
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        SecretKey key = kgen.generateKey();
        byte[] symmKey = key.getEncoded();
        return symmKey;
    }

    public static byte[] EncryptUsingSessionKey(byte[] skey, byte[] data) throws InvalidCipherTextException {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new AESEngine(), new PKCS7Padding());
        cipher.init(true, new KeyParameter(skey));
        int outputSize = cipher.getOutputSize(data.length);
        byte[] tempOP = new byte[outputSize];
        int processLen = cipher.processBytes(data, 0, data.length, tempOP, 0);
        int outputLen = cipher.doFinal(tempOP, processLen);
        byte[] result = new byte[processLen + outputLen];
        System.arraycopy(tempOP, 0, result, 0, result.length);
        return result;
    }

    public static byte[] GenerateSha256Hash(byte[] message) {
        String algorithm = "SHA-256";
        String SECURITY_PROVIDER = "BC";
        byte[] hash = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            hash = digest.digest(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static byte[] EncryptUsingPublicKey(byte[] data) throws IOException, GeneralSecurityException, Exception {
        PublicKey publicKey = null;
        String certFilePath = propertyInputs.getCERT_FILE();
        File file = new File(certFilePath);
        byte[] bytevalue = Files.readAllBytes(file.toPath());

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        InputStream streamvalue = new ByteArrayInputStream(bytevalue);
        Certificate certificate = certificateFactory.generateCertificate(streamvalue);
        publicKey = certificate.getPublicKey();
        Cipher pkCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        pkCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encSessionKey = pkCipher.doFinal(data);
        return encSessionKey;
    }

    public static byte[] decryptText1(byte[] text, byte[] key) throws Exception {
        byte[] bytePlainText = null;
        try {
//            byte[] dec_Key = org.apache.commons.codec.binary.Base64.decodeBase64(key);
            SecretKey dec_Key_original = new SecretKeySpec(key, 0, key.length, "AES");
//            byte[] decodedValue = org.apache.commons.codec.binary.Base64.decodeBase64(text);
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, dec_Key_original);
            bytePlainText = aesCipher.doFinal(text);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bytePlainText;
    }

    public static Map<String, String> getRequestParametersMap(Map<String, String[]> map) {
        Map<String, String> RequestParameters = new HashMap<>();
        for (String parameterName : map.keySet()) {
            String[] values = map.get(parameterName);
            if (values != null && values.length > 0) {
                RequestParameters.put(parameterName, values[0]);
            } else {
                RequestParameters.put(parameterName, null);
            }
        }
        return RequestParameters;
    }

    public static Map<String, Object> getRequestParametersMapObj(Map<String, String[]> map) {
        Map<String, Object> RequestParameters = new HashMap<>();
        for (String parameterName : map.keySet()) {
            String[] values = map.get(parameterName);
            if (values != null && values.length > 0) {
                RequestParameters.put(parameterName, values[0]);
            } else {
                RequestParameters.put(parameterName, null);
            }
        }
        return RequestParameters;
    }

    public static Map<String, String> getRequestParametersJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String[]>>() {
        }.getType();
        Map<String, String[]> map = gson.fromJson(jsonString, mapType);

        Map<String, String> requestParameters = new HashMap<>();
        for (String parameterName : map.keySet()) {
            String[] values = map.get(parameterName);
            if (values != null && values.length > 0) {
                requestParameters.put(parameterName, values[0]);
            } else {
                requestParameters.put(parameterName, null);
            }
        }
        return requestParameters;
    }

    public static String getStringFromInputStream(InputStream is) throws Exception {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return sb.toString();
    }

    public static String decryptWithPublicKey(String enc, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(Base64.decode(enc));
            return new String(Base64.encode(decryptedData));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        byte[] enc = java.util.Base64.getDecoder().decode("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");
        byte[] skey = java.util.Base64.getDecoder().decode("MKrvEVbTfUgaDuwcdM6qztLMeHlPOZkENgmEnBxZWQo=");
        String a = new String(Utilities.decryptText1(enc, skey));
        System.out.println(a);
    }

}

