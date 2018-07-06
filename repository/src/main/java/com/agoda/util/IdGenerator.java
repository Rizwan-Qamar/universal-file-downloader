package com.agoda.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerator {
  private static SecureRandom random = new SecureRandom();

  public static String createId() {
    return new BigInteger(32, random).toString();
  }

  public static String createUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
