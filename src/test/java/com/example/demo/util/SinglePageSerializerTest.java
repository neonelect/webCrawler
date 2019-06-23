package com.example.demo.util;

import com.example.demo.model.SinglePage;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class SinglePageSerializerTest {

  @Value("classpath:crawl_result_test.json")
  private Resource resourceFile;

  private static final SinglePageSerializer test = new SinglePageSerializer();

  @Test
  public void testSerialization() throws IOException {
    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    jsonGenerator.setCodec(new ObjectMapper());
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    test.serialize(prepareTestObject(), jsonGenerator, serializerProvider);
    jsonGenerator.flush();

    assertEquals(jsonWriter.toString(), new String(Files.readAllBytes(Paths.get(resourceFile.getURI()))));
  }

  private SinglePage prepareTestObject(){
    return SinglePage.builder()
      .url(new Document("TEST"))
      .internalLinks(new HashSet<>(Arrays.asList("internal1","internal2")))
      .externalLinks(new HashSet<>(Arrays.asList("external1","external2")))
      .assets(new HashSet<>(Arrays.asList("asset1","asset2")))
      .build();
  }
}