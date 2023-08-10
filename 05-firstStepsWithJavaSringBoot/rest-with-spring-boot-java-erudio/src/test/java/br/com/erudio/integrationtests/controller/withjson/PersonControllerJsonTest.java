package br.com.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	@Test
	public void sholdDisplaySwaggerPage() {
	    var content = 
        	    given()
        	    .basePath("/swagger-ui/index.html")
        	    .port(TestConfigs.SERVER_PORT)
        	    .when()
        	        .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body().asString();
	    
	    assertTrue(content.contains("Swagger UI"));
	}

}
