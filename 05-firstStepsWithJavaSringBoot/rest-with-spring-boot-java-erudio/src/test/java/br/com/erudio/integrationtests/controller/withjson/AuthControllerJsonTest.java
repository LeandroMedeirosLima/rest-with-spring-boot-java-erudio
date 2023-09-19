package br.com.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {
    
    private static TokenVO tokenVO;
    
    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        
        AccountCredentialsVO user = 
                new AccountCredentialsVO("leandro", "admin234");
        
        RequestSpecification specification = new RequestSpecBuilder()
                 .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                 .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                 .build();
        
        tokenVO = given().spec(specification)
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                            .extract()
                            .body()
                                .as(TokenVO.class);
        
        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }
    
    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        
        RequestSpecification specification = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                 .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                 .build();
        
        var newTokenVO = given().spec(specification)
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("username", tokenVO.getUsername())
                    .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                    .put("{username}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .as(TokenVO.class);
        
        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
    
    
}
