package com.mycontacts.app.contactbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import java.util.logging.Logger;

import com.googlecode.protobuf.format.JsonFormat;

import com.mycontacts.app.contactbook.model.ContractBookProto.ContactBook;
import com.mycontacts.app.contactbook.model.ContractBookProto.Contact;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;
import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.*;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)


public class ContactBookApplicationTest {
	private static  int port = 8088;
	private static final String CONTACTS = "http://localhost:"+ port + "/contacts/";

	@Autowired
	private RestTemplate restTemplate;

	protected Logger logger = Logger.getLogger(ContactBookApplicationTest.class.getName());

	@Test
	public void testPostContact() throws IOException {

		JSONObject request = new JSONObject();
		request.put("id", "23");
		request.put("name", "Charles");

		HttpEntity responseStream = postHttpRequest(CONTACTS, request);
		String content = EntityUtils.toString(responseStream);

		assertTrue("Post Json", content.equals("Successful"));
	}

	@Test
	public void testPostContactUsingRestTemplate() {
		JSONObject request = new JSONObject();
		request.put("id", "23");
		request.put("name", "Charles");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(request.toString(), headers);


		ResponseEntity<String> contact = restTemplate.exchange(CONTACTS, HttpMethod.POST, entity, String.class);

		int status = contact.getStatusCode().value();
		String body = contact.getBody();
		logger.info(body);
		logger.info(Integer.toString(status));
		logger.info("================***********========");

		assertTrue("Status", status == 200);

		assertThat(contact.toString(), StringContains.containsString("Successful"));
	}

	@Test
    public void testGetAllContacts() {
        ResponseEntity<ContactBook> contact = restTemplate.getForEntity(CONTACTS, ContactBook.class);
		assertTrue(contact.getBody().getContactList().size() ==  2);
		assertTrue(true);
		 assertThat(contact.toString(), StringContains.containsString("id"));
	}


    private HttpEntity postHttpRequest(String url, JSONObject jsonObject) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		StringEntity params = new StringEntity(jsonObject.toString());
		request.setEntity(params);
		request.addHeader("content-type", "application/json");
		HttpResponse httpResponse = httpClient.execute(request);
		return httpResponse.getEntity();
	}


}

