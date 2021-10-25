package specs.daily

import http.HTTPCreator
import spock.lang.Specification

class DailyAPISpeck extends Specification{

    def "Send request with right API key" () {
        given: "set base URI, city name and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [q: 'London', APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and:
        assert httpResponse.city.name == 'London'
    }

    def "Send request with isn`t right API key" () {
        given: "set base URI, city name and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [q: 'London', APPID: '187c05111c48c6e4033a664f5951aece']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 401"
        assert httpResponse.cod == 401

        and: "Check that response message is right"
        assert httpResponse.message == 'Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.'
    }
}
