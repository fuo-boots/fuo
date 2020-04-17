package cn.night.fuo.persistent.mybatis.routing.source;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.persistent.mybatis.MybatisProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class CommonDynamicDataSourceGroup {
    private static final String CONNECTION_MASTER_PREFIX = "forge-database-mybatis-connection-master-";

    private static final String CONNECTION_SLAVE_PREFIX = "forge-database-mybatis-connection-slave-";

    private volatile Boolean initialized;

    private MybatisProperties properties;

    private Map<String, Group> groupMap;

    public CommonDynamicDataSourceGroup(MybatisProperties properties) {
        this.properties = properties;
        this.groupMap = new HashMap<>();
    }

    public synchronized void init() {
        if (initialized != null && initialized) {
            log.info("duplicate init for DynamicDataSourceGroup");
            return;
        }
        if (properties == null) {
            log.error("DataSourceGroup init failed");
            throw new RuntimeException("DataSourceGroup init failed");
        }
        groupMap = properties.getConnectionGroup().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> this.convertGroup(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        initialized = true;
    }

    public Map<String, Group> getGroupMap() {
        if (!initialized) {
            init();
        }
        return this.groupMap;
    }

    public String getMasterDataSource(String groupKey) {
        return StringUtils.isNotBlank(groupKey) && groupMap.containsKey(groupKey) ? groupMap.get(groupKey).getMasterName() : null;
    }

    public String getSlaveDataSource(String groupKey) {
        return StringUtils.isNotBlank(groupKey) && groupMap.containsKey(groupKey) ? groupMap.get(groupKey).pickSlave() : null;
    }

    private Pair<String, Group> convertGroup(String groupKey, MybatisProperties.ConnectionGroup connectionGroup) {
        Group group = new Group();
        group.setCheckDefault(connectionGroup.getCheckDefault());
        group.setMasterName(getConnectionMasterName(groupKey));
        if (!CollectionUtils.isEmpty(connectionGroup.getSlaveConnection())) {
            group.setSlaveNames(connectionGroup.getSlaveConnection().stream().map(config -> getConnectionSlaveName(groupKey)).collect(Collectors.toList()));
        }
        return Pair.of(groupKey, group);
    }

    private static String getConnectionMasterName(String groupName) {
        return CONNECTION_MASTER_PREFIX + groupName;
    }

    private static String getConnectionSlaveName(String groupName) {
        return CONNECTION_SLAVE_PREFIX + UUID.randomUUID().toString() + ConfConstants.DEFAULT_HYPHEN + groupName;
    }

    @Data
    public static class Group {

        private AtomicInteger count = new AtomicInteger();

        private Boolean checkDefault;

        private String masterName;

        private List<String> slaveNames;

        String pickSlave() {
            if (CollectionUtils.isEmpty(slaveNames)) {
                return null;
            }
            if (count.get() >= Integer.MAX_VALUE - 1000) {
                count.set(0);
            }
            return slaveNames.get(count.getAndIncrement() % slaveNames.size());
        }
    }
}
