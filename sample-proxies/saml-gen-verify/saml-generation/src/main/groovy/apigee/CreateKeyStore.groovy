/**
 * Created by carlosfrias on 11/9/15.
 */
//
//import apigee.*
//import apigee.security.*
//import wslite.rest.*
//import wslite.http.*
//import wslite.http.auth.*


//
//println "Reading and loading gradle.properties..."
//props = new java.util.Properties()
//props.load(new File('gradle.properties').newReader())
//config = new ConfigSlurper().parse(props)
//
//println "Creating Apigee Profile with gradle.properties..."
//profile = new ApigeeProfile(
//        hostURL: config.proxyHostURL,
//        orgName: config.proxyOrgName,
//        envName: config.proxyEnvName,
//        application: config.proxyApplication,
//        apiVersion: config.proxyApiVersion,
//        projectVersion: config.proxyProjectVersion,
//        username: config.proxyUsername,
//        password: config.proxyPassword,
//        buildDirectory: config.proxyBuildDirectory,
//        baseDirectory: System.properties.'user.dir',
//        apiProxySource: config.proxyApiProxySource,
//        revision: config.proxyRevision,
//        apiProxyEndpointProtocol: config.proxyEndpointProtocol,
//)
//
//println "Creating Apigee Objects..."
//apigee = new Apigee(profile: profile)
//
//println "Creating Starter Params for RESTClient requests"
//params = [:]
//params.path = "/environments/${profile.envName}/keystores"
//params.query = [:]
//params.headers = [:]
//params.headers.Accept = ContentType.JSON.acceptHeader
//
//rest = apigee.authorization(new RESTClient(profile.organizationURL))
//keystoreFilename = 'idpKeystore'
//resp = rest.post(params) {
//    type ContentType.XML
//    xml {
//        KeyStore(name: keystoreFilename)
//    }
//}
//
//if (resp.statusCode == 201 && resp.json.name == keystoreFilename) {
    certFile = new File('build/libs/saml-generation.jar')
    if (certFile.exists()) {
        params.headers.'Content-Type' = ContentType.URLENC.acceptHeader
        params.headers.Accept = ContentType.ANY.acceptHeader
//        params.headers.'Accept-Encoding' = 'gzip'
        params.headers.'Accept-Language' = 'en-US'
        params.query.password = 'password'
        params.query.alias = 'saml-gen'
        params.path = "${params.path}/${keystoreFilename}/keys"
        try {
            resp = rest.post(params) {
                type ContentType.URLENC
                bytes certFile.bytes
            }
        } catch (any) {
            resp = any
        }
    }
//}
