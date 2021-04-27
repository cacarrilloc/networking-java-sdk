/*
 * (C) Copyright IBM Corp. 2021.
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
package com.ibm.cloud.networking.direct_link.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * Fallback connectivity association key.
 *
 * The `fallback_cak` crn cannot match the `primary_cak` crn. MACsec keys must be type=standard with key name lengths
 * between 2 to 64 inclusive and contain only characters [a-fA-F0-9]. The key material must be exactly 64 characters in
 * length and contain only [a-fA-F0-9].
 */
public class GatewayMacsecConfigTemplateFallbackCak extends GenericModel {

  protected String crn;

  /**
   * Builder.
   */
  public static class Builder {
    private String crn;

    private Builder(GatewayMacsecConfigTemplateFallbackCak gatewayMacsecConfigTemplateFallbackCak) {
      this.crn = gatewayMacsecConfigTemplateFallbackCak.crn;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Instantiates a new builder with required properties.
     *
     * @param crn the crn
     */
    public Builder(String crn) {
      this.crn = crn;
    }

    /**
     * Builds a GatewayMacsecConfigTemplateFallbackCak.
     *
     * @return the new GatewayMacsecConfigTemplateFallbackCak instance
     */
    public GatewayMacsecConfigTemplateFallbackCak build() {
      return new GatewayMacsecConfigTemplateFallbackCak(this);
    }

    /**
     * Set the crn.
     *
     * @param crn the crn
     * @return the GatewayMacsecConfigTemplateFallbackCak builder
     */
    public Builder crn(String crn) {
      this.crn = crn;
      return this;
    }
  }

  protected GatewayMacsecConfigTemplateFallbackCak(Builder builder) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.crn,
      "crn cannot be null");
    crn = builder.crn;
  }

  /**
   * New builder.
   *
   * @return a GatewayMacsecConfigTemplateFallbackCak builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the crn.
   *
   * connectivity association key crn.
   *
   * @return the crn
   */
  public String crn() {
    return crn;
  }
}

