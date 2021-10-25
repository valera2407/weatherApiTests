package http

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.Method


class HTTPCreator {

    def httpBase
    def header
    def bodyResponse

    HTTPCreator(httpRequest) {
        httpBase = httpRequest
    }

    def getRequest(pathRequest, queryRequest) {
        def http = new HTTPBuilder(httpBase)
        http.request(Method.GET, ContentType.JSON) {

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
