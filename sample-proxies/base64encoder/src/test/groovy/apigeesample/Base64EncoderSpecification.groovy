package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import spock.lang.Unroll
import wslite.rest.RESTClient

class Base64EncoderSpecification extends Specification {

    Apigee apigee

    def serviceUrl = 'https://carlosfrias-test.apigee.net/base64encoder'

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'base64encoder',
                apiVersion: 'v1',
                projectVersion: '1.0.0-SNAPSHOT',
                username: 'carlos.frias.01@gmail.com',
                password: 'P1zr5pDxhzhu',
                buildDirectory: 'build',
                baseDirectory: System.properties.'user.dir',
                revision: 1,
                apiProxySource: 'apiproxy',
                apiProxyEndpointProtocol: 'https'
        )
        apigee = new Apigee(profile: profile)
    }

    def "validate that X-Encoded-Credentials header is returned"() {
        when:
        def params = [:]
        params.query = [:]
        params.query.username = apigee.profile.username
        params.query.password = apigee.profile.password
        def restClient = new RESTClient(apigee.profile.apiProxyEndpointURL)
        def response = restClient.get(params)

        then:
        response.statusCode == 200
        response.headers['X-Encoded-Credentials']

    }
}