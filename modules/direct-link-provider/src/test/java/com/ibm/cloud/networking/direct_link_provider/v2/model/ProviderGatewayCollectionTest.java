/*
 * (C) Copyright IBM Corp. 2020.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.ibm.cloud.networking.direct_link_provider.v2.model;

import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGateway;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayChangeRequestProviderGatewayCreate;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayCollection;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayCollectionFirst;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayCollectionNext;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayPortReference;
import com.ibm.cloud.networking.direct_link_provider.v2.utils.TestUtilities;

import com.ibm.cloud.sdk.core.service.model.FileWithMetadata;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Unit test class for the ProviderGatewayCollection model.
 */
public class ProviderGatewayCollectionTest {
  final HashMap<String, InputStream> mockStreamMap = TestUtilities.createMockStreamMap();
  final List<FileWithMetadata> mockListFileWithMetadata = TestUtilities.creatMockListFileWithMetadata();

  @Test
  public void testProviderGatewayCollection() throws Throwable {
    ProviderGatewayCollection providerGatewayCollectionModel = new ProviderGatewayCollection();
    assertNull(providerGatewayCollectionModel.getFirst());
    assertNull(providerGatewayCollectionModel.getLimit());
    assertNull(providerGatewayCollectionModel.getNext());
    assertNull(providerGatewayCollectionModel.getTotalCount());
    assertNull(providerGatewayCollectionModel.getGateways());
  }
}