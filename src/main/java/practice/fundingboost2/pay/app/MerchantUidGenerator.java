package practice.fundingboost2.pay.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class MerchantUidGenerator {

    public String generate() {
        String prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        int randomSuffix = ThreadLocalRandom.current().nextInt(10000, 99999); // 10000 ~ 99999

        return prefix + randomSuffix;
    }
}
