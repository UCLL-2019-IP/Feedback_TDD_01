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

        @Bean
        public FeedbackService feedbackService() {
            return new FeedbackService();
        }
    }

    @Autowired
    private FeedbackService feedbackService;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Before
    public void setUp() {
        Feedback ok = new Feedback("Elke", "OK");

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
