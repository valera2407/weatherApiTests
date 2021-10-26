package specs.daily

import http.HTTPCreator
import spock.lang.Specification

class DailyCountSpec extends Specification {

    def "Check that response can have different size"() {
        given: "set base URI, geographic coordinate and list size"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [lon: longitude, lat: latitude, cnt: count, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequestJSON('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Check that we have right city name and list size"
        assert httpResponse.city.name == cityName
        assert httpResponse.cnt == size
        assert httpResponse.list.size == size

        where:
        latitude | longitude  || cityName
        48.8714  | 2.2293     || 'Suresnes'
        15.8395  | 120.496696 || 'Anulid'
        -4.06667 | 37.73333   || 'Same'
        count << [16, 1, 9]
        size << [16, 1, 9]
    }

    def "Check that response can`t have size smaller that 1 and bigger that 17"() {
        given: "set base URI, geographic coordinate and list size"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [lon: longitude, lat: latitude, cnt: count, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequestJSON('/data/2.5/forecast/daily', query)

        then: "Check that response have status cod 400"
        assert httpResponse.cod == '400'

        and: "Check that response message is 'cnt from 1 to 17'"
        assert httpResponse.message == 'cnt from 1 to 17'

        where:
        latitude | longitude
        48.8714  | 2.2293
        15.8395  | 120.496696

        count << [18, 0]
    }
}
