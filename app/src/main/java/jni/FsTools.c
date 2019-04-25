#include <jni.h>
#include <string.h>

/*
 *  "0226dee8829c64a16c53a3029f8ddb69",
 *  "0226dee8829c64a16c53a3029f8ddb69",
 *  "8bcd9eff9ddfe72935f915c0ff6b036a"
 */
 const jobjectArray keys[] = {"0226dee8829c64a16c53a3029f8ddb69","0226dee8829c64a16c53a3029f8ddb69","8bcd9eff9ddfe72935f915c0ff6b036a"};

 const char[] keyChars = {'0', '2', '2'}
/*
 * Class:     com_shouzhan_design_FsTools
 * Method:    getKeyFromJNI
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_shouzhan_design_FsTools_getKeyFromJNI
   (JNIEnv *env, jclass jclass, jint type) {

   return (*env)->NewStringUTF(env, str.data());
 }