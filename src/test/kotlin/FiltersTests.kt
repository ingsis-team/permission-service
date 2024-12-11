package permission.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.MDC

@ExtendWith(MockitoExtension::class)
class FiltersTests {
    @Mock
    private lateinit var request: HttpServletRequest

    @Mock
    private lateinit var response: HttpServletResponse

    @Mock
    private lateinit var filterChain: FilterChain

    @InjectMocks
    private lateinit var correlationIdFilter: CorrelationIdFilter

    @InjectMocks
    private lateinit var requestLogFilter: RequestLogFilter

    @BeforeEach
    fun setUp() {
        MDC.clear()
    }
/*
    @Test
    fun testCorrelationIdFilter() {
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(null)
        correlationIdFilter.doFilterInternal(request, response, filterChain)
        verify(filterChain).doFilter(request, response)
        assert(MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY) != null)
    }

    @Test
    fun testCorrelationIdFilterWithHeader() {
        val correlationId = UUID.randomUUID().toString()
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(correlationId)
        correlationIdFilter.doFilterInternal(request, response, filterChain)
        verify(filterChain).doFilter(request, response)
        assertEquals(correlationId, MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY))
    }

 */

    @Test
    fun testRequestLogFilter() {
        `when`(request.requestURI).thenReturn("/test")
        `when`(request.method).thenReturn("GET")
        `when`(response.status).thenReturn(200)
        requestLogFilter.doFilterInternal(request, response, filterChain)
        verify(filterChain).doFilter(request, response)
    }
}
