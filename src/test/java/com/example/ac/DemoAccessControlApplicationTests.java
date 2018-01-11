package com.example.ac;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"logging.level.org.springframework.web.client.RestTemplate=DEBUG",
		"logging.level.org.springframework.web.servlet.mvc=DEBUG" })
public class DemoAccessControlApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	ObjectMapper objectMapper;
	@LocalServerPort
	int port;

	@Test
	public void getClientA_ab() {
		String auth = "client_id=A;role=get";
		RequestEntity<Void> req = RequestEntity
				.get(fromHttpUrl("http://localhost:" + port + "/demo") //
						.queryParam("fields", "a,b") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.build();
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().get("a").asText()).isEqualTo("A");
		assertThat(res.getBody().get("b").asText()).isEqualTo("B");
		assertThat(res.getBody().has("c")).isFalse();
	}

	@Test
	public void getClientA_a() {
		String auth = "client_id=A;role=get";
		RequestEntity<Void> req = RequestEntity
				.get(fromHttpUrl("http://localhost:" + port + "/demo") //
						.queryParam("fields", "a") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.build();
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().get("a").asText()).isEqualTo("A");
		assertThat(res.getBody().has("b")).isFalse();
		assertThat(res.getBody().has("c")).isFalse();
	}

	@Test
	public void getClientA_b() {
		String auth = "client_id=A;role=get";
		RequestEntity<Void> req = RequestEntity
				.get(fromHttpUrl("http://localhost:" + port + "/demo") //
						.queryParam("fields", "b") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.build();
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().has("a")).isFalse();
		assertThat(res.getBody().get("b").asText()).isEqualTo("B");
		assertThat(res.getBody().has("c")).isFalse();
	}

	@Test
	public void getClientA_c() {
		String auth = "client_id=A;role=get";
		RequestEntity<Void> req = RequestEntity
				.get(fromHttpUrl("http://localhost:" + port + "/demo") //
						.queryParam("fields", "c") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.build();
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(res.getBody().get("message").asText()).isEqualTo("Access is denied");
	}

	@Test
	public void setClientB_abc() {
		String auth = "client_id=B;role=set";
		ObjectNode body = objectMapper.createObjectNode();
		body.putObject("fields").put("a", "testA").put("b", "testB").put("c", "testC");

		RequestEntity<JsonNode> req = RequestEntity
				.post(fromHttpUrl("http://localhost:" + port + "/demo") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.contentType(MediaType.APPLICATION_JSON) //
				.body(body);
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(res.getBody().get("message").asText()).isEqualTo("Access is denied");
	}

	@Test
	public void setClientB_c() {
		String auth = "client_id=B;role=set";
		ObjectNode body = objectMapper.createObjectNode();
		body.putObject("fields").put("c", "testC");

		RequestEntity<JsonNode> req = RequestEntity
				.post(fromHttpUrl("http://localhost:" + port + "/demo") //
						.build().toUri()) //
				.header("X-Role-Auth", auth) //
				.contentType(MediaType.APPLICATION_JSON) //
				.body(body);
		ResponseEntity<JsonNode> res = restTemplate.exchange(req, JsonNode.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().get("a").asText()).isEqualTo("A");
		assertThat(res.getBody().get("b").asText()).isEqualTo("B");
		assertThat(res.getBody().get("c").asText()).isEqualTo("testC");
	}
}
