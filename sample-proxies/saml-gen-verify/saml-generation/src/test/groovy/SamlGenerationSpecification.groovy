package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient

class SamlGenerationSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: System.properties.proxyHostURL,
                orgName: System.properties.proxyOrgName,
                envName: System.properties.proxyEnvName,
                application: System.properties.proxyApplication,
                apiVersion: System.properties.proxyApiVersion,
                projectVersion: System.properties.proxyProjectVersion,
                username: System.properties.proxyUsername,
                password: System.properties.proxyPassword,
                buildDirectory: System.properties.proxyBuildDirectory,
                baseDirectory: System.properties.'user.dir',
                revision: System.properties.proxyRevision,
                apiProxySource: System.properties.proxyApiProxySource,
                apiProxyEndpointProtocol: System.properties.proxyEndpointProtocol,
                apiKey: System.properties.proxyApiKey,
                apiSecret: System.properties.proxyApiSecret,
        )
        apigee = new Apigee(profile: profile)
    }

    def "validate that proxy can be invoked"() {
        given:
        def params = [:]
        params.headers = [:]
        params.headers.Accept = ContentType.JSON.acceptHeader
        def rest = new RESTClient(apigee.profile.appServiceEndpointURL)

        when:
        def response = rest.post(params) {
            type ContentType.XML
            xml {
                envelope {
                    security
                    header
                }
            }
        }

        then:
        response.statusCode == 200
        response.contentType.contains('json')

        // Other possible attributes to examine
        response.json.access_token
        response.json.expiresIn
        response.json.user
    }

}