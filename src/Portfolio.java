import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Portfolio {
    private final ArrayList<Investment> investments = new ArrayList<>();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    //finds investment by their ticker (ex: AAPL)
    public Investment findByTicker(String ticker) {
        if (ticker == null) {
            return null;
        }

        for (Investment investment : investments) {
            if (investment.getTicker().equalsIgnoreCase(ticker.trim())) {
                return investment;
            }
        }
        return null;
    }
}
