package com.example.hfta

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class HFTokenizerTest(
    private val sentence: String,
    private val tokens: List<Int>
) {
    private lateinit var tokenizer: HFTokenizer

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        tokenizer = HFTokenizer(context.assets.open("gemma.json").use { it.readBytes() })
    }

    @After
    fun tearDown() {
        tokenizer.tearDown()
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
            name = "when a sentence is {0}, and the tokens are {1}"
        )
        fun getValidateTokens(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf("Hello world", listOf(2, 4521, 2134)),
                arrayOf("hello world", listOf(2, 17534, 2134)),
                arrayOf("hello jặy", listOf(2, 17534, 678, 236245, 235267))
            )
        }
    }

    @Test
    fun `test tokenization `() {
        assertEquals(tokenizer.encode(sentence).asList(), tokens)
    }
}