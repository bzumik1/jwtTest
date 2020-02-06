package com.znamenacek.jakub.main;

import io.jsonwebtoken.Claims;
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
import java.sql.Date;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
    //secret key algorithms
        System.out.println("SECRET KEY ALGORITHMS");
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); //generate key
        String store = Encoders.BASE64.encode(key.getEncoded()); // this can be stored
        byte[] load = Decoders.BASE64.decode(store); // this is how to load stored key
        Key key1 = Keys.hmacShaKeyFor(load);

        String jws = Jwts.builder().setSubject("Jakub").signWith(key1).compact();

        System.out.println(jws);
        System.out.println(Encoders.BASE64.encode(key.getEncoded()));

        System.out.println( Jwts.parser().setSigningKey(key1).parseClaimsJws(jws).getBody().getSubject().equals("Jakub"));



    //asymmetric key algorithms
        System.out.println("ASYMETRIC KEY ALGORITHMS:");
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        var student = new Student("Jakub","Znameanacek");

        String jwsAsymetric = Jwts.builder()
                .setSubject("Jakub")
                .claim("student",student)
                .setIssuedAt(Date.valueOf(LocalDate.now().minusDays(2)))
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(privateKey)
                .compact();

        System.out.println(jwsAsymetric);
        //Decode
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwsAsymetric).getBody();
//        HashMap<String, Long> hashMap = (HashMap<String, Long>)body.get("student");
//        System.out.println(hashMap.get("birthday"));
        Student decodedStudent = body.get("student",Student.class); //Doesn't work
        System.out.println(decodedStudent);





    }
}
