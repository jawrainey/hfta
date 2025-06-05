rustup target add aarch64-linux-android

cargo ndk -t aarch64-linux-android -o ../app/src/main/jniLibs/ build --release