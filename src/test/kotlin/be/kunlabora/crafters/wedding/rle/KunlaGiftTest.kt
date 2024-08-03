package be.kunlabora.crafters.wedding.rle

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import kotlin.collections.Map.Entry

private val input =
    "T2KLKTKLKLKLTLK2LTKLT2KLK3LNLT2LKLT2KLKTLTKLNLKTK3LNLTLK2LT2LNLTLT3LNLKTKLKLTKTKLKLK2LK3TLKLNLT3LK2TLKTKLNLT2KLK2LK2TKLTLT2K2T2LNLT2LKTLK2LKTK2LNLK2TLK3LNLKTLTLNLKLKTK2LKLT2KLKTLTKLTLKTKTKTLK2LTKTKLKLTK4LKT4LK5LKT4LKT2KTKLK2TKLKTLK3LTLT2LKTLK2LKTK2LKTKTKTLTKTKLT3LT2LNLTK4TLTK4TLTK4TLNLK3LKLKT2KLKT2KLKLNLT2KLK2LKLKTK2LKLTKLNLT2KLK2LKTK2LKTK2LK2LKTLT2LNLTKTLKTKLK2LK3LNLTKLKLKTK2LKLNLKTK3LNLKT3LKTLTKLNLTKLT3LKTKLTK2LK2LTKLNLTLK2LT2LNLKT2KLK2LKT3LKT2KLT3LKT2KLK3LNLK3LTLKLK3TLKLTKLNLKTK3LNLK3LT3LK2TKLK2LKLNLTLKLK3LK3LKTLNLKTK3LNLKLKTK2LTKTLKLNLTLK2LT2LNLKTK3LNLKTK2LKTLK2TLKTKLKLTKLTKTKLKLNLTLT3LT2LNLTLT3LK2TLTLKLTKLKLKTK2LNLKT2LK2LT2LNLKTLK3LTLKTKLK2LTK2LNLKTK3LNLKT2KLK2LKLT"

class KunlaGiftTest {

    @Test
    fun `Run Length Encoding - decoded`() {
        SoftAssertions.assertSoftly {
            assertThat("T3".rleDecoded()).isEqualTo("TTT")
            assertThat("KT3".rleDecoded()).isEqualTo("KTTT")
            assertThat("K4T".rleDecoded()).isEqualTo("KKKKT")
            assertThat("K2N2L2T2".rleDecoded()).isEqualTo("KKNNLLTT")
        }
    }

    @Test
    fun decode() {
        println(decode(input))
    }
}



private fun decode(input: String): String {
    val simpleMap: Map<Char, List<Char>> = mapOf(
        'A' to 'K', 'B' to 'T', 'C' to 'L', 'D' to 'N',
        'E' to 'K', 'F' to 'T', 'G' to 'L', 'H' to 'N',
        'I' to 'K', 'J' to 'T', 'K' to 'L', 'L' to 'N',
        'M' to 'K', 'N' to 'T', 'O' to 'L', 'P' to 'N',
        'Q' to 'K', 'R' to 'T', 'S' to 'L', 'T' to 'N',
        'U' to 'K', 'V' to 'T', 'W' to 'L', 'X' to 'N',
        'Y' to 'K', 'Z' to 'T', ' ' to 'L', ' ' to 'N',
    ).entries.groupBy(Entry<Char, Char>::value) { (k,_) -> k}
    return input.rleDecoded().mapNotNull { c -> simpleMap.getValue(c)[0] }.joinToString(separator = "")
}

private fun tryCombinations(input: String): String {
    val _4letterKotlinWords = listOf('K', 'T', 'L', 'N').generatePermutations()
    val alphabet = ('a'..'z') + ('A'..'Z')
    val groups: List<String> = input.rleDecoded().windowed(4, 4, false)
    return (0.._4letterKotlinWords.size).mapIndexed { index, _ ->
        val cypher = _4letterKotlinWords.shift(index).zip(alphabet).toMap()
        groups.mapNotNull { cypher[it] }.joinToString("")
    }.joinToString("\n") { it }
}

private fun String.rleDecoded() =
    fold(mutableListOf<Pair<Char, String>>()) { acc, char ->
        if (char.isDigit() && acc.isNotEmpty()) {
            acc[acc.size - 1] = acc.last().first to (acc.last().second + char)
        } else {
            acc.add(char to "")
        }
        acc
    }.flatMap { (char, count) ->
        val repeatCount = if (count.isEmpty()) 1 else count.toInt()
        List(repeatCount) { char }
    }.joinToString("")


private fun <T> List<T>.generatePermutations(length: Int = this.size): List<String> {
    if (length == 0) return listOf("")
    val smallerPermutations = generatePermutations(length - 1)
    val result = mutableListOf<String>()
    for (char in this) {
        for (permutation in smallerPermutations) {
            result.add("$char$permutation")
        }
    }
    return result
}

private fun <T> List<T>.shift(): List<T> {
    val first = take(1)
    return drop(1).toMutableList() + first
}

private fun <T> List<T>.shift(step: Int): List<T> {
    return if (step == 0) this
    else (1..step).fold(this) { acc, _ -> acc.shift() }
}

//other cyphers that didn't work
val correspondingAlphabeticalIndex = mapOf('K' to 11, 'L' to 12, 'N' to 14, 'T' to 20)
val numerical = mapOf('K' to 0, 'L' to 1, 'N' to 2, 'T' to 3)
val ktlnNumerical = mapOf('K' to 0, 'T' to 1, 'L' to 2, 'N' to 3)
val kotlinNumerical = mapOf('K' to 0, 'T' to 2, 'L' to 3, 'N' to 4)