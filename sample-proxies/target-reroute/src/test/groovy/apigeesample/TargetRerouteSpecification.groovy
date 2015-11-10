package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import spock.lang.Unroll
import wslite.rest.RESTClient

class TargetRerouteSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'WOEID',
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

    def "validate that /WOEID returns yahoo weather"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}/32277"
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.xml.channel.title.text().contains('Pontardawe')

    }

}