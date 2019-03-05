package be.ucll.feedback.repository;

import be.ucll.feedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Feedback findFeedbackByName(String name);

}
