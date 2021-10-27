package specs.daily

import http.HTTPCreator
import spock.lang.Specification

class DailyBasePositiveSpec extends Specification {

    def "Check daily forecast by city name"() {
        given: "set base URI, city name and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org', 'json')
        def query = [q: city, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that response have right city name, time zone, population and country"
        assert httpResponse.city.name == cityResponse
        assert httpResponse.city.country == country
        assert httpResponse.city.population == population
        assert httpResponse.city.timezone == timezone

        where:
        city      || cityResponse | country | population | timezone
        'London'  || 'London'     | 'GB'    | 1000000    | 3600
        'Kharkiv' || 'Kharkiv'    | 'UA'    | 1430885    | 10800
        'Kherson' || 'Kherson'    | 'UA'    | 15000      | 10800
    }

    def "Check daily forecast by city ID"() {
        given: "set base URI, city ID and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org', 'json')
        def query = [id: cityId, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that response have right city name, time zone, country and population = 0"
        assert httpResponse.city.name == cityName
        assert httpResponse.city.country == country
        assert httpResponse.city.timezone == timezone
        assert httpResponse.city.population == 0

        where:
        cityId  || cityName              | country | timezone
        2641434 || 'Northam'             | 'GB'    | 3600
        5281031 || 'Sheffield'           | 'US'    | -14400
        6807148 || 'Kampung Alor Perang' | 'MY'    | 28800
    }

    def "Check daily forecast by city ZIP code"() {
        given: "set base URI, city ZIP code and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org', 'json')
        def query = [zip: cityZip, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that response have right city name, time zone, population = 0 and country is US"
        assert httpResponse.city.name == cityName
        assert httpResponse.city.timezone == timezone
        assert httpResponse.city.country == 'US'
        assert httpResponse.city.population == 0

        where:
        cityZip || cityName      | timezone
        90001   || 'Los Angeles' | -25200
        33124   || 'Miami'       | -14400
        62701   || 'Springfield' | -18000
    }

    def "Check daily forecast by city geographic coordinate"() {
        given: "set base URI,latitude and longitude "
        def httpRequest = new HTTPCreator('https://api.openweathermap.org', 'json')
        def query = [lon: longitude, lat: latitude, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that response have right city name, time zone, population and country"
        assert httpResponse.city.name == cityName
        assert httpResponse.city.country == country
        assert httpResponse.city.population == population || 44738
        assert httpResponse.city.timezone == timezone

        where:
        latitude | longitude  || cityName   | country | population | timezone
        48.8714  | 2.2293     || 'Suresnes' | 'FR'    | 44697      | 7200
        15.8395  | 120.496696 || 'Anulid'   | 'PH'    | 2177       | 28800
        -4.06667 | 37.73333   || 'Same'     | 'TZ'    | 17455      | 10800
    }
}
