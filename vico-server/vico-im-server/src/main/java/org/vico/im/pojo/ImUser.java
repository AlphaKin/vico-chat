package org.vico.im.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImUser {
    private String userId;
    private String nickName;
    private int age;
}
