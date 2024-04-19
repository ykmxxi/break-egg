package com.example.order;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;

@Component
public class DatabaseCleanup implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        tableNames = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> e.getJavaType().getAnnotation(Table.class).name())
                .toList();

        List<String> entityNames = entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .toList();

        if (!entityNames.isEmpty()) {
            tableNames.addAll(entityNames);
        }
    }

    // @Entity 어노테이션이 붙어있는지 확인
    private boolean isEntity(final EntityType<?> entity) {
        return null != entity.getJavaType().getAnnotation(Entity.class);
    }

    // @Table 어노테이션이 붙어있는지 확인
    private boolean hasTableAnnotation(final EntityType<?> entity) {
        return null != entity.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        // 참조 무결성: 다른 테이블들과 FK로 연결되어 있으면 테이블 삭제가 되지 않아 참조 무결성을 FALSE 설정
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE")
                .executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName)
                    .executeUpdate();

            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1")
                    .executeUpdate();
        }

        // 테이블 초기화가 끝나면 다시 참조 무결성 TRUE
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE")
                .executeUpdate();
    }

}
