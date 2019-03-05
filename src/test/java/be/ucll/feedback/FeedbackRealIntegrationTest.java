package be.ucll.feedback;

import be.ucll.feedback.model.Feedback;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedbackRealIntegrationTest {

    @LocalServerPort
    private int port;

    // Needed to test REST api
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testAddFeedback() throws JSONException {

        Feedback ok = new Feedback("Elke", "OK well done!");

        HttpEntity<Feedback> entity = new HttpEntity<Feedback>(ok, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/feedbacks/add"), HttpMethod.POST, entity, String.class);

        String jsonInBodyResponse = "[{ 'id': 1, 'name': 'Elke', 'feedback': 'OK well done!'}]";
        JSONAssert.assertEquals(jsonInBodyResponse, response.getBody(), true);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
