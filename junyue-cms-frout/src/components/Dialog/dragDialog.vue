<template>
	<div style="border: 1px dashed #eee; margin-bottom: 50px; border-radius: 8px;">
		<draggable element="div"
		           v-model="items"
		           :options="dragOptions">
			<div class="dragBox" style="padding: 30px;" v-for="(item,itemIndex) in items" :key="itemIndex">
				<el-input v-model="item.id" type="hidden"></el-input>
				<el-form-item label="子标题:">
					<el-input style="max-width:200px" v-model="item.title"></el-input>
				</el-form-item>
				<div style="position: relative;" v-for="(module, index) in item.profileList" :key="index">
					<i class="el-icon-error" @click="deleteModuleItem(itemIndex, index)" title="删除该素材" style="position: absolute;right: -20px;top: 0;cursor: pointer;font-size: 16px;color: red;"></i>
					<el-form-item v-if="module.type === 'image'" label="图片:">
						<ql-upload accept="image/*" :url.sync="module.content" :max-kb="40000"></ql-upload>
					</el-form-item>
					<el-form-item v-else-if="module.type === 'text'" label="文字:">
						<el-input type="textarea" v-model="module.content"></el-input>
					</el-form-item>
					<el-form-item v-else-if="module.type === 'video'" label="视频:">
						<ql-video-upload :extraData="{itemIndex: itemIndex, moduleIndex: index}"
						                 :url.sync="module.content"
						                 @handlePath="handleVideoPath"
						                 :max-kb="102400"></ql-video-upload>
						<!--上传视频封面:-->
						<!--<ql-upload accept="image/*" :url.sync="module.introImage" :max-kb="40000"></ql-upload>-->
					</el-form-item>
				</div>

				<div style="text-align: right">
					<el-button type="danger" @click="deleteModule(itemIndex)">删除该模块</el-button>
					<el-button type="primary" @click="addVideoModule(itemIndex)">添加视频</el-button>
					<el-button type="primary" @click="addTextModule(itemIndex)">添加文字</el-button>
					<el-button type="primary" @click="addImageModule(itemIndex)">添加图片</el-button>
				</div>
			</div>
		</draggable>

		<div slot="footer" style="padding: 30px;">
			<el-button type="primary" @click="addModule">新增子模块</el-button>
			<el-button type="primary" @click="saveData">保存</el-button>
			<el-button @click="subCancelDialog">取消</el-button>
		</div>

		<!--<draggable element="div" v-model="list">-->
		<!--<div v-for="item in list">{{item}}</div>-->
		<!--</draggable>-->
	</div>
</template>
<style scoped>
	.dragBox {
		border-bottom: 1px dashed #eee;
	}

	.dragBox.dragging {
		background-color: #000;
	}

	.dragBox:hover {
		background-color: #ddd;
	}
</style>
<script>
	import {getOssInfo} from "@/api/sys";
	import {noblankValidator, numberValidator} from "@/utils/formValidator";
	import {
		responseNotify,
		statusFilter,
		statusMap,
		displayStatusFilter,
		displayStatusMap,
		formatTimeFilter,
		dpOpts,
		clearObj,
		businessStatusMap
	} from "@/utils";
	import picUpload from "@/components/PicUpload/index.vue";
	import midEvent from "@/components/Dialog/midEvent.vue";
	import draggable from 'vuedraggable'

	export default {
		name: "desc-dialog",
		props: {
			accept: String,
			url: String,
			maxKb: Number,
			duration: Number
			// moduleId: undefined,
			// moduleTitle: undefined,
			// profileList: []
		},
		data() {
			return {
				dragOptions: {
					draggable: '.dragBox',
					filter: 'input,textarea',
					preventOnFilter: false,
					scroll: true,
				},
				items: [
					{
						id: undefined,
						title: undefined,
						videoUrl: '',
						profileList: [
							{
								type: "image",
								content: ""
							},
							{
								type: "text",
								content: ""
							},
							{
								type: "video",
								content: "",
								introImage: ''
							},
						]
					}
				],
				descDialogFormVisible: false,
				// list: [
				// 	1, 2, 3, 4, 5, 6, 7, 8, 9, 0
				// ]
			};
		},

		created() {
			midEvent.$on("modifyDesc", val => {
				console.log("修改简介,从父组件传值过来");
				console.log(val);
				this.items = [
					{
						id: undefined,
						title: undefined,
						profileList: [
							{
								type: "image",
								content: ""
							},
							{
								type: "text",
								content: ""
							},
							{
								type: "video",
								content: "",
								introImage: ''
							}
						]
					}
				];
				this.items = val;
			});
		},

		components: {
			picUpload,
			draggable
		},

		methods: {
			saveData() {
				// console.log(this.items);
				// // 校验video中有无introImage
				// let flag = true
				// for (let item of this.items) {
				// 	for (let profile of item.profileList) {
				// 		if(profile.type == 'video' && profile.content) {
				// 			if (!profile.introImage) {
				// 				flag = false
				// 				break
				// 			}
				// 		}
				// 	}
				// }
				// if(!flag) {
				// 	this.$notify({
				// 		title: "校验",
				// 		message: "简介视频请上传对应封面图",
				// 		type: "error",
				// 		duration: 2000
				// 	});
				// 	return
				// }
				this.$emit("saveDesc", this.items);
			},
			subCancelDialog() {
				this.items = [
					{
						id: undefined,
						title: undefined,
						profileList: [
							{
								type: "image",
								content: ""
							},
							{
								type: "text",
								content: ""
							},
							{
								type: "video",
								content: "",
								introImage: ''
							}
						]
					}
				];
				this.$emit("cancelDialog");
			},
			addModule() {
				const self = this;
				this.items.push({
					moduleId: "",
					moduleTitle: "",
					profileList: [
						{
							type: "image",
							content: ""
						},
						{
							type: "text",
							content: ""
						},
						{
							type: "video",
							content: "",
							introImage: ''
						}
					]
				});
			},
			saveDescData() {
			},
			resetTemp() {
				const self = this;
				clearObj(self.modules);
				self.fileList = [];
			},
			addTextModule(itemIndex) {
				this.items[itemIndex].profileList.push({
					type: "text",
					content: ""
				});
			},
			addImageModule(itemIndex) {
				this.items[itemIndex].profileList.push({
					type: "image",
					content: ""
				});
			},
			addVideoModule(itemIndex) {
				this.items[itemIndex].profileList.push({
					type: "video",
					content: "",
					introImage: ''
				});
			},
			handelDragMove(e) {
				console.log(e);
			},
			handleVideoPath(ret, extraData) {
				console.log(ret);
				console.log(extraData);
				let itemIndex = extraData.itemIndex
				let moduleIndex = extraData.moduleIndex
				let resourceId = ret.uploadInfo.videoId;
				let resourceUrl = ret.uploadInfo.bucket + "," + ret.uploadInfo.object;
				// this.temp.fileSize = ret.fileSize;
				// this.temp.fileName = ret.fileName;
				this.items[itemIndex].profileList[moduleIndex].content = resourceId
			},
			deleteModule(itemIndex) {
				this.items.splice(itemIndex, 1)
			},
			deleteModuleItem(itemIndex, index) {
				this.items[itemIndex]['profileList'].splice(index, 1)
			}
		}
	};
</script>
