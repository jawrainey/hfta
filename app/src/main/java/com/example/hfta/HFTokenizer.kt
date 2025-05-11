package com.example.hfta

class HFTokenizer(tokenizerBytes: ByteArray) {
    private external fun createTokenizer(tokenizerBytes: ByteArray): Long
    private external fun encode(tokenizerPtr: Long, text: String): IntArray
    private external fun deleteTokenizer(tokenizerPtr: Long)

    private var tokenizerPtr: Long = createTokenizer(tokenizerBytes)

    companion object {
        init {
            System.loadLibrary("hfta")
        }
    }

    fun encode(text: String): IntArray {
        return encode(tokenizerPtr, text)
    }

    fun tearDown() {
        return deleteTokenizer(tokenizerPtr)
    }
}
