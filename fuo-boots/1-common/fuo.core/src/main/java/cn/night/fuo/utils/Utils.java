package cn.night.fuo.utils;

import cn.night.fuo.utils.clazz.ClazzUtils;
import cn.night.fuo.utils.collection.CollectionUtils;
import cn.night.fuo.utils.serializer.SerializerFacade;
import cn.night.fuo.utils.type.TypeFacade;
import cn.night.fuo.utils.web.WebUtils;

public class Utils {
    public static final SerializerFacade serializer = new SerializerFacade();
    public static final TypeFacade type = new TypeFacade();
    public static final CollectionUtils collection = new CollectionUtils();
    public static final WebUtils web = new WebUtils();
    public static final ClazzUtils clazz = new ClazzUtils();
}
