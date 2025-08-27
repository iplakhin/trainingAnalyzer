package com.iplakhin.flow.models;

import jakarta.persistence.*;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Entity
@Table(name = "polar_user")
public class PolarUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public PolarUser() {};

    public PolarUser(String username, String password) {
        this.username = username;
        this.password = encryptPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return decryptPassword(this.password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    private static String decryptPassword(String encryptedPassword) {
        try {
            SecretKey secretKey = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptedPassword;
        }
    }

    private static String encryptPassword(String password) {
        try {
            SecretKey secretKey = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
    
    
