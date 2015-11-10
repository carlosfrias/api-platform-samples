package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient

class TokenGenSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'v1/datastore/token',
                apiVersion: 'v1',
                projectVersion: '1.0.0-SNAPSHOT',
//                username: System.properties.username,
                username: System.properties.apikey,
//                password: System.properties.password,
                password: System.properties.apisecret,
                buildDirectory: 'build',
                baseDirectory: System.properties.'user.dir',
                revision: 1,
                apiProxySource: 'apiproxy',
                apiProxyEndpointProtocol: 'http',
                apiKey: System.properties.apikey,
                apiSecret: System.properties.apisecret,
                clientId: System.properties.clientid,
                clientSecret: System.properties.clientsecret
        )
        apigee = new Apigee(profile: profile)
    }

    def "validate how the datastore proxy is invoked"() {
        when:
        def response
        apigee.with {
            def params = [:]
            params.headers = [:]
            params.headers.auth_client_id = profile.clientId
            params.headers.Accept = "${ContentType.JSON}"


            def restClient = authorization(new RESTClient(profile.appServiceEndpointURL))
            response = restClient.post(params) {
                type ContentType.JSON
                json grant_type: 'password', username: System.properties.username, password: System.properties.password
            }
        }

        then:
        response.statusCode == 200
        response.json.access_token
        response.json.expiresIn
        response.json.user

    }
}