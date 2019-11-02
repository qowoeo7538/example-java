package org.lucas.example.foundation.io.kata.longpolling.impl.dto;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageStore {

    private final ConcurrentMap<String/* group */, ConcurrentMap<String/* name */, ResponseDTO>> groupMessageTable = new ConcurrentHashMap<>();

    /**
     * 获取最大版本号
     *
     * @param group 组
     * @param name  名
     * @return 最大版本号
     */
    public long getMaxVersion(String group, String name) {
        ConcurrentMap<String/* name */, ResponseDTO> nameMessageTable = groupMessageTable.get(group);
        if (Objects.isNull(nameMessageTable)) {
            ConcurrentMap<String/* name */, ResponseDTO> newMap = new ConcurrentHashMap<>(128);
            ConcurrentMap<String/* name */, ResponseDTO> oldMap = groupMessageTable.putIfAbsent(group, newMap);
            if (Objects.nonNull(oldMap)) {
                nameMessageTable = oldMap;
            } else {
                nameMessageTable = newMap;
            }
        }
        ResponseDTO responseDTO = nameMessageTable.get(name);
        if (Objects.isNull(responseDTO)) {
            ResponseDTO newResponseDTO = new ResponseDTO();
            // TODO 查库和缓存构建该对象
            newResponseDTO.setData("测试");
            newResponseDTO.setVersion(1L);
            ResponseDTO oldResponseDTO = nameMessageTable.putIfAbsent(name, newResponseDTO);
            if (Objects.nonNull(oldResponseDTO)) {
                responseDTO = oldResponseDTO;
            } else {
                responseDTO = newResponseDTO;
            }
        }
        return responseDTO.getVersion();
    }

}
