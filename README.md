# HuggingFace Tokenizers on Android (HFTA)

> Reference implementation using [HuggingFace's (HF) tokenizers](https://github.com/huggingface/tokenizers) in Android.

## Overview

Run _any_ HF's tokenizer on Android using the associated `tokenizers.json` from `huggingface.co`. To achieve that, the HF library is built [via rust](./rs-hfta/README.md) into a [shared library](./app/src/main/jniLibs/arm64-v8a/libhfta.so) and uses Java Native Interface (JNI) to load the library.

### Features

- [`rust` to `java` NDK bindings of HF's tokenizers in `rs-hfta`](./rs-hfta/README.md)
- [Parameterized instrumentation tests (runs on-device)](./app/src/androidTest/java/com/example/hfta/HFTokenizer.kt)

### Thanks to

- [Hugging Face's `tokenizers` library](https://github.com/huggingface/tokenizers)
- [Qualcomm's Genie Library](https://softwarecenter.qualcomm.com/api/download/software/sdks/Qualcomm_AI_Runtime_Community/All/2.34.0.250424/v2.34.0.250424.zip) has a rust to C++ static library implementation of HF's tokenizers at `qairt/2.34.0.250424/examples/Genie/Genie/src/qualla/tokenizers/rust`
- [Shubham Panchal's `Sentence-Embeddings-Android`](https://github.com/shubham0204/Sentence-Embeddings-Android/)