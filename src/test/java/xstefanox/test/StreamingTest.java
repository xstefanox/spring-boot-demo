package xstefanox.test;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xstefanox.ExampleApplication;

import static io.restassured.RestAssured.when;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ExampleApplication.class,
        webEnvironment = DEFINED_PORT)
@ActiveProfiles("test")
public class StreamingTest {

    public static class TestConfiguration {
    }

    @Test
    public void test() {
        assertThat(true, is(true));

        RestAssured.port = 8081;
        RestAssured.baseURI = "http://localhost/api/v1";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        when()
                .get("/customers")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(SC_OK);
    }
}
