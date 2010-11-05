package com.ambientideas;

import java.io.FileReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAEncrypt
{
    public static final int RSA_BITSTRENGTH = 3072;
    private KeyPair keyPair;

    public RSAEncrypt() throws Exception
    {
        initialize();
    }

    public void initialize() throws Exception
    {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(RSA_BITSTRENGTH);
        keyPair = keygen.generateKeyPair();
    }

    public static long runExample() throws Exception {
    		long beforeRSA = java.lang.System.currentTimeMillis();
    	
    		RSAEncrypt app = new RSAEncrypt();

//        System.out.println("Enter a line: ");
//        java.io.InputStreamReader sreader = new java.io.InputStreamReader(System.in);
//        java.io.BufferedReader breader = new java.io.BufferedReader(sreader);
//        String input = breader.readLine();
    		String input = HeadToHeadTest.getTextFromFile(HeadToHeadTest.CLEARTEXT_FILENAME);

//        System.out.println("Plaintext = " + input);

        String ciphertext = app.encrypt(input);
//        System.out.println("After Encryption Ciphertext = " + ciphertext);
//        System.out.println("After Decryption Plaintext = " + app.decrypt(ciphertext));
        
        long afterRSA = java.lang.System.currentTimeMillis();

        return afterRSA - beforeRSA;   
    }

    public String encrypt(String plaintext)  throws Exception
    {
        PublicKey key = keyPair.getPublic();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF8"));
        return encodeBASE64(ciphertext);
    }

    public String decrypt(String ciphertext)  throws Exception
    {
        PrivateKey key = keyPair.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plaintext = cipher.doFinal(decodeBASE64(ciphertext));
        return new String(plaintext, "UTF8");
    }

    private static String encodeBASE64(byte[] bytes)
    {
        BASE64Encoder b64 = new BASE64Encoder();
        return b64.encode(bytes);
    }

    private static byte[] decodeBASE64(String text) throws Exception
    {
        BASE64Decoder b64 = new BASE64Decoder();
        return b64.decodeBuffer(text);
    }    

    public static void main(String[] args) throws Exception
    {
        runExample();
    }
}