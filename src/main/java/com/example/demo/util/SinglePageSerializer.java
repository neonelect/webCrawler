/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.example.demo.util;

import com.example.demo.model.SinglePage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Serializer class for formatting {@link SinglePage} to JSON.
 */
public class SinglePageSerializer extends StdSerializer<SinglePage> {

  public SinglePageSerializer() {
    super(SinglePage.class);
  }

  @Override
  public void serialize(SinglePage singlePage, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("root_url", singlePage.getUrl().baseUri());
    jsonGenerator.writeObjectField("internal_links", singlePage.getInternalLinks());
    jsonGenerator.writeObjectField("external_links", singlePage.getExternalLinks());
    jsonGenerator.writeObjectField("assets", singlePage.getAssets());
    jsonGenerator.writeEndObject();
  }

}
