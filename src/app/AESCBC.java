/*
 * Copyright (C) Global Accounting
 * http://www.marstech.co.id
 * Email. info@marstech.co.id
 * Telp. 0811-3636-09
 * Office        : Jl. Bulu Mas II No. 1 Kanigoro Kartoharjo Kota Madiun - Jawa Timur
 * Branch Office : Perum Griya Gadang Sejahtera Kav. 14 Gadang - Sukun - Kota Malang - Jawa Timur
 * 
 * Global Accounting
 * Adalah merek dagang dari PT. Marstech Global
 * 
 * License Agreement
 * Software komputer atau perangkat lunak komputer ini telah diakui sebagai salah satu aset perusahaan yang bernilai.
 * Di Indonesia secara khusus,
 * software telah dianggap seperti benda-benda berwujud lainnya yang memiliki kekuatan hukum.
 * Oleh karena itu pemilik software berhak untuk memberi ijin atau tidak memberi ijin orang lain untuk menggunakan softwarenya.
 * Dalam hal ini ada aturan hukum yang berlaku di Indonesia yang secara khusus melindungi para programmer dari pembajakan software yang mereka buat,
 * yaitu diatur dalam hukum hak kekayaan intelektual (HAKI).
 * 
 * ********************************************************************************************************
 * Pasal 72 ayat 3 UU Hak Cipta berbunyi,
 * " Barangsiapa dengan sengaja dan tanpa hak memperbanyak penggunaan untuk kepentingan komersial "
 * " suatu program komputer dipidana dengan pidana penjara paling lama 5 (lima) tahun dan/atau "
 * " denda paling banyak Rp. 500.000.000,00 (lima ratus juta rupiah) "
 * ********************************************************************************************************
 * 
 * Proprietary Software
 * Adalah software berpemilik, sehingga seseorang harus meminta izin serta dilarang untuk mengedarkan,
 * menggunakan atau memodifikasi software tersebut.
 * 
 * Commercial software
 * Adalah software yang dibuat dan dikembangkan oleh perusahaan dengan konsep bisnis,
 * dibutuhkan proses pembelian atau sewa untuk bisa menggunakan software tersebut.
 * Detail Licensi yang dianut di software https://en.wikipedia.org/wiki/Proprietary_software
 * EULA https://en.wikipedia.org/wiki/End-user_license_agreement
 * 
 * Lisensi Perangkat Lunak https://id.wikipedia.org/wiki/Lisensi_perangkat_lunak
 * EULA https://id.wikipedia.org/wiki/EULA
 */
package app;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author LILO | lilo.suryo@gmail.com Created : 20 October 2020 - 23:23:39
 * Version : 1.0
 *
 */
public class AESCBC {

    private String SECRET_KEY = "";
    private String SALT = "";
    private final byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final IvParameterSpec ivspec = new IvParameterSpec(iv);
    
    private String getHash(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            this.SECRET_KEY = secret;
            this.SALT = this.getHash(this.SECRET_KEY);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(this.SECRET_KEY.toCharArray(), this.SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, this.ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            this.SECRET_KEY = secret;
            this.SALT = this.getHash(this.SECRET_KEY);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(this.SECRET_KEY.toCharArray(), this.SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, this.ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
