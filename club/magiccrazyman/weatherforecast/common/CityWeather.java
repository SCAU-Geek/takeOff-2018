package club.magiccrazyman.weatherforecast.common;

import club.magiccrazyman.weatherforecast.common.CityWeather.Weather.A.AC;
import club.magiccrazyman.weatherforecast.common.CityWeather.Weather.A.AD;
import club.magiccrazyman.weatherforecast.common.CityWeather.Weather.A.AE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;

/**
 *
 * @author Magic Crazy Man
 */
public class CityWeather {

    private String location;

    public CityWeather(String location) {
        this.location = location;
    }

    public Weather getWeather() throws NullPointerException {
        String weatherJson = this.getWeatherJson();
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        Weather weather = (Weather) gson.fromJson(weatherJson, this.new Weather().getClass());
        if (weather.getResponseStatus().equals("unknown city")) {
            throw new NullPointerException("Unknown City");
        } else {
            return weather;
        }
    }

    private String getWeatherJson() {
        try {
            HttpConnection conn = (HttpConnection) Jsoup.connect("https://free-api.heweather.com/s6/weather?location=" + location + "&key=0f41673a08f74fd0a9ea4583d1ff2f1d");
            conn.method(Connection.Method.GET);
            conn.ignoreContentType(true);
            return conn.execute().body();
        } catch (IOException ex) {
            Logger.getLogger(CityWeather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public class Weather {

        List<A> HeWeather6;

        public class A {

            AA basic;
            AB update;
            String status;
            AC now;
            List<AD> daily_forecast;
            List<AE> lifestyle;

            public class AA {

                String cid;
                String location;
                String parent_city;
                String admin_area;
                String cnty;
                String lat;
                String lon;
                String tz;
            }

            public class AB {

                String loc;
                String utc;

            }

            public class AC {

                String cloud;
                String cond_code;
                String cond_txt;
                String fl;
                String hum;
                String pcpn;
                String pres;
                String tmp;
                String vis;
                String wind_deg;
                String wind_dir;
                String wind_sc;
                String wind_spd;

                public String getCloudLevel() {
                    return this.cloud;
                }

                public String getPresentWeatherCode() {
                    return this.cond_code;
                }

                public String getPresentWeather() {
                    return this.cond_txt;
                }

                public String getWindDirection() {
                    return this.wind_dir;
                }

                public String getWindSpeed() {
                    return this.wind_spd;
                }

                public String getWindPower() {
                    return this.wind_sc;
                }

                public String getWindDegree() {
                    return this.wind_deg;
                }

                public String getHumidity() {
                    return this.hum;
                }

                public String getPrecipitation() {
                    return this.pcpn;
                }

                public String getAtmosphericPressure() {
                    return this.pres;
                }

                public String getVisibility() {
                    return this.vis;
                }

                public String getSomatosensoryTemperature() {
                    return this.fl;
                }

                public String getTemperature() {
                    return this.tmp;
                }
            }

            public class AD {

                String cond_code_d;
                String cond_code_n;
                String cond_txt_d;
                String cond_txt_n;
                String date;
                String hum;
                String mr;
                String ms;
                String pcpn;
                String pop;
                String pres;
                String sr;
                String ss;
                String tmp_max;
                String tmp_min;
                String uv_index;
                String vis;
                String wind_deg;
                String wind_dir;
                String wind_sc;
                String wind_spd;

                public String getDate() {
                    return this.date;
                }

                public String getSunriseTime() {
                    return this.sr;
                }

                public String getSunsetTime() {
                    return this.ss;
                }

                public String getMoonriseTime() {
                    return this.mr;
                }

                public String getMoonsetTime() {
                    return this.ms;
                }

                public String getDaytimeWeatherCode() {
                    return this.cond_code_d;
                }

                public String getDaytimeWeather() {
                    return this.cond_txt_d;
                }

                public String getNighttimeWeatherCode() {
                    return this.cond_code_n;
                }

                public String getNighttimeWeather() {
                    return this.cond_txt_n;
                }

                public String getWindDirection() {
                    return this.wind_dir;
                }

                public String getWindSpeed() {
                    return this.wind_spd;
                }

                public String getWindPower() {
                    return this.wind_sc;
                }

                public String getWindDegree() {
                    return this.wind_deg;
                }

                public String getHumidity() {
                    return this.hum;
                }

                public String getPrecipitation() {
                    return this.pcpn;
                }

                public String getProbabiltyOfPrecipitation() {
                    if (this.pop == null) {
                        return "无数据";
                    } else {
                        return this.pop;
                    }
                }

                public String getUltravioletIndex() {
                    if (this.uv_index == null) {
                        return "无数据";
                    } else {
                        return this.uv_index;
                    }
                }

                public String getVisibility() {
                    return this.vis;
                }

                public String getMaxTemperature() {
                    return this.tmp_max;
                }

                public String getMinTemperature() {
                    return this.tmp_min;
                }

                public String getAtmosphericPressure() {
                    return this.pres;
                }
            }

            public class AE {

                String type;
                String brf;
                String txt;

                public String getType() {
                    return this.type;
                }

                public String getDescription() {
                    return this.txt;
                }

                public String getLevel() {
                    return this.brf;
                }
            }
        }

        public AC getNowWeather() {
            return this.HeWeather6.get(0).now;
        }

        public AD getForecastWeather(int index) {
            return this.HeWeather6.get(0).daily_forecast.get(index);
        }

        public AE getTodayLifeStyle(int index) {
            return this.HeWeather6.get(0).lifestyle.get(index);
        }

        public String getCountry() {
            return this.HeWeather6.get(0).basic.cnty;
        }

        public String getProvience() {
            return this.HeWeather6.get(0).basic.admin_area;
        }

        public String getCity() {
            return this.HeWeather6.get(0).basic.location;
        }

        public String getTimeZone() {
            char[] zone = this.HeWeather6.get(0).basic.tz.toCharArray();
            if (zone[0] == '+') {
                return String.valueOf(zone, 0, 2);
            } else {
                return "-" + String.valueOf(zone[1]);
            }
        }

        public String getResponseStatus() {
            return this.HeWeather6.get(0).status;
        }
    }
}
