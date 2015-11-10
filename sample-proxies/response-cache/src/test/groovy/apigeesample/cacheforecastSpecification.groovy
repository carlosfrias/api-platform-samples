package apigeesample

import apigee.Apigee
import apigee.*
import spock.lang.Ignore
import spock.lang.Specification
import wslite.rest.RESTClient

class CacheforecastSpecification extends Specification {

    Apigee apigee

    def setup() {
        def profile = new ApigeeProfile(
                hostURL: 'https://api.enterprise.apigee.com',
                orgName: 'carlosfrias',
                envName: 'test',
                application: 'cacheforecast',
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

    def "validate that /cacheforecast can be invoked with w only"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.query = [:]
        params.query.w = '32277'
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.headers.cache_hit
        response.headers.cache_name
        response.headers.cache_key
        response.headers.cache_invalidentry
    }

    def "validate /cacheforest header skip-lookup set"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.query = [:]
        params.query.w = '32277'
        params.headers = [:]
        params.headers['skip-lookup'] = "true"
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.headers.cache_hit == 'false'
        !response.headers.cache_name
        response.headers.cache_key
        response.headers.cache_invalidentry == 'false'
    }

    def "validate /cacheforest header skip-population set"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.query = [:]
        params.query.w = '32277'
        params.headers = [:]
        params.headers['skip-population'] = "true"
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.headers.cache_hit
        response.headers.cache_name
        response.headers.cache_key
        response.headers.cache_invalidentry
    }


    def "validate /cacheforest header by-pass set"() {
        when:
        def params = [:]
        params.path = "${apigee.profile.apiProxyEndpointURI}"
        params.query = [:]
        params.query.w = '32277'
        params.headers = [:]
        params.headers['by-pass'] = "true"
        def response = new RESTClient(apigee.profile.apiProxyEndpointURL).get(params)

        then:
        response.statusCode == 200
        response.contentType.contains('xml')
        response.headers.cache_hit
        response.headers.cache_name
        response.headers.cache_key
        response.headers.cache_invalidentry
    }

}