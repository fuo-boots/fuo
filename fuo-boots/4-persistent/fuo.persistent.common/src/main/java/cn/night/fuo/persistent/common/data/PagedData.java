package cn.night.fuo.persistent.common.data;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class PagedData<T> extends PageImpl<T> {
    private PagedData(List<T> content, Pageable pageable, Long totalCount) {
        super(content, pageable, totalCount);
    }
}
