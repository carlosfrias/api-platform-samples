package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import spock.lang.Unroll
import wslite.rest.RESTClient

class ConditionalPolicySpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'timer',
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

    @Unroll
    def "validate that /timer returns the #header header"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.headers = [:]
        params.headers.responsetime = 'true'
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.headers[header]

        where:
        header                         | _
        'X-Apigee-target'              | _
        'X-Apigee-start-time'          | _
        'X-Apigee-start-timestamp'     | _
        'X-Apigee-end-time'            | _
        'X-Apigee-end-timestamp'       | _
        'X-Apigee-target-responseTime' | _
    }

}