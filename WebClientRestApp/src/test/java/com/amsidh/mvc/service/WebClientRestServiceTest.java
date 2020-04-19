package com.amsidh.mvc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;


public class WebClientRestServiceTest {
	private final MockWebServer mockWebServer = new MockWebServer();

	private WebClientRestService webClientRestService;
	private WebClientRestService spyWebClientRestService;

	@Before
	public void setup() {
		mockWebServer.url("/");
		webClientRestService = new WebClientRestService(mockWebServer.url("/").toString());
		
		this.spyWebClientRestService = Mockito.spy(webClientRestService);
		
	}

	@Test
	public void testGetAllEmployeeWithCorrectDataAndCallGivesHttpOKResponse() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
				.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setBody("[{\r\n" + "  \"employeeId\": 1,\r\n" + "  \"employeeName\": \"A B Lokhande\"\r\n" + "}, {\r\n"
						+ "  \"employeeId\": 2,\r\n" + "  \"employeeName\": \"A A Lokhande\"\r\n" + "}, {\r\n"
						+ "  \"employeeId\": 3,\r\n" + "  \"employeeName\": \"Adity ALokhande\"\r\n" + "}]")

		);

		spyWebClientRestService.getAllEmployee();
		RecordedRequest takeRequest = mockWebServer.takeRequest();
		assertEquals("/employees", takeRequest.getPath());
		assertEquals(HttpMethod.GET.name(), takeRequest.getMethod());
		verify(spyWebClientRestService,Mockito.times(1)).getAllEmployee();
	}
	
	@Test
	public void testGetAllEmployeeWithRestServiceNotAvailableResponseIsEmpty() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value())
				.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setBody("[]"));

		spyWebClientRestService.getAllEmployee();
		
		verify(spyWebClientRestService,Mockito.times(1)).getAllEmployee();
	}
	
	
	@Test
	public void testForSaveEmployee() throws InterruptedException {
		
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.CREATED.value())
				.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setBody("{\"employeeId\":1,\"employeeName\":\"Xyz\"}"));

		spyWebClientRestService.saveEmployee();
		
		RecordedRequest takeRequest = mockWebServer.takeRequest();
		assertEquals("/employees", takeRequest.getPath());
		assertEquals(HttpMethod.POST.name(), takeRequest.getMethod());
		verify(spyWebClientRestService,Mockito.times(1)).saveEmployee();
	}
	
	@Test
	public void testForUpdateEmployee() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
				.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setBody("{\"employeeId\":1,\"employeeName\":\"Xyz\"}"));

		spyWebClientRestService.updateEmployee();
		
		RecordedRequest takeRequest = mockWebServer.takeRequest();
		assertEquals("/employees/1", takeRequest.getPath());
		assertEquals(HttpMethod.PATCH.name(), takeRequest.getMethod());
		verify(spyWebClientRestService,Mockito.times(1)).updateEmployee();
	}
	
	@Test
	public void testForDeleteEmployee() throws InterruptedException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
				.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.setBody("{\"employeeId\":1,\"employeeName\":\"Xyz\"}"));

		spyWebClientRestService.deleteEmployee();
		
		RecordedRequest takeRequest = mockWebServer.takeRequest();
		assertEquals("/employees/4", takeRequest.getPath());
		assertEquals(HttpMethod.DELETE.name(), takeRequest.getMethod());
		verify(spyWebClientRestService,Mockito.times(1)).deleteEmployee();
	}
	

}
