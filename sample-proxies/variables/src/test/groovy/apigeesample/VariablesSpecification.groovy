package apigeesample

import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import spock.lang.Unroll
import wslite.rest.RESTClient

class VariablesSpecification extends Specification {


    Apigee apigee

    def serviceUrl = 'http://carlosfrias-test.apigee.net/xmltojson'

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'xmltojson',
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

    @Unroll
    def "validate that header #expectedHeader has been populated"() {
        when:
        def params = [:]
        params.query = [:]
        params.query.'q' = '32277'
        def restClient = new RESTClient(serviceUrl)
        def response = restClient.get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('json')
        response.headers.size() >
                response.headers[expectedHeader]

        where:
        expectedHeader                     | _
        'system.timestamp'                 | _
        'system.time'                      | _
        'organization.name'                | _
        'apiproxy.name'                    | _
        'apiproxy.revision'                | _
        'proxy.basepath'                   | _
        'proxy.name'                       | _
        'proxy.pathsuffix'                 | _
        'message.headers.count'            | _
        'message.headers.names'            | _
        'client.ip'                        | _
        'request.uri'                      | _
        'request.headers.name'             | _
        'request.header.user_agent.values' | _
        'request.path'                     | _
        'request.querystring'              | _
        'request.queryparams.names'        | _
        'request.queryparams.w'            | _
        'request.verb'                     | _
        'target.url'                       | _
        'target.host'                      | _
        'target.ip'                        | _
        'weather.location'                 | _
        'weather.condition'                | _
        'weather.forecast_today'           | _
        'weather.forecast_tomorrow'        | _
        'weather.description'              | _
        'weather.humidity'                 | _
    }
}