myrrix-example
==============

example of building a myrrix recommender with a rescorer

# Compiling

 - run `mvn clean install` from project root
 - myrrix-web/src/main/webapp/WEB-INF/web.xml points to the rescorer to use

# Running

 - deploy myrrix-web/target/myrrix-web.war on servlet container (e.g. Tomcat)
