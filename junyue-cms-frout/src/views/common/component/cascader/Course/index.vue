<template>
  <el-cascader :props="props" @change="handleChange" clearable filterable placeholder="--选择训练营/课程--"></el-cascader>
</template>

<script>
import { loadCourseOption, loadLessonOption } from "@/api/courseCascade";
export default {
  props: {
    listQuery: Object,
    change: Function,
  },
  data() {
    return {
      props: {
        lazy: true,
        checkStrictly: true,
        lazyLoad(node, resolve) {
          const { level, data, parent } = node;
          if (level === 200) {
            const subjectMap = [
              {
                value: "CHINESE",
                label: "语文",
              },
              {
                value: "MATH",
                label: "数学",
              },
            ];
            const subjectOptions = subjectMap.map((subject) => ({
              value: subject.value,
              label: subject.label,
              leaf: false,
            }));
            resolve(subjectOptions);
          } else if (level === 1) {
            loadCourseOption(data.value).then((response) => {
              resolve(response.data.list);
            });
          } else if (level === 2) {
            loadLessonOption(parent.data.value, data.value).then((response) => {
              resolve(response.data.list);
            });
          } else {
            resolve([]);
          }
        },
      },
    };
  },
  components: {},
  watch: {},
  methods: {
    handleChange(val) {
      if (val.length === 1) {
        this.invokeChange(val[0], null, null);
      } else if (val.length === 2) {
        this.invokeChange(val[0], val[1], null);
      } else if (val.length === 3) {
        this.invokeChange(val[0], val[1], val[2]);
      }
    },
    invokeChange(subject, courseId, lessonId) {
      if (this.listQuery) {
        this.listQuery.subject = subject;
        this.listQuery.courseId = courseId;
        this.listQuery.lessonId = lessonId;
      }

      if (this.change) {
        this.change(subject, courseId, lessonId);
      }
    },
  },
};
</script>
