package iot.utility;

import java.util.Random;

/**
 * The Java util.
 */
public final class JavaUtil {

    /**
     * Gets salt string.
     *
     * @param len the len
     * @return the salt string
     */
    public static String getSaltString(int len) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
