rustup target add armv7-linux-androideabi

cargo ndk -t arm64-v8a -o ../app/src/main/jniLibs/ build --release