import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
*/
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
* Reads text from the given input txt file.
*/
fun readInputAsText(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun String.remove(vararg strings: String) = strings.fold(this) { acc: String, s: String ->
    acc.replace(s, "")
}