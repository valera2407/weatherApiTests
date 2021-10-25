package specs.weather

import http.HTTPCreator
import spock.lang.Specification

class WeatherByCityNameSpec extends Specification {

    def "search weather by city name"() {

        given: "set base URI"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')

        when: "set path and city name, response language, count and API key"
        def httpResponse = httpRequest.getRequestJSON('/data/2.5/forecast', [q: city, lang: lan, cnt: listSize, APPID: '187c05111c48c6e4033a664f5951aece'])

        then: "check that response have different count and it come with another language"
        assert httpResponse.list.weather.get(0).description == weather
        assert httpResponse.list.size == numberOfDays

        where:
        city           | lan  | listSize || weather                   | numberOfDays
        'London'       | 'en' | 1        || ['broken clouds']            | 1
        'Kharkiv'      | 'ru' | 3        || ['переменная облачность'] | 3
        'Rechtenstein' | 'de' | 2        || ['Bedeckt']               | 2
    }
}
