package com.example.quranapplication.matchingalgo


class Pair(var s: String, var b: Boolean)

class MatchingAlgorithm(Surah: String) {
    private var ptrRecite = 0
    private var lastWord = ""
    private val surahWords: List<String>

    init {
        val words = split(Surah)
        val removedNums = removeNums(words)
        surahWords = ArrayList(removedNums)
    }

    fun match(inputVerse: String): List<Pair> {
        val inputWords = split(inputVerse)
        val returnWords: MutableList<Pair> = ArrayList()
        var i = 0
        while (i < inputWords.size && ptrRecite < surahWords.size) {
            var inputWord = inputWords[i]
            val actualWord = surahWords[ptrRecite]
            if (inputWord == actualWord) {
                returnWords.add(Pair(actualWord, true))
            } else {
                var inputWordWithout = filter(inputWord)
                val actualWordWithout = filter(actualWord)
                if (inputWordWithout == actualWordWithout) returnWords.add(
                    Pair(
                        actualWord,
                        false
                    )
                ) else {
                    if (i == 0) {
                        inputWord = lastWord + inputWord
                        if (inputWord == actualWord) returnWords.add(Pair(actualWord, true)) else {
                            inputWordWithout = filter(inputWord)
                            if (inputWordWithout == actualWordWithout) returnWords.add(
                                Pair(
                                    actualWord,
                                    false
                                )
                            ) else {
                                lastWord = inputWord.substring(lastWord.length)
                                return returnWords
                            }
                        }
                    } else {
                        lastWord = inputWord
                        return returnWords
                    }
                }
            }
            i++
            ptrRecite++
        }
        return returnWords
    }

    companion object {
        private fun split(s: String): List<String> {
            val words: MutableList<String> = ArrayList()
            val splitWords = s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (word in splitWords) {
                if (!word.isEmpty()) {
                    words.add(word)
                }
            }
            return words
        }

        private fun filter(s: String): String {
            var s = s
            val diacritics = arrayOf(
                "ؐ",
                "ؑ",
                "ؒ",
                "ؓ",
                "ؔ",
                "ؕ",
                "ؖ",
                "ؗ",
                "ؘ",
                "ؙ",
                "ؚ",
                "ً",
                "ٌ",
                "ٍ",
                "َ",
                "ُ",
                "ِ",
                "ّ",
                "ْ",
                "ٓ",
                "ٔ",
                "ٕ",
                "ٖ",
                "ٗ",
                "٘",
                "ٙ",
                "ٚ",
                "ٛ",
                "ٜ",
                "ٝ",
                "ٞ",
                "ٟ",
                "ٰ",
                "ۖ",
                "ۗ",
                "ۘ",
                "ۙ",
                "ۚ",
                "ۛ",
                "ۜ",
                "۟",
                "۠",
                "ۡ",
                "ۢ",
                "ۣ",
                "ۤ",
                "ۧ",
                "ۨ",
                "۪",
                "۫",
                "۬",
                "ۭ"
            )
            for (diacritic in diacritics) {
                var pos = s.indexOf(diacritic)
                while (pos != -1) {
                    s = s.substring(0, pos) + s.substring(pos + diacritic.length)
                    pos = s.indexOf(diacritic)
                }
            }
            return s
        }

        private fun removeNums(words: List<String>): List<String> {
            val returnWords: MutableList<String> = ArrayList()
            for (word in words) {
                var valid = true
                for (c in word.toCharArray()) {
                    if (Character.isDigit(c)) {
                        valid = false
                        break
                    }
                }
                if (valid) returnWords.add(word)
            }
            return returnWords
        }
    }
}
