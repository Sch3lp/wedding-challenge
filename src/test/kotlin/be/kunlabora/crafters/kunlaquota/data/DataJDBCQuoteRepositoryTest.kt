package be.kunlabora.crafters.kunlaquota.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [
    DataConfig::class,
    PostgresContainerConfig::class,
])
@AutoConfigureDataJdbc
class DataJDBCQuoteRepositoryTest(
    @Autowired val quoteRepository: DataJDBCQuoteRepository,
) : QuoteRepositoryContractTest(quoteRepository)