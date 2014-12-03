/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.json;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.json.MetricsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.record.FieldDeserializer;

import java.util.concurrent.TimeUnit;

public class ObjectMapperFactory {

  private static final ObjectMapper OBJECT_MAPPER = create();

  private static ObjectMapper create() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new MetricsModule(TimeUnit.SECONDS, TimeUnit.SECONDS, false, MetricFilter.ALL));
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    return objectMapper;
  }

  public static ObjectMapper get() {
    return OBJECT_MAPPER;
  }

}
