package org.vico.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImServerMetaInfo implements Serializable {
    // 服务地址
    private String host;
    // 服务端口
    private int port;
    // 连接数
    private int connectingCount;

}