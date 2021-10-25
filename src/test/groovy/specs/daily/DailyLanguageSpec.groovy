package specs.daily

import http.HTTPCreator
import spock.lang.Specification

class DailyLanguageSpec extends Specification {

    def "Verify language response"() {
        given: "Set base URI, language and city ID"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [id: cityId, lang: language, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that city name have ${language} language"
        assert httpResponse.city.name == cityName

        where:
        cityId  | language || cityName
        2643743 | 'ar'     || 'لندن'
        524901  | 'de'     || 'Moskau'
        5128581 | 'ru'     || 'Нью-Йорк'
    }

    def "Verify language ressponse"() {
        given: "Set base URI, language and city ID"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')
        def query = [id: cityId, lang: language, APPID: 'b2ce5b9466a4cdcec5e7a6bf11465c5a']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast/daily', query)

        then: "Check that response have status code 200"
        assert httpResponse.cod == '200'

        and: "Verify that city name have not ${language} language"
        assert httpResponse.city.name !== cityName

        where:
        cityId  | language || cityName
        2643743 | 'ar'     || 'London'
        524901  | 'de'     || 'Moskow'
        5128581 | 'ru'     || 'New York'
    }
}
