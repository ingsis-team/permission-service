package permission.exceptions

import org.springframework.http.HttpStatus

open class PermissionException(override val message: String, val status: HttpStatus) : RuntimeException(message)
