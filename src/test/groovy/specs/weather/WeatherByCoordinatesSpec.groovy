package specs.weather

import http.HTTPCreator
import spock.lang.Specification

class WeatherByCoordinatesSpec extends Specification {

    def "check weather by geographic coordinates"() {

        given: "set base URI"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')

        when: "set path and latitude, longitude, unit type, count and API key"
        def httpResponse = httpRequest.getRequestJSON('/data/2.5/forecast',
                [lat: latitude, lon: longitude, units: unitsType, cnt: 1, APPID: '187c05111c48c6e4033a664f5951aece'])

        then: "check that geographic coordinates corresponds to city name and response come back with different unit type"
        assert httpResponse.city.name == cityName
        assert httpResponse.list.main.temp_max == tempMax

        where:
        latitude  | longitude | unitsType
        46.655281 | 35.151669 | 'metric'
        48.748379 | 30.22184  | 'imperial'
        54.339008 | -1.43243  | 'metric'

        cityName << ['Volodymyrivka', 'Uman\'', 'Northallerton']
        tempMax << [[9.75], [46.26], [10.51]]
    }
}
