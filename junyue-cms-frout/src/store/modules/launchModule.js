
const launchModule = {
    state: {
        launchModuleData: {}
    },
    mutations: {
        changeModule(state, moduleData) {
            
            if (moduleData.module === 'landing' && !moduleData.pageEleList) {
                moduleData.pageEleList = []
            }
            if (moduleData.module === 'retention' && !moduleData.noteList) {
                moduleData.noteList = ['']
            }
            if (moduleData.module === 'retention' && !moduleData.giftJsonList) {
                moduleData.giftJsonList = [{ label: "", title: "" }]
            }
            if (moduleData.module === 'retention' && !moduleData.leadingBase) {
                moduleData.leadingBase = 31
            }
            if (moduleData.module === 'subscribe' && !moduleData.subscribePageEleList) {
                moduleData.subscribePageEleList = []
            }
            if (moduleData.module === 'teacher') {
                if (moduleData.style === 'one') {
                    // moduleData.successDetailList = []
                } else {
                    if (
                        !moduleData.successDetailList ||
                        !moduleData.successDetailList.length
                    ) {
                        moduleData.successDetailList = [
                            "成功领取课程",
                            "长按扫描二维码",
                            "添加专属老师获取上课链接",
                            "务必添加老师，否则无法上课",
                            "此二维码已经验证，安全"
                        ];
                    }
                }
            }

            state.launchModuleData = {
                evaluateEleList: [
                    { type: 'guide', url: '',btnTxt: '吸底图'},
                    { url: '', type: 'btn', btnTxt: '按钮图' },
                ],
                ...moduleData                
            };
        }
    }
}


export default launchModule
