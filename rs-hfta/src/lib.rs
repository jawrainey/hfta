use jni::objects::{JByteArray, JClass, JString, ReleaseMode};
use jni::sys::{jintArray, jlong};
use jni::JNIEnv;
use tokenizers::Tokenizer;

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_createTokenizer<'a>(
    mut env: JNIEnv<'a>,
    _: JClass<'a>,
    tokenizer_as_bytes: JByteArray<'a>,
) -> jlong {
    unsafe {
        let tokenizer_bytes_rs: Vec<u8> = env
            .get_array_elements(&tokenizer_as_bytes, ReleaseMode::CopyBack)
            .expect("No bytes :O")
            .iter()
            .map(|x| *x as u8)
            .collect();
        Box::into_raw(Box::new(Tokenizer::from_bytes(&tokenizer_bytes_rs))) as jlong
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_encode<'a>(
    mut env: JNIEnv<'a>,
    _: JClass<'a>,
    tokenizer_ptr: jlong,
    text: JString<'a>,
) -> jintArray {
    let tokenizer = unsafe { &mut *(tokenizer_ptr as *mut Tokenizer) };
    let text: String = env.get_string(&text).unwrap().into();
    let encoding = tokenizer
        .encode(text.clone(), true)
        .expect("ENCODING FAILED");
    let ids = encoding.get_ids();
    let java_array = env.new_int_array(ids.len() as i32).unwrap();
    let ids_jint: Vec<i32> = ids.iter().map(|&x| x as i32).collect();
    env.set_int_array_region(&java_array, 0, &ids_jint).unwrap();
    java_array.into_raw()
}

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_deleteTokenizer(
    _: JNIEnv,
    _: JClass,
    tokenizer_ptr: jlong,
) {
    unsafe {
        let _ = Box::from_raw(tokenizer_ptr as *mut Tokenizer);
    };
}
