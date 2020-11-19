package io.github.sejoung.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

@Slf4j
class ReusableRequestWrapperTest {

    @Test
    void ok() throws IOException {
        var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
        request.setContent(msg.getBytes());

        var reusableRequestWrapper = new ReusableRequestWrapper(request);

        try (var reader = reusableRequestWrapper.getReader()) {
            log.debug(" data {}", IOUtils.toString(reader));
        }

        try (var inputStream = request.getInputStream()) {
            log.debug(" data {}", IOUtils.toString(inputStream, StandardCharsets.UTF_8));
        }
        Assertions.assertThat(reusableRequestWrapper.getInputStream()).as("정상").hasContent(msg);


    }


    @Test
    void getReader() throws IOException {
        assertThatThrownBy(() -> {
            var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            request.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
            request.setContent(msg.getBytes());

            try (var reader = request.getReader()) {
                log.debug(" data {}", IOUtils.toString(reader));
            }
            try (var reader = request.getReader()) {
                log.debug(" data {}", IOUtils.toString(reader));
            }
        }).isInstanceOf(IOException.class);

    }


    @Test
    void getInputStream() throws IOException {

        assertThatThrownBy(() -> {
            var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            request.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
            request.setContent(msg.getBytes());

            try (var reader = request.getReader()) {
                log.debug(" data {}", IOUtils.toString(reader));
            }

            try (var inputStream = request.getInputStream()) {
                log.debug(" data {}", IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            }
        }).isInstanceOf(IllegalStateException.class);

    }


    @Test
    void encoding_default_utf8() throws IOException {
        var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
        request.setContent(msg.getBytes());
        var reusableRequestWrapper = new ReusableRequestWrapper(request);

        Assertions.assertThat(reusableRequestWrapper.getCharacterEncoding())
            .isEqualTo(StandardCharsets.UTF_8.name());
    }


    @Test
    void isFinished_False_Test() throws IOException {
        var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
        request.setContent(msg.getBytes());
        var reusableRequestWrapper = new ReusableRequestWrapper(request);

        try (var inputStream = (CachedServletInputStream) reusableRequestWrapper.getInputStream()) {
            Assertions.assertThat(inputStream.isFinished()).isFalse();
        }
    }

    @Test
    void isFinished_True_Test() throws IOException {
        var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
        request.setContent(msg.getBytes());
        var reusableRequestWrapper = new ReusableRequestWrapper(request);

        try (var inputStream = (CachedServletInputStream) reusableRequestWrapper.getInputStream()) {
            byte[] data = new byte[16384];
            while ((inputStream.read(data, 0, data.length)) != -1) {
            }
            Assertions.assertThat(inputStream.isFinished()).isTrue();
        }
    }


    @Test
    void isReady_Test() throws IOException {
        var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
        request.setContent(msg.getBytes());
        var reusableRequestWrapper = new ReusableRequestWrapper(request);

        try (var inputStream = (CachedServletInputStream) reusableRequestWrapper.getInputStream()) {
            Assertions.assertThat(inputStream.isReady()).isTrue();
        }
    }


    @Test
    void setReadListener_Test() {
        assertThatThrownBy(() -> {
            var request = new MockHttpServletRequest(HttpMethod.POST.name(), "test");
            request.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String msg = "{\"loyPgmNo\":\"LP0001\",\"trackingNo\":\"7f8d1e25-e766-11e6-91bc-a0b3ccc9c371\",\"tgNo\":\"A100\",\"siteCode\":\"30\",\"csrId\":\"test001\",\"pbpId\":\"1234567890\",\"actionType\":\"MW\"}";
            request.setContent(msg.getBytes());
            var reusableRequestWrapper = new ReusableRequestWrapper(request);

            try (var inputStream = (CachedServletInputStream) reusableRequestWrapper
                .getInputStream()) {
                inputStream.setReadListener(null);
            }
        }).isInstanceOf(UnsupportedOperationException.class);

    }

}