package com.example.common1.utils.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    public static void out(HttpServletResponse response, R r) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter out = response.getWriter();
        try {
            out.write(mapper.writeValueAsString(r));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
