package org.example.consumer;

import org.example.consumer.dtos.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.assertj.core.api.BDDAssertions;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "org.example.jms:spring-cloud-contract-producer:+:stubs:8090")
public class BasicControllerIntegrationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void given_WhenAskHello_ThenReturnHello()
            throws Exception {
        ResponseEntity<Greeting> greetingResponseEntity = restTemplate.getForEntity("http://localhost:8090/hello", Greeting.class);
        BDDAssertions.then(greetingResponseEntity.getBody().getMessage().toLowerCase().startsWith("hello"));
    }

    @Test
    public void given_WhenAskHelloWithoutName_ThenReturnWorld()
            throws Exception {
        ResponseEntity<Greeting> greetingResponseEntity = restTemplate.getForEntity("http://localhost:8090/hello", Greeting.class);
        BDDAssertions.then(greetingResponseEntity.getBody().getName().equalsIgnoreCase("world"));
    }

    @Test
    public void given_WhenAskHelloWithName_ThenReturnName()
            throws Exception {
        ResponseEntity<Greeting> greetingResponseEntity = restTemplate.getForEntity("http://localhost:8090/hello?name=javier", Greeting.class);
        BDDAssertions.then(greetingResponseEntity.getBody().getName().equalsIgnoreCase("javier"));
    }
}
