# `treecalc-rt.jar` - TreeCalc Runtime in Java




## Build Instructions

Use maven. To build the `treecalc-rt.jar` issue the command:

    mvn package

That's it.

### JavaDocs

Issue the command:

    mvn javadoc:javadoc

Note, by default maven puts the help content under `target/site/apidocs`

You can change the output folder by using the plugin configuration
parameter `reportOutputDirectory`.

For more info, see the [Maven JavaDoc Plugin](http://maven.apache.org/plugins/maven-javadoc-plugin/).
