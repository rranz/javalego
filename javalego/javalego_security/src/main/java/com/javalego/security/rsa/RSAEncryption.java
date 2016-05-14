package com.javalego.security.rsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.Security;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;

import sun.misc.BASE64Decoder;
 
@SuppressWarnings("restriction")
public class RSAEncryption {
 
    public static void main(String[] args)
    {
    	args = new String[] {"C:/TEMP/publickey.crt","Hello world"};
    	
        String publicKeyFilename = null;
        String inputData = null;
        
        RSAEncryption rsaEncryption = new RSAEncryption();
 
        if (args.length < 2)
        {
            System.err.println("Usage: java "+ rsaEncryption.getClass().getName()+
            " Public_Key_Filename Input_String_data");
            System.exit(1);
        }
 
        publicKeyFilename = args[0].trim();
        inputData = args[1].trim();
        rsaEncryption.encrypt(publicKeyFilename, inputData);
 
    }
 
    @SuppressWarnings("unused")
	private void encrypt (String publicKeyFilename, String inputFilename, String encryptedFilename){
 
        try {
 
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
 
            String key = readFileAsString(publicKeyFilename);
            BASE64Decoder b64 = new BASE64Decoder();
            AsymmetricKeyParameter publicKey = 
                (AsymmetricKeyParameter) PublicKeyFactory.createKey(b64.decodeBuffer(key));
            AsymmetricBlockCipher e = new RSAEngine();
            e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
            e.init(true, publicKey);
 
            String inputdata = readFileAsString(inputFilename);
            byte[] messageBytes = inputdata.getBytes();
            byte[] hexEncodedCipher = e.processBlock(messageBytes, 0, messageBytes.length);
 
            System.out.println(getHexString(hexEncodedCipher));
            BufferedWriter out = new BufferedWriter(new FileWriter(encryptedFilename));
            out.write(getHexString(hexEncodedCipher));
            out.close();
 
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private String encrypt (String publicKeyFilename, String inputData){
 
        String encryptedData = null;
        try {
 
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
 
            BASE64Decoder b64 = new BASE64Decoder();
            String key = readFileAsString(publicKeyFilename);
            AsymmetricKeyParameter publicKey = 
                (AsymmetricKeyParameter) PublicKeyFactory.createKey(b64.decodeBuffer(key));
            AsymmetricBlockCipher e = new RSAEngine();
            e = new org.bouncycastle.crypto.encodings.PKCS1Encoding(e);
            e.init(true, publicKey);
 
            byte[] messageBytes = inputData.getBytes();
            byte[] hexEncodedCipher = e.processBlock(messageBytes, 0, messageBytes.length);
 
            System.out.println(getHexString(hexEncodedCipher));
            encryptedData = getHexString(hexEncodedCipher);
    
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return encryptedData;
    }
 
    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
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
