package api.weather

import spock.lang.Specification

class WeatherByCityIdSpec extends Specification {

    def "check weather by city ID"() {

        given:
        def httpReqest = new HTTPCreator('https://api.openweathermap.org')

        when:
        def httpResponse = httpReqest.getRequest('/data/2.5/forecast', [id: cityID, mode: contentType, APPID: '187c05111c48c6e4033a664f5951aece'])

        then:
        assert httpReqest.header.'Content-Type' == contentTypeResponse
        if (contentType == 'json')
            assert httpResponse.city.name == cityName
        else if (contentType == 'xml') {
            assert httpResponse.location.name == cityName
        }

        where:
        cityID << [2878234, 3105789, 688157, 687345]
        contentType << ['json', 'xml', 'xml', 'json']
        cityName << ['Leverkusen', 'Vilecha', 'Omelyaniv', 'Zelenyy Hay']
        contentTypeResponse << ['application/json; charset=utf-8', 'application/xml; charset=utf-8', 'application/xml; charset=utf-8', 'application/json; charset=utf-8']
    }
}
