package com.covengers.grouping.adapter.api.dto;

import com.covengers.grouping.vo.RecommendGroupResponseVo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendGroupResponseDto {
    private List<GroupResponseDto> groupList;

    public static RecommendGroupResponseDto of(RecommendGroupResponseVo vo) {
        return builder()
                .groupList(
                        vo.getGroupList()
                                .stream()
                                .map(groupResponseVo -> GroupResponseDto.of(groupResponseVo))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public RecommendGroupResponseVo toVo() {
        return RecommendGroupResponseVo.builder()
                .groupList(
                        groupList
                                .stream()
                                .map(groupResponseDto -> groupResponseDto.toVo())
                                .collect(Collectors.toList()))
                .build();
    }

}