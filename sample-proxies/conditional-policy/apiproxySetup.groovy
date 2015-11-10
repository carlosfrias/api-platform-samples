import apigee.*
import wslite.rest.*
import wslite.http.*
import wslite.http.auth.*

profile = new ApigeeProfile(
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
        apiProxySource: 'apiproxy',
        revision: '1'
)
apigee = new Apigee(profile: profile)
apiProxy = apigee.apiProxyService
