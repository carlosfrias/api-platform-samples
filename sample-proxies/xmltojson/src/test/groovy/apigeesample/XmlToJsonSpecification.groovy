import apigee.Apigee
import apigee.ApigeeProfile
import spock.lang.Specification
import wslite.rest.RESTClient

class XmlToJsonSpecification extends Specification {

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
        apigee.apiProxyService.lastRevision
    }

    def "validate content is json"() {
        given:
        serviceUrl
        def restClient = new RESTClient(serviceUrl)
        def params = [:]
        params.query = [:]
        params.query.'q' = '32277'

        when:
        def resp = restClient.get(params)

        then:
        resp
        resp.statusCode == 200
        resp.contentType.contains('json')
    }
}