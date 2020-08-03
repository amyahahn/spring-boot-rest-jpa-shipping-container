package com.amyhahn.ShippingContainerApplication;

import com.amyhahn.ShippingContainerApplication.model.ShippingContainer;
import com.amyhahn.ShippingContainerApplication.util.ShippingContainerUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShippingContainerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShippingContainerApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Before
	public void setUp(){
		addTwoShippingContainers();
	}

	private void addTwoShippingContainers() {
		ShippingContainer shippingContainer = new ShippingContainer();
		shippingContainer.setContainerOwnerId((long) 2);
		shippingContainer.setStatusTimestamp(ShippingContainerUtils.getCurrentTimestampAsString());
		shippingContainer.setStatus(ShippingContainer.statusAvailable);
		ResponseEntity<ShippingContainer> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/shippingcontainers", shippingContainer, ShippingContainer.class);
		assertEquals(200, postResponse.getStatusCodeValue());

		ShippingContainer shippingContainer2 = new ShippingContainer();
		shippingContainer2.setContainerOwnerId((long) 1);
		shippingContainer2.setStatusTimestamp(ShippingContainerUtils.getCurrentTimestampAsString());
		shippingContainer2.setStatus(ShippingContainer.statusAvailable);
		ResponseEntity<ShippingContainer> postResponse2 = restTemplate.postForEntity(getRootUrl() + "/api/v1/shippingcontainers", shippingContainer2, ShippingContainer.class);
		assertEquals(200, postResponse.getStatusCodeValue());
	}


	@Test
	public void testCreateShippingContainer1() {
		ShippingContainer shippingContainer = new ShippingContainer();
		shippingContainer.setContainerOwnerId((long) 1);
		shippingContainer.setStatusTimestamp(ShippingContainerUtils.getCurrentTimestampAsString());
		shippingContainer.setStatus(ShippingContainer.statusAvailable);
		System.out.println(getRootUrl() + "/api/v1/shippingcontainers");
		ResponseEntity<ShippingContainer> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/shippingcontainers", shippingContainer, ShippingContainer.class);
		assertEquals(200, postResponse.getStatusCodeValue());
	}

	@Test
	public void testCreateShippingContainer2() {
		ShippingContainer shippingContainer = new ShippingContainer();
		shippingContainer.setContainerOwnerId((long) 2);
		shippingContainer.setStatusTimestamp(ShippingContainerUtils.getCurrentTimestampAsString());
		shippingContainer.setStatus(ShippingContainer.statusAvailable);
		System.out.println(getRootUrl() + "/api/v1/shippingcontainers");
		ResponseEntity<ShippingContainer> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/shippingcontainers", shippingContainer, ShippingContainer.class);
		assertEquals(200, postResponse.getStatusCodeValue());
	}

	@Test
	public void testGetAllShippingContainers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/shippingcontainers",
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("{\"containerId\":2") && response.getBody().contains("{\"containerId\":1"));
	}

	@Test
	public void testGetShippingContainerByContainerId() {
		ShippingContainer shippingContainer = restTemplate.getForObject(getRootUrl() + "/api/v1/shippingcontainers/1", ShippingContainer.class);
		System.out.println(shippingContainer.getContainerId());
		System.out.println(shippingContainer.toString());

		assertEquals(1, shippingContainer.getContainerId());
	}

	@Test
	public void testGetShippingAvailableContainersByContainerOwnerId() {
		ShippingContainer[] shippingContainers = restTemplate.getForObject(getRootUrl() + "/api/v1/shippingcontainers/1/AVAILABLE", ShippingContainer[].class);
		System.out.println("We got back " + shippingContainers.length + " containters");
		Assert.assertTrue(shippingContainers.length > 0);
		for (ShippingContainer container : shippingContainers){
			Assert.assertTrue(container.getStatus().equals(ShippingContainer.statusAvailable) && container.getContainerOwnerId() == 1);
		}
	}

	@Test
	public void testUpdateContainerStatus() {
		ShippingContainer shippingContainer = restTemplate.getForObject(getRootUrl() + "/api/v1/shippingcontainers/1", ShippingContainer.class);
		System.out.println(shippingContainer.getContainerId());
		System.out.println(shippingContainer.toString());

		assertEquals(1, shippingContainer.getContainerId());
		shippingContainer.setStatus(ShippingContainer.statusUnavailable);

		restTemplate.put(getRootUrl() + "/api/v1/shippingcontainers/1", shippingContainer);

		ShippingContainer updatedShippingContainer = restTemplate.getForObject(getRootUrl() + "/api/v1/shippingcontainers/1", ShippingContainer.class);
		assertNotNull(updatedShippingContainer);
		assertEquals(ShippingContainer.statusUnavailable, updatedShippingContainer.getStatus());
	}

	@Test
	public void testDeleteShippingContainer() {
		int containerId = 2;
		ShippingContainer shippingContainer = restTemplate.getForObject(getRootUrl() + "/api/v1/shippingcontainers/1", ShippingContainer.class);
		assertNotNull(shippingContainer);

		restTemplate.delete(getRootUrl() + "/api/v1/shippingcontainers/" + containerId);

		try {
			shippingContainer = restTemplate.getForObject(getRootUrl() + "/api/v1/" +
					"shippingcontainers/" + containerId, ShippingContainer.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}

