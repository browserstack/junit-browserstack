##  junit5-browserstack
Master branch contains **Selenium 4 - W3C protocol** samples, for **Selenium 3** please checkout [selenium-3](https://github.com/nithyamn/junit5-browserstack/tree/selenium-3) branch
</br>
<a href="https://browserstack.com"><img src="https://avatars.githubusercontent.com/u/1119453?s=200&v=4" width="40" height="40"></a>
<a href="https://junit.org/junit5/"><img src="https://camo.githubusercontent.com/abbaedce4b226ea68b0fd43521472b0b146d5ed57956116f69752f43e7ddd7d8/68747470733a2f2f6a756e69742e6f72672f6a756e6974352f6173736574732f696d672f6a756e6974352d6c6f676f2e706e67" width="40" height="40" ></a>

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Update `browserstack.yml` files inside the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).

## Running your tests
* To run a single test, run `mvn test -P single`
* To run local tests, run `mvn test -P local`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

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

## Open Issues
* Value for `junit.jupiter.execution.parallel.config.fixed.parallelism` gets multiplied in selenium versions above `4.0.0-alpha-3`. 
  Github issue:
  * https://github.com/SeleniumHQ/selenium/issues/9359
  * https://github.com/SeleniumHQ/selenium/issues/10113
