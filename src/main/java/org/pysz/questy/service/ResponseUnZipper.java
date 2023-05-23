package org.pysz.questy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Slf4j
@AllArgsConstructor
@Service
public class ResponseUnZipper {

    private final ObjectMapper mapper;

    public <T> T getEntity(ResponseEntity<byte[]> response, Class<T> clazz) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(response.getBody()))) {
            gzipInputStream.transferTo(byteArrayOutputStream);
        } catch (IOException e) {
            log.error("Problem while unzipping the response", e);
            throw new RuntimeException(e);
        }

        try {
            byte[] content = byteArrayOutputStream.toByteArray();
            T entity = mapper.readValue(content, clazz);
            log.debug("Unzipped entity: {}", entity);
            return entity;
        } catch (IOException e) {
            log.error("Problem while mapping the response", e);
            throw new RuntimeException(e);
        }

    }
}
