package com.turi.turi0411;

import com.turi.turi0411.controller.PostController;
import com.turi.turi0411.controller.TestController;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
class Turi0411ApplicationTests {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    MockMvc mockMvc;
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();


    static class Sibal extends RequestMappingHandlerMapping {

        void sibal(MockHttpServletRequest request) throws Exception {
            String path = this.initLookupPath(request);
            System.out.println("******" + path);
            System.out.println(this.lookupHandlerMethod(path, request));
        }
    }

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setRequestURI("/test");
        request.setMethod("GET");
    }

    @Test
    void contextLoads() throws Exception {

        Sibal sibal = new Sibal();
        sibal.sibal(request);

    }

}
