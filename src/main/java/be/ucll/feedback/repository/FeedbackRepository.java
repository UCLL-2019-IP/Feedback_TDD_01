package be.ucll.feedback.repository;

import be.ucll.feedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Feedback findFeedbackByName(String name);

}
