package com.revolut.banktransferapi.account.mock;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.revolut.banktransferapi.account.domain.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTemplateLoader implements TemplateLoader {

    public static final BigDecimal ACCOUNT_FROM_INITIAL_AMOUNT = BigDecimal.valueOf(10000);
    public static final BigDecimal ACCOUNT_TO_INITIAL_AMOUNT = BigDecimal.valueOf(50);

    @Override
    public void load() {
        Fixture.of(Account.class).addTemplate("validFrom", new Rule(){{
            add("id", random(Integer.class, range(1, 200)));
            add("balance", ACCOUNT_FROM_INITIAL_AMOUNT);
            add("createDate", LocalDateTime.now());
        }});

        Fixture.of(Account.class).addTemplate("validTo", new Rule(){{
            add("id", random(Integer.class, range(1000, 2000)));
            add("balance", ACCOUNT_TO_INITIAL_AMOUNT);
            add("createDate", LocalDateTime.now());
        }});

    }
}