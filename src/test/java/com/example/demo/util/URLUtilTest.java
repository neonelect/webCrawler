package com.example.demo.util;

import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class URLUtilTest {

  private static final String[] VALID_URLS = {
    "http://google.com",
    "http://mystite.sth.org",
    "https://www.domain.com",
    "http://www.domain.gov.com"
  };

  private static final String[] INVALID_URLS = {
    "google.com",
    "http://mystite",
    "htt://www.domain.com",
    "www.domain.gov.com"
  };

  private static final String[] URLS_FOR_FORMAT = {
    "www.google.com",
    "http://somedomain.com/",
    "https://somewhere.gov.com"
  };

  private static final List<Pair<String, String>> DIFFERENT_URL_PAIRS = new ArrayList<Pair<String,String>>(){{
    add(Pair.with("http://google.com","http://www.google.com"));
    add(Pair.with("https://www.google.com","http://www.google.com"));
    add(Pair.with("www.google.com","http://www.google.com"));
  }};

  @Test
  public void validateURL() throws Exception {
    validateURLs(true);
    validateURLs(false);
  }

  private void validateURLs(boolean validURLs){
    for(String url : validURLs ? VALID_URLS : INVALID_URLS){
      if(validURLs) assertTrue(URLUtil.validateURL(url));
      else assertFalse(URLUtil.validateURL(url));
    }
  }

  @Test
  public void formatUrl() throws Exception {
    for(String url : URLS_FOR_FORMAT){
      String formattedURL = URLUtil.formatUrl(url);
      assertTrue(formattedURL.startsWith("http://") || formattedURL.startsWith("https://"));
    }
  }

  @Test
  public void compareURLs() throws Exception {
    for(Pair<String,String> pairOfURLs : DIFFERENT_URL_PAIRS){
      assertFalse(URLUtil.compareURLs(pairOfURLs.getValue0(), pairOfURLs.getValue1()));
    }
  }

}