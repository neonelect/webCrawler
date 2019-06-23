package com.example.demo.controller;

import com.example.demo.service.ScannerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(URLController.class)
public class URLControllerTest {

  private static final String ENDPOINT = "/scanURL";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ScannerService scannerService;

  @Before
  public void init(){
    Mockito.when(scannerService.scan(any(String.class))).thenReturn("Test");
  }

  @Test
  public void scanURL() throws Exception {
    //Test availability of the endpoint and the response
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT)
      .param("url", "test")
    )
      .andExpect(status().isOk());

    assertEquals("\"Test\"", result.andReturn().getResponse().getContentAsString());

    //Test CORS
    mockMvc.perform(
      get(ENDPOINT)
        .header("Access-Control-Request-Method", "GET")
        .header("Origin", "https://evil.com")
    ).andExpect(status().isForbidden());

  }

}