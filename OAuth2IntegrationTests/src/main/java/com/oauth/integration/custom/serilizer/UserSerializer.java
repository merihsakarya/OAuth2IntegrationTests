package com.oauth.integration.custom.serilizer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.oauth.integration.entity.User;

public class UserSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User user, JsonGenerator jsonGenerator,
		SerializerProvider provider) throws IOException,
		JsonProcessingException {

		jsonGenerator.writeStartObject(); 
		
		jsonGenerator.writeNumberField("id", user.getId());
		jsonGenerator.writeStringField("email", user.getEmail());
		jsonGenerator.writeStringField("username", user.getUsername());
		jsonGenerator.writeStringField("name", user.getName());
		jsonGenerator.writeStringField("surname", user.getSurname());

		Date createdDate = user.getRegistrationDate();
		Instant instant = Instant.ofEpochMilli(createdDate.getTime());
		LocalDateTime localCreatedDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());		
		String absoluteDate = localCreatedDateTime.format(DateTimeFormatter.ofPattern("dd MMM uuuu"));
		jsonGenerator.writeStringField("timestamp", absoluteDate);
		
        jsonGenerator.writeEndObject();
	}
}
