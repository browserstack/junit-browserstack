# junit-browserstack
[JUnit](http://junit.org/junit4/) Integration with BrowserStack.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780) 

![JUnit](http://junit.org/junit4/images/junit-logo.png)

## Using Maven

### Setup
* Clone the repo
* Install dependencies `mvn install`
* Update `browserstack.yml` files inside the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings). 

### Running your tests
* To run a parallel test, run `mvn test -P sample`
* To run local tests, set `browserStackLocal: true` in `browserstack.yml` and  run `mvn test -P local`

 Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)


## Using Gradle

### Prerequisites
- If using Gradle, Java v9+ is required.

### Setup
* Clone the repo
* Update `browserstack.yml` files inside the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).

### Running your tests
* To run a parallel test, run `gradle sampleTest`
* To run local tests, set `browserStackLocal: true` in `browserstack.yml` and  run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Following are the changes required in `build.gradle` -
  * Add `id 'com.browserstack.gradle-sdk' version "1.0.0"` in plugins
  * Add `implementation 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies
  * Fetch Artifact Information and add `jvmArgs` property in tasks *Sample* and *Local* :
  ```
  def browserstackSDKArtifact = configurations.compileClasspath.resolvedConfiguration.resolvedArtifacts.find { it.name == 'browserstack-java-sdk' }
  
  task sampleTest(type: Test) {
    useJUnit() {
        dependsOn cleanTest
        include '**/*BStackSampleTest.*'
        jvmArgs += "-javaagent:${browserstackSDKArtifact.file}"
    }
  }
  ```

* Install dependencies and run the test `gradle sample`

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* To test on a different set of browsers, check out our [platform configurator](https://www.browserstack.com/automate/java#setting-os-and-browser)
* You can export the environment variables for the Username and Access Key of your BrowserStack account. 

  * For Unix-like or Mac machines:
  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  * For Windows:
  ```
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
