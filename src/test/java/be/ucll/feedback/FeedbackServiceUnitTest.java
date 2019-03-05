package be.ucll.feedback;

import be.ucll.feedback.model.Feedback;
import be.ucll.feedback.model.FeedbackService;
import be.ucll.feedback.repository.FeedbackRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;


// Bridge between Spring Boot test features and JUnit
@RunWith(SpringRunner.class)
public class FeedbackServiceUnitTest {

    @TestConfiguration
    static class FeedbackServiceTestContextConfiguration {

        // Creates an instance of service in order to be able to autowire it
        @Bean
        public FeedbackService feedbackService() {
            return new FeedbackService();
        }
    }

    @Autowired
    private FeedbackService feedbackService;

    // Service is dependent of Repository, however implementation of Repository is
    // not important => therefor mock it
    // Mock support of Spring Boot Test
    @MockBean
    private FeedbackRepository feedbackRepository;

    private Feedback ok, nok, maybe;
    private List<Feedback> feedbacks;

    @Before
    public void setUp() {
        ok = new Feedback("Elke", "OK");
        nok = new Feedback("Rudi", "NOK");
        maybe = new Feedback("Rudy", "MAYBE");
        feedbacks = new ArrayList<Feedback>();
        feedbacks.add(ok);
        feedbacks.add(nok);
    }

    @Test
    public void should_find_feedback_by_given_name () {
        // Mock
        // When we ask at the repo for the ok feedback, it will return it
        Mockito.when(feedbackRepository.findFeedbackByName(ok.getName())).thenReturn(ok);

        // given
        String name = "Elke";
        String feedback = "OK";

        // when
        Feedback found = feedbackService.findFeedbackByName(name);

        // then
        assertThat(found.getName()).isEqualTo(name);
        assertThat(found.getFeedback()).isEqualTo(feedback);
    }

    @Test
    public void should_get_all_feedbacks () {
        // Mock
        Mockito.when(feedbackRepository.findAll()).thenReturn(feedbacks);

        //when
        List<Feedback> foundFeedbacks = feedbackService.findAll();

        //then
        assertThat(foundFeedbacks.size()).isEqualTo(2);
        assertThat(foundFeedbacks).contains(ok);
        assertThat(foundFeedbacks).contains(nok);
    }

    @Test
    public void should_add_feedback () {
        //Mock
        Mockito.when(feedbackRepository.save(maybe)).thenReturn(maybe);

        //when
        Feedback addedFeedback = feedbackService.add(maybe);

        //then
        assertThat(addedFeedback.getName()).isEqualTo("Rudy");
        assertThat(addedFeedback.getFeedback()).isEqualTo("MAYBE");
    }
}
