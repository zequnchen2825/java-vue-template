import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'
// 注入VUE自定义prototype
import './prototype';

import '@/icons' // icon
import '@/permission' // permission control
import 'element-ui/lib/theme-chalk/base.css';
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition';
import QlUpload from '@/components/QlUpload';
import QlUploadV2 from '@/components/QlUploadV2';
import QlVideoUpload from '@/components/QlVideoUpload';
import QlAudioUpload from '@/components/QlAudioUpload';
import QlUploadWithRadius from '@/components/QlUploadWithRadius'
import hls from 'videojs-contrib-hls'
import directives from './directives' // 注册滚动条加载触发事件v-loadmore绑定

Vue.use(hls)
Vue.use(ElementUI, { locale })
Vue.use(directives)
Vue.config.productionTip = false
Vue.component(CollapseTransition.name, CollapseTransition)
Vue.component(QlUpload.name, QlUpload)
Vue.component(QlUploadV2.name, QlUploadV2)
Vue.component(QlVideoUpload.name, QlVideoUpload)
Vue.component(QlAudioUpload.name, QlAudioUpload)
Vue.component(QlUploadWithRadius.name, QlUploadWithRadius)


new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: { App }
})
