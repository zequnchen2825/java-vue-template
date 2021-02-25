<template>
  <section class="item-time-wrap">
    <el-input-number class="time-input" :value="time" @input="handleTimeInput" size="small" :min="1"  controls-position="right"/>
    <el-select class="time-select" :value="type" @input="handleSelectInput" size="small" placeholder="单位" :clearable='false'>
      <el-option label="秒" :value="1"></el-option>
      <el-option label="分" :value="2"></el-option>
      <el-option label="时" :value="3"></el-option>
    </el-select>
    <span>后发送</span>
  </section>
</template>

<script>
export default {
  name: "TimeIntervalControl",
  props:{
    time:{
      require: true
    },
    type: {
      require: true
    }
  },
  methods:{
    handleSelectInput(value) {
      this.$emit('update:type', value);
    },
    handleTimeInput(value) {
      this.$emit('update:time', value);
    }
  }
}
</script>

<style lang="scss" scoped>
.item-time-wrap {
  display: flex;
  align-items: center;
}
.time-input{
  width: 100px;
}
.time-select {
  width: 70px;
}

.time-select {
  margin-left: 10px;
}
</style>
