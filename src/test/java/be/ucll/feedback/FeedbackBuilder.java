package be.ucll.feedback;

import be.ucll.feedback.model.Feedback;

public class FeedbackBuilder {

    private String name;
    private String feedback;

    private FeedbackBuilder () {
    }

    public static FeedbackBuilder aFeedback () {
        return new FeedbackBuilder();
    }

    public static FeedbackBuilder anOKFeedback () {
        return aFeedback().withName("Elke").withFeedback("OK well done!!!");
    }

    public FeedbackBuilder withName (String name) {
        this.name = name;
        return this;
    }

    public FeedbackBuilder withFeedback (String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Feedback build() {
        Feedback feedback = new Feedback();
        feedback.setName(name);
        feedback.setFeedback(this.feedback);
        return feedback;
    }
}
