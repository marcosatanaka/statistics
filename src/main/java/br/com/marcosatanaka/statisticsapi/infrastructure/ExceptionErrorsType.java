package br.com.marcosatanaka.statisticsapi.infrastructure;

public enum ExceptionErrorsType {

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

