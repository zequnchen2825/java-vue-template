<template>
  <div class="menu-wrapper">
    <el-submenu v-for="item in menuList" :key="item.menuUrl" :index="item.menuName">
      <template slot="title">
        <svg-icon icon-class="table"></svg-icon>
        <span class="menu-text" v-if="item.menuName">{{item.menuName}}</span>
      </template>

      <template v-for="(itemchild,index) in item.subWocMenu">

        <el-submenu class="submenu-child" v-if="itemchild.subWocMenu && itemchild.subWocMenu.length > 0" :index="index" :key="itemchild.menuName">
          <template slot="title">
            <span class="menu-text" v-if="itemchild.menuName">{{itemchild.menuName}}</span>
          </template>

          <template v-for="(itemchildsub) in itemchild.subWocMenu">
            <router-link :to="itemchildsub.menuUrl"
                         :key="itemchildsub.menuName"
                         @click.native="clickMenu(itemchildsub.menuUrl, itemchildsub.menuName)"
            >
              <el-menu-item :index="itemchildsub.menuName">
                <span class="menu-text" v-if="itemchildsub.menuName">{{itemchildsub.menuName}}</span>
              </el-menu-item>
            </router-link>
          </template>

        </el-submenu>

        <template v-else>
          <router-link
            :to="itemchild.menuUrl"
            :key="itemchild.menuName"
            @click.native="clickMenu(itemchild.menuUrl, itemchild.menuName)"
          >
            <el-menu-item :index="itemchild.menuUrl">
              <span class="menu-text" v-if="itemchild.menuName">{{itemchild.menuName}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </template>
    </el-submenu>
  </div>
</template>

<script>
export default {
  name: 'sidebar-item',
  props: {
    menuList: {
      type: Array
    }
  },
  data() {
    return {
      openedTab: []
    }
  },
  mounted() {
    this.openedTab = this.$store.state.navTab.openedTab || [];
  },
  methods: {
    clickMenu(path, name) {
      const tabData= { name, path }
      let openTab = this.openedTab.find(item => item.name == name)

      if(!openTab){
          this.$store.commit('addTab', tabData)
      } else{
          this.$store.commit('changeTab',tabData)
      }
    }
  },
}
</script>
<style lang="scss" scoped>
.menu-text{
  line-height: 61px;
}
.submenu-child {
  /deep/ .el-submenu__title{
    background-color: #1f2d3d !important;
  }
}
</style>

