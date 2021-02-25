<template>
    <div>
        <div  @click="openDialog">
            <slot>
                <el-button>
                新建录音
                </el-button>
            </slot>
        </div>

        <el-dialog
            :visible.sync="dialogVisible"
            append-to-body
            destroy-on-close
            :show-close="true"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            :before-close="handleClose"
            title="录音"
            custom-class="audio-recorder-dialog"
        >
            <div style="text-align:center">
            <div>
                <canvas
                    style="vertical-align:top"
                    width="200"
                    height="150"
                    ref="canvas"
                ></canvas>
            </div>
            <div v-if="url && state === 'complete'" >
                <div style="margin-bottom:20px;">
                    当前录音：
                    <audio
                        :src="url"
                        controls
                        style="vertical-align: middle;"
                    ></audio>
                </div>
                <div>
                    <el-button
                        @click="startRecord"
                    >
                        重录
                    </el-button>
                    <el-button
                        @click="updateUrl"
                        type="primary"
                    >
                        确定
                    </el-button>
                </div>
            </div>
            <div v-else>
                <el-button
                    type="primary"
                    @click="authorize"
                    v-if="!init"
                >
                    获取权限
                </el-button>
                <el-button
                    type="primary"
                    @click="startRecord"
                    v-if="state === 'ready'"
                >
                    开始录音
                </el-button>
                <el-button
                    type="primary"
                    @click="stopRecord"
                    v-if="state === 'recording'"
                >
                    <i class="el-icon-loading" />
                    停止录音
                </el-button>

                <el-button
                    type="primary"
                    disabled
                    v-if="state === 'processing'"
                >
                    <i class="el-icon-loading" />
                    正在处理
                </el-button>
                <template v-if="state === 'ready'" >
                    <ql-audio-upload
                        style="margin-top:50px"
                        accept="audio/*"
                        @update:url="handleUrlChange"
                        :max-kb="40000"
                        :encrypt="false"
                    ></ql-audio-upload>
                </template>
            </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { randomFileName } from "@/utils";
import { getOssInfo } from "@/api/sys";
import Recorder from 'recorder-core/recorder.mp3.min';
import OSS from "ali-oss";
/**
 * 录音组件
 */

export default {
    name: "audio-recorder",
    data() {
        return {
            stream: null,
            init: false,
            recorder: null,
            chunks: [],
            mediaOptions: {
                audio: {
                    tag: "audio",
                    type: "audio/mp3",
                    ext: ".mp3",
                    gUM: { audio: true }
                }
            },
            state: "ready", //recording,processing
            dialogVisible: false,
            url:"",
        };
    },
    methods: {
        handleUrlChange(url){
            this.url = url;
            this.state = "complete"
        },
        authorize(){
            this.initRecord();
        },
        openDialog(){
            this.dialogVisible = true;
        },
        //初始化录音。
        initRecord(){
            if (this.init === false) {
                this.initialize();
            } else {
                this.dialogVisible = true;
                this.state = "ready";
            }
        },
        //开始录音
        startRecord() {
            if(!this.init){
                this.$notify({
                    type:"warning",
                    message:"需要先获取录音权限！"
                })
                return;
            }
            this.chunks = [];
            this.recorder.start();
            this.state = "recording";
            this.visualize(this.stream);
        },
        //停止录音
        stopRecord() {
            this.recorder.stop((blob)=>{
                this.completeRecord(blob);
            }, (msg) => {
                console.log("录音失败:"+msg);
                this.$notify({
                        title: "录音失败, 请重试",
                        message: message,
                        type: "error",
                        duration: 2000
                    });
            });
            this.state = "processing";
        },
        //完成录音 发送数据
        completeRecord(blob) {
            // let blob = new Blob(this.chunks, {
            //     type: this.mediaOptions.audio.type
            // });
            let url = URL.createObjectURL(blob);
            let fileName = randomFileName();
            let type = blob.type;
            let file = new File([blob], fileName, { type }); //音频文件

            return getOssInfo({})
                .then(res => {
                    let client = new OSS.Wrapper({
                        accessKeyId: res.data.ossAccessId,
                        accessKeySecret: res.data.ossAccessKey,
                        region: res.data.region,
                        bucket: res.data.bucket
                    });
                    return client.multipartUpload(
                        res.data.ossPath + fileName + ".mp3",
                        file,
                        {
                            progress: function(p) {
                                return function(done) {
                                    done();
                                };
                            }
                        }
                    );
                })
                .catch(error => {
                    console.log("error---", error);
                })
                .then(res => {
                    // debugger;
                    // console.log(res);
                    let url = "https://media.nicebooker.com/" + res.name;
                    console.log(url);
                    this.url = url;
                    this.state = "complete"
                });
        },
        //通过事件更新url
        updateUrl() {
            this.$emit("change", this.url);
            this.dialogVisible = false;
            this.state = "ready";
            this.url = '';
        },
        //初始化&&获取权限
        initialize() {
            let media = this.mediaOptions.audio;
            navigator.mediaDevices
                .getUserMedia(media.gUM)
                .then(stream => {
                    this.stream = stream;
                    this.recorder = Recorder({
                        type:"mp3",
                        sampleRate:16000,
                        bitRate:16, //mp3格式，指定采样率hz、比特率kbps，其他参数使用默认配置；注意：是数字的参数必须提供数字，不要用字符串；需要使用的type类型，需提前把格式支持文件加载进来，比如使用wav格式需要提前加载wav.js编码引擎
                        onProcess:function(buffers,powerLevel,bufferDuration,bufferSampleRate,newBufferIdx,asyncEnd){

                            //录音实时回调，大约1秒调用12次本回调
                            //可利用extensions/waveview.js扩展实时绘制波形
                            //可利用extensions/sonic.js扩展实时变速变调，此扩展计算量巨大，onProcess需要返回true开启异步模式
                        }
                    })
                    this.recorder.open(() => {//打开麦克风授权获得相关资源
                        //dialog&&dialog.Cancel(); 如果开启了弹框，此处需要取消
                        //rec.start() 此处可以立即开始录音，但不建议这样编写，因为open是一个延迟漫长的操作，通过两次用户操作来分别调用open和start是推荐的最佳流程

                        // success&&success();
                        this.init = true;
                        console.log("got media successfully");
                        this.initRecord();
                    }, (msg,isUserNotAllow) => {//用户拒绝未授权或不支持
                        this.$notify({
                            title: "录音失败",
                            message: msg,
                            type: "error",
                            duration: 2000
                        });
                    });
                })
                .catch((e) => {
                    let message = e.message;
                    if (e.code === 200) {
                        message = "需要开启浏览器录音权限！";
                    }
                    if (e.code === 8) {
                        message = "检测不到录音设备!";
                    }
                    this.$notify({
                        title: "录音失败",
                        message: message,
                        type: "error",
                        duration: 2000
                    });
                    console.log(e);
                })
        },
        visualize(stream) {
            let audioCtx = new AudioContext();
            const source = audioCtx.createMediaStreamSource(stream);
            const analyser = audioCtx.createAnalyser();
            analyser.fftSize = 256; //宽度
            const bufferLength = analyser.frequencyBinCount;
            const dataArray = new Uint8Array(bufferLength);
            const canvas = this.$refs.canvas;
            const canvasCtx = canvas.getContext("2d");
            source.connect(analyser);
            const self = this;
            //analyser.connect(audioCtx.destination);

            draw();

            function draw() {
                let WIDTH = canvas.width;
                let HEIGHT = canvas.height;
                if (self.state === "recording") {
                    requestAnimationFrame(draw);
                }

                analyser.getByteTimeDomainData(dataArray); //拷贝数据

                canvasCtx.fillStyle = "rgb(255,255,255)";
                canvasCtx.fillRect(0, 0, WIDTH, HEIGHT);

                canvasCtx.lineWidth = 2;
                canvasCtx.strokeStyle = "rgb(0, 0, 0)";

                canvasCtx.beginPath();

                let sliceWidth = (WIDTH * 1.0) / bufferLength;
                let x = 0;

                for (let i = 0; i < bufferLength; i++) {
                    let v = dataArray[i] / 128.0;
                    let y = (((v - 1) * 1.5 + 1) * HEIGHT) / 2; //垂直放大1.3倍

                    if (i === 200) {
                        canvasCtx.moveTo(x, y);
                    } else {
                        canvasCtx.lineTo(x, y);
                    }

                    x += sliceWidth;
                }

                canvasCtx.lineTo(canvas.width, canvas.height / 2);
                canvasCtx.stroke();
            }
        },
        handleClose(done){
            if(this.state === 'ready' || this.state === "complete"){
                done();
                return;
            };
            this.$alert("正在录音无法操作",{
                type:'warning'
            })
        }
    }
};
</script>
<style >
.audio-recorder-dialog{
    opacity: 0.8;
}
</style>
