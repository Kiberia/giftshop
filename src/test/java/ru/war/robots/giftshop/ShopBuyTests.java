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
class ShopBuyTests {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static final String TEST_USER_GUID = "6591c91a-f41a-47c3-9d4e-325086f10784";
    public static final Long TEST_ITEM_ID = 1L;
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    InventoryRepository inventoryRepository;
    
    @Autowired
    WalletRepository walletRepository;
    
    private void setNoMoneyWallet() {
        Wallet wallet = new Wallet(
            TEST_USER_GUID,
            0L,
            0L
        );
        
        walletRepository.update(wallet);
    }
    
    private void setLotsOfMoneyWallet() {
        Wallet wallet = new Wallet(
            TEST_USER_GUID,
            100_000L,
            100_000L
        );
        
        walletRepository.update(wallet);
    }
    
    private MvcResult returnQueryResult(BuyDto dto, boolean isValidCall) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        )
        .andExpect(isValidCall ? status().isOk() : status().isInternalServerError())
        .andReturn();
    }
    
    @Test
    void testInvalidData() throws Exception {
        BuyDto dto = new BuyDto();
        MvcResult result = returnQueryResult(dto, false);
    
        String expectedMessage = "Invalid data";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testBadGuid() throws Exception {
        BuyDto dto = new BuyDto(
            "Bad guid",
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "User not found";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testBadItem() throws Exception {
        BuyDto dto = new BuyDto(
            TEST_USER_GUID,
            -1L
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "Wrong item id";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testNoMoney() throws Exception {
        setNoMoneyWallet();
        
        BuyDto dto = new BuyDto(
            TEST_USER_GUID,
            TEST_ITEM_ID
        );
        MvcResult result = returnQueryResult(dto, false);
        
        String expectedMessage = "User can't afford this item";
        String actualMessage = result.getResponse().getContentAsString();
        
        assertTrue(expectedMessage, actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testValid() throws Exception {
        BuyDto dto = new BuyDto(
            TEST_USER_GUID,
            TEST_ITEM_ID
        );
    
        setLotsOfMoneyWallet();
        inventoryRepository.wipeItem(TEST_USER_GUID, TEST_ITEM_ID);
        
        MvcResult result = returnQueryResult(dto, true);
        
        assertTrue(
            "Buy failed",
            inventoryRepository.existsItemInInventory(TEST_USER_GUID, TEST_ITEM_ID)
        );
    }
}
