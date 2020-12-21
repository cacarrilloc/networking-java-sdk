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
package com.ibm.cloud.networking.dns_svcs.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * The getPool options.
 */
public class GetPoolOptions extends GenericModel {

  protected String instanceId;
  protected String poolId;
  protected String xCorrelationId;

  /**
   * Builder.
   */
  public static class Builder {
    private String instanceId;
    private String poolId;
    private String xCorrelationId;

    private Builder(GetPoolOptions getPoolOptions) {
      this.instanceId = getPoolOptions.instanceId;
      this.poolId = getPoolOptions.poolId;
      this.xCorrelationId = getPoolOptions.xCorrelationId;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Instantiates a new builder with required properties.
     *
     * @param instanceId the instanceId
     * @param poolId the poolId
     */
    public Builder(String instanceId, String poolId) {
      this.instanceId = instanceId;
      this.poolId = poolId;
    }

    /**
     * Builds a GetPoolOptions.
     *
     * @return the new GetPoolOptions instance
     */
    public GetPoolOptions build() {
      return new GetPoolOptions(this);
    }

    /**
     * Set the instanceId.
     *
     * @param instanceId the instanceId
     * @return the GetPoolOptions builder
     */
    public Builder instanceId(String instanceId) {
      this.instanceId = instanceId;
      return this;
    }

    /**
     * Set the poolId.
     *
     * @param poolId the poolId
     * @return the GetPoolOptions builder
     */
    public Builder poolId(String poolId) {
      this.poolId = poolId;
      return this;
    }

    /**
     * Set the xCorrelationId.
     *
     * @param xCorrelationId the xCorrelationId
     * @return the GetPoolOptions builder
     */
    public Builder xCorrelationId(String xCorrelationId) {
      this.xCorrelationId = xCorrelationId;
      return this;
    }
  }

  protected GetPoolOptions(Builder builder) {
    com.ibm.cloud.sdk.core.util.Validator.notEmpty(builder.instanceId,
      "instanceId cannot be empty");
    com.ibm.cloud.sdk.core.util.Validator.notEmpty(builder.poolId,
      "poolId cannot be empty");
    instanceId = builder.instanceId;
    poolId = builder.poolId;
    xCorrelationId = builder.xCorrelationId;
  }

  /**
   * New builder.
   *
   * @return a GetPoolOptions builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the instanceId.
   *
   * The unique identifier of a service instance.
   *
   * @return the instanceId
   */
  public String instanceId() {
    return instanceId;
  }

  /**
   * Gets the poolId.
   *
   * The unique identifier of a load balancer pool.
   *
   * @return the poolId
   */
  public String poolId() {
    return poolId;
  }

  /**
   * Gets the xCorrelationId.
   *
   * Uniquely identifying a request.
   *
   * @return the xCorrelationId
   */
  public String xCorrelationId() {
    return xCorrelationId;
  }
}

