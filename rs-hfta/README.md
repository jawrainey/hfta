# HuggingFace Tokenizers on Android (HFTA)

[HuggingFace Tokenizers](https://github.com/huggingface/tokenizers) are written in [rust](https://www.rust-lang.org/) and can be loaded directly from the [`tokenizers` crate](https://crates.io/crates/tokenizers).

This package creates a shared library (`.so`) where tokenizers from HF can be loaded directly on Android from as HF `.JSON` files using a Java Native Interface (`JNI`). When the `.so` is built it contains _references_ to methods (e.g., `Java_com_example_hftok_HFTokenizer_createTokenizer`) so a file in Android must exist at [`java/com/example/hftok/HFtokenizer.kt`](../app/src/main/java/com/example/hfta/HFTokenizer.kt) where the shared library is loaded.

## Open Clip Support

[`open_clip`](https://github.com/mlfoundations/open_clip) implements a [`wrapper`](https://github.com/mlfoundations/open_clip/blob/main/src/open_clip/tokenizer.py#L403) in python that runs preprocessing via the [`get_clean_fn`](https://github.com/mlfoundations/open_clip/blob/main/src/open_clip/tokenizer.py#L439) method before tokenization [with padding](https://github.com/mlfoundations/open_clip/blob/main/src/open_clip/tokenizer.py#L443-L444). This is notable when using this library as a preprocessing step may be required, e.g., to parse input and pad the output vector to the desired `context_length`.

## Development

Builds automatically in Android via [`rust-android-gradle`](https://github.com/mozilla/rust-android-gradle). For manual builds:

```bash
# install rust: https://www.rust-lang.org/tools/install

# To make life easier install the cargo extension:
# this simplifies building shared libraries (.so) into the jniLibs dir
cargo install cargo-ndk

# Depending what you want to target install the toolchain:
rustup target add aarch64-linux-android

cargo ndk -t arm64-v8a -o ../app/src/main/jniLibs/ build --release

# NOTE: app/src/main/jniLibs/arm64-v8a/libhfta.so should be 2.7M
```