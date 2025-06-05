use jni::JNIEnv;
use jni::objects::{JByteArray, JClass, JString};
use jni::sys::{jintArray, jlong};
use tokenizers::Tokenizer;

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_createTokenizer(
    env: JNIEnv,
    _: JClass,
    tokenizer_as_bytes: JByteArray,
) -> jlong {
    match env.convert_byte_array(&tokenizer_as_bytes) {
        Ok(bytes) => Box::into_raw(Box::new(Tokenizer::from_bytes(&bytes))) as jlong,
        Err(_) => 0,
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_encode(
    mut env: JNIEnv,
    _: JClass,
    tokenizer_ptr: jlong,
    text: JString,
) -> jintArray {
    let tokenizer = unsafe { &mut *(tokenizer_ptr as *mut Tokenizer) };
    let text: String = env.get_string(&text).unwrap().into();
    let encoding = tokenizer.encode(text.clone(), true).unwrap();
    let ids_jint: Vec<i32> = encoding.get_ids().iter().map(|&x| x as i32).collect();
    let java_array = env.new_int_array(ids_jint.len() as i32).unwrap();
    env.set_int_array_region(&java_array, 0, &ids_jint).unwrap();
    java_array.into_raw()
}

#[unsafe(no_mangle)]
pub extern "C" fn Java_com_example_hfta_HFTokenizer_deleteTokenizer(
    _: JNIEnv,
    _: JClass,
    tokenizer_ptr: jlong,
) {
    drop(unsafe { Box::from_raw(tokenizer_ptr as *mut Tokenizer) });
}
