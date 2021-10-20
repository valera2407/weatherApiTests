package api.weather

import spock.lang.Specification

class WeatherByCityNameSpec extends Specification {

    def "search weather by city name"() {

        given:
        def httpReqest = new HTTPCreator('https://api.openweathermap.org')

        when:
        def httpResponse = httpReqest.getRequest('/data/2.5/forecast', [q: city, lang: lan, cnt: listSize, APPID: '187c05111c48c6e4033a664f5951aece'])

        then:
        assert httpResponse.list.weather.get(0).description == clouds
        assert httpResponse.list.size == numberOfDays

        where:
        city           | lan  | listSize || clouds                  | numberOfDays
        'London'       | 'en' | 1        || ['light rain']          | 1
        'Kharkiv'      | 'ru' | 3        || ['ясно']                | 3
        'Rechtenstein' | 'de' | 2        || ['Überwiegend bewölkt'] | 2
    }
}
