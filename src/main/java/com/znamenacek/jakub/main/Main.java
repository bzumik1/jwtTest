package com.znamenacek.jakub.main;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
    //secret key algorithms
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); //generate key
        String store = Encoders.BASE64.encode(key.getEncoded()); // this can be stored
        byte[] load = Decoders.BASE64.decode(store); // this is how to load stored key
        Key key1 = Keys.hmacShaKeyFor(load);

        String jws = Jwts.builder().setSubject("Jakub").signWith(key1).compact();

        System.out.println(jws);
        System.out.println(Encoders.BASE64.encode(key.getEncoded()));

        System.out.println( Jwts.parser().setSigningKey(key1).parseClaimsJws(jws).getBody().getSubject().equals("Jakub"));



    //asymmetric key algorithms
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();





    }
}
