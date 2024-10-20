package permission.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedShareException(message: String) : PermissionException(message, HttpStatus.BAD_REQUEST)
