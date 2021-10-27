package specs.weather

import http.HTTPCreator
import spock.lang.Specification

class TestOtherParametersSpec extends Specification {

    def "check  cnt, units, lang parameters"() {

        given: "set base URI, city name, language,list size and API key"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org', 'json')
        def mapParm = [q: city, lang: lan, cnt: listSize, units: unitsType, APPID: '187c05111c48c6e4033a664f5951aece']

        when: "Sent request"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast', mapParm)

        then: "check that response have different unit type, language and count"
        assert httpResponse.list.weather.get(0).description == weather
        assert httpResponse.list.wind.get(0).speed == windSpeed
        assert httpResponse.cnt == count

        where:
        city       | lan  | listSize | unitsType  || weather          | windSpeed | count
        'Neyland'  | 'fr' | 3        | 'metric'   || ['légère pluie'] | 11.74     | 3
        'Terni'    | 'sl' | 1        | 'imperial' || ['jasno']        | 6.87      | 1
        'Dedenëvo' | 'ru' | 2        | 'metric'   || ['пасмурно']     | 6.07      | 2
    }

}
