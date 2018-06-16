package br.com.marcosatanaka.statisticsapi.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> generalException(HttpServletRequest request, Exception exception) {
		LOGGER.error(exception.getMessage(), exception);

		return ExceptionErrorsResponseBuilder.of()
				.withError(ExceptionErrorsType.INTERNAL_SERVER_ERROR)
				.withException(exception.getClass().getName())
				.withPersonalizedMessage(exception.getMessage())
				.withPath(request.getServletPath())
				.build();
	}

	@ResponseBody
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
		LOGGER.error(exception.getMessage(), exception);

		return ExceptionErrorsResponseBuilder.of()
				.withError(ExceptionErrorsType.BAD_REQUEST)
				.withException(exception.getClass().getName())
				.withPersonalizedMessage(exception.getMessage())
				.withPath(request.getServletPath())
				.build();
	}

	@ResponseBody
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult()
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(joining("; "));
		LOGGER.error(message, exception);

		return ExceptionErrorsResponseBuilder.of()
				.withError(ExceptionErrorsType.ACTION_NOT_NULL_PROPERTIES)
				.withException(exception.getClass().getName())
				.withPersonalizedMessage(message)
				.withPath(request.getServletPath())
				.build();
	}

}
