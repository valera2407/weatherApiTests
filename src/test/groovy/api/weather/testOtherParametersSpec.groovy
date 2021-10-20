package api.weather

import spock.lang.Specification

class testOtherParametersSpec extends Specification {

    def "check  cnt, units, lang parameters"() {

        given: "set base URI"
        def httpRequest = new HTTPCreator('https://api.openweathermap.org')

        when: "set path and city name, language,list size and API key"
        def httpResponse = httpRequest.getRequest('/data/2.5/forecast',
                [q: city, lang: lan, cnt: listSize, units: unitsType, APPID: '187c05111c48c6e4033a664f5951aece'])

        then: "check that response have different unit type, language and count"
        assert httpResponse.list.weather.get(0).description == weather
        assert httpResponse.list.wind.get(0).speed == windSpeed
        assert httpResponse.cnt == count
        assert httpResponse.list.dt_txt == answerTime

        where:
        city       | lan  | listSize | unitsType  || weather                 | windSpeed | count | answerTime
        'Neyland'  | 'fr' | 3        | 'metric'   || ['légère pluie']        | 6.14      | 3     | ['2021-10-20 15:00:00', '2021-10-20 18:00:00', '2021-10-20 21:00:00']
        'Terni'    | 'sl' | 1        | 'imperial' || ['pretrgana oblačnost'] | 8.81      | 1     | ['2021-10-20 15:00:00']
        'Dedenëvo' | 'ru' | 2        | 'metric'   || ['пасмурно']            | 7.7       | 2     | ['2021-10-20 15:00:00', '2021-10-20 18:00:00']
    }

}
