package be.kunlabora.crafters.kunlaquota.data

import be.kunlabora.crafters.kunlaquota.service.domain.QuoteShareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [
    DataConfig::class,
    PostgresContainerConfig::class,
])
@AutoConfigureDataJdbc
class DataJDBCQuoteShareRepositoryTest(
    @Autowired val quoteShareRepository: QuoteShareRepository,
) : QuoteShareRepositoryContractTest(quoteShareRepository)