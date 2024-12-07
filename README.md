# Dobitnik

###  Major upgrade of the "Syndicate" project
- ##### Switch build from Maven to Gradle 
- ##### Upgrade Java from 11 to 21 
- ##### Move some tests into the "main" stream of code
### TODO:
- Refactor the testing side 
  - Remove Spock
  - Retain minimum number of integration tests
  - Implement a unit testing framework, preferably Mockito
- Audit the libraries used, remove the redundant ones
- figure out what to do with the following line in `build.gradle`:
  `//	implementation 'org.springframework.boot:spring-boot-starter-security'`




