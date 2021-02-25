<template>
  <scroll-bar>
    <el-menu mode="vertical" unique-opened :default-active="$route.path" :collapse="isCollapse" background-color="#304156" text-color="#fff" active-text-color="#409EFF">
      <el-input placeholder="搜索菜单" v-model="search"  class="input-with-select" :style="display">
        <el-button   slot="append" size="mini" icon="el-icon-search" @click="searchMenu()"></el-button>
      </el-input>
      <sidebar-item :menuList="menuList"></sidebar-item>
    </el-menu>
  </scroll-bar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import ScrollBar from '@/components/ScrollBar'
import store from '@/store'
import { getInfo } from '@/api/login'

export default {
  components: { SidebarItem, ScrollBar },
  data() {
    return {
      menuList: [],
      search:undefined,
      diaplay:undefined,
    }
  },
  watch: {
    menuList (val){
      this.menuList = val;
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
    ]),
    /*routes() {
      return this.$router.options.routes
    },*/
    isCollapse() {
      if(!this.sidebar.opened){
        this.display = "display:none"
      }else{
        this.display =undefined;
      }
      return !this.sidebar.opened;
    }
  },

  methods: {
    searchMenu(){
      this.fetchData();
    },
    fetchData() {

      getInfo().then(response =>
        this.menuList = response.data.menus
      )
    }
      
  },
  created() {
    this.fetchData();
  }
  
}
</script>

