package com.example.quranapplication.matchingalgo

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator


class Pair(var s: String, var b: Boolean)
class MatchingAlgorithm(Surah: String,var context:Context) {
    private var ptrRecite = 0
    private var lastWord = ""
    private val surahWords: List<String>

    init {
        val words = split(Surah)
        val removedNums = removeNums(words)
        surahWords = ArrayList(removedNums)
    }

    fun CheckForBetterStart(inputVerse: String, curPtr: Int, start: Int): Int {
        var curPtr = curPtr
        val inputWords = split(inputVerse)
        var tmp_lastword = lastWord
        var cnt = 0
        var i = start
        while (i < inputWords.size && curPtr < surahWords.size) {
            var inputWord = inputWords[i]
            val actualWord = surahWords[curPtr]
            if (inputWord == actualWord) {
                cnt++
            } else {
                var inputWordWithout = filter(inputWord)
                val actualWordWithout = filter(actualWord)
                if (inputWordWithout == actualWordWithout) cnt++ else {
                    if (i == 0) {
                        inputWord = tmp_lastword + inputWord
                        if (inputWord == actualWord) {
                            cnt++
                        } else {
                            inputWordWithout = filter(inputWord)
                            if (inputWordWithout == actualWordWithout) cnt++ else {
                                tmp_lastword = inputWord.substring(tmp_lastword.length)
                                break
                            }
                        }
                    } else {
                        tmp_lastword = inputWord
                        break
                    }
                }
            }
            i++
            curPtr++
        }
        return cnt
    }

    fun match(inputVerse: String): List<Pair> {
        val inputWords = split(inputVerse)
        var MaxCorrect = 0
        var BetterStartID = 0
        for (i in inputWords.indices) {
            val Cnt = CheckForBetterStart(inputVerse, ptrRecite, i)
            if (Cnt > MaxCorrect) {
                BetterStartID = i
                MaxCorrect = Cnt
            }
        }
        //System.out.println(MaxCorrect);
        val returnWords: MutableList<Pair> = ArrayList()
        var i = BetterStartID
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
                        val vibe: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
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

