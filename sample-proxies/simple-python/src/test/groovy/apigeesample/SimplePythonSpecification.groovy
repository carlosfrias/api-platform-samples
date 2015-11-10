package apigeesample

import apigee.Apigee
import apigee.*
import spock.lang.Ignore
import spock.lang.Specification
import wslite.rest.RESTClient

class SimplePythonSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'python',
                apiVersion: 'v1',
                projectVersion: '1.0.0-SNAPSHOT',
                username: 'carlos.frias.01@gmail.com',
                password: 'P1zr5pDxhzhu',
                buildDirectory: 'build',
                baseDirectory: System.properties.'user.dir',
                revision: 1,
                apiProxySource: 'apiproxy',
                apiProxyEndpointProtocol: 'http'
        )
        apigee = new Apigee(profile: profile)
    }

    def "validate that /python returns the header X-Apigee-Demo-target"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.headers.'X-Apigee-Demo-target' =='http://weather.yahooapis.com/forecastrss?w=12797282'
    }

}