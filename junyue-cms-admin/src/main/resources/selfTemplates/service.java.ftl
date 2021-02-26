package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    ${entity} selectById (Integer id);

    List<${entity}> selectList(Wrapper<${entity}> ${entity?uncap_first}Wrapper);

    Boolean insert(${entity} ${entity?uncap_first});

    Boolean update(${entity} ${entity?uncap_first});

    Boolean delete(Integer id);
}
</#if>
