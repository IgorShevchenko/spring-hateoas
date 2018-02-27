package com.igor.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.igor.client.ClientMember;
import com.igor.utils.RequestInterceptors;

/**
 * Starts an application like it would do in production. Assume you are a client, send an HTTP request and assert the response.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MembersControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

	/*
     *
	 * Example of integration test mimicking client side
	 *
	 */

    @Test
    public void shouldGetMember() {
        // Setup
        String url = "http://localhost:" + port + "/members/1";

        // Action
        RequestInterceptors.autoSetJsonHeaders(this.restTemplate);
        ClientMember member = this.restTemplate.getForObject(url, ClientMember.class);

        // Assertion
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.get_links()).isNotNull();
        Assertions.assertThat(member.get_links().getSelf().getHref()).isEqualTo(url);
        // ... more assertions ...
    }

}
