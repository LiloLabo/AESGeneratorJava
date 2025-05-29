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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author LILO | lilo.suryo@gmail.com Created : 20 October 2020 - 23:23:39
 * Version : 1.0
 *
 */
public class AES {

    private SecretKeySpec secretKey;
    private byte[] key;

    public void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "";
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return "";
    }
}
