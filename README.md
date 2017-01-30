# TestingExampleContacts

Contacts example using MVP arquitecture and testing.

Libraries used in this project are:
* Retrofit
* OkHttp
* Gigigo Interactor Executor Library
* Thread Decorated View
* Junit
* Mockito 
* Mock Webserver

To execute the test coverage we have to run this command: ./gradlew clean assembleDebug jacocoTestReport

When the task have finished, it will create new folders in build app module path. We have to open the index.html file in the directory build/reports/jacoco/jacocoTestDebugUnitTestReport/html 
