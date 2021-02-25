<template>
    <div class="drag-img">
        <qlUploadMaxImg :max-kb="40000" :buttonText="text" @beforeUpload="handleBeforeLoadImg" @update="handleGetContentImg"></qlUploadMaxImg>
        <div>可上传多张<span v-if="loading">（正在上传。。。）</span></div>
        <div class="draggable-image-list">
            <draggable element="div" v-model="imageLists" :options="dragOptions" @change="handleChange">
                <div
                    class="dragBox item"
                    v-for="(item, index) in imageLists"
                    :key="index"
                >
                    <img :src="item">
                    <i class="el-icon-error" @click="handleRemoveImage(index)"></i>
                </div>
            </draggable>
        </div>
    </div>
</template>
<script>
import qlUploadMaxImg from "@/components/QlUploadMaxImg/index.vue";
import draggable from "vuedraggable";

export default {
    name: "max-pic-upload-dragger",
    props: {
        // 按钮文案
        text: {
            default: '上传图片',
            type: String
        }, 
        imageList: {
            default: ()=>[],
            type: Array
        }
        
    },
    components: {
        qlUploadMaxImg,
        draggable
    },
    data() {
        return {
             dragOptions: {
                draggable: ".dragBox",
                filter: "input,textarea",
                preventOnFilter: false,
                scroll: true
            },
            imageLists: [],
            loading: true,
        }
    },
    watch: {
        imageList: {
            handler(val, oldVal) {
                this.imageLists = val
                this.loading = false;
            },
            immediate: true
        }
    },
    methods: {
        handleGetContentImg(data) {
            console.log("handleGetContentImg: ", data )
            this.imageLists.push(data)
            this.$emit("update:imageList", this.imageLists);
            console.log("this.imageLists: ", this.imageLists )
            this.loading = false
        },
        handleRemoveImage(index) {
            this.imageLists.splice(index, 1)
        },
        handleChange() {
            console.log("handleChange: ", this.imageLists)
            this.$emit("update:imageList", this.imageLists);
        },
        handleBeforeLoadImg(){
            this.loading = true;
        }
    }
}
</script>
