package practice.fundingboost2.pay.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class MerchantUidGenerator {

    private static final String PREFIX = "FB-";
    private static final String RANDOM_SEED = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int RANDOM_SEED_LENGTH = 5;

    public String generate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        StringBuilder randomSuffix = new StringBuilder();

        for (int i=0;i<RANDOM_SEED_LENGTH;i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, RANDOM_SEED.length());
            randomSuffix.append(RANDOM_SEED.charAt(randomIndex));
        }

        return PREFIX + date + randomSuffix;
    }
}
