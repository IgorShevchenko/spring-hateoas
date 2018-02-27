package com.igor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.igor.model.Member;
import com.igor.repository.MemberRestRepository;

/**
 * Not start the server at all, but test only the layer below that, where Spring
 * handles the incoming HTTP request and hands it off to your controller. That
 * way, almost the full stack is used, and your code will be called exactly the
 * same way as if it was processing a real HTTP request, but without the cost of
 * starting the server. We can narrow down the tests to just the web
 * layer by using @WebMvcTest.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = MembersController.class)
public class MembersControllerTest {

    private final static DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberRestRepository repository;

	/*
     *
	 * Example of unit test verifying the logic
	 *
	 */

    @Test
    public void shouldUpdateMember() throws Exception {
        // Setup
        Member original = createMember("Ben", "Musterman", "10141", "1990-10-15");
        Member updated = createMember("Alica", "Musterman", "10141", "2001-01-01");
        Mockito.when(this.repository.findOne(1L)).thenReturn(original);

        String url = "/members/1";
        String requestJson = getAsJson(updated);

        // Action
        this.mockMvc.perform(put(url)
                .content(requestJson)
                .contentType(JSON_MEDIA_TYPE))
                .andExpect(status().isOk());

        // Assertion, field-by-field comparison
        Assertions.assertThat(original).isEqualToComparingFieldByField(updated);
    }

    private Member createMember(String firstName, String lastName, String zipcode, String dob) {
        LocalDate dateOfBirth = LocalDate.parse(dob, DOB_FORMATTER);

        Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setZipcode(zipcode);
        member.setDateOfBirth(dateOfBirth);

        return member;
    }

    private String getAsJson(Member member) {
        String jsonFormat = "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"zipcode\":\"%s\",\"dateOfBirth\":\"%s\"}";

        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String zipCode = member.getZipcode();
        String dob = DOB_FORMATTER.format(member.getDateOfBirth());

        return String.format(jsonFormat, firstName, lastName, zipCode, dob);
    }

}
