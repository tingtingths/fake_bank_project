package com.acmebank.accountmanager;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AccountManagerApplicationTest {

    public static final double ERROR = 0.000001;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllAccounts() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id", contains(12345678, 88888888)))
                .andExpect(jsonPath("$.[?(@.id=='12345678')].name").value("Eric Chan"))
                .andExpect(jsonPath("$.[?(@.id=='88888888')].name").value("Jason Ma"));
    }

    @Test
    public void testGetAccountByID() throws Exception {
        mockMvc.perform(get("/accounts/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eric Chan"));
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        /*
         */
        mockMvc.perform(get("/accounts/12345678/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isNumber());
    }

    @Test
    public void testTransferInsufficientFund() throws Exception {
        MvcResult result = mockMvc.perform(get("/accounts/12345678/balance"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Double balance = JsonPath.parse(result.getResponse().getContentAsString()).read("$.value");

        String jsonStr = new JSONObject()
                .put("source", 12345678L)
                .put("destination", 88888888L)
                .put("amount", balance + 1).toString();

        mockMvc.perform(post("/transactions")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    public void testTransferNegativeFund() throws Exception {
        String jsonStr = new JSONObject()
                .put("source", 12345678L)
                .put("destination", 88888888L)
                .put("amount", -500.0).toString();

        mockMvc.perform(post("/transactions")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    public void testTransferSuccess() throws Exception {
        // 12345678 balance
        MvcResult result = mockMvc.perform(get("/accounts/12345678/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(greaterThan(1.00)))
                .andReturn();
        Double account1Balance = JsonPath.parse(result.getResponse().getContentAsString()).read("$.value");

        // 88888888 balance
        result = mockMvc.perform(get("/accounts/88888888/balance"))
                .andExpect(status().isOk())
                .andReturn();
        Double account2Balance = JsonPath.parse(result.getResponse().getContentAsString()).read("$.value");

        // transfer
        String jsonStr = new JSONObject()
                .put("source", 12345678L)
                .put("destination", 88888888L)
                .put("amount", 1.0).toString();

        mockMvc.perform(post("/transactions")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // check new balance
        mockMvc.perform(get("/accounts/12345678/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(
                        closeTo(account1Balance - 1, ERROR)
                ));

        mockMvc.perform(get("/accounts/88888888/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(
                        closeTo(account2Balance + 1, ERROR)
                ));
    }
}
