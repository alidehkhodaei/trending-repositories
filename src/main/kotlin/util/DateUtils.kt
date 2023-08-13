package util

import java.time.LocalDate

/**
 * Retrieves the current date using the LocalDate class.
 *
 * @return The current date represented as a LocalDate object.
 */
fun today(): LocalDate = LocalDate.now()