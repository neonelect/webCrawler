package com.example.demo.service;

import com.example.demo.util.URLUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@SpringBootTest
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({Jsoup.class, URLUtil.class})
public class ScannerServiceTest {

  private static final String TEST_URL = "http://www.test.com/";

  @Value("classpath:test_html.html")
  private Resource htmlFile;

  @Value("classpath:test_html.json")
  private Resource jsonOutput;

  @Autowired
  private ScannerService scannerService;

  @MockBean
  private Connection connection;

  @MockBean
  private Connection.Response response;

  @Before
  public void init() throws IOException {
    PowerMockito.mockStatic(Jsoup.class);
    PowerMockito.mockStatic(URLUtil.class);

    PowerMockito.when(Jsoup.connect(anyString())).
      thenReturn(connection);
    PowerMockito.when(Jsoup.parse(anyString())).thenCallRealMethod();
    PowerMockito.when(URLUtil.validateURL(anyString())).
      thenReturn(true);
    PowerMockito.when(URLUtil.formatUrl(anyString())).
      thenReturn(TEST_URL);


    Document testHtml = Jsoup.parse(new String(Files.readAllBytes(Paths.get(htmlFile.getURI()))));

    when(connection.get()).thenReturn(testHtml);
    when(connection.followRedirects(anyBoolean())).thenReturn(connection);
    when(connection.execute()).thenReturn(response);
    when(response.url()).thenReturn(new URL(TEST_URL));
  }

  @Test
  public void scan() throws Exception {
    String crawlOutput = new ObjectMapper().writeValueAsString(scannerService.scan(TEST_URL));
    assertEquals(new String(Files.readAllBytes(Paths.get(jsonOutput.getURI()))), crawlOutput);
  }

}