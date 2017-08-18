# scala-questrade

[![Build Status](https://travis-ci.org/elcurto99/scala-questrade.svg?branch=master)](https://travis-ci.org/elcurto99/scala-questrade)[![Known Vulnerabilities](https://snyk.io/test/github/elcurto99/scala-questrade/badge.svg)](https://snyk.io/test/github/elcurto99/scala-questrade)

Scala REST client API for the Questrade trading platform [http://www.questrade.com/api/documentation](http://www.questrade.com/api/documentation)

## Code Example

**TODO**: Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

## Motivation

After searching around for a Java/Scala client adapter for the Questrade API it became apparent that one did not exist yet. While there were projects in Python<sup>[1](https://github.com/pcinat/QuestradeAPI_PythonWrapper)</sup><sup>[2](https://github.com/leanderlee/questrade)</sup>, Node.js<sup>[3](https://github.com/leanderlee/questrade)</sup> and Ruby<sup>[4](https://github.com/dalehamel/questrade_client)</sup>, I was not able to locate a Java/Scala based solution. This project exists to provide a Scala language client for the Questrade API, to enable **AKKA** and **Play** based projects.

## Features
This package currently includes the following features:

* Wrappers for all **Account calls**
* Error handling and logging

### TODO

* Wrappers for all **Market calls**
* Wrappers for all **Order calls**
* Streaming quotes
* OAuth 2.0 API requests via HTTPS (TLS)
* Automatically request new access tokens via refresh tokens when necessary

## Getting Started

### Prerequisites

#### Java 8 JDK

To check your version of Java, open the terminal and type:
```
java -version
```
If you don't have version 1.8 you can [download Java here](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### Scala 2.12.2

Install Scala, either by installing an IDE (such as [IntelliJ](https://www.jetbrains.com/idea/)), or Scala's build tool ([sbt](http://www.scala-sbt.org/download.html)).

See `https://www.scala-lang.org/download/2.12.2.html` for full details.

#### Questrade Practice Account

Set up a practice account to play around:

`http://www.questrade.com/api/free-practice-account`

Login with it at:

`https://practicelogin.questrade.com/Signin.aspx?ReturnUrl=%2fAPIAccess%2fUserApps.aspx`

Register a practice app (give it all permissions, this is a test account)

Generate a new device authorization token, you'll need that! It's considered a "refresh" token. After being used once, it's good no more, but you will get a new refresh token in the response.

### IDE Setup

#### IntelliJ IDEA

1. `git clone git@github.com:elcurto99/scala-questrade.git`
2. IntelliJ IDEA > File > New > Project from Existing Sources...
3. Open the directory we just checked out
4. **TODO**

## Running the tests

Navigate to the projects directory in a Terminal

```
cd ~/scala-questrade/
```

Run the tests from sbt

```
sbt test
```

The Unit tests will run

```
[info] Loading global plugins from /Users/username/.sbt/0.13/plugins
[info] Set current project to scalaquestrade (in build file:/Users/username/git/scala-questrade/)
[info] Compiling 1 Scala source to /Users/username/git/scala-questrade/target/scala-2.12/test-classes...
Enter your single use API Refresh Token: [info] QuestradeClientUnitTests:
[info] The Questrade API client
[info] - should authenticate with the server
[info] - should retrieve the current server time
[info] - should retrieve the accounts list
[info] - should retrieve the positions for an account
[info] - should retrieve the balances for an account
[info] - should retrieve the executions that occurred today for an account
[info] - should retrieve the executions that occurred from a date for an account
[info] - should retrieve the executions that occurred in a date range for an account
[info] - should retrieve the orders the occurred today for an account
[info] - should retrieve the orders that occurred from a date for an account
[info] - should retrieve the orders that occurred in a date range for an account
[info] - should retrieve the orders of a specific state for an account
[info] - should retrieve specific orders by orderId for an account
[info] - should retrieve a single order for an account
[info] - should retrieve the activities that occurred from a date for an account
[info] - should retrieve the activities that occurred in a date range for an account
[info] - should retrieve activities that occurred over the API maximum of 31 days for an account
```

Generate a new single use API refresh token in your Questrade Practice Account to enter into the console

```
AbCdefGhIjKlmnoPQrStUvwXYzOP1234567890
```

The integration tests will now run

```
[info] QuestradeClientIntegrationTests:
[info] The Questrade API client
[info] - should authenticate with the server
[info] - should retrieve the current server time
[info] - should retrieve the accounts list
[info] - should retrieve the positions for an account
[info] - should retrieve the balances for an account
[info] - should retrieve the executions that occurred today for an account
[info] - should retrieve the executions that occurred from a date for an account
[info] - should retrieve the executions that occurred in a date range for an account
[info] - should retrieve the orders the occurred today for an account
[info] - should retrieve the orders that occurred from a date for an account
[info] - should retrieve the orders that occurred in a date range for an account
[info] - should retrieve the orders of a specific state for an account
[info] - should retrieve specific orders by orderId for an account
[info] - should retrieve a single order for an account
[info] - should retrieve the activities that occurred from a date for an account
[info] - should retrieve the activities that occurred under the API maximum of 31 days for an account
[info] - should retrieve activities that occurred over the API maximum of 31 days for an account
[info] Run completed in 2 minutes, 33 seconds.
[info] Total number of tests run: 34
[info] Suites: completed 2, aborted 0
[info] Tests: succeeded 34, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 344 s, completed 18-Aug-2017 6:09:12 AM
```

## Deployment

**TODO**: Add additional notes about how to deploy this on a live system

## Built With

* [Scala](http://www.scala-lang.org/)
* [SBT](http://www.scala-sbt.org/)
* [scalaj-http](https://github.com/scalaj/scalaj-http)
* [http4s](http://http4s.org/)
* [Json4s](http://json4s.org/)
* [slf4j](https://www.slf4j.org/)
* [ScalaTest](http://www.scalatest.org/)

## Versioning

[SemVer](http://semver.org/) is used for versioning. For the versions available, see the [tags on this repository](https://github.com/elcurto99/scala-questrade/tags). 

## Authors

* **[Curtis Muller](https://github.com/elcurto99)**

See also the list of [contributors](https://github.com/elcurto99/scala-questrade/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Disclaimer

This software is not affiliated with Questrade in any way. Use at your own risk. Test all operations in a sandbox before executing them with a real account and real money. Don't be an idiot.

