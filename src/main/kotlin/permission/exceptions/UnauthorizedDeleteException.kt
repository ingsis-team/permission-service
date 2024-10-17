package permission.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedDeleteException : PermissionException(message = "User cannot delete this resource", HttpStatus.UNAUTHORIZED)
