package be.kunlabora.crafters.kunlaquota.web.ui.screens

import be.kunlabora.crafters.kunlaquota.service.aMultiLineQuote
import be.kunlabora.crafters.kunlaquota.service.aSingleLineQuote
import be.kunlabora.crafters.kunlaquota.service.domain.Quote
import be.kunlabora.crafters.kunlaquota.web.ui.partial
import be.kunlabora.crafters.kunlaquota.web.ui.screens.ShowQuotesScreen.showQuotes
import org.junit.jupiter.api.Test
import verify

class ShowQuotesScreenTest {

    @Test
    fun `showQuotes when no quotes found`() {
        val quotes = emptyList<Quote>()
        partial { showQuotes(quotes) }.verify()
    }

    @Test
    fun `showQuotes when some quotes found`() {
        val quotes = buildList<Quote> {
            aMultiLineQuote {
                "Charlie" said "Liam, my brother, just had a daughter. So you know, we're taking some time off."
                "Charlie" said "Liam and I don't keep in touch and there's been some trouble with... royalties."
                "Charlie" said "The fact of the matter sir is that. DriveShaft is... dead."
            }
            aSingleLineQuote(name = "Jooones", text = "snorf snorf?")
        }
        partial { showQuotes(quotes) }.verify()
    }
}