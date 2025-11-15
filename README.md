# HuggingFace Tokenizers on Android (HFTA)

> Reference implementation using [HuggingFace's (HF) tokenizers](https://github.com/huggingface/tokenizers) in Android.

## Demo Video

UI to show text to tokens via the tokenizers library in real-time on Android:


### Try a Tokenizer

1. Find a model you want to test on HF, e.g., Google's [gemma-3-4b-it](https://huggingface.co/google/gemma-3-4b-it/blob/main/tokenizer.json)
2. Download and add the `tokenizer.json` to [`app/src/main/assets`](app/src/main/assets) named `gemma-3-4b-it.json`
3. Modify `SELECTED_TOKENIZER` in [`app/build.gradle.kts`](app/build.gradle.kts)

## Features

- Run any [HuggingFace's (HF) tokenizers](https://github.com/huggingface/tokenizers) on-device in Android.
- [`rust` to `java` NDK bindings of HF's tokenizers in `rs-hfta`](./rs-hfta/README.md)
- [Parameterized instrumentation tests (runs on-device)](./app/src/androidTest/java/com/example/hfta/HFTokenizer.kt)

## Implementation Details

Run _any_ HF's tokenizer on Android using the associated `tokenizers.json` from `huggingface.co`. To achieve that, the HF library is built [via rust](./rs-hfta/README.md) into a [shared library](./app/src/main/jniLibs/arm64-v8a/libhfta.so) and uses Java Native Interface (JNI) to load the library.

### Thanks to

- [Hugging Face's `tokenizers` library](https://github.com/huggingface/tokenizers)
- [Qualcomm's Genie Library](https://softwarecenter.qualcomm.com/api/download/software/sdks/Qualcomm_AI_Runtime_Community/All/2.34.0.250424/v2.34.0.250424.zip) has a rust to C++ static library implementation of HF's tokenizers at `qairt/2.34.0.250424/examples/Genie/Genie/src/qualla/tokenizers/rust`
- [Shubham Panchal's `Sentence-Embeddings-Android`](https://github.com/shubham0204/Sentence-Embeddings-Android/)