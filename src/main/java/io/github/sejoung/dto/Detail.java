package io.github.sejoung.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Detail {

    private String name;

    private String data;

    public Detail(String name, String data) {
        this.name = name;
        this.data = data;
    }
}
