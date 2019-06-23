/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.example.demo.util;

import org.apache.commons.validator.UrlValidator;

import java.net.URI;

public class URLUtil {

  private static final UrlValidator URL_VALIDATOR = new UrlValidator();

  /**
   * Checks if the given string is a valid URL.
   *
   * @param url string to be checked if it's a proper URL
   * @return true if the URL is in valid format
   */
  public static boolean validateURL(String url) {
    return URL_VALIDATOR.isValid(url);
  }

  /**
   * Adds if neeed the protocol prefix to the given URL
   *
   * @param url URL to be formatted
   * @return URL with protocol prefix
   */
  public static String formatUrl(String url) {
    return url.startsWith("http://") || url.startsWith("https://") ? url : "http://" + url;
  }

  /**
   * Checks if the given URLs are the same.
   *
   * @param url1 First URL
   * @param url2 Second URL
   * @return true if two URLs are the same
   */
  public static boolean compareURLs(String url1, String url2) {
    return URI.create(url1).equals(URI.create(url2));
  }
}
