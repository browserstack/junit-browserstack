# junit-browserstack
[JUnit](http://junit.org/junit4/) Integration with BrowserStack.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780) 

![JUnit](http://junit.org/junit4/images/junit-logo.png)

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Update *.conf.json files inside the `src/test/resources/conf` directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings). 

## Running your tests
* To run a single test, run `mvn test -P single`
* To run parallel tests, run `mvn test -P parallel`
* To run local tests, run `mvn test -P local`

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* To test on a different set of browsers, check out our [Platform Configurator](https://www.browserstack.com/automate/python#setting-os-and-browser)
* You can export the environment variables for the Username and Access Key of your BrowserStack account. 
  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

## Resources
* [Documentation for writing automate test scripts in Java on BrowserStack](https://www.browserstack.com/automate/java)
* [Customize your tests on BrowserStack Automate](https://www.browserstack.com/automate/capabilities)
* [Browsers & Mobile Devices for Selenium Testing on BrowserStack](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) 
