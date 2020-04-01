package cn.night.fuo.persistent.mybatis.factories.object;

import cn.night.fuo.persistent.common.data.PagedData;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.data.domain.Page;

import java.util.List;

public class MybatisObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof Page;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (object instanceof Page) {
            return new PageObjectWrapper(metaObject, object);
        }
        return null;
    }

    private static class PageObjectWrapper extends BeanWrapper {
        private final Object page;

        public PageObjectWrapper(MetaObject metaObject, Object object) {
            super(metaObject, object);
            this.page = object;
        }

        @Override
        public <E> void addAll(List<E> element) {
            if (page instanceof PagedData && element instanceof PagedDataList) {
                ((PagedData) page).setPageList((CommonPageList) element);
            }
        }
    }
}
