package com.javalego.security.rsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Security;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import sun.misc.BASE64Decoder;
 
@SuppressWarnings("restriction")
public class RSADecryption {
 
    public static void main(String[] args)
    {
    	args = new String[] {"C:/TEMP/privatekey.crt","44cad516495e16d369f69fda88cf7d925822cfd2d8c35dfa5a98d54849efae1455d1116e4b795bbfa067163c709e62a12d59eb069ff7c92084e567f944ecb54a964c32e0b9e92bb68c27bedd205ad0d60192c97253ca5b0b3b329020400cd6682ebba9ab6c0d276c7a3dae8f442341b7450cca573f8330f68238857f88552f7f"};
    	
        String privateKeyFilename = null;
        String encryptedData = null;
        
        RSADecryption rsaDecryption = new RSADecryption();
 
        if (args.length < 2)
        {
            System.err.println("Usage: java "+ rsaDecryption.getClass().getName()+
            " Private_Key_Filename Encrypted_String_Data");
            System.exit(1);
        }
 
        privateKeyFilename = args[0].trim();
        encryptedData = args[1].trim();
        rsaDecryption.decrypt(privateKeyFilename, encryptedData);
 
    }
 
    private String decrypt (String privateKeyFilename, String encryptedData) {
 
        String outputData = null;
        try {
 
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
 
            String key = readFileAsString(privateKeyFilename);
            BASE64Decoder b64 = new BASE64Decoder();
            AsymmetricKeyParameter privateKey = 
                (AsymmetricKeyParameter) PrivateKeyFactory.createKey(b64.decodeBuffer(key));
            AsymmetricBlockCipher e = new RSAEngine();
            e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
            e.init(false, privateKey);
 
            byte[] messageBytes = hexStringToByteArray(encryptedData);
            byte[] hexEncodedCipher = e.processBlock(messageBytes, 0, messageBytes.length);
 
            System.out.println(new String(hexEncodedCipher));
            outputData = new String(hexEncodedCipher);
 
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return outputData;
    }
 
    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
 
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
 
    private static String readFileAsString(String filePath)
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        //System.out.println(fileData.toString());
        return fileData.toString();
    }
 
}
