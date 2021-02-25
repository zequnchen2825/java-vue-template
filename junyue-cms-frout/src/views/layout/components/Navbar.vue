<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>
    <breadcrumb></breadcrumb>
    <div class="right-container">
      <el-badge :value="totalOfUnread" class="item-badge">
        <i class="el-icon-bell" @click="showDrawer"></i>
        <el-drawer title="我是标题" :visible.sync="drawer" :with-header="false" size="26%">
          <el-tabs type="border-card" :stretch="true" v-model="activeName" @tab-click="tabsClick">
            <el-tab-pane name="N" :label="unreadNumTab" class="tab-pane-o">
              <el-container>
                <el-header>
                  <div style="margin-top: 10px;margin-bottom: 10px;margin-right: 10px;">
                    <el-button style="float: right;" type="primary" size="small" @click="markManyAsRead">全部标位已读</el-button>
                  </div>
                </el-header>
                <el-main style="height: 726px;">
                  <div v-for="(item,index)  in unreadMessageList" :key="index">
                    <el-card class="box-card" shadow="hover">
                      <div slot="header" class="clearfix text-title item-title" >
                        <span><b><i class="el-icon-message"></i> {{item.title}}</b></span>
                        <el-button class="card-button" type="primary" size="small" @click="markSingleAsRead(item)">标位已读</el-button>
                      </div>
                      <div class="text-content item-content" @click="messageTextEvent(item)">
                        {{item.content}}
                      </div>
                    </el-card>
                  </div>
                </el-main>
                <el-footer>
                  <el-pagination
                    small
                    background
                    @current-change="handleCurrentChangOfUnread"
                    :current-page="currentPageOfUnread"
                    :page-size="10"
                    layout="total, prev, pager, next"
                    :total="totalOfUnread">
                  </el-pagination>
                </el-footer>
              </el-container>
            </el-tab-pane>
            <el-tab-pane name="Y" label="已读" class="tab-pane-o">
              <el-container>
                <el-main style="height: 786px;">
                  <div v-for="(item,index)  in readMessageList" :key="index">
                    <el-card class="box-card" shadow="hover">
                      <div slot="header" class="clearfix text-title item-title">
                        <span><b><i class="el-icon-message"></i> {{item.title}}</b></span>
                      </div>
                      <div class="text-content item-content">
                        {{item.content}}
                      </div>
                    </el-card>
                  </div>
                </el-main>
                <el-footer>
                  <el-pagination
                    small
                    background
                    @current-change="handleCurrentChangeOfRead"
                    :current-page="currentPageOfRead"
                    :page-size="10"
                    layout="total, prev, pager, next"
                    :total="totalOfRead">
                  </el-pagination>
                </el-footer>
              </el-container>
            </el-tab-pane>
          </el-tabs>
        </el-drawer>
      </el-badge>
      <span class="userinfo-container">{{userName}}</span>
      <el-divider direction="vertical"></el-divider>
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper" style="font-size: 14px;color: #606266;">
          <span>
            操作
            <i class="el-icon-arrow-down"></i>
          </span>
        </div>
        <el-dropdown-menu class="user-dropdown" slot="dropdown">
          <router-link class="inlineBlock" to="/updateUser">
            <el-dropdown-item>修改信息</el-dropdown-item>
          </router-link>
          <router-link class="inlineBlock" to="/changePwd">
            <el-dropdown-item>修改密码</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import {findLiveList, listPage, markAsRead} from "@/api/sysMessage";

import eventBus from '@/events'

export default {
  data() {
    return {
      drawer: false,
      activeName: 'Y',
      unreadNumTab: '未读',
      currentPageOfUnread: 1,
      totalOfUnread: 0,
      currentPageOfRead: 1,
      totalOfRead: 0,
      unreadMessageList: [],
      readMessageList: [],
      messageQuery: {
        page: 1,
        size: 10,
        receiverId: null,
        status: 'N',
        type: null,
        title: null
      },
      timer:"",
      notifyPromise: Promise.resolve()
    };
  },
  components: {
    Breadcrumb,
    Hamburger
  },
  created() {
    console.log(this.$route.path);
  },
  mounted(){
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "userName", "currentUserId"])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch("ToggleSideBar");
    },
    logout() {
      this.$store.dispatch("LogOut").then(() => {
        location.reload(); // 为了重新实例化vue-router对象 避免bug
      });
    },
    tabsClick(tab, event) {
      if(this.activeName == 'N') {
        this.getUnreadMessageList();
      } else if(this.activeName == 'Y') {
        this.getReadMessageList();
      }
    },
    showDrawer() {
      this.activeName = 'N';
      this.getUnreadMessageList();
      this.drawer = true;
    },
    handleCurrentChangOfUnread(val) {
      this.currentPageOfUnread = val;
      this.getUnreadMessageList();
    },
    handleCurrentChangeOfRead(val) {
      this.currentPageOfRead = val;
      this.getReadMessageList();
    },
    // 获取未读消息列表
    getUnreadMessageList() {
      const self = this;
      self.messageQuery.page = self.currentPageOfUnread;
      self.messageQuery.receiverId = this.currentUserId;
      self.messageQuery.status = 'N';
      return listPage(self.messageQuery).then(response => {
        self.unreadMessageList = response.data.list || [];
        self.totalOfUnread = response.data.total || 0;
        self.unreadNumTab = '未读（' + self.totalOfUnread + '）';
        return response
      });
    },
    // 获取已读消息列表
    getReadMessageList() {
      const self = this;
      self.messageQuery.page = self.currentPageOfRead;
      self.messageQuery.receiverId = this.currentUserId;
      self.messageQuery.status = 'Y';
      listPage(self.messageQuery).then(response => {
        self.readMessageList = response.data.list || [];
        self.totalOfRead = response.data.total || 0;
      });
    },
    
    // 单条消息标记为已读
    markSingleAsRead(item) {
      const self = this;
      let markAsReadParams = {};
      let messageIds = [];
      messageIds.push(item.id);
      markAsReadParams.receiverId = this.currentUserId;
      markAsReadParams.messageIds = messageIds;
      markAsRead(markAsReadParams)
        .then(response => {
          return self.getUnreadMessageList();
        })
        .then((res) => {
          const {state} = res
          if (state.code !== 0) return;

          let redirectUrl = item.redirectUrl;
          if (!redirectUrl || redirectUrl==='') return;

          let arr = redirectUrl.split("?");
          let url = arr[0];
          let params = arr[1];
          let paramsArr = params.split("&");
          let id = (paramsArr[0].split("="))[1];
          let tabName = (paramsArr[1].split("="))[1];


          this.handleSetRouteTab(url, tabName);

          this.$router.push({
            path: url,
            query: {
              id: id
            }
          });

          eventBus.$emit('searchAssignedByParams', {
            id: id
          });

          this.drawer = false;
        });
    },
    // 消息文本点击事件
    messageTextEvent(item) {
      let redirectUrl = item.redirectUrl;
      if (!redirectUrl || redirectUrl==='') return;
      
      const self = this;
      let markAsReadParams = {};
      let messageIds = [];
      messageIds.push(item.id);
      markAsReadParams.receiverId = this.currentUserId;
      markAsReadParams.messageIds = messageIds;
      markAsRead(markAsReadParams)
        .then(response => {
          return self.getUnreadMessageList();
        })
        .then((res) => {
          const {state} = res
          if (state.code !== 0) return;

          let arr = redirectUrl.split("?");
          let url = arr[0];
          let params = arr[1];
          let paramsArr = params.split("&");
          let id = (paramsArr[0].split("="))[1];
          let tabName = (paramsArr[1].split("="))[1];


          this.handleSetRouteTab(url, tabName);

          this.$router.push({
            path: url,
            query: {
              id: id
            }
          });

          eventBus.$emit('searchAssignedByParams', {
            id: id
          });

          this.drawer = false;
        });
    },
    // 设置路由状态栏
    handleSetRouteTab(path, name) {
      const tabData = {name, path}
      let openTab = this.$store.state.navTab.openedTab.find(item => item.name === name);

      if (!openTab) {
        this.$store.commit('addTab', tabData)
      } else {
        this.$store.commit('changeTab', tabData)
      }
    },
    // 多条消息标记为已读
    markManyAsRead() {
      let markAsReadParams = {};
      let messageIds = [];
      if (this.unreadMessageList) {
        this.unreadMessageList.forEach((item, index) => {
          messageIds.push(item.id);
        });
      }
      markAsReadParams.receiverId = this.currentUserId;
      markAsReadParams.messageIds = messageIds;
      markAsRead(markAsReadParams)
        .then(response => {
          return this.getUnreadMessageList();
        });
    },
   
    // 新建未读消息提示
    showTips(item) {
      let tips = '<i class="el-icon-message"></i>有一条【' + item.title + '】消息，请注意查看';
      this.notifyPromise = this.notifyPromise.then(this.$nextTick).then(()=>{
        this.$notify({
          title: '新消息提醒',
          message: tips,
          offset: 100,
          duration: 2000
        });
      })
    }
  },
  beforeDestroy() {
    // 销毁定时器
    clearInterval(this.timer)
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .right-container {
    float: right;
    display: inline-block;
    position: absolute;
    right: 20px;
  }
  .userinfo-container {
    line-height: 50px;
    height: 50px;
    font-size: 14px;
    color: #606266;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    .avatar-wrapper {
      cursor: pointer;
    }
  }
  .item-badge {
    margin-right: 30px;
    line-height: 30px;
  }
  .item-title {
    margin-top: -10px;
    margin-bottom: -10px;
    line-height: 10px;
  }
  .item-content {
    margin-top: -16px;margin-bottom: 0px;
    line-height: 30px;
  }
  .text-title {
    font-size: 15px;
  }
  .text-content {
    font-size: 14px;
  }
  .el-header {
    background-color: #f5f8f4;
    color: #333;
    text-align: center;
    line-height: 20px;
  }
  .el-main {
    background-color: #f5f8f4;
    color: #333;
    text-align: left;
    line-height: 60px;
    height: 740px;
  }
  .el-footer {
    background-color: #f5f8f4;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-pagination {
    padding-top: 15px;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  .box-card {
    margin-bottom: 6px;
  }
  .card-button {
    float: right;
    padding: 6px 6px;
    margin-top: -4px;
  }
  .tab-pane-o {
    height:865px;
    overflow-y:auto;
    overflow-x:hidden;
  }
}
</style>

