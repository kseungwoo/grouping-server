package com.covengers.grouping.service;

import com.covengers.grouping.constant.ResponseCode;
import com.covengers.grouping.domain.GroupingUser;
import com.covengers.grouping.domain.Keyword;
import com.covengers.grouping.exception.CommonException;
import com.covengers.grouping.repository.GroupingUserRepository;
import com.covengers.grouping.repository.KeywordRepository;
import com.covengers.grouping.vo.SearchHistoryListResultVo;
import com.covengers.grouping.vo.SearchTrendsListResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final GroupingUserRepository groupingUserRepository;
    private final KeywordRepository keywordRepository;

    public SearchHistoryListResultVo getSearchHistoryList(String groupingUserId) {

        final Optional<GroupingUser> groupingUserOptional =
                groupingUserRepository.findTopById(groupingUserId);

        if(!groupingUserOptional.isPresent()) {
            throw new CommonException(ResponseCode.USER_NOT_EXISTED);
        }

        return SearchHistoryListResultVo.builder()
                .searchHistoryList(groupingUserOptional.get().toSearchHistoryList())
                .build();
    }

    public SearchTrendsListResultVo getSearchTrendsList() {
        return null;
    }

    @Transactional
    public void addSearchHistory(String groupingUserId, String keyword) {

        final Optional<GroupingUser> groupingUserOptional =
                groupingUserRepository.findTopById(groupingUserId);

        GroupingUser groupingUser =
                groupingUserOptional.orElseThrow(() -> new CommonException(ResponseCode.USER_NOT_EXISTED));

        final Keyword keywordEntity = new Keyword(keyword);

        keywordRepository.save(keywordEntity);

        groupingUser.getSearchHistory().add(keywordEntity);

        return;
    }

}
