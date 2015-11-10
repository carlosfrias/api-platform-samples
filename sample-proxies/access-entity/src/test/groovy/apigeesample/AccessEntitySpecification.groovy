package apigeesample

import apigee.Apigee
import apigee.*
import spock.lang.Ignore
import spock.lang.Specification
import wslite.rest.RESTClient

class AccessEntitySpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'access-entity',
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

    def "validate that /python returns the header X-Developer-emai"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.query = [:]
        params.query.apikey = 'flpYG832CABJSMRH7AXR8IGrJLhBNYbi'
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.headers.'X-Developer-email' =='carlos.frias.01@gmail.com'
    }

}