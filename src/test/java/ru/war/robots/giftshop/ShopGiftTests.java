package ru.war.robots.giftshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.war.robots.giftshop.jooq.tables.pojos.Wallet;
import ru.war.robots.giftshop.model.dto.BuyDto;
import ru.war.robots.giftshop.model.dto.GiftDto;
import ru.war.robots.giftshop.repository.InventoryRepository;
import ru.war.robots.giftshop.repository.WalletRepository;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopGiftTests {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static final String TEST_USER_SENDER_GUID = "6591c91a-f41a-47c3-9d4e-325086f10784";
    public static final String TEST_USER_ACCEPTOR_GUID = "5153d807-8f2d-45c5-b2fe-31990becae2c";
    public static final Long TEST_ITEM_ID = 1L;
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    InventoryRepository inventoryRepository;
    
    private MvcResult returnQueryResult(GiftDto dto, boolean isValidCall) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/gift")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        )
        .andExpect(isValidCall ? status().isOk() : status().isInternalServerError())
        .andReturn();
    }
    
    @Test
    void testInvalidData() throws Exception {
        GiftDto dto = new GiftDto();
        MvcResult result = returnQueryResult(dto, false);
    
        String expectedMessage = "Invalid data";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testBadSenderGuid() throws Exception {
        GiftDto dto = new GiftDto(
            "Bad guid",
            TEST_USER_ACCEPTOR_GUID,
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "Sender not found";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testBadAcceptorGuid() throws Exception {
        GiftDto dto = new GiftDto(
            TEST_USER_SENDER_GUID,
            "Bad guid",
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
    
        String expectedMessage = "Acceptor not found";
        String actualMessage = result.getResponse().getContentAsString();
    
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
   
    @Test
    void testBadItem() throws Exception {
        GiftDto dto = new GiftDto(
            TEST_USER_SENDER_GUID,
            TEST_USER_ACCEPTOR_GUID,
            -1L
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "Wrong item id";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testSelfGift() throws Exception {
        GiftDto dto = new GiftDto(
            TEST_USER_SENDER_GUID,
            TEST_USER_SENDER_GUID,
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "Can't gift to self";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testNoItem() throws Exception {
        inventoryRepository.wipeItem(TEST_USER_SENDER_GUID, TEST_ITEM_ID);
        
        GiftDto dto = new GiftDto(
            TEST_USER_SENDER_GUID,
            TEST_USER_ACCEPTOR_GUID,
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "Sender doesn't have this item";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testValid() throws Exception {
        inventoryRepository.wipeItem(TEST_USER_SENDER_GUID, TEST_ITEM_ID);
        inventoryRepository.wipeItem(TEST_USER_ACCEPTOR_GUID, TEST_ITEM_ID);
        inventoryRepository.addItem(TEST_USER_SENDER_GUID, TEST_ITEM_ID);
    
        GiftDto dto = new GiftDto(
            TEST_USER_SENDER_GUID,
            TEST_USER_ACCEPTOR_GUID,
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, true);
        
        assertTrue(
            "Gift failed",
            inventoryRepository.existsItemInInventory(TEST_USER_ACCEPTOR_GUID, TEST_ITEM_ID)
        );
    }
}
