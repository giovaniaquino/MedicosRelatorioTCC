package com.example.pacientes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String Encriptar(String input) throws NoSuchAlgorithmException {
        //Seleciona o algoritimo de hash
        MessageDigest md = MessageDigest.getInstance("SHA-224");

        //Realiza o hashing
        byte[] messageDigest = md.digest(input.getBytes());

        //Formata os bytes para hexadecimal
        BigInteger BigInt = new BigInteger(1, messageDigest);

        //Retorna o hexadecimal convertido em String
        return BigInt.toString(16);
    }
}
