
import apigee.*
import wslite.rest.*
import wslite.http.*
import wslite.http.auth.*

println "Reading and loading gradle.properties..."
props = new java.util.Properties()
props.load(new File('gradle.properties').newReader())
config = new ConfigSlurper().parse(props)

println "Creating Apigee Profile with gradle.properties..."
profile = new ApigeeProfile(
        hostURL: config.proxyHostURL,
        orgName: config.proxyOrgName,
        envName: config.proxyEnvName,
        application: config.proxyApplication,
        apiVersion: config.proxyApiVersion,
        projectVersion: config.proxyProjectVersion,
        username: username,
        password: password,
        buildDirectory: config.proxyBuildDirectory,
        baseDirectory: System.properties.'user.dir',
        apiProxySource: config.proxyApiProxySource,
        revision: config.proxyRevision,
        apiProxyEndpointProtocol: config.proxyEndpointProtocol,
)

println "Creating Apigee Objects..."
apigee = new Apigee(profile: profile)

println "Creating Starter Params for RESTClient requests"
params = [:]
params.query = [:]
params.headers = [:]
params.headers.Accept = ContentType.JSON.acceptHeader

println "Apigee Api Proxy shell ready."

