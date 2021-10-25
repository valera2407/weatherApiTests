package specs.daily

import http.HTTPCreator
import spock.lang.Specification

class DaileBaseNegativeSpec extends Specification {

    def "Check daily forecast with isn`t right city name"() {
        given: "set base URI, city name and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [q: city, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 404"
        assert httpResponse.cod == '404'

        and: "Check that response message is 'city not found'"
        assert httpResponse.message == 'city not found'

        where:
        city << ['Lonkon', 'Kharciv', 'Xerson']
    }

    def "Check daily forecast with isn`t right city ID"() {
        given: "set base URI, city ID and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [id: cityId, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 404"
        assert httpResponse.cod == '404'

        and: "Check that response message is 'city not found'"
        assert httpResponse.message == 'city not found'

        where:
        cityId || _
        256    || _
        1      || _
        95     || _
    }

    def "Check daily forecast with isn`t right city ZIP code"() {
        given: "set base URI, city ZIP code and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [zip: cityZip, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 404"
        assert httpResponse.cod == '404'

        and: "Check that response message is 'city not found'"
        assert httpResponse.message == 'city not found'

        where:
        cityZip << [605, 9258473, 1478]
    }

    def "Check daily forecast with isn`t right city geographic coordinate"() {
        given: "set base URI, "
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [lon: longitude, lat: latitude, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 400"
        assert httpResponse.cod == '400'

        and:
        "Check that response message is ${errorMessage}"
        assert httpResponse.message == errorMessage

        where:
        latitude | longitude || errorMessage
        488      | 293       || 'wrong latitude'
        15.8395  | 1204      || 'wrong longitude'
        -406     | 37.7      || 'wrong latitude'
    }
}
