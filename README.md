Data Synthesis Workshop
=======================

This repository is a result of the final project in "Data Synthesis Representation" course.

- Given at Tel-Aviv University in the fall semester of 2013
- Submission by Yevgeny Levanzov, Eli Polonsky and Daniel Samuelov

It consists out of 3 projects:

* 1. [relc](relc) - Compiles functional dependencies and decomposition graph into Java Code.
* 2. [relc-bridge](relc-bridge) - Compiles the output of the relc into bytecode and provides an object oriented API.
* 3. [relwiki](relwiki) - An example application using the relc-bridge.

![UML](uml.png)

### Requirements ###

In order to run/develop this repository you will need the following tools:

- Git - A distributed version control system. [(download)](http://git-scm.com/)
- Maven - A build and dependency management tool for Java projects. [(download)](http://maven.apache.org/)
- Java JDK version 7+ [(download)](http://www.oracle.com/technetwork/java/javase/downloads/index.html).  
be sure to download the JDK, and not the JRE.
- JavaEE enabled webserver. tomcat should do it. [(download)](http://tomcat.apache.org/download-60.cgi)

### Running unit tests ###

First you need to clone this repo to your local file system.

    git clone https://github.com/iliapolo/sadna-data-synthesis.git

Each project has its own unit tests. for example to run the rel-bridge unit tests use:

    cd relc-bridge
    mvn clean test
    
### Running the wiki app ###

    cd relwiki
    mvn package
  
this will create a relwiki.war file in relwiki/target. take this war and deploy it on your webserver.




