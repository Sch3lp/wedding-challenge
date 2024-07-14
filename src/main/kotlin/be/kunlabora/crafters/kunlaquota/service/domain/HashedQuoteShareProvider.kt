package be.kunlabora.crafters.kunlaquota.service.domain

class HashedQuoteShareProvider: QuoteShareProvider {
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun invoke(quoteId: QuoteId): QuoteShare =
        quoteId.value.take(8)
            .hexToNumber().toLong()
            .encodeToBase26()
            .asQuoteShare()

    private fun String.asQuoteShare() = QuoteShare(this)

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.hexToNumber(): String = hexToUInt(HexFormat.Default).toString()

    private fun Long.encodeToBase26(): String {
        val base = alphabet.length
        var num = this
        val result = StringBuilder()

        return if (num == 0L) {
            alphabet[0].toString()
        } else {
            while (num > 0) {
                val remainder = (num % base).toInt()
                result.append(alphabet[remainder])
                num /= base
            }

            result.reverse().toString()
        }
    }
}