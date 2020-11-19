package io.github.sejoung.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class ReusableRequestWrapper extends HttpServletRequestWrapper {

    private final Charset encoding;

    private final String characterEncodingStr;
    private byte[] rawData;

    public ReusableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String characterEncoding = request.getCharacterEncoding();

        if (StringUtils.isEmpty(characterEncoding)) {
            characterEncoding = StandardCharsets.UTF_8.name();
        }
        this.characterEncodingStr = characterEncoding;

        log.debug("characterEncoding {}", characterEncoding);

        this.encoding = Charset.forName(characterEncoding);

        try (InputStream inputStream = request.getInputStream()) {
            this.rawData = IOUtils.toByteArray(inputStream);
        }
    }

    @Override
    public String getCharacterEncoding() {
        return characterEncodingStr;
    }

    @Override
    public ServletInputStream getInputStream() {

        return new CachedServletInputStream(this.rawData);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }


}