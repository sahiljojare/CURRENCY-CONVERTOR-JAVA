package converter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

import org.json.JSONObject;

public class backend {

    String from, to;
    double currency;
    private double rate;
    private double result = 0;

    protected backend(String from, String to, double currency) {
        this.from = from;
        this.to = to;
        this.currency = currency;
    }

    public boolean checkstring() {
        if (from.equalsIgnoreCase(to)) {
            return false;
        } else {
            convert();
            return true;
        }
    }

    private void convert() {

        try {
            // FREE API (NO KEY REQUIRED)
            String api =
                    "https://api.frankfurter.app/latest?amount=" + currency +
                    "&from=" + from +
                    "&to=" + to;

            URL u = URI.create(api).toURL(); // Java 20+ safe
            HttpURLConnection http = (HttpURLConnection) u.openConnection();
            http.setRequestMethod("GET");
            http.setConnectTimeout(5000);
            http.setReadTimeout(5000);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(http.getInputStream()));

            String response = br.readLine();
            br.close();

            JSONObject obj = new JSONObject(response);
            JSONObject rates = obj.getJSONObject("rates");

            result = rates.getDouble(to);
            rate = result / currency;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getRate() {
        return rate;
    }

    public double getConvertedCurrency() {
        return result;
    }
}
