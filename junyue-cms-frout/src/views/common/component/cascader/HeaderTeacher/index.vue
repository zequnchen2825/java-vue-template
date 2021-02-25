<template>
  <el-cascader :props="props" @change="handleChange" clearable filterable placeholder="--选择班主任账号/微信--"></el-cascader>
</template>

<script>
import {
  loadHeaderTeacherOptions,
  loadHeaderTeacherQrOptions,
} from "@/api/teacher";
export default {
  props: {
    listQuery: Object,
  },
  data() {
    return {
      props: {
        lazy: true,
        checkStrictly: true,
        lazyLoad(node, resolve) {
          const { level, data, parent } = node;
          if (level === 200) {
            loadHeaderTeacherOptions().then((response) => {
              resolve(response.data.list);
            });
          } else if (level === 1) {
            loadHeaderTeacherQrOptions(data.value).then((response) => {
              resolve(response.data.list);
            });
          } else {
            resolve([]);
          }
        },
      },
    };
  },
  created() {},
  methods: {
    handleChange(val) {
      this.listQuery.teacherId = null;
      this.listQuery.teacherQrId = null;

      if (val.length === 1) {
        this.listQuery.teacherId = val[0];
      } else if (val.length === 2) {
        this.listQuery.teacherId = val[0];
        this.listQuery.teacherQrId = val[1];
      }
    },
  },
};
</script>
