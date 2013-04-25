myrrix-example
==============

example of building a myrrix recommender with a rescorer

# Compiling

 - run `mvn clean install` from project root
 - `myrrix-web/src/main/webapp/WEB-INF/web.xml` points to the rescorer to use

# Running

 - deploy `myrrix-web/target/myrrix-web.war` on servlet container (e.g. Tomcat)
 - pass rescoring parameters in the following format: `<item_id1>:<score1>,<item_id2>:<score2>,...`
 - be careful to correctly urlencode your parameter before sending it to the myrrix serving layer
 - only the first `rescorerParams` argument will be used
