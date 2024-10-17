package permission.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedSharedException(message: String) : PermissionException(message, HttpStatus.BAD_REQUEST)
