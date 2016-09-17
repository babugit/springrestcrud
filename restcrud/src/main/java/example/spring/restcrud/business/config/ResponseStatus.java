package example.spring.restcrud.business.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum ResponseStatus {

	SUCCESS,
	FAILED,
	INVALID,
	UNDETERMINED;
	
	@JsonCreator
    public static ResponseStatus fromValue(String value) {
        return Utils.getEnumFromString(ResponseStatus.class, value);
    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
