package com.hoursmanager.HoursManager.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
* Hash and Compare Passwords with SHA-256
* */

public class PasswordHasher
{
    public static String getHashedPassword(String password)
    {
        return SHA256Hashing.generateSHA256Hash(password);
    }

    public static boolean isPasswordCorrect(String givenPassword, String dbHash)
    {
        return dbHash.equals(getHashedPassword(givenPassword));
    }
}

/*
* Class that defines how to produce SHA-256 passwords
* */

class SHA256Hashing
{
    public static String generateSHA256Hash(String str)
    {
        try
        {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash computation
            byte[] encodedHash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into hexadecimal string
            StringBuilder hexString = new StringBuilder();

            // Loop through encoded bytes
            for (byte b : encodedHash)
            {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1)
                {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        }

        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
