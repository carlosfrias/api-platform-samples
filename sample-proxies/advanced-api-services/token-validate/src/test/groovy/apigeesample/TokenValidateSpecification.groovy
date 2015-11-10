package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import spock.lang.Unroll
import wslite.rest.RESTClient

class TokenValidateSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'token-validate',
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
    }

    def "validate invocation of token validate"() {
        when:
        apigee.profile.apiProxyEndpointProtocol = 'http'
        def restClient = apigee.authorization(new RESTClient(apigee.profile.appServiceEndpointURL))
        def response = restClient.get()

        then:
        response.statusCode == 200

    }
}