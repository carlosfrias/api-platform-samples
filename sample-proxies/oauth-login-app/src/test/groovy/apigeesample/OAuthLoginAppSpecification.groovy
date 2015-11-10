package apigeesample

import apigee.ApiProxyService
import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import wslite.rest.RESTClient

class OAuthLoginAppSpecification extends Specification {

    ApiProxyService proxy

    def serviceUrl

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'oauth-login-app',
                apiVersion: 'v1',
                projectVersion: '1.0.0-SNAPSHOT',
                username: 'carlos.frias.01@gmail.com',
                password: 'P1zr5pDxhzhu',
                buildDirectory: 'build',
                baseDirectory: System.properties.'user.dir',
                revision: 1,
                apiProxySource: 'apiproxy'
        )
        def apigee = new Apigee(profile: profile)
        proxy = apigee.apiProxyService
        serviceUrl = profile.apiProxyEndpointURL
    }

    def "validate that the samplelogingpage on oauth-login-app can be invoked with response_type code"() {
        when:
        def params = [:]
        params.path = "${profile.apiProxyEndpointURI}/samplelogingpage"
        params.query = [:]
        params.query.client_id = 'flpYG832CABJSMRH7AXR8IGrJLhBNYbi'
        params.query.response_type = 'code'
        params.query.scope = 'code_scope'
        def response = new RESTClient(profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('html')
        def data = new String(response.data)
        data.contains('Sample Login Page for User Consent')
        data.contains(params.query.client_id)
        data.contains(params.query.response_type)
        data.contains(params.query.scope)
    }

    def "validate that the samplelogingpage on oauth-login-app can be invoked with response_type token"() {
        when:
        def params = [:]
        params.path = "${profile.apiProxyEndpointURI}/samplelogingpage"
        params.query = [:]
        params.query.client_id = 'flpYG832CABJSMRH7AXR8IGrJLhBNYbi'
        params.query.response_type = 'token'
        params.query.scope = 'token_scope'
        def response = new RESTClient(profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('html')
        def data = new String(response.data)
        data.contains('Sample Login Page for User Consent')
        data.contains(params.query.client_id)
        data.contains(params.query.response_type)
        data.contains(params.query.scope)
    }

    def "validate /autohrizationcode"() {
//        when:
//        def
    }
}