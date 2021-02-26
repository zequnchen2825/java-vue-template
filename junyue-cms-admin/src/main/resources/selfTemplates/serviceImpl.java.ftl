package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private ${table.mapperName} ${table.mapperName?uncap_first};

    @Override
    public ${entity} selectById (Integer id){
      return ${table.mapperName?uncap_first}.selectById(id);
    }
    @Override
    public List<${entity}> selectList(Wrapper<${entity}> ${entity?uncap_first}Wrapper){
        return ${table.mapperName?uncap_first}.selectList(${entity?uncap_first}Wrapper);
    }
    @Override
    public Boolean insert(${entity} ${entity?uncap_first}){
        return ${table.mapperName?uncap_first}.insert(${entity?uncap_first}) > 0;
    }
    @Override
    public Boolean update(${entity} ${entity?uncap_first}){
    return ${table.mapperName?uncap_first}.updateById(${entity?uncap_first}) > 0;
    }
    @Override
    public Boolean delete(Integer id){
    return ${table.mapperName?uncap_first}.deleteById(id) > 0;
    }
}
</#if>
