package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.service} ${table.service?uncap_first};

    @GetMapping("/get${table.entity}List")
    public RespResult getUserList(${table.entity} ${table.entity?uncap_first}, Integer page, Integer limit) {
    if (page == null) {
        page = 1;
    }
    if (limit == null) {
        limit = 20;
    }
    Integer skip = page * limit;
    QueryWrapper<${table.entity}> wrapper = new QueryWrapper<>();
        wrapper.setEntity(${table.entity?uncap_first});
        wrapper.last("limit "+ (skip-limit) +","+skip);
        return new RespResult(${table.service?uncap_first}.selectList(wrapper));
    }

    @PostMapping("/insert${table.entity}")
    public RespResult insert(${table.entity} ${table.entity?uncap_first}){
        return new RespResult(${table.service?uncap_first}.insert(user));
    }

    @PostMapping("/update${table.entity}")
    public RespResult update(${table.entity} ${table.entity?uncap_first}){
        return new RespResult(${table.service?uncap_first}.update(user));
    }

    @PostMapping("/delete${table.entity}")
    public RespResult delete(Integer id){
        return new RespResult(${table.service?uncap_first}.delete(id));
    }
}
</#if>
