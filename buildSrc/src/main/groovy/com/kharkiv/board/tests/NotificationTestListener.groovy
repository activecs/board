package com.kharkiv.board.tests

import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.Project;
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestResult

class NotificationTestListener implements TestListener {
	
	final Project project;
	
	NotificationTestListener(Project project) {
		this.project = project
	}
	
	@Override
	void afterSuite(TestDescriptor suite, TestResult result) {
		if(result.getTestCount() > 0) {
			long elapsedTestTime = result.getEndTime() - result.getStartTime();
			project.logger.debug("Elapsed time for execution of ${suite.getName()} ${result.getTestCount()} test(s): $elapsedTestTime ms");
		}
	}
	
	@Override
	void afterTest(TestDescriptor testDescriptor, TestResult result) {

	}
	
	@Override
	void beforeSuite(TestDescriptor suite) {

	}
	
	@Override
	void beforeTest(TestDescriptor testDescriptor) {

	}
}