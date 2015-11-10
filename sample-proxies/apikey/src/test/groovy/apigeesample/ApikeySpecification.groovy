package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import wslite.rest.RESTClient

class ApikeySpecification extends Specification {

    Apigee apigee

    def serviceUrl

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'weatherapikey',
                apiVersion: 'v1',
                projectVersion: '1.0.0-SNAPSHOT',
                username: 'carlos.frias.01@gmail.com',
                password: 'P1zr5pDxhzhu',
                buildDirectory: 'build',
                baseDirectory: System.properties.'user.dir',
                revision: 1,
                apiProxySource: 'apiproxy'
        )
        apigee = new Apigee(profile: profile)
        serviceUrl = 'http://carlosfrias-test.apigee.net/xmltojson'
    }

    def "validate that weatherapikey can be invoked"() {
        when:
        def params = [:]
        params.query = [:]
        params.path =  apigee.profile.apiProxyEndpointURI
        params.query.q = '32277'
        params.query.apikey = 'flpYG832CABJSMRH7AXR8IGrJLhBNYbi'
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
    }
}