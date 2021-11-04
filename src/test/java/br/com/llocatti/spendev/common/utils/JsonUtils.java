package br.com.llocatti.spendev.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String objectToJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }
}
