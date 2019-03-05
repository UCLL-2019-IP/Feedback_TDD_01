package be.ucll.feedback;

import be.ucll.feedback.controller.FeedbackController;
import be.ucll.feedback.model.Feedback;
import be.ucll.feedback.model.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// Bootstraps the controller and auto-configures MockMvc, this are testing controllers
// without starting a full HTTP server
@WebMvcTest(FeedbackController.class)
public class FeedbackRestControllerUnitTest {

    @Autowired
    private MockMvc feedbackController;

    @MockBean
    private FeedbackService service;

    @Test
    public void givenTwoFeedbacks_whenGetFeedbacks_thenReturnJsonArray() throws Exception {

        Feedback ok = FeedbackBuilder.anOKFeedback().build();
        Feedback nok = new Feedback("Miyo", "NOK you need to do that again");
        when(service.findAll()).thenReturn(Arrays.asList(ok, nok));

        feedbackController.perform(get("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Elke"))
                .andExpect(jsonPath("$[0].feedback").value("OK well done!"))
                .andExpect(jsonPath("$[1].name").value("Miyo"))
                .andExpect(jsonPath("$[1].feedback").value("NOK you need to do that again"));
    }

    @Test
    public void givenNoFeedbacks_whenAddFeedback_thenReturnJsonArray() throws Exception {
        Feedback ok = FeedbackBuilder.anOKFeedback().build();
        when(service.findAll()).thenReturn(Arrays.asList(ok));

        feedbackController.perform(post("/feedbacks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\": \"Elke\", \"feedback\": \"OK well done!\"}"))
                //.andDo(print()) // with this you print the request and response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].feedback").value(ok.getFeedback()));
    }

    @Test
    public void givenNoFeedbacks_whenAddFeedbackWithNoValidFeedback_thenReturnJsonArray() throws Exception {
        feedbackController.perform(post("/feedbacks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\": \"Yuki\", \"feedback\": \"OK\"}"))
                //.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("size must be between 5 and 80"));
    }

}