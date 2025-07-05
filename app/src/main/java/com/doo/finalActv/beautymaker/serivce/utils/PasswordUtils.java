package com.doo.finalActv.beautymaker.serivce.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 256;

  public static String generateSalt() {
    byte[] salt = new byte[16];
    new SecureRandom().nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  public static String hashPassword(char[] password, String salt) {
    try {
      PBEKeySpec spec = new PBEKeySpec(
              password,
              Base64.getDecoder().decode(salt),
              ITERATIONS,
              KEY_LENGTH
      );
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      byte[] hash = skf.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(hash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Error while hashing a password", e);
    }
  }

  public static boolean verifyPassword(char[] password, String salt, String expectedHash) {
    String computedHash = hashPassword(password, salt);
    return computedHash.equals(expectedHash);
  }

  public static boolean isValidPassword(String password) {
    // Check if the password is at least 8 characters long
    if (password.length() < 8) {
      return false;
    }
    return true;
  }
}
