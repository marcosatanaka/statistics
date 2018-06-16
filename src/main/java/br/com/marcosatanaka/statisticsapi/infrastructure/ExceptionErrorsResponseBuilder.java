package br.com.marcosatanaka.statisticsapi.infrastructure;

import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionErrorsResponseBuilder {

	private final Map<String, Object> errorResponse = new LinkedHashMap<>();

	private ExceptionErrorsResponseBuilder() {
		errorResponse.put("timestamp", System.currentTimeMillis());
	}

	static ExceptionErrorsResponseBuilder of() {
		return new ExceptionErrorsResponseBuilder();
	}

	ExceptionErrorsResponseBuilder withError(ExceptionErrorsType error) {
		errorResponse.put("code", error.getCode());
		errorResponse.put("message", error.getDescription());
		return this;
	}

	ExceptionErrorsResponseBuilder withException(String exception) {
		errorResponse.put("exception", exception);
		return this;
	}

	ExceptionErrorsResponseBuilder withPath(String path) {
		errorResponse.put("path", path);
		return this;
	}

	ExceptionErrorsResponseBuilder withPersonalizedMessage(String message) {
		if (Strings.isNotEmpty(message)) {
			errorResponse.remove("message");
			errorResponse.put("message", message);
		}
		return this;
	}

	Map<String, Object> build() {
		return Collections.unmodifiableMap(new LinkedHashMap<>(errorResponse));
	}

}
