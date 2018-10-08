package club.magiccrazyman.weatherforecast.gui;

import club.magiccrazyman.weatherforecast.common.CityWeather;
import club.magiccrazyman.weatherforecast.common.CityWeather.Weather;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Magic Crazy Man
 */
public class WeatherForecastController implements Initializable {

    @FXML
    private VBox backgroundPane;
    @FXML
    private Text cityText;
    @FXML
    private Text presentTempText;
    @FXML
    private Text presentTimeText;
    @FXML
    private Text presentWeatherText;
    @FXML
    private Text presentDateText;
    @FXML
    private ImageView weatherStatus0;
    @FXML
    private ImageView weatherStatus1;
    @FXML
    private ImageView weatherStatus2;
    @FXML
    private ImageView weatherStatus3;
    @FXML
    private ImageView weatherStatus4;
    @FXML
    private ImageView weatherStatus5;
    @FXML
    private Text weatherDate0;
    @FXML
    private Text weatherDate1;
    @FXML
    private Text weatherDate2;
    @FXML
    private Text textBox00;
    @FXML
    private Text textBox01;
    @FXML
    private Text textBox02;
    @FXML
    private Text textBox03;
    @FXML
    private Text textBox04;
    @FXML
    private Text textBox05;
    @FXML
    private Text textBox06;
    @FXML
    private Text textBox07;
    @FXML
    private Text textBox10;
    @FXML
    private Text textBox11;
    @FXML
    private Text textBox12;
    @FXML
    private Text textBox13;
    @FXML
    private Text textBox14;
    @FXML
    private Text textBox15;
    @FXML
    private Text textBox16;
    @FXML
    private Text textBox17;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    private Weather weather;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh("auto_ip");
    }

    private void getCityWeather(String city) {
        CityWeather cw = new CityWeather(city);
        try {
            weather = cw.getWeather();
        } catch (NullPointerException exp) {
            System.out.println("Unknown City");
        }
    }

    private void setBackgroundImg() {

        int hour = LocalDateTime.now(ZoneId.of("UTC" + weather.getTimeZone())).getHour();
        if (hour >= 6 && hour < 18) {
            backgroundPane.setStyle("-fx-background-image:url('/club/magiccrazyman/weatherforecast/assets/img/0.png');");
        } else {
            backgroundPane.setStyle("-fx-background-image:url('/club/magiccrazyman/weatherforecast/assets/img/1.png');");
        }
        InnerShadow effect = new InnerShadow(BlurType.THREE_PASS_BOX, Color.rgb(255, 255, 255), 10.0, 1.0, 0.0, 0.0);
        weatherStatus0.setEffect(effect);
        weatherStatus1.setEffect(effect);
        weatherStatus2.setEffect(effect);
    }

    private void setDate() {
        String today = weather.getForecastWeather(0).getDate().split(" ")[0];
        String forecastTormorrow = weather.getForecastWeather(1).getDate();
        String forecastAfterTormorrow = weather.getForecastWeather(2).getDate();
        presentDateText.setText(today);
        weatherDate0.setText("今天\n" + today.substring(2));
        weatherDate1.setText(LocalDateTime.of(Integer.parseInt(forecastTormorrow.split("-")[0]), Integer.parseInt(forecastTormorrow.split("-")[1]), Integer.parseInt(forecastTormorrow.split("-")[2]), 0, 0).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINESE) + "\n" + forecastTormorrow.substring(2));
        weatherDate2.setText(LocalDateTime.of(Integer.parseInt(forecastAfterTormorrow.split("-")[0]), Integer.parseInt(forecastAfterTormorrow.split("-")[1]), Integer.parseInt(forecastAfterTormorrow.split("-")[2]), 0, 0).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINESE) + "\n" + forecastAfterTormorrow.substring(2));
    }

    private void setPresentTime() {

        class Time implements Runnable {

            Text timeText;

            public Time(Text timeText) {
                this.timeText = timeText;
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        String formatter = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now(ZoneId.of("UTC" + weather.getTimeZone())));
                        timeText.setText(formatter);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WeatherForecastController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        Thread th = new Thread(new Time(presentTimeText));
        th.start();
    }

    private void setPresentTemp() {
        presentTempText.setText(weather.getNowWeather().getTemperature());
    }

    private void setCity() {
        cityText.setText(weather.getCountry() + " · " + weather.getCity());
    }

    private void setWeather() {
        presentWeatherText.setText(weather.getNowWeather().getPresentWeather() + " " + weather.getForecastWeather(0).getMaxTemperature() + " / " + weather.getForecastWeather(0).getMinTemperature() + " °");
        
        weatherStatus0.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(0).getDaytimeWeatherCode() + ".png"));
        weatherStatus1.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(1).getDaytimeWeatherCode() + ".png"));
        weatherStatus2.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(2).getDaytimeWeatherCode() + ".png"));

        weatherStatus3.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(0).getNighttimeWeatherCode() + ".png"));
        weatherStatus4.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(1).getNighttimeWeatherCode() + ".png"));
        weatherStatus5.setImage(new Image("/club/magiccrazyman/weatherforecast/assets/weather/" + weather.getForecastWeather(2).getNighttimeWeatherCode() + ".png"));
    }

    private void setTextBox(int index, boolean lifeStyle) {
        boolean hasLifeStyle = lifeStyle;
        if (index == 0 && !hasLifeStyle) {
            textBox00.setText("体感温度：" + weather.getNowWeather().getSomatosensoryTemperature() + " °");
            textBox01.setText("风力：" + weather.getNowWeather().getWindPower());
            textBox02.setText("风速：" + weather.getNowWeather().getWindSpeed() + " km/h");
            textBox03.setText("风向：" + weather.getNowWeather().getWindDirection());
            textBox04.setText("云量：" + weather.getNowWeather().getCloudLevel());
            textBox05.setText("相对湿度：" + weather.getNowWeather().getHumidity() + " %");
            textBox06.setText("能见度：" + weather.getNowWeather().getVisibility());
            textBox07.setText("降水量：" + weather.getNowWeather().getPrecipitation() + " mm");
            try {
                textBox10.setText("舒适度指数：" + weather.getTodayLifeStyle(0).getLevel());
                textBox11.setText("穿衣指数：" + weather.getTodayLifeStyle(1).getLevel());
                textBox12.setText("感冒指数：" + weather.getTodayLifeStyle(2).getLevel());
                textBox13.setText("运动指数：" + weather.getTodayLifeStyle(3).getLevel());
                textBox14.setText("旅游指数：" + weather.getTodayLifeStyle(4).getLevel());
                textBox15.setText("紫外线指数：" + weather.getTodayLifeStyle(5).getLevel());
                textBox16.setText("洗车指数：" + weather.getTodayLifeStyle(6).getLevel());
                textBox17.setText("空气污染扩散指数：" + weather.getTodayLifeStyle(7).getLevel());
            } catch (NullPointerException exp) {
                setTextBox(0, true);
            }
        } else {
            textBox00.setText("最高温度：" + weather.getForecastWeather(index).getMaxTemperature() + " °");
            textBox01.setText("最高温度：" + weather.getForecastWeather(index).getMinTemperature() + " °");
            textBox02.setText("风力：" + weather.getForecastWeather(index).getWindPower());
            textBox03.setText("风速：" + weather.getForecastWeather(index).getWindSpeed() + " km/h");
            textBox04.setText("风向：" + weather.getForecastWeather(index).getWindDirection());
            textBox05.setText("紫外线强度指数：" + weather.getForecastWeather(index).getUltravioletIndex());
            textBox06.setText("相对湿度：" + weather.getForecastWeather(index).getHumidity() + " %");
            textBox07.setText("能见度：" + weather.getForecastWeather(index).getVisibility());
            if (weather.getForecastWeather(index).getProbabiltyOfPrecipitation().equals("无数据")) {
                textBox10.setText("降水概率：" + weather.getForecastWeather(index).getProbabiltyOfPrecipitation());
            } else {
                textBox10.setText("降水概率：" + weather.getForecastWeather(index).getProbabiltyOfPrecipitation() + " %");
            }
            textBox11.setText("降水量：" + weather.getNowWeather().getPrecipitation() + " mm");
            textBox12.setText("");
            textBox13.setText("");
            textBox14.setText("");
            textBox15.setText("");
            textBox16.setText("");
            textBox17.setText("");
        }
    }

    private void refresh(String city) {
        this.getCityWeather(city);
        this.setBackgroundImg();
        this.setPresentTime();
        this.setDate();
        this.setPresentTemp();
        this.setCity();
        this.setWeather();
        this.setTextBox(0, false);
        this.clearSearchField();
    }

    private void clearSearchField() {
        searchField.clear();
    }

    public void mouseClickDate(MouseEvent event) {
        Text source = (Text) event.getSource();
        int index = Integer.parseInt(source.getId().substring(11));
        this.setTextBox(index, false);
    }

    public void searchNewCityOnButton(ActionEvent event) {
        String newCity = searchField.getText().trim();
        if (!newCity.equals("")) {
            refresh(newCity);
        }
    }

    public void searchNewCityOnKeyboard(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String newCity = searchField.getText().trim();
            if (!newCity.equals("")) {
                refresh(newCity);
            }
        }
    }
}
