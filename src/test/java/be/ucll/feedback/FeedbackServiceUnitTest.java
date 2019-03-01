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

    @Before
    public void setUp() {
        Feedback ok = new Feedback("Elke", "OK");

        // When we ask at the repo for the ok feedback, it will return it
        // Mock
        Mockito.when(feedbackRepository.findFeedbackByName(ok.getName())).thenReturn(ok);
    }

    @Test
    public void whenNewFeedback_thenFeedbackShouldBeFound() {
        // given
        String name = "Elke";
        String feedback = "OK";

        // when
        Feedback found = feedbackService.findFeedbackByName(name);

        // then
        assertThat(found.getName()).isEqualTo(name);
        assertThat(found.getFeedback()).isEqualTo(feedback);
    }
}
