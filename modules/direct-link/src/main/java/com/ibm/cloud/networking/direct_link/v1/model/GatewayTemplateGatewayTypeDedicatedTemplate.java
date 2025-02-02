/*
 * (C) Copyright IBM Corp. 2022.
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

/**
 * Gateway fields specific to type=dedicated gateway create.
 */
public class GatewayTemplateGatewayTypeDedicatedTemplate extends GatewayTemplate {

  /**
   * Type of services this Gateway is attached to. Mode transit means this Gateway will be attached to Transit Gateway
   * Service and direct means this Gateway will be attached to vpc or classic connection. The list of enumerated values
   * for this property may expand in the future. Code and processes using this field  must tolerate unexpected values.
   */
  public interface ConnectionMode {
    /** direct. */
    String DIRECT = "direct";
    /** transit. */
    String TRANSIT = "transit";
  }

  /**
   * Offering type.
   */
  public interface Type {
    /** connect. */
    String CONNECT = "connect";
    /** dedicated. */
    String DEDICATED = "dedicated";
  }


  /**
   * Builder.
   */
  public static class Builder {
    private GatewayTemplateAuthenticationKey authenticationKey;
    private GatewayBfdConfigTemplate bfdConfig;
    private Long bgpAsn;
    private String bgpBaseCidr;
    private String bgpCerCidr;
    private String bgpIbmCidr;
    private String connectionMode;
    private Boolean global;
    private Boolean metered;
    private String name;
    private String patchPanelCompletionNotice;
    private ResourceGroupIdentity resourceGroup;
    private Long speedMbps;
    private String type;
    private String carrierName;
    private String crossConnectRouter;
    private String customerName;
    private String locationName;
    private GatewayMacsecConfigTemplate macsecConfig;

    public Builder(GatewayTemplate gatewayTemplateGatewayTypeDedicatedTemplate) {
      this.authenticationKey = gatewayTemplateGatewayTypeDedicatedTemplate.authenticationKey;
      this.bfdConfig = gatewayTemplateGatewayTypeDedicatedTemplate.bfdConfig;
      this.bgpAsn = gatewayTemplateGatewayTypeDedicatedTemplate.bgpAsn;
      this.bgpBaseCidr = gatewayTemplateGatewayTypeDedicatedTemplate.bgpBaseCidr;
      this.bgpCerCidr = gatewayTemplateGatewayTypeDedicatedTemplate.bgpCerCidr;
      this.bgpIbmCidr = gatewayTemplateGatewayTypeDedicatedTemplate.bgpIbmCidr;
      this.connectionMode = gatewayTemplateGatewayTypeDedicatedTemplate.connectionMode;
      this.global = gatewayTemplateGatewayTypeDedicatedTemplate.global;
      this.metered = gatewayTemplateGatewayTypeDedicatedTemplate.metered;
      this.name = gatewayTemplateGatewayTypeDedicatedTemplate.name;
      this.patchPanelCompletionNotice = gatewayTemplateGatewayTypeDedicatedTemplate.patchPanelCompletionNotice;
      this.resourceGroup = gatewayTemplateGatewayTypeDedicatedTemplate.resourceGroup;
      this.speedMbps = gatewayTemplateGatewayTypeDedicatedTemplate.speedMbps;
      this.type = gatewayTemplateGatewayTypeDedicatedTemplate.type;
      this.carrierName = gatewayTemplateGatewayTypeDedicatedTemplate.carrierName;
      this.crossConnectRouter = gatewayTemplateGatewayTypeDedicatedTemplate.crossConnectRouter;
      this.customerName = gatewayTemplateGatewayTypeDedicatedTemplate.customerName;
      this.locationName = gatewayTemplateGatewayTypeDedicatedTemplate.locationName;
      this.macsecConfig = gatewayTemplateGatewayTypeDedicatedTemplate.macsecConfig;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Instantiates a new builder with required properties.
     *
     * @param bgpAsn the bgpAsn
     * @param global the global
     * @param metered the metered
     * @param name the name
     * @param speedMbps the speedMbps
     * @param type the type
     * @param carrierName the carrierName
     * @param crossConnectRouter the crossConnectRouter
     * @param customerName the customerName
     * @param locationName the locationName
     */
    public Builder(Long bgpAsn, Boolean global, Boolean metered, String name, Long speedMbps, String type, String carrierName, String crossConnectRouter, String customerName, String locationName) {
      this.bgpAsn = bgpAsn;
      this.global = global;
      this.metered = metered;
      this.name = name;
      this.speedMbps = speedMbps;
      this.type = type;
      this.carrierName = carrierName;
      this.crossConnectRouter = crossConnectRouter;
      this.customerName = customerName;
      this.locationName = locationName;
    }

    /**
     * Builds a GatewayTemplateGatewayTypeDedicatedTemplate.
     *
     * @return the new GatewayTemplateGatewayTypeDedicatedTemplate instance
     */
    public GatewayTemplateGatewayTypeDedicatedTemplate build() {
      return new GatewayTemplateGatewayTypeDedicatedTemplate(this);
    }

    /**
     * Set the authenticationKey.
     *
     * @param authenticationKey the authenticationKey
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder authenticationKey(GatewayTemplateAuthenticationKey authenticationKey) {
      this.authenticationKey = authenticationKey;
      return this;
    }

    /**
     * Set the bfdConfig.
     *
     * @param bfdConfig the bfdConfig
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder bfdConfig(GatewayBfdConfigTemplate bfdConfig) {
      this.bfdConfig = bfdConfig;
      return this;
    }

    /**
     * Set the bgpAsn.
     *
     * @param bgpAsn the bgpAsn
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder bgpAsn(long bgpAsn) {
      this.bgpAsn = bgpAsn;
      return this;
    }

    /**
     * Set the bgpBaseCidr.
     *
     * @param bgpBaseCidr the bgpBaseCidr
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder bgpBaseCidr(String bgpBaseCidr) {
      this.bgpBaseCidr = bgpBaseCidr;
      return this;
    }

    /**
     * Set the bgpCerCidr.
     *
     * @param bgpCerCidr the bgpCerCidr
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder bgpCerCidr(String bgpCerCidr) {
      this.bgpCerCidr = bgpCerCidr;
      return this;
    }

    /**
     * Set the bgpIbmCidr.
     *
     * @param bgpIbmCidr the bgpIbmCidr
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder bgpIbmCidr(String bgpIbmCidr) {
      this.bgpIbmCidr = bgpIbmCidr;
      return this;
    }

    /**
     * Set the connectionMode.
     *
     * @param connectionMode the connectionMode
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder connectionMode(String connectionMode) {
      this.connectionMode = connectionMode;
      return this;
    }

    /**
     * Set the global.
     *
     * @param global the global
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder global(Boolean global) {
      this.global = global;
      return this;
    }

    /**
     * Set the metered.
     *
     * @param metered the metered
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder metered(Boolean metered) {
      this.metered = metered;
      return this;
    }

    /**
     * Set the name.
     *
     * @param name the name
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Set the patchPanelCompletionNotice.
     *
     * @param patchPanelCompletionNotice the patchPanelCompletionNotice
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder patchPanelCompletionNotice(String patchPanelCompletionNotice) {
      this.patchPanelCompletionNotice = patchPanelCompletionNotice;
      return this;
    }

    /**
     * Set the resourceGroup.
     *
     * @param resourceGroup the resourceGroup
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder resourceGroup(ResourceGroupIdentity resourceGroup) {
      this.resourceGroup = resourceGroup;
      return this;
    }

    /**
     * Set the speedMbps.
     *
     * @param speedMbps the speedMbps
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder speedMbps(long speedMbps) {
      this.speedMbps = speedMbps;
      return this;
    }

    /**
     * Set the type.
     *
     * @param type the type
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Set the carrierName.
     *
     * @param carrierName the carrierName
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder carrierName(String carrierName) {
      this.carrierName = carrierName;
      return this;
    }

    /**
     * Set the crossConnectRouter.
     *
     * @param crossConnectRouter the crossConnectRouter
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder crossConnectRouter(String crossConnectRouter) {
      this.crossConnectRouter = crossConnectRouter;
      return this;
    }

    /**
     * Set the customerName.
     *
     * @param customerName the customerName
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder customerName(String customerName) {
      this.customerName = customerName;
      return this;
    }

    /**
     * Set the locationName.
     *
     * @param locationName the locationName
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder locationName(String locationName) {
      this.locationName = locationName;
      return this;
    }

    /**
     * Set the macsecConfig.
     *
     * @param macsecConfig the macsecConfig
     * @return the GatewayTemplateGatewayTypeDedicatedTemplate builder
     */
    public Builder macsecConfig(GatewayMacsecConfigTemplate macsecConfig) {
      this.macsecConfig = macsecConfig;
      return this;
    }
  }

  protected GatewayTemplateGatewayTypeDedicatedTemplate(Builder builder) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.bgpAsn,
      "bgpAsn cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.global,
      "global cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.metered,
      "metered cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.name,
      "name cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.speedMbps,
      "speedMbps cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.type,
      "type cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.carrierName,
      "carrierName cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.crossConnectRouter,
      "crossConnectRouter cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.customerName,
      "customerName cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.notNull(builder.locationName,
      "locationName cannot be null");
    authenticationKey = builder.authenticationKey;
    bfdConfig = builder.bfdConfig;
    bgpAsn = builder.bgpAsn;
    bgpBaseCidr = builder.bgpBaseCidr;
    bgpCerCidr = builder.bgpCerCidr;
    bgpIbmCidr = builder.bgpIbmCidr;
    connectionMode = builder.connectionMode;
    global = builder.global;
    metered = builder.metered;
    name = builder.name;
    patchPanelCompletionNotice = builder.patchPanelCompletionNotice;
    resourceGroup = builder.resourceGroup;
    speedMbps = builder.speedMbps;
    type = builder.type;
    carrierName = builder.carrierName;
    crossConnectRouter = builder.crossConnectRouter;
    customerName = builder.customerName;
    locationName = builder.locationName;
    macsecConfig = builder.macsecConfig;
  }

  /**
   * New builder.
   *
   * @return a GatewayTemplateGatewayTypeDedicatedTemplate builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }
}

