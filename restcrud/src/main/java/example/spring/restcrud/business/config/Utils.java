package example.spring.restcrud.business.config;

import org.springframework.http.HttpStatus;

public class Utils {

	public static final int ERROR_NULL_VALUE = 100;
	public static final int ERROR_VALUE_MISSING = 101;
	public static final int ERROR_VALUE_INVALID = 102;
	public static final int ERROR_VALUE_DUPLICATE = 103;
	public static final int ERROR_INVALID_JSON = 901;
	public static final int CACHE_EXPIRY_DAY = 2;

	public static boolean isEmpty(String string) {
		if (string == null || string.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Integer integer) {
		if (integer == null || integer.intValue() == 0) {
			return true;
		}
		return false;
	}

	public static String[] stringToArray(String str) {
		if (isEmpty(str)) {
			return new String[0];
		}
		String[] ret = str.split(",");

		return ret;
	}

	public static String arrayToString(String[] arr) {
		if (arr == null || arr.length == 0) {
			return "";
		}

		return String.join(",", arr);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
		if (enumClass == null) {
			throw new IllegalArgumentException("EnumClass value can't be null.");
		}

		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			if (enumValue.toString().equalsIgnoreCase(value)) {
				return (T) enumValue;
			}
		}

		// Construct an error message that indicates all possible values for the
		// enum.
		StringBuilder errorMessage = new StringBuilder();
		boolean bFirstTime = true;
		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			errorMessage.append(bFirstTime ? "" : ", ").append(enumValue);
			bFirstTime = false;
		}
		throw new IllegalArgumentException(value + " is invalid value. Supported values are " + errorMessage);
	}

	public static HttpStatus responseToHttpStatus(ResponseStatus responseStatus) {
		if (responseStatus == null) {
			return HttpStatus.OK;
		}
		switch (responseStatus) {
			case FAILED:
				return HttpStatus.NOT_FOUND;
			case INVALID:
				return HttpStatus.BAD_REQUEST;
			case SUCCESS:
				return HttpStatus.OK;
			case UNDETERMINED:
				return HttpStatus.INTERNAL_SERVER_ERROR;
			default:
				return HttpStatus.OK;
		}
	}

}
