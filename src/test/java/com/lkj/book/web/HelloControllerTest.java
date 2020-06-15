package com.lkj.book.web;

import com.lkj.book.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * @SpringBootTest의 경우 일반적인 테스트로 전체 응용 프로그램 컨텍스트를 시작함.
 * 따라서 전체 응용 프로그램을 로드하여 모든 bean을 주입하기 때문에 속도가 느림.
 * @WebMvcTest의 경우는 Controller layer를 테스트하고 모의 객체를 사용하기 때문에 나머지 필요한 bean을 직접 세팅.
 * 그렇기 때문에 가볍고 빠르게 테스트 가능.
 */
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles="USER")
    public void hello가_리턴된다() throws Exception {
        //given
        String hello = "hello";

        //when
        ResultActions resultActions = mockMvc.perform(get("/hello"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    @WithMockUser(roles="USER")
    public void helloDto가_리턴된다() throws Exception {

        //given
        String name = "hello";
        int amount = 1000;

        //when
        ResultActions resultActions = mockMvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}