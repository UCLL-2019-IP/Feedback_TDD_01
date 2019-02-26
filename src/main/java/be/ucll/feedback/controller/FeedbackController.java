package be.ucll.feedback.controller;

import be.ucll.feedback.model.Feedback;
import be.ucll.feedback.model.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello from Springboot! By Rudy";
    }

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedback() {
        return feedbackService.findAll();
    }

    @PostMapping("/feedbacks/add")
    public List<Feedback> createNewFeedback(@RequestBody @Valid Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                throw new IllegalArgumentException(error.getDefaultMessage());
            }
        }
        feedbackService.add(feedback);
        return feedbackService.findAll();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String userError (Exception exc) {
        return exc.getMessage();
    }

}


