/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.example.demo.service;

import com.example.demo.model.SinglePage;
import com.example.demo.util.URLUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Scanner service providing the logic to scan and crawl through the given site.
 */
@Service
public class ScannerService {

  private static final String LINK_TAG = "a[href]";
  private static final String LINK_ATTR = "abs:href";
  private static final String ASSET_TAG = "img";
  private static final String ASSET_ATTR = "src";

  private SortedSet<SinglePage> scannedPages = new TreeSet<>();
  private String rootURL;

  /**
   * Scans the given URL and its sub-sites.
   *
   * @param url URL which should be scanned
   * @return Collection of scanned scannedPages or error message if something went wrong
   */
  public Object scan(String url) {
    String formattedUrl = URLUtil.formatUrl(url);
    if (URLUtil.validateURL(formattedUrl)) {
      return startCrawling(formattedUrl);
    } else {
      return "Not valid URL: " + formattedUrl;
    }
  }

  /**
   * Starts crawling the site.
   * <p>
   * First of all it sets the root page and then moves deeper into the site.
   *
   * @param url formatted URL
   * @return Collection of scanned scannedPages or error message if something went wrong
   */
  private Object startCrawling(String url) {
    //Clear scannedPages from previous crawl
    scannedPages.clear();
    setRootURL(url);
    crawl(rootURL);
    return scannedPages;
  }

  /**
   * Crawles through the given URL
   *
   * @param url URL to be crawled through
   */
  private void crawl(String url) {
    try {
      SinglePage page = breakdownSite(url);
      for (String nextLink : page.getInternalLinks()) {
        if (isUdiscoveredLink(nextLink)) crawl(nextLink);
      }
    } catch (IOException e) {
      System.out.println("Something wrong happened during the site breakdown: " + e.getMessage());
    }
  }

  /**
   * Checks if the given link was crawled.
   *
   * @param nextLink URL with the next URL to crawl through
   * @return true if the link wasn't crawled through
   */
  private boolean isUdiscoveredLink(String nextLink) {
    return scannedPages.stream()
      .filter(
        p -> URLUtil.compareURLs(p.getUrl().baseUri(), nextLink))
      .findFirst()
      .orElse(null) == null;
  }

  /**
   * Connects to the given URL and breaks it down to:
   * <p>
   * 1. Root URL
   * 2. Internal links (within {@link #rootURL})
   * 3. External links
   * 4. Assets - links to the assets marked with src tag
   *
   * @param url URL to breakdown
   * @return {@link SinglePage} object with all the info from the given URL
   * @throws IOException if the site couldn't be read
   */
  private SinglePage breakdownSite(String url) throws IOException {
    Document root = Jsoup.connect(url).followRedirects(false).get();
    SinglePage singlePage = SinglePage.builder().url(root).externalLinks(new HashSet<>()).internalLinks(new HashSet<>()).assets(new HashSet<>()).build();
    getLinksForPage(singlePage);
    getAssets(singlePage);
    scannedPages.add(singlePage);
    return singlePage;
  }

  /**
   * Gathers the assets from the {@link SinglePage#url}
   *
   * @param singlePage {@link SinglePage}
   */
  private void getAssets(SinglePage singlePage) {
    for (Element source : singlePage.getUrl().select(ASSET_TAG)) {
      singlePage.getAssets().add(source.attr(ASSET_ATTR));
    }
  }

  /**
   * Gathers the internal and external links from the {@link SinglePage#url}
   *
   * @param singlePage {@link SinglePage}
   */
  private void getLinksForPage(SinglePage singlePage) throws IOException {
    for (Element page : singlePage.getUrl().select(LINK_TAG)) {
      String link = page.attr(LINK_ATTR);
      if (link.startsWith(rootURL)) {
        singlePage.getInternalLinks().add(link);
      } else {
        singlePage.getExternalLinks().add(link);
      }
    }
  }

  /**
   * Sets the main URL - Connects to it and formats the main site prefix
   *
   * @param givenUrl formatted url given from the user
   */
  private void setRootURL(String givenUrl) {
    try {
      rootURL = Jsoup.connect(givenUrl).followRedirects(true).execute().url().toExternalForm().replaceAll("/$", "");
      ;
    } catch (IOException e) {
      System.out.println("Something went wrong during the connection to " + givenUrl + "\nReason: " + e.getMessage());
    }
  }

}
