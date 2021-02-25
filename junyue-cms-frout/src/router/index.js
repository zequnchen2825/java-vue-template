import Vue from "vue";
import Router from "vue-router";
/* Layout */
import Layout from "../views/layout/Layout";

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router);

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [{
    path: "/login",
    component: () =>
        import("@/views/login/index"),
    hidden: true
},
{
    path: "/404",
    component: () =>
        import("@/views/404"),
    hidden: true
},

{
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    name: "首页",
    hidden: true,
    children: [{
        path: "/dashboard",
        component: () =>
            import("@/views/dashboard/index")
    }]
},


{
    path: "/setting",
    component: Layout,
    redirect: "",
    name: "系统设置",
    meta: {
        title: "系统设置",
        icon: "user"
    },
    children: [
        {
            path: 'permission',
            name: '权限列表',
            component: () =>
                import('@/views/setting/permission/index'),
            meta: { title: '权限列表' }
        },
        {
            path: 'role',
            name: '角色列表',
            component: () =>
                import('@/views/setting/role/index'),
            meta: { title: '角色列表' }
        },
        {
            path: 'sysUser',
            name: '后台用户管理',
            component: () =>
                import('@/views/setting/sysUser/index'),
            meta: { title: '后台用户管理' }
        },
        {
            path: 'platform',
            name: '平台管理',
            component: () =>
                import('@/views/setting/platform/index'),
            meta: { title: '平台管理' }
        },
      {
        path: 'channel/:platformId/:platformName',
        name: '渠道管理',
        component: () =>
          import('@/views/setting/channel/index'),
        meta: { title: '渠道管理' }
      }
    ]
},



{
    path: "/changePwd",
    component: Layout,
    redirect: "",
    name: "修改密码",
    meta: {
        title: "修改密码",
        icon: "user"
    },
    children: [{
        path: "/changePwd",
        name: "changePwd",
        component: () => import("@/views/login/changePwd"),
        meta: {
            title: "修改密码"
        }
    }]
},

{
    path: "*",
    redirect: "/404",
    hidden: true
},
];

export default new Router({
    // mode: 'history', //后端支持可开
    // mode: 'hash',
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRouterMap
});
