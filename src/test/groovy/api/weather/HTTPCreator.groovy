package api.weather

import groovyx.net.http.HTTPBuilder

class HTTPCreator {

    def httpBase
    def header

    HTTPCreator(httpRequest) {
        httpBase = httpRequest
    }

    def getRequest(pathRequest, queryRequest) {
        def http = new HTTPBuilder(httpBase)
        def response = http.get(path: pathRequest, query: queryRequest) {
            resp, body ->
                header = resp.headers
                queryRequest = body
        }
        return response
    }

}
