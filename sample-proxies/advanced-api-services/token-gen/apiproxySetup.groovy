import apigee.*
import wslite.rest.*
import wslite.http.*
import wslite.http.auth.*

profile = new ApigeeProfile(
        hostURL: 'https://api.enterprise.apigee.com',
        orgName: 'carlosfrias',
        envName: 'test',
        application: 'v1/datastore/token',
        apiVersion: 'v1',
        projectVersion: '1.0.0-SNAPSHOT',
        username: username,
        password: password,
        buildDirectory: 'build',
        baseDirectory: System.properties.'user.dir',
        apiProxySource: 'apiproxy',
        revision: '1',
        apiProxyEndpointProtocol: 'http',
        clientId: System.properties.clientid,
        clientSecret: System.properties.clientsecret
)
apigee = new Apigee(profile: profile)

params = [:]
params.headers = [:]
params.headers.auth_client_id = apikey
params.headers.Accept = "${ContentType.JSON}"
