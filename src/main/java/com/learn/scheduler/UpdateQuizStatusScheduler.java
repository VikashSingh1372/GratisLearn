package com.learn.scheduler;
import com.learn.Models.Quiz;
import com.learn.Models.QuizStatus;
import com.learn.Models.Quizsubject;
import com.learn.Repository.QuizRepository;
import com.learn.Repository.QuizSubjectRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Component
public class UpdateQuizStatusScheduler {

	@Autowired
	private QuizSubjectRepo quizRepo;

	@Scheduled(cron ="@daily", zone = "Asia/Kolkata")
	public void updateQuizStatus() {

		// find All quizzes
		List<Quizsubject> quizList = quizRepo.findAll();
		List<Quizsubject> updatedQuizList = new ArrayList<>();
		Date currDate = new Date();

		if (!CollectionUtils.isEmpty(quizList)) {
			for (Quizsubject quiz : quizList) {
				if (Objects.nonNull(quiz.getStartDate()) && Objects.nonNull(quiz.getEndDate())) {
					// Set quizStatus based on startDate and endDate and currDate
					if (currDate.after(quiz.getStartDate()) && currDate.before(quiz.getEndDate()))
						quiz.setQuizStatus(QuizStatus.ACTIVE);
					else if (currDate.before(quiz.getStartDate())) {
						quiz.setQuizStatus(QuizStatus.INACTIVE);
					} else if (currDate.after(quiz.getEndDate())) {
						quiz.setQuizStatus(QuizStatus.FINISHED);
					}

					updatedQuizList.add(quiz);
				}
			}

			if (updatedQuizList.size() > 0) {
				quizRepo.saveAll(updatedQuizList);
			}
		}

	}

}
