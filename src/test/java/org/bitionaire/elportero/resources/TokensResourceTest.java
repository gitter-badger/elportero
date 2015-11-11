package org.bitionaire.elportero.resources;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.bitionaire.elportero.ElPorteroApplication;
import org.bitionaire.elportero.ElPorteroConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static com.jayway.restassured.RestAssured.*;

import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.matchers.Times.exactly;

public class TokensResourceTest {

    @ClassRule
    public static final DropwizardAppRule<ElPorteroConfiguration> RULE = new DropwizardAppRule<>(ElPorteroApplication.class, resourceFilePath("test.yml"));

    private ClientAndProxy proxy;
    private ClientAndServer mockServer;

    @Before
    public void startProxy() {
        mockServer = startClientAndServer(1080);
        proxy = startClientAndProxy(1090);
    }

    @After
    public void stopProxy() {
        proxy.stop();
        mockServer.stop();
    }

    @Test
    public void testSuccessfulLoginForExistingUser() throws Exception {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/consumers/bitionaire"),
                exactly(1)
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "    \"id\": \"4d924084-1adb-40a5-c042-63b19db421d1\"," +
                                "    \"custom_id\": \"bitionaire\"," +
                                "    \"created_at\": 1422386534" +
                                "}"
                        )
        );

        mockTokenRequest();

        given()
                .port(RULE.getLocalPort())
                .auth().basic("bitionaire", "secret123")
        .when()
                .post("/tokens")
        .then().assertThat()
                .statusCode(200)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhMzZjMzA0OWIzNjI0OWEzYzlmODg5MWNiMTI3MjQzYyJ9.5uUYhXpZ1H8tpWSmVqC3JPCiJCxB2aqL5PHGX_0jlOA");
    }

    @Test
    public void testSuccessfulLoginForNonExistingUser() throws Exception {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/consumers/bitionaire"),
                exactly(1)
        ).respond(
                response()
                        .withStatusCode(404)
        );

        mockServer.when(
                request()
                        .withMethod("POST")
                        .withHeader("Content-Type", "application/json")
                        .withPath("/consumers/bitionaire"),
                exactly(1)
        ).respond(
                response()
                        .withStatusCode(201)
                        .withBody("{" +
                                "    \"id\": \"4d924084-1adb-40a5-c042-63b19db421d1\"," +
                                "    \"custom_id\": \"bitionaire\"," +
                                "    \"created_at\": 1422386534" +
                                "}"
                        )
        );

        mockTokenRequest();

        given()
                .port(RULE.getLocalPort())
                .auth().basic("bitionaire", "secret123")
        .when()
                .post("/tokens")
        .then().assertThat()
                .statusCode(200)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhMzZjMzA0OWIzNjI0OWEzYzlmODg5MWNiMTI3MjQzYyJ9.5uUYhXpZ1H8tpWSmVqC3JPCiJCxB2aqL5PHGX_0jlOA");

    }

    private void mockTokenRequest() {
        mockServer.when(
                request()
                        .withMethod("POST")
                        .withPath("/consumers/bitionaire/jwt"),
                exactly(1)
        ).respond(
                response()
                        .withStatusCode(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "    \"consumer_id\": \"7bce93e1-0a90-489c-c887-d385545f8f4b\"," +
                                "    \"created_at\": 1442426001000," +
                                "    \"id\": \"bcbfb45d-e391-42bf-c2ed-94e32946753a\"," +
                                "    \"key\": \"a36c3049b36249a3c9f8891cb127243c\"," +
                                "    \"secret\": \"e71829c351aa4242c2719cbfbe671c09\"" +
                                "}"
                        )
        );
    }

    @Test
    public void testFailedLogin() {
        given()
                .port(RULE.getLocalPort())
                .auth().basic("bitionaire", "wrongpass")
        .when()
                .post("/tokens")
        .then().assertThat()
                .statusCode(401);
    }

}