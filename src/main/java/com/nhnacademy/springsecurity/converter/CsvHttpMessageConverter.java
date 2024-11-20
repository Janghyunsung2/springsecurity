package com.nhnacademy.springsecurity.converter;

import com.nhnacademy.springsecurity.model.Member;
import com.nhnacademy.springsecurity.model.Role;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public CsvHttpMessageConverter(){
        super(new MediaType("text", "csv"));
    }
    @Override
    protected boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputMessage.getBody(),
            StandardCharsets.UTF_8))){
            String line = bufferedReader.readLine();
            if(line != null) {
                String[] token = line.split(",");
                String id = token[0].trim();
                String name = token[1].trim();
                String password = token[2].trim();
                Integer age = Integer.valueOf(token[3].trim());
                Role role = Role.valueOf(token[4].trim());
                return new Member(id, name, password, age, role);
             }
        }        throw new HttpMessageNotReadableException("Empty input message", inputMessage);
    }

    @Override
    protected boolean canRead(MediaType mediaType){
        return mediaType != null && mediaType.equalsTypeAndSubtype(new MediaType("text", "csv"));

    }
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(MediaType.valueOf("text/csv; charset=UTF-8"));
        try(Writer writer = new OutputStreamWriter(outputMessage.getBody())){
            Member member = (Member) o;
            writer.write("id: " + member.getId() + " name: " + member.getName() + " password: " + member.getPassword() + " age: " + member.getAge() + " role: " + member.getRole());
        }
        throw new HttpMessageNotWritableException("Unsupported object type: " + o.getClass());
    }
}
