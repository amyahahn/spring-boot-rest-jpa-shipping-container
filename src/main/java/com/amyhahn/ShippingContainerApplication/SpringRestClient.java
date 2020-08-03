package com.amyhahn.ShippingContainerApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amyhahn.ShippingContainerApplication.model.ShippingContainer;

public class SpringRestClient {

	private static final String GET_SHIPPINGCONTAINERS_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers";
	private static final String GET_SHIPPINGCONTAINERS_BYOWNERID_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers/{containerOwnerId}";
	private static final String GET_SHIPPINGCONTAINER_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers/{containerId}";
	private static final String CREATE_SHIPPINGCONTAINER_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers";
	private static final String UPDATE_SHIPPINGCONTAINER_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers/{containerId}";
	private static final String DELETE_SHIPPINGCONTAINER_ENDPOINT_URL = "http://localhost:8080/api/v1/shippingcontainers/{containerId}";
	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringRestClient springRestClient = new SpringRestClient();

		// Step1: first create a new shipping container
		springRestClient.createShippingContainer();

		// Step 2: get new created shipping container from step1
		springRestClient.getShippingContainerByContainerId();

		// Step3: get all shipping containers
		springRestClient.getShippingContainers();

		// Step4: Update shipping container with status "UNAVAILABLE"
		springRestClient.updateContainerStatusStatus(false);

		// Step5: Delete employee with id = 1
		springRestClient.deleteShippingContainer();
	}

	private void getShippingContainers() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_SHIPPINGCONTAINERS_ENDPOINT_URL, HttpMethod.GET, entity,
				String.class);

		System.out.println(result);
	}

	private void getShippingContainerByContainerId() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("containerId", "1");

		RestTemplate restTemplate = new RestTemplate();
		ShippingContainer result = restTemplate.getForObject(GET_SHIPPINGCONTAINER_ENDPOINT_URL, ShippingContainer.class, params);

		System.out.println(result);
	}

	private void getShippingContainerByContainerOwnerId() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("containerCustomerId", "1");

		RestTemplate restTemplate = new RestTemplate();
		ShippingContainer result = restTemplate.getForObject(GET_SHIPPINGCONTAINERS_BYOWNERID_ENDPOINT_URL, ShippingContainer.class, params);

		System.out.println(result);
	}

	private void createShippingContainer() {

		ShippingContainer newShippingContainer = new ShippingContainer(1, 1);

		RestTemplate restTemplate = new RestTemplate();
		ShippingContainer result = restTemplate.postForObject(CREATE_SHIPPINGCONTAINER_ENDPOINT_URL, newShippingContainer, ShippingContainer.class);

		System.out.println(result);
	}

	private void updateContainerStatusStatus(boolean status) {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		params.put("status", status);
		ShippingContainer updatedShippingContainer = new ShippingContainer(1, 1);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(UPDATE_SHIPPINGCONTAINER_ENDPOINT_URL, updatedShippingContainer, params);
	}

	private void deleteShippingContainer() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("containerId", "1");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(DELETE_SHIPPINGCONTAINER_ENDPOINT_URL, params);
	}
}

