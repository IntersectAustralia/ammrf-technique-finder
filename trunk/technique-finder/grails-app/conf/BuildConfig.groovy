grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {        
        grailsPlugins()
        grailsHome()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenRepo "http://maven.intersect.org.au:8081/artifactory/libs-releases"
        mavenRepo "http://maven.intersect.org.au:8081/artifactory/plugins-releases"
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

         runtime 'mysql:mysql-connector-java:5.1.11'
    }
    
    plugin("searchable") {
        compile "org.compass-project:compass:2.1.4"
    }
}

//cobertura
coverage {
    // excluding plugins, GSP pages and generated code (controllers)
    // TODO: if generated controller is changed then remove it from exclusions
    exclusions = [
                  '**/org/grails/tomcat/**',
                  '**/org/grails/plugins/**',
                  '**/org/gualdi/grails/plugins/ckeditor/**',
		  '**/es/osoco/grails/plugins/transparentmessage/**',
                  '**/com/**',
                  '**/org/grails/**',
                  '**/*Config*',
                  'gsp_*',
                  'JQuery*',
                  '**/StaticContentController*',
                  '**/OptionCombinationController*',
                  '**/LoginController*',
                  '**/LogoutController*',
                  '**/AdminController*',
                  '**/Role*',
                  '**/CaseStudyController*',
                  '**/Searchable*',
                  '**/GrailsMelodyUtil*',
                  '**/CacheFilters*',
                  '**/CompassBugfixFilters*'
                  ]
}

// war plugin customization
// This closure is passed the location of the staging directory that
// is zipped up to make the WAR file, and the command line arguments.
grails.war.resources = { stagingDir, args ->
    def props = new Properties()
    new File("${stagingDir}/WEB-INF/classes/application.properties").withInputStream { 
       stream -> props.load(stream) 
    }
	def appVersion = props.get("app.version")
	def revision = new File("$grailsSettings.baseDir/revision.txt").getText()
    props.put("app.version", "$appVersion-$revision".toString())
    new File("${stagingDir}/WEB-INF/classes/application.properties").withOutputStream { 
       stream -> props.store(stream,"Modified for war deployment") 
    }
}

