package org.lucas.example.persistence.mysql.spring.dao;

import org.lucas.example.persistence.mysql.common.entity.AutoIdCommonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 当创建时候，SpringData会检查所有方法，解析方法的名称，并基于被持久化
 * 的对象来试图推测方法的目的。本质上，SpringData定义了一组小型的领域特
 * 定语言（Domain-SpecificLanguage，DSL），在这里持久化的细节都是通
 * 过方法的签名来描述。
 * <p>
 * 操作符：IsAfter、After、IsGreaterThan、GreaterThanIsGreaterThanEqual、
 * GreaterThanEqualIsBefore、Before、IsLessThan、LessThanIsLessThanEqual、
 * LessThanEqualIsBetween、BetweenIsNull、NullIsNotNull、NotNullIsIn、InIsNotIn、
 * NotInIsStartingWith、StartingWith、StartsWithIsEndingWith、EndingWith、
 * EndsWithIsContaining、Containing、ContainsIsLike、LikeIsNotLike、NotLikeIsTrue、
 * True、IsFalse、FalseIs、EqualsIsNot、NotIgnoringCase、IgnoresCase
 * <p>
 * 1 操作：
 * 1.1 查询：readxxx、getxxx、findxxx
 * 1.2 聚合：count(返回 int)
 * <p>
 * 2 声明匹配的属性：xxxByxxx
 * <p>
 * 3 指定范围：xxxBetween(方法最后两位为参数)
 * <p>
 * 4 排序：xxxOrderByxxx
 */
public interface JAPRepository extends CrudRepository<AutoIdCommonEntity, Long> {

    @Query(value = "SELECT * FROM discover_live_permission", nativeQuery = true)
    List<AutoIdCommonEntity> customFindAll();

}
