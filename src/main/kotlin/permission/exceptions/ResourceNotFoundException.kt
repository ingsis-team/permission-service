package permission.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ResourceNotFoundException(message: String) : PermissionException(message, HttpStatus.BAD_REQUEST)
