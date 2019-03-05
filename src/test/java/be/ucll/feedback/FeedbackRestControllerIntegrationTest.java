package be.ucll.feedback;

import be.ucll.feedback.model.Feedback;
import be.ucll.feedback.repository.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class FeedbackRestControllerIntegrationTest {

    @Autowired
    private MockMvc feedbackController;

    @Autowired
    private FeedbackRepository repository;

    @Test
    public void givenFeedbacks_whenGetFeedbacks_thenStatus200AndJSONofFeedbacks() throws Exception {
        createTestFeedback("Elke", "OK well done!!!");

        feedbackController.perform(get("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Elke"))
                .andExpect(jsonPath("$[0].feedback").value("OK well done!!!"));
    }


    @Test
    public void givenNoFeedbacks_whenAddFeedback_thenStatus200AndJSONofFeedbacks() throws Exception {

        feedbackController.perform(post("/feedbacks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\": \"Yuki\", \"feedback\": \"OKOKOK\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Elke"))
                .andExpect(jsonPath("$[0].feedback").value("OK well done!!!"))
                .andExpect(jsonPath("$[1].name").value("Yuki"))
                .andExpect(jsonPath("$[1].feedback").value("OKOKOK"));
    }
    @Test
    public void givenNoFeedbacks_whenAddInvalidFeedback_thenStatus404AndErrorMessage() throws Exception {

        feedbackController.perform(post("/feedbacks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\": \"Yuki\", \"feedback\": \"OK\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("size must be between 5 and 80"));
    }

    private void createTestFeedback(String name, String text) {
        Feedback feedback = new Feedback(name, text);
        repository.saveAndFlush(feedback);
    }

}
