package be.ucll.feedback.model;

import be.ucll.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    public FeedbackRepository feedbackRepo;

    public List<Feedback> findAll() {
        return feedbackRepo.findAll();
    }

    public Feedback findFeedbackByName(String name) {
        return feedbackRepo.findFeedbackByName(name);
    }

    public Feedback add(Feedback feedback) {
        feedbackRepo.save(feedback);
        return feedback;
    }
}
