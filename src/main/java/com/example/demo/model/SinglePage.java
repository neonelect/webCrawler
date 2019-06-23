/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.example.demo.model;

import com.example.demo.util.SinglePageSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;

import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = SinglePageSerializer.class)
public class SinglePage implements Comparable{

  private Document url;
  private Set<String> internalLinks;
  private Set<String> externalLinks;
  private Set<String> assets;

  @Override
  public int compareTo(Object o) {
    return url.baseUri().compareTo(((SinglePage) o).getUrl().baseUri());
  }
}
