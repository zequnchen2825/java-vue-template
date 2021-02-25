<template>
    <el-row type="flex" justify="space-between" align="middle" class="link-item-container">
      <el-col :span="1" style="width:20px;text-align:center;">{{index}}</el-col>
      <el-divider direction="vertical"></el-divider>
      <el-col class="content" :span="18" :xl="18" :lg="18">
          <div style="width: 16.6%" class="grid-content">
            <img :src="image" alt="" width="50px" height="50px"/>
          </div>
        <div>
          <div class="grid-content bg-purple" >{{title}}</div>
          <div class="grid-content bg-purple" style="margin-top:10px">{{content}}</div>
        </div>
      </el-col>
      <el-col v-if="!simple" :span="4" :lg="6" :xl="4" style="text-align:right;">
        <el-button size="mini" type="primary" icon="el-icon-edit" circle @click="editLinkItem"></el-button>
        <el-divider direction="vertical"></el-divider>
        <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="delLinkItem"></el-button>
      </el-col>
    </el-row>
</template>

<script>

export default {
  name: 'LinkItem',
  props: {
    // linkItem: Object,
    index: {
      type: Number,
      require: true
    },
    isEdit: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      require: true
    },
    content: {
      type: String,
      require: true
    },
    image: {
      type: String,
      require: true
    },
    simple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
    };
  },
  methods: {
    editLinkItem() {
      this.$emit("link-handle", "edit");
    },
    delLinkItem() {
      this.$emit("link-handle", "del");
    },
  },
};
</script>

<style lang="scss" scoped>
  .link-item-container{
    .content {
      display: flex;
      align-items: center;
      padding: 6px;
      border: 1px solid #f1f1f1;
    }
  }
</style>
