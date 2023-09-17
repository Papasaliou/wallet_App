package sn.sir.wallet.sir_wallet_app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("sn.sir.wallet.sir_wallet_app.domain")
@EnableJpaRepositories("sn.sir.wallet.sir_wallet_app.repos")
@EnableTransactionManagement
public class DomainConfig {
}
