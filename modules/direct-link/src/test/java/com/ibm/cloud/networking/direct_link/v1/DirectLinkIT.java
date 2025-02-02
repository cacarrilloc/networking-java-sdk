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
package com.ibm.cloud.networking.direct_link.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import com.ibm.cloud.networking.direct_link.v1.model.CreateGatewayCompletionNoticeOptions;
import com.ibm.cloud.networking.direct_link.v1.model.CreateGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.CreateGatewayVirtualConnectionOptions;
import com.ibm.cloud.networking.direct_link.v1.model.DeleteGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.DeleteGatewayVirtualConnectionOptions;
import com.ibm.cloud.networking.direct_link.v1.model.Gateway;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayCollection;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayTemplateGatewayTypeDedicatedTemplate;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayMacsecConfigTemplate;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayMacsecConfigTemplatePrimaryCak;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayMacsecConfigPatchTemplate;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayMacsecConfigPatchTemplateFallbackCak;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayTemplateGatewayTypeConnectTemplate;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayVirtualConnection;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayVirtualConnectionCollection;
import com.ibm.cloud.networking.direct_link.v1.model.GetGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GetGatewayVirtualConnectionOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GetPortOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListGatewayCompletionNoticeOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListGatewayLetterOfAuthorizationOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListGatewayVirtualConnectionsOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListGatewaysOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListOfferingTypeLocationCrossConnectRoutersOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListOfferingTypeLocationsOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListOfferingTypeSpeedsOptions;
import com.ibm.cloud.networking.direct_link.v1.model.ListPortsOptions;
import com.ibm.cloud.networking.direct_link.v1.model.LocationCollection;
import com.ibm.cloud.networking.direct_link.v1.model.LocationCrossConnectRouterCollection;
import com.ibm.cloud.networking.direct_link.v1.model.OfferingSpeedCollection;
import com.ibm.cloud.networking.direct_link.v1.model.OfferingSpeed;
import com.ibm.cloud.networking.direct_link.v1.model.Port;
import com.ibm.cloud.networking.direct_link.v1.model.PortCollection;
import com.ibm.cloud.networking.direct_link.v1.model.UpdateGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.UpdateGatewayVirtualConnectionOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayBfdConfigTemplate;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayBfdPatchTemplate;
import com.ibm.cloud.networking.direct_link.v1.model.GetGatewayStatusOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayStatusCollection;
import com.ibm.cloud.networking.test.SdkIntegrationTestBase;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.util.CredentialUtils;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayPortIdentity;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayTemplateAuthenticationKey;
// import com.ibm.cloud.networking.direct_link.v1.model.GatewayPatchTemplateAuthenticationKey;

/**
 * Integration test class for the DirectLink service.
 * 
 * How to run the tests:
 *    mvn -Dtest=DirectLinkIT -DfailIfNoTests=false test
 */
public class DirectLinkIT extends SdkIntegrationTestBase {
	// Directlink service v1 integration
	public DirectLink testService = null;
	String gatewayId = null;
	String firstPortId = null;
	String classicVcId = null;
	String vpcVcId = null;

	Map<String, String> config = null;

	/**
	 * This method provides our config filename to the base class.
	 */
	public String getConfigFilename() {
		return "../../directlink.env";
	}

	/**
	 * This method is invoked before any of the @Test-annotated methods, and is
	 * responsible for creating the instance of the service that will be used by the
	 * rest of the test methods, as well as any other required initialization.
	 */
	@BeforeClass
	public void constructService() {
		// Ask super if we should skip the tests.
		if (skipTests()) {
			return;
		}

		/**
		 * Construct our service client instance via external config (see the
		 * example-service.env file for details). The newInstance() method will load up
		 * our config file and look for properties that start with "EXAMPLE_SERVICE_"
		 * (the default service name associated with the Example Service, as specified
		 * by the ExampleService.DEFAULT_SERVICE_NAME constant). Specifically,
		 * newInstance() will construct an authenticator based on the value of the
		 * EXAMPLE_SERVICE_AUTH_TYPE property. After constructing the appropriate
		 * authenticator, it will construct an instance of the service and then set the
		 * URL to the value specified by the EXAMPLE_SERVICE_URL property.
		 */

		final String serviceName = "network_service";

		// Load up the config properties for this service.
		config = CredentialUtils.getServiceProperties(serviceName);

		String version;
		// Date date = new Date();  
    	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			version = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			testService = DirectLink.newInstance(version, serviceName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public void cleanup() {
		deleteGateways();
	}
	
	private void deleteGateways() {
		// ********** List all gateways ************* 
		ListGatewaysOptions listGatewaysOptionsModel = new ListGatewaysOptions();

		// Invoke operation with valid options model (positive test)
		Response<GatewayCollection> lisResp = testService.listGateways(listGatewaysOptionsModel).execute();
		assertNotNull(lisResp); assertEquals(200, lisResp.getStatusCode());
		
		GatewayCollection lisresponseObj = lisResp.getResult();
		assertNotNull(lisresponseObj); 
		assertNotEquals(0,lisresponseObj.getGateways().size());

		List<Gateway> gateways = lisresponseObj.getGateways();
		for(Gateway gateway: gateways){
			boolean isBreak = false;
			boolean isProviderApiManaged = false;
			if(null != gateway.isProviderApiManaged()){
				isProviderApiManaged=gateway.isProviderApiManaged();
			}
			if(!gateway.getOperationalStatus().equalsIgnoreCase("delete_pending") && gateway.getName().toLowerCase().contains("java-int-sdk") && !isProviderApiManaged)
			{
				// Check if it has any VC connected
				ListGatewayVirtualConnectionsOptions listGatewayVirtualConnectionsOptionsModel = new ListGatewayVirtualConnectionsOptions.Builder()
				.gatewayId(gateway.getId()).build();

				// Invoke operation with valid options model (positive test)
				Response<GatewayVirtualConnectionCollection> resp = testService
						.listGatewayVirtualConnections(listGatewayVirtualConnectionsOptionsModel).execute();
				assertNotNull(resp);
				assertEquals(200, resp.getStatusCode());
				GatewayVirtualConnectionCollection respObj = resp.getResult();
				
				if (respObj.getVirtualConnections().size() > 0) {
					List<GatewayVirtualConnection> vcs = respObj.getVirtualConnections();

					for(GatewayVirtualConnection vc: vcs) {
						if (vc.getType().toLowerCase().equals("spoke")){
							isBreak = true;
							break;
						}
						deleteVirtualConnection(gateway.getId(), vc.getId());
					} 
				}

				if (isBreak) {
					break;
				}

				// Delete the DL GW 
				DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gateway.getId()).build();
				Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
				assertNotNull(Delresponse); 
				assertEquals(204, Delresponse.getStatusCode());
				
				Void delResponseObj = Delresponse.getResult();
				assertNull(delResponseObj);
			}
		
		}
	}
	

	// Issue virtual connection delete API and poll for VC to be deleted
	private void deleteVirtualConnection(String gwId, String vcId) {
		if (gwId == null || vcId == null) {
			return;
		}
		
		// Construct an instance of the DeleteGatewayVirtualConnectionOptions model
		DeleteGatewayVirtualConnectionOptions deleteGatewayVirtualConnectionOptionsModel = new DeleteGatewayVirtualConnectionOptions.Builder()
		.gatewayId(gwId).id(vcId).build();

		// Delete the virtual connection and make sure the API responds with a 204 status code
		Response<Void> delVCResponse = testService
			.deleteGatewayVirtualConnection(deleteGatewayVirtualConnectionOptionsModel).execute();
		assertNotNull(delVCResponse);
		assertEquals(204, delVCResponse.getStatusCode());

		// Construct an instance of the GetGatewayVirtualConnectionOptions model
		GetGatewayVirtualConnectionOptions getGatewayVirtualConnectionOptionsModel = new GetGatewayVirtualConnectionOptions.Builder()
			.gatewayId(gwId).id(vcId).build();

		// Poll the gateway's virtual connection waiting for it to actually be removed.
		boolean done = false;
		int timerCount = 1;

		while (!done) {
			try {
				// Query the virtual connection to see if it still exists.
				Response<GatewayVirtualConnection> VCResponse = testService.getGatewayVirtualConnection(getGatewayVirtualConnectionOptionsModel).execute();

				// Did not hit the exception. Either VC still exists or different error occurred this pass.  Continue polling
				if (timerCount > 40) {
						// Timed out.  Throw an error
						assertNotEquals(404, VCResponse.getStatusCode());
						done = true;
				} else {
					++timerCount;
					try {
						Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
					} catch (InterruptedException e) {
					// 
					}
				}
			} catch (com.ibm.cloud.sdk.core.service.exception.NotFoundException nfe) {
				// When the virtual connection no longer exists, this exception is thrown.
				done = true;
			}
		}
	
	}

	private String getPortForConnect(List<Port> ports){
		String providerToBeUsed = "DL2-TEST";
		String portId = "";

		for (Port port: ports){
			if (port.getProviderName().equals(providerToBeUsed)){
				portId = port.getId();
			}
		}

		return portId;
	}
	
	@Test 
	public void testDedicatedGatewayOptions() { 
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file 
		String gatewayName = "JAVA-INT-SDK-DEDICATED-"+timestamp; 
		String updatedGatewayName = "JAVA-INT-SDK-DEDICATED-PATCH-"+timestamp; 
		Long bgpAsn = 64999L; 
		String bgpBaseCidr = "169.254.0.0/16"; 
		String crossConnectRouter = "LAB-xcr01.dal09"; 
		boolean global = true; 
		Long speedMbps = 1000L; 
		boolean metered = false; 
		String carrierName = "carrier1"; 
		String customerName = "customer1"; 
		String gatewayType = "dedicated";
	  
	  GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
	  	.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).bgpCerCidr("10.254.30.78/30").bgpIbmCidr("10.254.30.77/30")
	  	.global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
	  	.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
	  	.locationName(locationName).build();
	  
	  // ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
	  // Invoke operation with valid options model (positive test)
	  Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
		assertNotNull(response); 
		assertEquals(201, 
		response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		
		// save gw id for clean up routine if we terminate
	  gatewayId = responseObj.getId();
	  
	  //********** Get the dedicate gateway just created *************
	  GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();
	  
	  // Invoke operation with valid options model (positive test)
	  Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
		assertNotNull(getGatewayResponse); 
		assertEquals(200, getGatewayResponse.getStatusCode());
	  
		responseObj = getGatewayResponse.getResult(); 
		assertNotNull(responseObj);
	  
		// ********** List all gateways ************* 
		ListGatewaysOptions listGatewaysOptionsModel = new ListGatewaysOptions();
	  
		// Invoke operation with valid options model (positive test)
		Response<GatewayCollection> lisResp = testService.listGateways(listGatewaysOptionsModel).execute();
		assertNotNull(lisResp); assertEquals(200, lisResp.getStatusCode());
		
		GatewayCollection lisresponseObj = lisResp.getResult();
		assertNotNull(lisresponseObj); assertNotEquals(0,lisresponseObj.getGateways().size());
	  
		// ********** Patch the gateway using attributes that can be changed with the current gw status ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.global(false).metered(true).name(updatedGatewayName).speedMbps(Long.valueOf("1000")).build(); 
			
		// Invoke operation with valid options model (positive test) 
		Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
		assertNotNull(updateResponse); 
		assertEquals(200, updateResponse.getStatusCode());
	  
	  Gateway updateResponseObj = updateResponse.getResult();
	  assertNotNull(updateResponseObj);
	  
		// ********** Patch the gateway using valid attributes, but cannot be changed with the current gw status ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.loaRejectReason("Some reason for the failure").build(); 
			
			// Invoke operation with valid options model 
			try { 
				// should not end up here 
				Response<Gateway>	updateResponseLoa = testService.updateGateway(updateGatewayOptionsModel).execute();
				assertNotNull(updateResponseLoa); 
				assertEquals(400, updateResponseLoa.getStatusCode()); 
			} catch (com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
				// expect this error for loa status update response 
			}
	  
	  // Construct an instance of the UpdateGatewayOptions model
	  updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.operationalStatus("loa_rejected").loaRejectReason("Some reason for the failure").build(); 
				
		// Invoke operation with valid options model (positive test) 
		try { 
			// should not end up here
	  	Response<Gateway> updateResponseStatus = testService.updateGateway(updateGatewayOptionsModel).execute();
			assertNotNull(updateResponseStatus); 
			assertEquals(400, updateResponseStatus.getStatusCode()); 
		} catch	(com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			// expect this error for loa status update response 
		}
	  
		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(responseObj.getId()) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.
	}
	 
	// @Test(dependsOnMethods = "testDedicatedGatewayOptions")
	// public void testMacsecEnabledGatewayOptions() {
	// 	Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
	// 	assertNotNull(testService);
	// 	String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file 
	// 	String gatewayName = "JAVA-INT-SDK-MACSEC-"+timestamp; 
	// 	String updatedGatewayName = "JAVA-INT-SDK-MACSEC-PATCH-"+timestamp; 
	// 	Long bgpAsn = 64999L; 
	// 	String bgpBaseCidr = "169.254.0.0/16"; 
	// 	String crossConnectRouter =	"LAB-xcr01.dal09"; 
	// 	boolean global = true; 
	// 	Long speedMbps = 1000L; 
	// 	boolean metered = false; 
	// 	String carrierName = "carrier1"; 
	// 	String customerName = "customer1"; 
	// 	String gatewayType = "dedicated";
	// 	String cak = config.get("CAK");
	  
	// 	GatewayMacsecConfigTemplatePrimaryCak primaryCak = new GatewayMacsecConfigTemplatePrimaryCak.Builder(cak).build();

	//   GatewayMacsecConfigTemplate macsecConfig = new GatewayMacsecConfigTemplate.Builder(true, primaryCak).build();

	//   GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
	//   	.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).bgpCerCidr("10.254.30.78/30").bgpIbmCidr("10.254.30.77/30")
	//   	.global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
	//   	.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
	//   	.locationName(locationName).macsecConfig(macsecConfig).build();
	  
	//   // ***************** Create macsec enabled dedicated Gateway ********************* //
	// 	// Construct an instance of the CreateGatewayOptions model 
	// 	CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
	//   // Invoke operation with valid options model (positive test)
	//   Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
	// 	assertNotNull(response); 
	// 	assertEquals(201, 
	// 	response.getStatusCode());
	  
	// 	Gateway responseObj = response.getResult(); 
	// 	assertNotNull(responseObj);
	// 	assertNotNull(responseObj.getMacsecConfig());
		
	// 	// save gw id for clean up routine if we terminate
	//   gatewayId = responseObj.getId();
	  
	//   //********** Get the macsec enabled dedicate gateway just created *************
	//   GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();
	  
	//   // Invoke operation with valid options model (positive test)
	//   Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
	// 	assertNotNull(getGatewayResponse); 
	// 	assertEquals(200, getGatewayResponse.getStatusCode());
	  
	// 	responseObj = getGatewayResponse.getResult(); 
	// 	assertNotNull(responseObj);

	// 	GatewayMacsecConfigPatchTemplateFallbackCak fallbackCak = new GatewayMacsecConfigPatchTemplateFallbackCak.Builder(cak).build();
	// 	GatewayMacsecConfigPatchTemplate macsecPatchConfig = new GatewayMacsecConfigPatchTemplate.Builder().fallbackCak(fallbackCak).build();
	  
	// 	// ********** Patch the gateway using attributes that can be changed with the current gw status ************* 
	// 	// Construct an instance of the UpdateGatewayOptions model 
	// 	UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
	// 		.name(updatedGatewayName).macsecConfig(macsecPatchConfig).build(); 
			
	// 	// Invoke operation with valid options model (positive test) 
	// 	Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
	// 	assertNotNull(updateResponse); 
	// 	assertEquals(200, updateResponse.getStatusCode());
	  
	//    	Gateway updateResponseObj = updateResponse.getResult();
	//   	assertNotNull(updateResponseObj);
	  
	// 	// Delete the dedicated GW 
	// 	DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(responseObj.getId()) .build(); 
			
	// 	//  Invoke operation with valid options model (positive test) 
	// 	Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
	// 	assertNotNull(Delresponse); 
	// 	assertEquals(204, Delresponse.getStatusCode());
		
	// 	Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
	// 	assertNull(delResponseObj);
	  
	//   	gatewayId = null; // already cleaned up System.out.


	// }

	@Test (dependsOnMethods = "testDedicatedGatewayOptions")
	public void testPorts() {
		assertNotNull(testService);

		// ***************** List all ports but limit list to 2 entries (pagination test) *********************
		// Construct an instance of the ListPortsOptions model
		ListPortsOptions listPortsOptionsModel = new ListPortsOptions.Builder().limit(Long.valueOf("2")).build();
		// Invoke operation with valid options model (positive test)
		Response<PortCollection> res = testService.listPorts(listPortsOptionsModel).execute();
		assertNotNull(res);
		assertEquals(200, res.getStatusCode());
		PortCollection resObj = res.getResult();
		assertNotNull(resObj);

		// Save the first port found for get port and connect gw tests
		firstPortId = getPortForConnect(resObj.getPorts());

		// ***************** Get details of the 1st port *********************
		// Construct an instance of the GetPortOptions model
		GetPortOptions getPortOptionsModel = new GetPortOptions.Builder().id(firstPortId).build();
		// Invoke operation with valid options model (positive test)
		Response<Port> resp = testService.getPort(getPortOptionsModel).execute();
		assertNotNull(resp);
		assertEquals(200, res.getStatusCode());
		Port respObj = resp.getResult();
		assertNotNull(respObj);
	}

	@Test(dependsOnMethods = "testPorts")
	public void testConnectGatewayOptions() {
		assertNotNull(testService);
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

		String gatewayName = "JAVA-INT-SDK-CONNECT-" + timestamp;
		Long bgpAsn = 64999L;
		Long speedMbps = 1000L;
		boolean metered = false;
		boolean global = false;
		String gatewayType = "connect";
		String bgpBaseCidr = "169.254.0.0/16";

		GatewayPortIdentity gatewayPortIdentityModel = new GatewayPortIdentity.Builder().id(firstPortId).build();
		GatewayTemplateGatewayTypeConnectTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeConnectTemplate.Builder()
				.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.port(gatewayPortIdentityModel).bgpBaseCidr(bgpBaseCidr).build();

		// ***************** Create connect Gateway *********************
		// Construct an instance of the CreateGatewayOptions model
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder()
				.gatewayTemplate(gatewayTemplateModel).build();

		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
		assertNotNull(response);
		assertEquals(201, response.getStatusCode());

		Gateway responseObj = response.getResult();
		assertNotNull(responseObj);

		gatewayId = responseObj.getId();

		// Before a connect gateway can be deleted, it must be in the "provisioned"
		// state. This happens automagically, but we need to wait/poll for it.

		// ********** Get the connect just created *************
		GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		boolean done = false;
		int timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}
		
		// Delete the connect GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(responseObj.getId()) .build(); 
		// Invoke operation with valid options model (positive test)
		Response<Void> Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse);
		assertEquals(204, Delresponse.getStatusCode());

		Void delResponseObj = Delresponse.getResult();

		// Response does not have a return type. Check that the result is null.
		assertNull(delResponseObj);
		gatewayId = null; // already cleaned up 
	}
	
	@Test (dependsOnMethods = "testConnectGatewayOptions")
	public void testVirtualConnection() {
		assertNotNull(testService);
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

		String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file
		String gatewayName = "JAVA-INT-SDK-VC-" + timestamp;
		Long bgpAsn = 64999L;
		String bgpBaseCidr = "169.254.0.0/16";
		String crossConnectRouter = "LAB-xcr01.dal09";
		boolean global = true;
		Long speedMbps = 1000L;
		boolean metered = false;
		String carrierName = "carrier1";
		String customerName = "customer1";
		String gatewayType = "dedicated";

		// ********** Create a dedicated gateway *************
		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
				.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName)
				.locationName(locationName).build();
		
		// Construct an instance of the CreateGatewayOptions model
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder()
				.gatewayTemplate(gatewayTemplateModel).build();

		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
		assertNotNull(response);
		assertEquals(201, response.getStatusCode());
		Gateway responseObj = response.getResult();
		assertNotNull(responseObj);
		
		gatewayId = responseObj.getId();

		String crn = config.get("GEN2_VPC_CRN");

		// ********** Create a VPC virtual connection *************
		// Construct an instance of the CreateGatewayVirtualConnectionOptions model
		CreateGatewayVirtualConnectionOptions createGatewayVirtualConnectionOptionsModel = new CreateGatewayVirtualConnectionOptions.Builder()
			.gatewayId(gatewayId).name("JAVA-INT-SDK-VPC-VC").type("vpc").networkId(crn).build();

		// Invoke operation with valid options model (positive test)
		Response<GatewayVirtualConnection> createResp = testService
			.createGatewayVirtualConnection(createGatewayVirtualConnectionOptionsModel).execute();

		assertNotNull(createResp);
		assertEquals(201, createResp.getStatusCode());
		
		GatewayVirtualConnection createRespObj = createResp.getResult();
		assertNotNull(createRespObj);
		
		vpcVcId = createRespObj.getId();

		// ********** Create a Classic virtual connection *************
		// Construct an instance of the CreateGatewayVirtualConnectionOptions model
		createGatewayVirtualConnectionOptionsModel = new CreateGatewayVirtualConnectionOptions.Builder()
			.gatewayId(gatewayId).name("JAVA-INT-SDK-CLASSIC-VC").type("classic").build();

		// Invoke operation with valid options model (positive test)
		createResp = testService.createGatewayVirtualConnection(createGatewayVirtualConnectionOptionsModel).execute();

		assertNotNull(createResp);
		assertEquals(201, createResp.getStatusCode());
		
		createRespObj = createResp.getResult();
		assertNotNull(createRespObj);
		
		classicVcId = createRespObj.getId();

		// ********** List all the VCs (should be 2) *************
		// Construct an instance of the ListGatewayVirtualConnectionsOptions model
		ListGatewayVirtualConnectionsOptions listGatewayVirtualConnectionsOptionsModel = new ListGatewayVirtualConnectionsOptions.Builder()
				.gatewayId(gatewayId).build();

		// Invoke operation with valid options model (positive test)
		Response<GatewayVirtualConnectionCollection> resp = testService
				.listGatewayVirtualConnections(listGatewayVirtualConnectionsOptionsModel).execute();
		assertNotNull(resp);
		assertEquals(200, resp.getStatusCode());
		GatewayVirtualConnectionCollection respObj = resp.getResult();
		assertNotNull(respObj);

		assertEquals(2, respObj.getVirtualConnections().size());

		// ********** GET both of the virtual connections attached to the gateway
		// Construct an instance of the GetGatewayVirtualConnectionOptions model
		GetGatewayVirtualConnectionOptions getGatewayVirtualConnectionOptionsModel = new GetGatewayVirtualConnectionOptions.Builder()
				.gatewayId(gatewayId).id(vpcVcId).build();

		// Invoke operation with valid options model (positive test)
		Response<GatewayVirtualConnection> VCResponse = testService
			.getGatewayVirtualConnection(getGatewayVirtualConnectionOptionsModel).execute();
		assertNotNull(VCResponse);
		assertEquals(200, VCResponse.getStatusCode());
		GatewayVirtualConnection VCresponseObj = VCResponse.getResult();
		assertNotNull(VCresponseObj);

		// ********** GET each of the virtual connections attached to the gateway
		// Construct an instance of the GetGatewayVirtualConnectionOptions model
		getGatewayVirtualConnectionOptionsModel = new GetGatewayVirtualConnectionOptions.Builder()
			.gatewayId(gatewayId).id(classicVcId).build();

		// Invoke operation with valid options model (positive test)
		VCResponse = testService.getGatewayVirtualConnection(getGatewayVirtualConnectionOptionsModel).execute();
		assertNotNull(VCResponse);
		assertEquals(200, VCResponse.getStatusCode());
		VCresponseObj = VCResponse.getResult();
		assertNotNull(VCresponseObj);

		// ********* Update VPC virtual connection using an attribute that can be changed ************
		// Construct an instance of the UpdateGatewayVirtualConnectionOptions model
		UpdateGatewayVirtualConnectionOptions updateGatewayVirtualConnectionOptionsModel = new UpdateGatewayVirtualConnectionOptions.Builder()
			.gatewayId(gatewayId).id(vpcVcId).name("JAVA-INT-SDK-VPC-VC-PATCH").build();

		// Invoke operation with valid options model (positive test)
		Response<GatewayVirtualConnection> updateResponse = testService
			.updateGatewayVirtualConnection(updateGatewayVirtualConnectionOptionsModel).execute();
		assertNotNull(updateResponse);
		assertEquals(200, updateResponse.getStatusCode());
		GatewayVirtualConnection updateResponseObj = updateResponse.getResult();
		assertNotNull(updateResponseObj);

		// ********* Update VPC virtual connection using a valid attribute that can not be changed because the VC does not span accounts requiring a status approval ************
		// Construct an instance of the UpdateGatewayVirtualConnectionOptions model
		updateGatewayVirtualConnectionOptionsModel = new UpdateGatewayVirtualConnectionOptions.Builder()
				.gatewayId(gatewayId).id(vpcVcId).status("rejected").build();

		// Invoke operation with valid options model (positive test) 
		try { 
			// should not end up here because the VC does not span accounts requiring a status approval
			updateResponse = testService.updateGatewayVirtualConnection(updateGatewayVirtualConnectionOptionsModel).execute();
			assertNotNull(updateResponse);
			assertEquals(400, updateResponse.getStatusCode()); 
		} catch	(com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			// expect this error for vc status update response 
		}

		//************************** Delete virtual connections *********************
		deleteVirtualConnection(gatewayId, classicVcId); 
		deleteVirtualConnection(gatewayId, vpcVcId); 

		classicVcId = null;
		vpcVcId = null;

		// delete the gateway
		// Construct an instance of the DeleteGatewayOptions model
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId).build();

		// Invoke operation with valid options model (positive test)
		Response<Void> Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse);
		Void delResponseObj = Delresponse.getResult();
		// Response does not have a return type. Check that the result is null.
		assertNull(delResponseObj);
		gatewayId = null;
	}

	@Test (dependsOnMethods = "testVirtualConnection")
	public void testLOA() {
		assertNotNull(testService);
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

		String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file
		String gatewayName = "JAVA-INT-SDK-LOA-" + timestamp;
		Long bgpAsn = 64999L;
		String bgpBaseCidr = "169.254.0.0/16";
		String crossConnectRouter = "LAB-xcr01.dal09";
		boolean global = true;
		Long speedMbps = 1000L;
		boolean metered = false;
		String carrierName = "carrier1";
		String customerName = "customer1";
		String gatewayType = "dedicated";

		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
			.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
			.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName)
			.locationName(locationName).build();

		// ***************** Create Gateway *********************
		// Construct an instance of the CreateGatewayOptions model
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder()
			.gatewayTemplate(gatewayTemplateModel).build();

		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
		// assertNotNull(response);
		Gateway responseObj = response.getResult();
		assertNotNull(responseObj);
	 	gatewayId = responseObj.getId();

		// Construct an instance of the ListGatewayLetterOfAuthorizationOptions model
		ListGatewayLetterOfAuthorizationOptions listGatewayLetterOfAuthorizationOptionsModel = new ListGatewayLetterOfAuthorizationOptions.Builder()
			.id(gatewayId).build();

		try {
			// Invoke operation with valid options model (positive test)
			Response<InputStream> loaResponse = testService
				.listGatewayLetterOfAuthorization(listGatewayLetterOfAuthorizationOptionsModel).execute();

			// We should never get here as the call should throw an exception
			assertNotNull(loaResponse);
			assertNotEquals(404, loaResponse.getStatusCode());
		} catch (com.ibm.cloud.sdk.core.service.exception.NotFoundException nfe) {
			// Expect a not found response since LOA does not exist
		}

		// delete the gateway
		// Construct an instance of the DeleteGatewayOptions model
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId).build();

		// Invoke operation with valid options model (positive test)
		Response<Void> Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse);
		Void delResponseObj = Delresponse.getResult();
		// Response does not have a return type. Check that the result is null.
		assertNull(delResponseObj);
		gatewayId = null;
	}

	@Test (dependsOnMethods = "testLOA")
  public void testCompletionNotice() throws IOException {
		assertNotNull(testService);

		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
		String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file
		String gatewayName = "JAVA-INT-SDK-CN-" + timestamp;
		Long bgpAsn = 64999L;
		String bgpBaseCidr = "169.254.0.0/16";
		String crossConnectRouter = "LAB-xcr01.dal09";
		boolean global = true;
		Long speedMbps = 1000L;
		boolean metered = false;
		String carrierName = "carrier1";
		String customerName = "customer1";
		String gatewayType = "dedicated";

		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
				.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName)
				.locationName(locationName).build();

		// ***************** Create Gateway *********************
		// Construct an instance of the CreateGatewayOptions model
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder()
				.gatewayTemplate(gatewayTemplateModel).build();

		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
		// assertNotNull(response);
		Gateway responseObj = response.getResult();
		assertNotNull(responseObj);
	 	gatewayId = responseObj.getId();

		// ***************** Attempt to upload a completion notice; it will fail due to github issue state *********************
		try { 
			// open the sample completion notice pdf
			File initialFile = new File("src/main/resources/completion_notice.pdf");

			// Construct an instance of the CreateGatewayCompletionNoticeOptions model
			CreateGatewayCompletionNoticeOptions createGatewayCompletionNoticeOptionsModel = new CreateGatewayCompletionNoticeOptions.Builder()
				.id(gatewayId).uploadContentType("application/pdf").upload(initialFile).build();

			// Invoke operation with valid options model (positive test)
				testService.createGatewayCompletionNotice(createGatewayCompletionNoticeOptionsModel).execute();
		} catch (com.ibm.cloud.sdk.core.service.exception.ServiceResponseException e) {
			// Expect upload to fail with a 412 since the GH state for the gateway is not correct for upload at this point.
			assertEquals(412, e.getStatusCode());
		}
		 
		// ***************** Attempt to get details for a completion notice; it will fail due to not being found *********************
   
		// Construct an instance of the ListGatewayCompletionNoticeOptions model
		ListGatewayCompletionNoticeOptions listGatewayCompletionNoticeOptionsModel = new ListGatewayCompletionNoticeOptions.Builder()
			.id(gatewayId).build();

		try {	
			// Invoke operation with valid options model (positive test)
			Response<InputStream> listActResponse = testService
				.listGatewayCompletionNotice(listGatewayCompletionNoticeOptionsModel).execute();
			assertNull(listActResponse);
			InputStream lisResponseObj = listActResponse.getResult();
			assertNull(lisResponseObj);
		} catch (com.ibm.cloud.sdk.core.service.exception.NotFoundException nfe) {
			// Expect a not found response since CN does not exist
		}

		// delete the gateway
		// Construct an instance of the DeleteGatewayOptions model
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId).build();

		// Invoke operation with valid options model (positive test)
		Response<Void> Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse);
		Void delResponseObj = Delresponse.getResult();
		// Response does not have a return type. Check that the result is null.
		assertNull(delResponseObj);
		gatewayId = null;
 	}
   
	@Test (dependsOnMethods = "testCompletionNotice")
	public void testOfferings() {
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");// "dal09";//read this from env file

		// ***************** Attempt to list dedicated offering types  **********************
		// Construct an instance of the ListOfferingTypeLocationsOptions model for dedicated GWs
		ListOfferingTypeLocationsOptions listOfferingTypeLocationsOptionsModel = new ListOfferingTypeLocationsOptions.Builder()
				.offeringType("dedicated").build();

		// Invoke operation with valid options model (positive test)
		Response<LocationCollection> response = testService
				.listOfferingTypeLocations(listOfferingTypeLocationsOptionsModel).execute();
		assertNotNull(response);
		assertEquals(200, response.getStatusCode());

		LocationCollection responseObj = response.getResult();
		assertNotNull(responseObj);
		assertNotEquals(0,responseObj.getLocations().size());

		// ***************** Attempt to list connect offering types  **********************
		// Construct an instance of the ListOfferingTypeLocationsOptions model for connect GWs
		listOfferingTypeLocationsOptionsModel = new ListOfferingTypeLocationsOptions.Builder()
			.offeringType("connect").build();

		// Invoke operation with valid options model (positive test)
	 	response = testService
			.listOfferingTypeLocations(listOfferingTypeLocationsOptionsModel).execute();
		assertNotNull(response);
		assertEquals(200, response.getStatusCode());

		responseObj = response.getResult();
		assertNotNull(responseObj);
		assertNotEquals(0,responseObj.getLocations().size());

		// ***************** Attempt to list cross connect routers  **********************
		// Construct an instance of the
		// ListOfferingTypeLocationCrossConnectRoutersOptions model
		ListOfferingTypeLocationCrossConnectRoutersOptions listOfferingTypeLocationCrossConnectRoutersOptionsModel = new ListOfferingTypeLocationCrossConnectRoutersOptions.Builder()
				.offeringType("dedicated").locationName(locationName).build();

		// Invoke operation with valid options model (positive test)
		Response<LocationCrossConnectRouterCollection> routerResponse = testService
				.listOfferingTypeLocationCrossConnectRouters(listOfferingTypeLocationCrossConnectRoutersOptionsModel)
				.execute();
		assertNotNull(routerResponse);
		assertEquals(200, routerResponse.getStatusCode());
		LocationCrossConnectRouterCollection routerResponseObj = routerResponse.getResult();
		assertNotNull(routerResponseObj);
		assertNotEquals(0,routerResponseObj.getCrossConnectRouters().size());

		// ***************** Attempt to list dedicated offering speeds  **********************
		// Construct an instance of the ListOfferingTypeSpeedsOptions model
		ListOfferingTypeSpeedsOptions listOfferingTypeSpeedsOptionsModel = new ListOfferingTypeSpeedsOptions.Builder()
				.offeringType("dedicated").build();

		// Invoke operation with valid options model (positive test)
		Response<OfferingSpeedCollection> res = testService.listOfferingTypeSpeeds(listOfferingTypeSpeedsOptionsModel)
				.execute();
		assertNotNull(res);
		assertEquals(200, response.getStatusCode());
		OfferingSpeedCollection resObj = res.getResult();
		assertNotNull(resObj);
		assertNotEquals(0,resObj.getSpeeds().size());
		List<OfferingSpeed> speeds = resObj.getSpeeds();
		assertNotNull(speeds.get(0).getCapabilities());

		// ***************** Attempt to list connect offering speeds  **********************
		// Construct an instance of the ListOfferingTypeSpeedsOptions model
		listOfferingTypeSpeedsOptionsModel = new ListOfferingTypeSpeedsOptions.Builder()
			.offeringType("connect").build();

		// Invoke operation with valid options model (positive test)
		res = testService.listOfferingTypeSpeeds(listOfferingTypeSpeedsOptionsModel).execute();
		assertNotNull(res);
		assertEquals(200, response.getStatusCode());
		resObj = res.getResult();
		assertNotNull(resObj);
		assertNotEquals(0,resObj.getSpeeds().size());
	}

	// @Test()
	// public void testDirectLinkGatewayWithMD5(){
	// 	Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
	// 	assertNotNull(testService);
	// 	String locationName = config.get("LOCATION_NAME");
	// 	String gatewayName = "JAVA-INT-SDK-DEDICATED-MD5-"+timestamp; 
	// 	Long bgpAsn = 64999L; 
	// 	String bgpBaseCidr = "169.254.0.0/16"; 
	// 	String crossConnectRouter =	"LAB-xcr01.dal09"; 
	// 	boolean global = true; 
	// 	Long speedMbps = 1000L; 
	// 	boolean metered = false; 
	// 	String carrierName = "carrier1"; 
	// 	String customerName = "customer1"; 
	// 	String gatewayType = "dedicated";

	// 	String authenticationCRN = config.get("AUTHENTICATION_KEY");

	// 	GatewayTemplateAuthenticationKey authKey = new GatewayTemplateAuthenticationKey.Builder(authenticationCRN).build();
	  
	// 	GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
	// 		.bgpAsn(bgpAsn).bgpBaseCidr(bgpBaseCidr).bgpCerCidr("10.254.30.78/30").bgpIbmCidr("10.254.30.77/30")
	// 		.global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
	// 		.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
	// 		.locationName(locationName).authenticationKey(authKey).build();
	  
	//   	// ***************** Create dedicated Gateway ********************* //
	// 	// Construct an instance of the CreateGatewayOptions model 
	// 	CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
	// 	// Invoke operation with valid options model (positive test)
	// 	Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
	// 		assertNotNull(response); 
	// 		assertEquals(201, 
	// 		response.getStatusCode());
	  
	// 	Gateway responseObj = response.getResult(); 
	// 	assertNotNull(responseObj);
	// 	assertEquals(responseObj.getName(), gatewayName);
	// 	assertEquals(responseObj.getAuthenticationKey().getCrn(), authenticationCRN);
		
	// 	// save gw id for clean up routine if we terminate
	//   	gatewayId = responseObj.getId();
	  
	// 	// ********** Clear the authentication key using Patch gateway ************* 
	// 	// Construct an instance of the UpdateGatewayOptions model 

	// 	GatewayPatchTemplateAuthenticationKey patchAuthKey = new GatewayPatchTemplateAuthenticationKey.Builder("").build();
	// 	UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
	// 		.authenticationKey(patchAuthKey).build();
			
	// 	// Invoke operation with valid options model (positive test) 
	// 	Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
	// 	assertNotNull(updateResponse); 
	// 	assertEquals(200, updateResponse.getStatusCode());
	  
	// 	Gateway updateResponseObj = updateResponse.getResult();
	// 	assertNotNull(updateResponseObj);
	// 	assertEquals(updateResponseObj.getId(), gatewayId);
	// 	assertNull(updateResponseObj.getAuthenticationKey());
	// 	assertEquals(updateResponseObj.getName(), gatewayName);

	// 	// Delete the dedicated GW 
	// 	DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
	// 	//  Invoke operation with valid options model (positive test) 
	// 	Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
	// 	assertNotNull(Delresponse); 
	// 	assertEquals(204, Delresponse.getStatusCode());
		
	// 	Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
	// 	assertNull(delResponseObj);
	  
	//   	gatewayId = null; // already cleaned up System.out.

	// }
	
	@Test()
	public void testDirectLinkDedicatedGatewayWithConnectionMode(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");
		String gatewayName = "JAVA-INT-SDK-DEDICATED-DLAAS-"+timestamp; 
		Long bgpAsn = 64999L; 
		String crossConnectRouter = "LAB-xcr01.dal09";
		boolean global = true; 
		Long speedMbps = 1000L; 
		boolean metered = false; 
		String carrierName = "carrier1"; 
		String customerName = "customer1"; 
		String gatewayType = "dedicated";
		String connectionMode = "direct";

	  
		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
			.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
			.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
			.locationName(locationName).connectionMode(connectionMode).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		assertEquals(responseObj.getConnectionMode(), connectionMode);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();
	  
		// ********** Update the connection mode using Patch gateway ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.connectionMode("transit").build();
			
		// Invoke operation with valid options model (positive test) 
		Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
		assertNotNull(updateResponse); 
		assertEquals(200, updateResponse.getStatusCode());
	  
		Gateway updateResponseObj = updateResponse.getResult();
		assertNotNull(updateResponseObj);
		assertEquals(updateResponseObj.getId(), gatewayId);
		assertEquals(updateResponseObj.getName(), gatewayName);
		assertEquals(updateResponseObj.getConnectionMode(), "transit");

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.

	}
	
	@Test(dependsOnMethods = "testPorts")
	public void testDirectLinkConnectGatewayWithConnectionMode(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String gatewayName = "JAVA-INT-SDK-CONNECT-DLAAS-"+timestamp; 
		Long bgpAsn = 64999L;
		Long speedMbps = 1000L;
		boolean metered = false;
		boolean global = false;
		String gatewayType = "connect";
		String bgpBaseCidr = "169.254.0.0/16";

		GatewayPortIdentity gatewayPortIdentityModel = new GatewayPortIdentity.Builder().id(firstPortId).build();
		GatewayTemplateGatewayTypeConnectTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeConnectTemplate.Builder()
				.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.port(gatewayPortIdentityModel).bgpBaseCidr(bgpBaseCidr).connectionMode("direct").build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		assertEquals(responseObj.getConnectionMode(), "direct");
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();

		  // ********** Get the connect just created and wait for it to move to provisioned state *************
		GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		boolean done = false;
		int timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}
	  
		// ********** Update the connection mode using Patch gateway ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.connectionMode("transit").build();
			
		// Invoke operation with valid options model (positive test) 
		Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
		assertNotNull(updateResponse); 
		assertEquals(200, updateResponse.getStatusCode());
	  
		Gateway updateResponseObj = updateResponse.getResult();
		assertNotNull(updateResponseObj);
		assertEquals(updateResponseObj.getId(), gatewayId);
		assertEquals(updateResponseObj.getName(), gatewayName);
		assertEquals(updateResponseObj.getConnectionMode(), "transit");


		// ********** Get the connect just created and wait for it to move to provisioned state *************
		getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		done = false;
		timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.
	}

	@Test()
	public void testDirectLinkDedicatedGatewayWithBgpIp(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");
		String gatewayName = "JAVA-INT-SDK-DEDICATED-BGP-IP-"+timestamp; 
		Long bgpAsn = 64999L; 
		String crossConnectRouter = "LAB-xcr01.dal09";
		boolean global = true; 
		Long speedMbps = 1000L; 
		boolean metered = false; 
		String carrierName = "carrier1"; 
		String customerName = "customer1"; 
		String gatewayType = "dedicated";

	  
		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
			.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
			.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
			.locationName(locationName).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();
	  
		// ********** Update the BGP ASN mode using Patch gateway *************
		long updatedBgpAsn = 63999L;
		UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.bgpAsn(updatedBgpAsn).build();
			
		Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
		assertNotNull(updateResponse); 
		assertEquals(200, updateResponse.getStatusCode());
	  
		Gateway updateResponseObj = updateResponse.getResult();
		assertNotNull(updateResponseObj);
		assertEquals(updateResponseObj.getId(), gatewayId);
		assertEquals(updateResponseObj.getName(), gatewayName);
		assertEquals(updateResponseObj.getBgpAsn().longValue(), updatedBgpAsn);
		
		// ********** Update the BGP IBM and CER CIDR mode using Patch gateway ************* 
		try { 
		
			updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
				.bgpCerCidr("172.17.252.2/29").bgpIbmCidr("172.17.252.1/29").build();
		
			updateResponse = testService.updateGateway(updateGatewayOptionsModel).execute();
			assertNotNull(updateResponse); 
			assertEquals(200, updateResponse.getStatusCode());
		
			updateResponseObj = updateResponse.getResult();
			assertNotNull(updateResponseObj);
			assertEquals(updateResponseObj.getId(), gatewayId);
			assertEquals(updateResponseObj.getName(), gatewayName);
			assertEquals(updateResponseObj.getBgpCerCidr(), "172.17.252.2/29");
			assertEquals(updateResponseObj.getBgpIbmCidr(), "172.17.252.1/29");
		} catch (com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			assertEquals(errResponse.getStatusCode(), 400);
			assertEquals(errResponse.getMessage(), "Please make sure localIP and remoteIP are not in use");
		}

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.
	}

	public void testDirectLinkConnectGatewayWithBgpIp(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String gatewayName = "JAVA-INT-SDK-CONNECT-BGP-IP-"+timestamp; 
		Long bgpAsn = 64999L;
		Long speedMbps = 1000L;
		boolean metered = false;
		boolean global = false;
		String gatewayType = "connect";

		GatewayPortIdentity gatewayPortIdentityModel = new GatewayPortIdentity.Builder().id(firstPortId).build();
		GatewayTemplateGatewayTypeConnectTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeConnectTemplate.Builder()
				.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.port(gatewayPortIdentityModel).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();

		  // ********** Get the connect just created and wait for it to move to provisioned state *************
		GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		boolean done = false;
		int timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}
	  
		// ********** Update the BGP IBM and CER CIDR mode using Patch gateway ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		try { 
			long updatedBgpAsn = 63999L;
			UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
				.bgpAsn(updatedBgpAsn).bgpCerCidr("172.17.252.2/29").bgpIbmCidr("172.17.252.1/29").build();
		
			Response<Gateway> updateResponse = testService.updateGateway(updateGatewayOptionsModel).execute();
			assertNotNull(updateResponse); 
			assertEquals(200, updateResponse.getStatusCode());
		
			Gateway updateResponseObj = updateResponse.getResult();
			assertNotNull(updateResponseObj);
			assertEquals(updateResponseObj.getId(), gatewayId);
			assertEquals(updateResponseObj.getName(), gatewayName);
			assertEquals(updateResponseObj.getBgpAsn().longValue(), updatedBgpAsn);
			assertEquals(updateResponseObj.getBgpCerCidr(), "172.17.252.2/29");
			assertEquals(updateResponseObj.getBgpIbmCidr(), "172.17.252.1/29");
		} catch (com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			assertEquals(errResponse.getStatusCode(), 400);
			assertEquals(errResponse.getMessage(), "Please make sure localIP and remoteIP are not in use");
		}


		// ********** Get the connect just created and wait for it to move to provisioned state *************
		getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		done = false;
		timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);

	  	gatewayId = null; // already cleaned up System.out.
	}

	public void testDirectLinkDedicatedGatewayWithBfd(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");
		String gatewayName = "JAVA-INT-SDK-DEDICATED-BFD-"+timestamp; 
		Long bgpAsn = 64999L; 
		String crossConnectRouter =	"LAB-xcr01.dal09"; 
		boolean global = true; 
		Long speedMbps = 1000L; 
		boolean metered = false; 
		String carrierName = "carrier1"; 
		String customerName = "customer1";
		String gatewayType = Gateway.Type.DEDICATED;

		Long bfdInterval = 300L;
		Long bfdMultiplier = 1L;
 
		GatewayBfdConfigTemplate bfdConfig = new GatewayBfdConfigTemplate.Builder().interval(bfdInterval).multiplier(bfdMultiplier).build();

		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
			.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
			.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
			.locationName(locationName).bfdConfig(bfdConfig).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		assertEquals(responseObj.getBfdConfig().getInterval(), bfdInterval);
		assertEquals(responseObj.getBfdConfig().getMultiplier(), bfdMultiplier);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();
	  
		// ********** Update the BFD parameters using Patch gateway *************
		Long updatedBfdInterval = 1000L;
		Long updatedBfdMultiplier = 200L;

		GatewayBfdPatchTemplate upadtedBfdConfig = new GatewayBfdPatchTemplate.Builder().interval(updatedBfdInterval).multiplier(updatedBfdMultiplier).build();		
		UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
			.bfdConfig(upadtedBfdConfig).build();
			
		Response<Gateway> updateResponse =	testService.updateGateway(updateGatewayOptionsModel).execute();
		assertNotNull(updateResponse); 
		assertEquals(200, updateResponse.getStatusCode());
	  
		Gateway updateResponseObj = updateResponse.getResult();
		assertNotNull(updateResponseObj);
		assertEquals(updateResponseObj.getId(), gatewayId);
		assertEquals(updateResponseObj.getName(), gatewayName);
		assertEquals(updateResponseObj.getBfdConfig().getInterval(), updatedBfdInterval);
		assertEquals(updateResponseObj.getBfdConfig().getMultiplier(), updatedBfdMultiplier);

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.
	}

	public void testDirectLinkConnectGatewayWithBfd(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String gatewayName = "JAVA-INT-SDK-CONNECT-BFD-"+timestamp; 
		Long bgpAsn = 64999L;
		Long speedMbps = 1000L;
		boolean metered = false;
		boolean global = false;
		String gatewayType = Gateway.Type.CONNECT;

		Long bfdInterval = 300L;
		Long bfdMultiplier = 1L;
 
		GatewayBfdConfigTemplate bfdConfig = new GatewayBfdConfigTemplate.Builder().interval(bfdInterval).multiplier(bfdMultiplier).build();

		GatewayPortIdentity gatewayPortIdentityModel = new GatewayPortIdentity.Builder().id(firstPortId).build();
		GatewayTemplateGatewayTypeConnectTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeConnectTemplate.Builder()
				.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
				.port(gatewayPortIdentityModel).bfdConfig(bfdConfig).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();

		  // ********** Get the connect just created and wait for it to move to provisioned state *************
		GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		boolean done = false;
		int timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}
	  
		// ********** Update the BFD paramters using Patch gateway ************* 
		// Construct an instance of the UpdateGatewayOptions model 
		try { 
			Long updatedBfdInterval = 300L;
			Long updatedBfdMultiplier = 1L;
 
			GatewayBfdPatchTemplate updatedBfdConfig = new GatewayBfdPatchTemplate.Builder().interval(updatedBfdInterval).multiplier(updatedBfdMultiplier).build();
			UpdateGatewayOptions updateGatewayOptionsModel = new UpdateGatewayOptions.Builder().id(responseObj.getId())
				.bfdConfig(updatedBfdConfig).build();
		
			Response<Gateway> updateResponse = testService.updateGateway(updateGatewayOptionsModel).execute();
			assertNotNull(updateResponse); 
			assertEquals(200, updateResponse.getStatusCode());
		
			Gateway updateResponseObj = updateResponse.getResult();
			assertNotNull(updateResponseObj);
			assertEquals(updateResponseObj.getId(), gatewayId);
			assertEquals(updateResponseObj.getName(), gatewayName);
			assertEquals(updateResponseObj.getBfdConfig().getInterval(), updatedBfdInterval);
			assertEquals(updateResponseObj.getBfdConfig().getMultiplier(), updatedBfdMultiplier);
		} catch (com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			assertEquals(errResponse.getStatusCode(), 400);
			assertEquals(errResponse.getMessage(), "Please make sure localIP and remoteIP are not in use");
		}


		// ********** Get the connect just created and wait for it to move to provisioned state *************
		getGatewayOptionsModel = new GetGatewayOptions.Builder().id(gatewayId).build();

		done = false;
		timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> getGatewayResponse = testService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(getGatewayResponse);
			assertEquals(200, getGatewayResponse.getStatusCode());

			responseObj = getGatewayResponse.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Delete the connect GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);

	  	gatewayId = null; // already cleaned up System.out.
	}

	public void testDirectLinkDedicatedGatewayGatewayStatus(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		assertNotNull(testService);
		String locationName = config.get("LOCATION_NAME");
		String gatewayName = "JAVA-INT-SDK-DEDICATED-GW-STATUS-"+timestamp; 
		Long bgpAsn = 64999L; 
		String crossConnectRouter =	"LAB-xcr01.dal09"; 
		boolean global = true; 
		Long speedMbps = 1000L; 
		boolean metered = false; 
		String carrierName = "carrier1"; 
		String customerName = "customer1";
		String gatewayType = Gateway.Type.DEDICATED;

		Long bfdInterval = 300L;
		Long bfdMultiplier = 1L;
 
		GatewayBfdConfigTemplate bfdConfig = new GatewayBfdConfigTemplate.Builder().interval(bfdInterval).multiplier(bfdMultiplier).build();

		GatewayTemplateGatewayTypeDedicatedTemplate gatewayTemplateModel = new GatewayTemplateGatewayTypeDedicatedTemplate.Builder()
			.bgpAsn(bgpAsn).global(global).metered(metered).name(gatewayName).speedMbps(speedMbps).type(gatewayType)
			.carrierName(carrierName).crossConnectRouter(crossConnectRouter).customerName(customerName) 
			.locationName(locationName).bfdConfig(bfdConfig).build();
	  
	  	// ***************** Create dedicated Gateway ********************* //
		// Construct an instance of the CreateGatewayOptions model 
		CreateGatewayOptions createGatewayOptionsModel = new CreateGatewayOptions.Builder().gatewayTemplate(gatewayTemplateModel).build();
	  
		// Invoke operation with valid options model (positive test)
		Response<Gateway> response = testService.createGateway(createGatewayOptionsModel).execute();
			assertNotNull(response); 
			assertEquals(201, 
			response.getStatusCode());
	  
		Gateway responseObj = response.getResult(); 
		assertNotNull(responseObj);
		assertEquals(responseObj.getName(), gatewayName);
		assertEquals(responseObj.getBfdConfig().getInterval(), bfdInterval);
		assertEquals(responseObj.getBfdConfig().getMultiplier(), bfdMultiplier);
		
		// save gw id for clean up routine if we terminate
	  	gatewayId = responseObj.getId();
	  
		// ********** Get GW Status of the GW using status API *************
		GetGatewayStatusOptions getGatewayStatusOptions = new GetGatewayStatusOptions.Builder(gatewayId).type("link").build();

		Response<GatewayStatusCollection> gatewayStatusResponse = testService.getGatewayStatus(getGatewayStatusOptions).execute(); 
		assertNotNull(response); 
		assertEquals(200, gatewayStatusResponse.getStatusCode());

		GatewayStatusCollection listStatusObj = gatewayStatusResponse.getResult();
		assertNotNull(listStatusObj);
		assertNotEquals(0,listStatusObj.getStatus().size());
		assertEquals(listStatusObj.getStatus().get(0).getType(), "link");
		assertNotNull(listStatusObj.getStatus().get(0).getValue());
		assertNotNull(listStatusObj.getStatus().get(0).getUpdatedAt());

		// Delete the dedicated GW 
		DeleteGatewayOptions deleteGatewayOptionsModel = new DeleteGatewayOptions.Builder().id(gatewayId) .build(); 
			
		//  Invoke operation with valid options model (positive test) 
		Response<Void>  Delresponse = testService.deleteGateway(deleteGatewayOptionsModel).execute();
		assertNotNull(Delresponse); 
		assertEquals(204, Delresponse.getStatusCode());
		
		Void delResponseObj = Delresponse.getResult(); // Response does not have a return type. Check that the result is null. 
		assertNull(delResponseObj);
	  
	  	gatewayId = null; // already cleaned up System.out.
	}

}
