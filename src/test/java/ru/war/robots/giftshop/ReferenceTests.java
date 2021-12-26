package ru.war.robots.giftshop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.war.robots.giftshop.controller.ReferenceController;
import ru.war.robots.giftshop.jooq.tables.pojos.Item;
import ru.war.robots.giftshop.model.pojo.UserPojo;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.test.properties")
class ReferenceTests {

    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    void testUsers() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/reference/list/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();
    
        List<UserPojo> users = mapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>(){}
        );
        
        assertTrue("users size is invalid", users.size() > 0);
    }    
    
    @Test
    void testItems() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/reference/list/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();
    
        List<Item> items = mapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>(){}
        );
        
        assertTrue("items size is invalid", items.size() > 0);
    }
    
}
