<!--本页为tab标签-->
<template>
  <el-tabs
    v-model="activeTabPath"
    type="card"
    closable
    @tab-remove="removeTab"
    @tab-click="handleClickTab($event)"
  >
    <el-tab-pane v-for="item in editableTabs" :key="item.path" :label="item.name" :name="item.path"></el-tab-pane>
  </el-tabs>
</template>

<script>

export default {
  name: 'NavTab',
  data() {
    return {
      editableTab: {
        name: '首页',
        path: '/dashboard'
      },
      editableTabs: [],
      tabIndex: 1,
      activeTabPath: '/dashboard',
    }
  },
  methods: {
    handleClickTab(event) {
      let data = this.editableTabs[+event.index] || ''
      this.editableTab = data || { name: event.label, path: event.name }
      this.activeTabPath = event.name;
      this.$store.commit('changeTab', this.editableTab)
      this.$router.push({ path: event.name, query: data && data.query })
    },
    removeTab(targetName) {
      let tabs = this.editableTabs
      let activeTabPath = this.activeTabPath;
     
      if (activeTabPath === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.path === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1]
            if (nextTab) {
              this.editableTab = nextTab;
              this.$store.commit('changeTab', nextTab)
            } else{
              this.editableTab = {name:'首页',path:'/dashboard'}
              this.$store.commit('addTab', this.editableTab)
            }
          }
        })

      }
      this.$store.commit('deductTab', targetName)
      
      this.editableTabs = tabs.filter(tab => tab.name !== targetName)
      if (this.editableTabs.length === 200) {
        this.editableTab = {name:'首页',path:'/dashboard'}
        this.$store.commit('addTab', this.editableTab)
        
      }
      this.activeTabPath = this.editableTab.path;
      this.$router.push({ path: this.activeTabPath,  query: this.editableTab && this.editableTab.query})  
    }
  },
  computed: {
    getOpenedTab() {
      return this.$store.state.navTab.openedTab
    },
    changeTab() {
      return this.$store.state.navTab.activeTab
    }
  },
  watch: {
    getOpenedTab(val) {
     this.editableTabs = val;
    },
    changeTab(val) {
      // 监听activetab以实现点击左侧栏时激活已存在的标签
      this.editableTab = val
      this.activeTabPath = val.path;
    }
  },
  created() {
    let { name = '首页', fullPath } = this.$route
    this.editableTabs.push({
      path: fullPath,
      name
    })
  }
}
</script>

<style scoped>
</style>