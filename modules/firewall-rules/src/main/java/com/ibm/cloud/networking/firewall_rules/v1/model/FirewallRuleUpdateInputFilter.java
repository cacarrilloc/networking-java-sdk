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
package com.ibm.cloud.networking.firewall_rules.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * An existing filter.
 */
public class FirewallRuleUpdateInputFilter extends GenericModel {

  protected String id;

  /**
   * Builder.
   */
  public static class Builder {
    private String id;

    private Builder(FirewallRuleUpdateInputFilter firewallRuleUpdateInputFilter) {
      this.id = firewallRuleUpdateInputFilter.id;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Instantiates a new builder with required properties.
     *
     * @param id the id
     */
    public Builder(String id) {
      this.id = id;
    }

    /**
     * Builds a FirewallRuleUpdateInputFilter.
     *
     * @return the new FirewallRuleUpdateInputFilter instance
     */
    public FirewallRuleUpdateInputFilter build() {
      return new FirewallRuleUpdateInputFilter(this);
    }

    /**
     * Set the id.
     *
     * @param id the id
     * @return the FirewallRuleUpdateInputFilter builder
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }
  }

  protected FirewallRuleUpdateInputFilter(Builder builder) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.id,
      "id cannot be null");
    id = builder.id;
  }

  /**
   * New builder.
   *
   * @return a FirewallRuleUpdateInputFilter builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the id.
   *
   * Identifier of the filter.
   *
   * @return the id
   */
  public String id() {
    return id;
  }
}

