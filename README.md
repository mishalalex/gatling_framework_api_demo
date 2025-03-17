Gatling Demostore Api Project - Java
=====================================
What is this repository?
------------------------
This repository contains code created to showcase a gatling framework created to test a REST api service. The project is built using maven.

How to run?
-----------
Go to terminal, reach the project root folder and execute either one of the following commands:
1. "mvn gatling:test" -> if you just want to run with default configurations 
or 
2. "mvn gatling:test -DUSERS={enter-number-of-users} -DRAMP_DURATION={enter-ramp-duration} -DDURATION={enter-test-duration}"
-> if you want to toggle the variables to suite each test requirement
