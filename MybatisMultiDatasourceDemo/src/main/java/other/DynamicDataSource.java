package other;

import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String MASTER_DATA_SOURCE_NAME = "master";

    private Integer slaveCount;

    private List<Object> slaveDataSources = new ArrayList<Object>(0);

    // 轮询计数,初始为-1,AtomicInteger是线程安全的
    private AtomicInteger counter = new AtomicInteger(-1);

    @Override
    protected Object determineCurrentLookupKey() {
//        return HandlerDataSource.getDbType();
        SqlCommandType dbType = HandlerDataSource.getDbType();
        System.out.println(dbType);
        if (dbType != null && dbType == SqlCommandType.SELECT) {
            return getSlaveKey();
        } else {
            return MASTER_DATA_SOURCE_NAME;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        // 由于父类的resolvedDataSources属性是私有的子类获取不到，需要使用反射获取
        Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
        field.setAccessible(true); // 设置可访问

        try {
            Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
            // 读库的数据量等于数据源总数减去写库的数量
            this.slaveCount = resolvedDataSources.size() - 1;
            for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
                if (MASTER_DATA_SOURCE_NAME.equals(entry.getKey())) {
                    continue;
                }
                slaveDataSources.add(entry.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 轮询算法实现
     *
     * @return
     */
    public Object getSlaveKey() {
        // 得到的下标为：0、1、2、3……
        Integer index = counter.incrementAndGet() % slaveCount;
        if (counter.get() > 9999) { // 以免超出Integer范围
            counter.set(-1); // 还原
        }
        return slaveDataSources.get(index);
    }

}
