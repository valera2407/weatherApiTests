package http

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class HTTPCreator {

    def httpBase
    def header
    def bodyResponse
    def contType


    HTTPCreator(httpRequest, contentType) {
        httpBase = httpRequest
        switch (contentType){
            case 'json':
                contType = ContentType.JSON
                break
            case 'xml':
                contType = ContentType.XML
                break
            default:
                println 'Please use content type JSON or XML'
        }
    }

    def getRequest(pathRequest, queryRequest) {
        def http = new HTTPBuilder(httpBase)
        http.request(Method.GET, contType) {
            uri.path = pathRequest
            uri.query = queryRequest

            response.success = {
                resp, body ->
                    header = resp.headers
                    bodyResponse = body
            }

            response.failure = {
                resp, body ->
                    header = resp.headers
                    bodyResponse = body
            }
        }
    }
}
