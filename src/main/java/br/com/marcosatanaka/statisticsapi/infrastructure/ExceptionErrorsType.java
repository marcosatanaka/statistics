package br.com.marcosatanaka.statisticsapi.infrastructure;

public enum ExceptionErrorsType {

	ACTION_NOT_NULL_PROPERTIES(97, "Input has invalid null fields"),
	BAD_REQUEST(98, "Invalid input"),
	INTERNAL_SERVER_ERROR(99, "Unknown internal server error");

	private final Integer code;

	private final String description;

	ExceptionErrorsType(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}

