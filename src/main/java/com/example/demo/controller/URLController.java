/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.example.demo.controller;

import com.example.demo.service.ScannerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller providing an endpoint to give the URL by the user.
 */
@RestController
@CrossOrigin("http://localhost")
public class URLController {

  @Autowired
  private ScannerService scannerService;

  /**
   * GET endpoint to run the the crawling algorithm.
   *
   * @param url URL that the user wants to crawl through
   * @return formatted JSON with the crawling result or error message
   */
  @GetMapping(path = "/scanURL")
  public String scanURL(@RequestParam("url") String url) {
    try {
      return new ObjectMapper().writeValueAsString(scannerService.scan(url));
    } catch (JsonProcessingException e) {
      return "Something went wrong with the answer processing.";
    }
  }
}
