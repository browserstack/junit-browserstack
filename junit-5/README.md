##  junit5-browserstack
Master branch contains **Selenium 3** samples, for **Selenium 4 - W3C protocol** please checkout [selenium-4](https://github.com/nithyamn/junit5-browserstack/tree/selenium-4) branch
</br>
<a href="https://browserstack.com"> <img src="https://avatars.githubusercontent.com/u/1119453?s=200&v=4" width="40" height="40"> </a>
<a href="https://junit.org/junit5/"><img src="https://camo.githubusercontent.com/abbaedce4b226ea68b0fd43521472b0b146d5ed57956116f69752f43e7ddd7d8/68747470733a2f2f6a756e69742e6f72672f6a756e6974352f6173736574732f696d672f6a756e6974352d6c6f676f2e706e67" width="40" height="40" ></a>

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Update credentials in the `/src/test/resources/caps.json` file with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).
* The platform details can be modified in the `/src/test/resources/caps.json` file within the respective profile i.e. `single`, `local`, `parallel`. Refer to our [Capabilities Generator](https://www.browserstack.com/automate/capabilities) page for all platform and capabilities related information.
* For parallel testing, control the concurrency by setting the value for `parallel. count`. Junit 5 uses the following properties for parallelism:
  ```
  junit.jupiter.execution.parallel.enabled = true
  junit.jupiter.execution.parallel.mode.default = concurrent
  junit.jupiter.execution.parallel.config.strategy=fixed
  junit.jupiter.execution.parallel.config.fixed.parallelism=${parallel.count}
  ```
## Running your tests
* To run a single test, run `mvn test -P single`
* To run local tests, run `mvn test -P local`
* To run parallel tests, run `mvn test -P parallel`

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
## Additional Resources
* [Documentation for writing Automate test scripts in Java](https://www.browserstack.com/automate/java)
* [Customizing your tests on BrowserStack](https://www.browserstack.com/automate/capabilities)
* [Browsers & mobile devices for selenium testing on BrowserStack](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate)
* [Using REST API to access information about your tests via the command-line interface](https://www.browserstack.com/automate/rest-api)